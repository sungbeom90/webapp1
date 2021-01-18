<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
		<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
		<link rel="stylesheet" href="<%=application.getContextPath()%>/resources/css/main.css">
	</head>
	
	<body>
		<div class="wrap">
			<%-- 공통 헤더 --%>
			<jsp:include page="/WEB-INF/views/include/header.jsp"/> <!-- 실행을하고 결과를 넣는다. -->
			<%-- <%@ include file="/WEB-INF/views/include/header.jsp" %> --%> <!-- 복사를 하고 넣는다. -->
			
			<%-- 내용 --%>
			<div class="mainCenter">
				<%-- 공통 메뉴 --%>
				<jsp:include page="/WEB-INF/views/include/menu.jsp"/>
				<div class="content">
					
					<div class="sector">
						<h5>의존성 주입</h5>
						<div>
							<a class="btn btn-success btn-sm" href="service1">Ch13Service1 사용</a>
							<a class="btn btn-success btn-sm" href="service2">Ch13Service2 사용</a>
						</div>
					</div>				
					<div class="sector">
						<h5>XML을 이용한 Setter 주입</h5>
						<div>
							<a class="btn btn-success btn-sm" href="service3">Ch13Service3 사용</a>
							<a class="btn btn-success btn-sm" href="service4">Ch13Service4 사용</a>
						</div>
					</div>
					<div class="sector">
						<h5>DAO -> Service -> Controller 주입</h5>
						<div>
							<a class="btn btn-success btn-sm" href="service5">Ch13Service5 사용</a>
						</div>
					</div>
					
					<div class="sector">
						<h5>인터페이스 타입 주입</h5>
						<div>
							<a class="btn btn-success btn-sm" href="service6">Ch13Service6 사용</a>
						</div>
					</div>	
					<div class="sector">
						<h5>Properties 값 주입</h5>
						<div>
							<a class="btn btn-success btn-sm" href="fileupload">변경될 수 있는 값 주입</a>
						</div>
					</div>	
				</div>
			</div>
		</div>
	</body>
</html>