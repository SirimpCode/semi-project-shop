<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 25. 6. 4.
  Time: 오전 9:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./include/header.jsp"/>
<div class="container">
    <p class="h2 text-center mt-5">장애발생</p>
    <img src="<%= request.getContextPath()%>/images/error1.png" class="img-fluid" style="display: block; margin: 0 auto;"/> <%-- 반응형 이미지 --%>
    <p class="h5 mt-3" style="text-align: center; color: black;">빠른 복구를 위해 최선을 다하겠습니다.</p>
</div>


<script>
    const message = "<%=request.getAttribute("message") == null ? "" : request.getAttribute("message")%>";
    // 페이지가 로드되면 자동으로 실행되는 함수
    document.addEventListener("DOMContentLoaded", function () {

        if (message) {
            alert(message);
        } else {
            alert("알 수 없는 오류가 발생했습니다.");
        }


    });
</script>
<jsp:include page="./include/footer.jsp"/>
