<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="mainHeader">
	<h3>Spring Framework</h3>
	<div class="loginBox">
		<div>
			<c:if test="${sessionMid == null}">
				<div>
					<a class="btn btn-danger btn-sm" href="<%=application.getContextPath()%>/ch14/join">회원가입</a>
					<a class="btn btn-danger btn-sm" href="<%=application.getContextPath()%>/ch14/login">로그인</a>
				</div>
			</c:if>
			<c:if test="${sessionMid != null}">
				<div>
					<img src="<%=application.getContextPath()%>/ch14/mphoto" width="50px"/>
					<a class="btn btn-danger btn-sm" href="<%=application.getContextPath()%>/ch14/logout">로그아웃</a>
				</div>
			</c:if>
		</div>		
	</div>
</div>