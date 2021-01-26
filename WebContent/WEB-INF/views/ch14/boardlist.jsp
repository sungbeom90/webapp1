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
						<h5>게시판 목록</h5>
						<div>
							<table class="table table-bordered">
								<thead>
							    	<tr>
										<th>번호</th>
									    <th>제목</th>
									    <th>글쓴이</th>
									    <th>조회수</th>
									    <th>날짜</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  		<c:forEach var="board" items="${list}">
							  			<tr>
											<td>${board.bno}</td>
											<td><a class="text-warning text-decoration-none" href="boardread?bno=${board.bno}">${board.btitle}</a></td>
											<td><img class="rounded-circle"src="mphoto?mid=${board.bwriter}" width="30px"/></td>
											<td>${board.bhitcount}</td>
											<td><fmt:formatDate value="${board.bdate}" pattern="yyyy.MM.dd"/></td>
										</tr>						  		
							  		</c:forEach>
								</tbody>
							</table>
							
							<div class="d-flex justify-content-between align-items-center">
								<c:if test="${sessionMid != null}">
									<a class="btn btn-primary btn-sm" href="boardwrite">글쓰기</a>
								</c:if>
								<c:if test="${sessionMid == null}">
									<span></span>
								</c:if>
								<div>
									<a class="btn btn-sm btn-outline-warning ml-1 mr-1" href="boardlist2?pageNo=1">처음</a>
									<c:if test="${pager.groupNo > 1}">
										<a class="btn btn-sm btn-outline-warning ml-1 mr-1" href="boardlist2?pageNo=${pager.startPageNo-1}">이전</a>
									</c:if>								
									<c:forEach var="i" begin="${pager.startPageNo}" end="${pager.endPageNo}">
										<c:if test="${i==pager.pageNo}">
											<a class="btn btn-sm btn-danger ml-1 mr-1" href="boardlist2?pageNo=${i}">${i}</a>
										</c:if>
										<c:if test="${i!=pager.pageNo}">
											<a class="btn btn-sm btn-outline-success ml-1 mr-1" href="boardlist2?pageNo=${i}">${i}</a>
										</c:if>								
									</c:forEach>
									<c:if test="${pager.groupNo < pager.totalGroupNo}">
										<a class="btn btn-sm btn-outline-warning ml-1 mr-1" href="boardlist2?pageNo=${pager.endPageNo+1}">다음</a>
									</c:if>	
									<a class="btn btn-sm btn-outline-warning ml-1 mr-1" href="boardlist2?pageNo=${pager.totalPageNo}">맨끝</a>
								</div>							
							</div>
						</div>						
					</div>
				</div>
			</div>
		</div>
	</body>
</html>