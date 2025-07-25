package com.github.semiprojectshop.service.sihu.product;

import com.github.semiprojectshop.repository.sihu.product.Product;
import com.github.semiprojectshop.repository.sihu.product.cart.ProductCart;
import com.github.semiprojectshop.repository.sihu.product.cart.ProductCartJpa;
import com.github.semiprojectshop.repository.sihu.user.MyUser;
import com.github.semiprojectshop.service.sihu.exceptions.CustomMyException;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartRequest;
import com.github.semiprojectshop.web.sihu.dto.product.cart.AddToCartResponse;
import com.github.semiprojectshop.web.sihu.dto.product.cart.CartListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductCartJpa productCartJpa;

    @Transactional
    public AddToCartResponse addToCart(AddToCartRequest addToCartRequest, long loginUserId) {

        long productId = addToCartRequest.getProductId();
        int quantity = addToCartRequest.getQuantity();
        MyUser userOnlyId = MyUser.onlyId(loginUserId);
        Product productOnlyId = Product.onlyId(productId);
        boolean isAdded = productCartJpa.existsByProductAndMyUser(productOnlyId, userOnlyId);
        if (isAdded) {
            // 이미 장바구니에 있는 경우
            long result = productCartJpa.addQuantity(addToCartRequest, loginUserId);
            if(result == 1)
                return AddToCartResponse.of(productId, quantity, quantity, "장바구니에 추가되었습니다.");
            throw CustomMyException.fromMessage("장바구니에 추가하는데 실패했습니다. 다시 시도해주세요.");
        } else {
            // 장바구니에 없는 경우
            productCartJpa.save(ProductCart.fromProductMyUserQuantity(productOnlyId, userOnlyId, quantity));
            return AddToCartResponse.of(productId, quantity, quantity, "장바구니에 추가되었습니다.");
        }

    }
    @Transactional
    public AddToCartResponse deleteFromCart(List<Long> productIds, long loginUserId) {

        MyUser userOnlyId = MyUser.onlyId(loginUserId);
        List<Product> productOnlyIds = productIds.stream().map(Product::onlyId).toList();
        long deletedCount = productCartJpa.deleteByProductInAndMyUser(productOnlyIds, userOnlyId);
        if(deletedCount == productIds.size())
            return AddToCartResponse.of(productIds.get(0), 0, 0, "장바구니에서 삭제되었습니다.");
        throw CustomMyException.fromMessage("장바구니에서 삭제하는데 실패했습니다. 다시 시도해주세요.");
    }

    @Transactional(readOnly = true)
    public List<CartListResponse> getAllCartListByUserId(long loginUserId) {

        return productCartJpa.findAllByUserId(loginUserId);

    }
    @Transactional
    public AddToCartResponse modifyQuantity(long productCartId, int quantity) {
        long result = productCartJpa.updateProductQuantity(productCartId, quantity);
        if (result != 1) throw CustomMyException.fromMessage("장바구니 수량 수정에 실패했습니다. 다시 시도해주세요.");
        return AddToCartResponse.of(productCartId, quantity, quantity, "장바구니 수량이 수정되었습니다.");
    }

    public long getMyCartCount(long loginUserId) {
        return productCartJpa.countByMyUser_UserId(loginUserId);
    }
}
