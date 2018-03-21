<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %><!--spring validation check 스프링  %@디렉티브-->  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- static 폴더명 아님 이후부터 폴더명 -->
<script type="text/javascript" 
		src="<c:url value="/static/js/jquery-3.3.1.min.js"/>">
</script>
<script type="text/javascript">
		$().ready(function(){
		      <c:if test="${mode == 'modify' && not empty communityVO.displayFilename}">
		      $("#file").closest("div").hide();
		      </c:if>      
		   
		   
		   // 클릭 됐는지 여부
		   $("#displayFilename").change(function(){
		      var isChecked = $(this).prop("checked");
		      if (isChecked){
		            $("label[for=displayFilename]").css({
		            	"text-decoration-line": "line-through",
		               "text-decoration-style": "double",
		               "text-decoration-color": "#FF0000"
		            });
		            $("#file").closest("div").show();
		      }
		      else {
		         $("label[for=displayFilename]").css({
		            "text-decoration": "none"
		         });
		            $("#file").closest("div").hide();
		      }
		   });
		   
		/* 
		      <c:if test = "${sessionScope.status eq 'emptyTitle'}">
		         $("#errorTitle").show();
		      </c:if>

		      <c:if test= "${sessionScope.status eq 'emptyBody'}">
		         $("#errorBody").show();
		      </c:if>

		      <c:if test= "${sessionScope.status eq 'emptyDate'}">
		         $("#errorDate").show();
		      </c:if>
		      
		 */
		      $("#writeBtn").click(function() {
		      var mode = "${mode}";
		         if (mode == 'modify') {
		            var url = "<c:url value="/modify/${communityVO.id}"/>";
		         } else {
		            var url = "<c:url value="/write"/>"
		         }

		         var writeForm = $("#writeForm");
		         writeForm.attr({
		            "method" : "post",
		            "action" : url

		         });

		         writeForm.submit();
		      });
		   });


</script>

<style>
	#body {
	
		width : 500px;
		height : 300px;
	
	}
	form {
		
		display : inline-block;
		
	}

</style>


</head>
<body>
	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />	
		<div>
			<form:form modelAttribute="writeForm" enctype="multipart/form-data"> 
				<p>
					제목 : <input type="text" id="title" name="title" placeholder="제목을 입력하시오" value="${communityVO.title }"/>
				</p>
				<div>
					<form:errors path="title"/><!--체크하고자하는 엘리먼트의 name을 적어준다.  -->
				</div>
				
				<div>
				<p>내용 :
					 <textarea id="body" name="body" placeholder="내용을 입력하세요">${communityVO.body}</textarea>
				</p>
				</div>
				
				<div>
				<c:if test="${mode == 'modify' && not empty communityVO.displayFilename}">
					<div>
						<input type="checkbox" 
							   id="displayFilename" 
							   name="displayFilename"
							   value="${communityVO.displayFilename}"/>
						<label for="displayFilename">
							${communityVO.displayFilename}
						</label>
					</div>
				</c:if>
				</div>	
				
				<div>
					<form:errors path="body"/><!--체크하고자하는 엘리먼트의 name을 적어준다.  -->
				</div>
				
										
				<p>
					<input type="hidden" id="userId" name="userId"  value="${sessionScope.__USER__.id} " placeholder="ID"/>
				</p>
				
				
				<div>
					<input type="file" id="file" name="file" />
				</div>
							
				<p>
					<input type="button" id="writeBtn" value="등록"/>
				</p>
			</form:form>
			
		</div>
	
		<%-- <a href="<c:url value="/logout"/>">로그아웃</a> --%>
	</div>	
</body>
</html>