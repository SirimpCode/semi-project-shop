//package com.github.semiprojectshop.web.sihu.viewcontroller;
//
//import com.github.semiprojectshop.config.encryption.AES256;
//import com.github.semiprojectshop.repository.sihu.Image;
//import com.github.semiprojectshop.repository.sihu.user.MyUser;
//import com.github.semiprojectshop.service.AdminService;
//import com.github.semiprojectshop.service.ViewService;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequiredArgsConstructor
//public class ViewController {
//
//    private final ViewService viewService;
//    private final ClientHttpRequestFactorySettings clientHttpRequestFactorySettings;
//    private final AES256 aes256;
//    private final AdminService adminService;
//
//
//    @GetMapping("/test.do")
//    public String test() {
//        return "test/test";
//    }
//
//    @GetMapping("/index.up")
//    public String index(HttpServletRequest request) {
//        List<Image> imgList = viewService.getAllImages();
//        request.setAttribute("imgList", imgList);
//        return "index";
//    }
//
//    @GetMapping("/login/idFind.up")
//    public String idFind() {
//        return "login/id_find";
//    }
//
//    @GetMapping("/login/pwdFind.up")
//    public String pwdFind() {
//        return "login/pwd_find";
//    }
//
//    @GetMapping("/error.up")
//    public void error() {
//
//        throw new RuntimeException("에러가 발생했습니다.");
//    }
//
//    @GetMapping("/member/coinPurchaseTypeChoice.up")
//    public String coinPurchaseTypeChoice(@RequestParam String userId, HttpServletRequest request) {
//
//        MyUser loginUser = ((MyUser) request.getSession().getAttribute("loginUser"));
//        String loginId = Optional.ofNullable(loginUser)
//                .map(MyUser::getUserId)
//                .orElse(null);
//        boolean isNormal = userId != null && userId.equals(loginId)
//                && normalApproach(request.getHeader("Referer"), "/index.up");
//        request.setAttribute("isNormal", isNormal);
//
//        if (isNormal)
//            return "member/coin_charge";
//        request.setAttribute("message", "잘못된 접근입니다.");
//        return "simple_error";
//    }
//
//    @GetMapping("/member/cash.up")
//    public String cash(HttpServletRequest request, HttpServletRequest response) {
//        response.setAttribute("message", "");
//
//
//        return "";
//    }
//
//    private boolean normalApproach(String referer, String sourceUrl) {
//        return referer != null && referer.contains(sourceUrl);
//    }
//
//    @PostMapping("/member/cash.up")
//    public String cashPost(@RequestParam String userId, HttpServletRequest request) {
//        return "member/cash";
//    }
//
//
//    public String decryption(String encryptedData) {
//        try {
//            return aes256.decrypt(encryptedData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("복호화에 실패했습니다.");
//        }
//    }
//
//    @GetMapping("/member/coinPayment.up")
//    public String coinPayment(@RequestParam String userId, @RequestParam String coin, @RequestParam String point,
//                              HttpServletRequest request, HttpServletRequest response) {
//
//        //원포트 결제창을 띄우기위한 전제조건은 먼저 로그인해야 하는것이다
//        MyUser loginUser = (MyUser) request.getSession().getAttribute("loginUser");
//        boolean isNormal = viewService.loginValidation(userId, loginUser) &&
//                normalApproach(request.getHeader("Referer"), "/index.up");
//
//        String decryptedEmail = decryption(loginUser.getEmail());
//        String decryptedNumber = decryption(loginUser.getPhoneNumber());
//
//
//        String productName = "코인 충전";
//        request.setAttribute("productName", productName);
////        request.setAttribute("coinMoney", coin);
//        request.setAttribute("coinMoney", "100");
//        request.setAttribute("email", decryptedEmail);
//        request.setAttribute("name", loginUser.getUserName());
//        request.setAttribute("phoneNumber", decryptedNumber);
//        request.setAttribute("point", point.replace(",", ""));
//        request.setAttribute("realCoin", coin);
//        request.setAttribute("userId", userId);
//
//        Boolean 개빡치네 = false;
//
//        if (isNormal)
//            return "member/payment_gateway";
//        response.setAttribute("message", "잘못된 접근입니다.");
//        return "simple_error";
//    }
//
//    @GetMapping("/member/memberEdit.up")
//    public String memberEdit(HttpServletRequest request) {
//        String referer = request.getHeader("Referer");
//        System.out.println(referer);
//        if (referer == null || !referer.contains("/index.up")) {
//            request.setAttribute("message", "잘못된 접근입니다.");
//            return "simple_error";
//        }
//        MyUser loginUser = (MyUser) request.getSession().getAttribute("loginUser");
//        if (loginUser == null) {
//            request.setAttribute("message", "로그인이 필요합니다.");
//            return "simple_error";
//        }
//
//        request.setAttribute("email", decryption(loginUser.getEmail()));
//        request.setAttribute("phoneNumber", decryption(loginUser.getPhoneNumber()));
//        request.setAttribute("user", loginUser);
//        return "member/member_edit";
//    }
//
//    //관리자전용 회원전용
//    @GetMapping("/admin/memberList.up")
//    public String showMemberListByAdmin(HttpServletRequest request) {
//        MyUser admin = (MyUser) request.getSession().getAttribute("loginUser");
//        if (admin == null || !admin.getUserId().equals("admin")) {
//            request.setAttribute("message", "관리자만 접근할 수 있습니다.");
//            return "error";
//        }
//        return "member/admin/member_list";
//    }
//
//    @PostMapping("/admin/memberDetail.up")
//    public String showMemberDetail(@RequestParam String userId,
//                                   @RequestParam String prevPage,
//                                   @RequestParam String prevSize,
//                                   @RequestParam String prevSearchType,
//                                   @RequestParam String prevSearchValue,
//                                   HttpServletRequest request) {
//        MyUser admin = (MyUser) request.getSession().getAttribute("loginUser");
//        String referer = request.getHeader("Referer");
//        if (referer == null || !referer.contains("/admin/memberList.up")) {
//            request.setAttribute("message", "잘못된 접근입니다.");
//            return "error";
//        }
//        if (admin == null || !admin.getUserId().equals("admin")) {
//            request.setAttribute("message", "관리자만 접근할 수 있습니다.");
//            return "error";
//        }
//        request.setAttribute("userId", userId);
//        request.setAttribute("prevPage", prevPage);
//        request.setAttribute("prevSize", prevSize);
//        request.setAttribute("prevSearchType", prevSearchType);
//        request.setAttribute("prevSearchValue", prevSearchValue);
//        return "member/admin/member_detail";
//
//    }
//
//
//    @GetMapping("/mall/more.up")
//    public String more(HttpServletRequest request, @RequestParam List<Long> deleteIds) {
//        long hitCount = viewService.getProductCountBySpec(1);
//        request.setAttribute("hitCount", hitCount);
//        return "mall/more";
//    }
//
//    @GetMapping("/mall/scroll.up")
//    public String scroll(HttpServletRequest request) {
//
//        return "mall/scroll";
//    }
//
//
//    //강사님 버전
//////    @GetMapping("/admin/memberList.up")
////    public void searchMemberList(
////
////            @ModelAttribute SearchConditions searchConditions,
////            @RequestParam(defaultValue = "1") long page,
////            @RequestParam(defaultValue = "10") long size,
////            HttpServletRequest request, HttpServletResponse response
////
////    ) {
////        // 관리자가 아니면 에러 페이지로 날려버림
////        MyUser admin = (MyUser) request.getSession().getAttribute("loginUser");
////        if (admin == null || !admin.getUserId().equals("admin")) {
////            request.setAttribute("message", "관리자만 접근할 수 있습니다.");
////            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/simple_error.jsp");
////            try {
////                dispatcher.forward(request, response);
////                return;
////            } catch (ServletException | IOException e) {
////                throw new RuntimeException(e);
////            }
////
////        }
////        if(searchConditions.getSearchValue()!=null)
////            searchConditions.covertSearchType();
////
////
////        PaginationDto<MemberListResponse> memberList =  adminService.searchMemberList(searchConditions, page, size)
////                .orElseThrow(()-> new RuntimeException("빈 목록이 아닌 null 반환"));
////        request.setAttribute("memberList", memberList);
////
////        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/admin/member_list.jsp");
////        try {
////            dispatcher.forward(request, response);
////        } catch (ServletException | IOException e) {
////            throw new RuntimeException(e);
////        }
////
////    }
//
//}
