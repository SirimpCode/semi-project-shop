package com.github.semiprojectshop.web.aery.productsearchcontroller;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.semiprojectshop.repository.kyeongsoo.model.DaoCustom;
import com.github.semiprojectshop.web.aery.commoncontroller.AbstractController;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class SearchResult extends AbstractController {

    private DaoCustom pdao;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (pdao == null) {
            ServletContext sc = request.getServletContext();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
            pdao = ctx.getBean(DaoCustom.class);
        }
/*
        String keyword = request.getParameter("keyword");
        List<ProductVO> productList;

        if (keyword == null || keyword.trim().isEmpty()) {
            productList = List.of();
            request.setAttribute("keyword", "");
        } else {
            productList = pdao.searchByKeyword(keyword.trim());
            request.setAttribute("keyword", keyword.trim());
        }

        request.setAttribute("productList", productList);
*/
        super.setViewPage("/WEB-INF/views/aery/productSearch/searchResult.jsp");
    }
}
