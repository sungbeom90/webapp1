<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
						<div class="alert alert-primary" role="alert">
						  	로그인
						</div>
						<div>
							<form name="loginform" onsubmit="login()" method="post">
								<div class="form-group">
									<label for="mid">아이디</label>
								    <input type="text" class="form-control" id="mid" name="mid">
								    <small id="errorMid" class="form-text text-danger"></small>
								</div>						
							 	<div class="form-group">
								    <label for="mpassword">비밀번호</label>
								    <input type="password" class="form-control" id="mpassword" name="mpassword">
								    <small id="errorMpassword" class="form-text text-danger"></small>
							 	</div>							 	
							 		<button type="submit" class="btn btn-primary">로그인</button>
							 		<a class="btn btn-primary" href="boardlist2">취소</a>							 		
							</form>
							<script>
								function login(){
									//<form> 태그의 기본 이동 기능을 취소
									event.preventDefault();
									
									//에러 초기화
									$("#errorMid").html("");
									$("#errorMpassword").html("");
									
									
									//입력값 받기
									var validation = true;
									
									const mid = $("#mid").val();
									
									if(mid===""){
										$("#errorMid").html("필수 입력 사항 입니다.");
										validation = false;
									}
									
									const mpassword = $("#mpassword").val();
									
									if(mpassword===""){
										$("#errorMpassword").html("필수 입력 사항 입니다.");
										validation = false;
									}
									
									if(!validation){
										return;
									}
									//AJAX 통신
									$.ajax({
										url: "login",
										method: "post",										
										// data: {mid:"xxx", mpassword:"yyy"} 원래 형태
										// data: {mid:mid, mpassword:mpassword} 위에 선언된 변수를 받은 형태
										data: {mid,mpassword},  // 같기때문에 간략화 가능 
										success: function(data){
											//{"result":"success | wrongMid | wrongMpassword"}
											if(data.result ==="success") {
												alert("로그인 성공");
												location.href="boardlist2";
											} else if(data.result === "wrongMid"){
												$("#errorMid").html("아이디가 존재하지 않습니다.");
											} else {
												$("#errorMpassword").html("비번이 맞지 않습니다.");
											}
										}
									});
								}
							
							
							</script>
							
						</div>
					</div>		
				</div>
			</div>
		</div>
	</body>
</html>