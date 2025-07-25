package com.github.semiprojectshop.web.sihu.dto.product.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class PaymentResponse {
    private final long ordersId;
    private final List<Long> productCartIds;
    private final long totalPrice;
    private final long totalStock;
//    private final String repProductName;// 대표 상품명
}
