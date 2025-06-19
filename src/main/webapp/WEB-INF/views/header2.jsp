<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    String ctxPath = request.getContextPath();
    //    /MyMVC
%>
<!DOCTYPE html>
<html>
<head>

    <title>:::HOMEPAGE:::</title>

    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <!-- Font Awesome 6 Icons -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.7.1/css/all.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- 직접 만든 CSS -->
    <link rel="stylesheet" type="text/css"
          href="<%= ctxPath%>/css/template/template.css"/>
    <link rel="stylesheet" type="text/css"
          href="<%= ctxPath%>/css/template/style.css"/>

    <!-- Optional JavaScript -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
    <%-- jQueryUI CSS 및 JS --%>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.1/themes/base/jquery-ui.min.css"/>
    <script src="https://code.jquery.com/ui/1.13.1/jquery-ui.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>


</head>
<body>

<!-- 상단 네비게이션 시작 -->
<nav
        class="navbar navbar-expand-lg navbar-light bg-light fixed-top mx-4 py-3">

    <!-- Brand/logo -->
    <!-- Font Awesome 6 Icons -->
    <a class="navbar-brand" href="<%= ctxPath %>/index.up"
       style="margin-right: 10%;"><img
            src="<%= ctxPath %>/images/sist_logo.png"/></a>

    <!-- 아코디언 같은 Navigation Bar 만들기 -->
    <button class="navbar-toggler" type="button" data-toggle="collapse"
            data-target="#collapsibleNavbar">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="collapsibleNavbar">
        <ul class="navbar-nav" style="font-size: 16pt;">
            <li class="nav-item active"><a class="nav-link menufont_size"
                                           href="<%= ctxPath %>/index.up">Home</a></li>
            <li class="nav-item active"><a class="nav-link menufont_size"
                                           href="<%= ctxPath %>/member/memberRegister.up">회원가입</a></li>
            <li class="nav-item active"><a class="nav-link menufont_size"
                                           href="<%= ctxPath %>/mall/more.up">쇼핑몰[더보기]</a>
            </li>

            <li class="nav-item active"><a class="nav-link menufont_size"
                                           href="<%= ctxPath %>/mall/mall/scroll.up">쇼핑몰[스크롤]</a>
            </li>

            <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.userId == 'admin'}">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle menufont_size text-primary"
                       href="#" id="navbarDropdown" data-toggle="dropdown"> 관리자전용 <%-- .text-primary 는 글자색으로 파랑색임 --%>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <a class="dropdown-item text-primary" href="<%=ctxPath%>/admin/memberList.up">회원목록</a>
                        <a class="dropdown-item text-primary" href="#">제품등록</a>
                        <div class="dropdown-divider"></div>
                        <a class="dropdown-item text-primary" href="#">전체주문내역</a>
                    </div>
                </li>
            </c:if>
        </ul>

        <c:if test="${not empty sessionScope.loginUser and sessionScope.loginUser.userId == 'admin'}">
            <div style="margin-left: auto;">
                <i class="fa-solid fa-user fa-2x"></i>&nbsp;&nbsp;<span style="font-size: 10pt;">관리자 로그인중..</span>&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="<%= ctxPath %>/api/member/logout"><i class="fa-solid fa-right-from-bracket fa-2x"></i>&nbsp;&nbsp;<span style="font-size: 10pt;">로그아웃</span></a>
            </div>
        </c:if>

    </div>
</nav>
<!-- 상단 네비게이션 끝 -->

<hr
        style="background-color: gold; height: 1.2px; position: relative; top: 85px; margin: 0 1.7%;">

<div class="container-fluid" id="container"
     style="position: relative; top: 90px; padding: 0.1% 2.5%;">

    <div class="row">
        <div class="col-md-12" id="maininfo">