<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/button.css"/>" />

<link rel="stylesheet" type="text/css"
	href="<c:url value="/static/css/input.css"/>" />

<script type="text/javascript"
	src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>

<script type="text/javascript">
	$().ready(function(){

		$("#email").keyup(function(){
			var value = $(this).val();
			if(value != ""){

				//Ajax CAll //http://localhsot:8080/api/exists/email
				//  GeT과 POST 지원 무조건 POST쓴다
																//파라미터 객체 리터럴 .
				$.post("<c:url value="/api/exists/email"/>", {
					email : value
				} , function(response) {

					console.log(response.response);

					if( response.response) {
						alert("작성한 이메일은 사용할 수 없습니다.");
						$("#email").removeClass("valid");
						$("#email").addClass("invalid");
					}
					else{
						$("#email").removeClass("invalid");
						$("#email").addClass("valid");
				}
			});
		}
					else{
						$(this).removeClass("valid");
						$(this).addClass("invalid");
			}
		});

				$("#nickname").keyup(function(){
					var value = $(this).val();
					if(value != ""){
				$.post("<c:url value="/api/exists/nickname"/>",{
					nickname : value  }, function(response){
						console.log(response.response);

					if(response.response){
						alert("작성한 닉네임이 이미 존재합니다.");
						$("#nickname").removeClass("valid");
						$("#nickname").addClass("invalid");
					}
					else{
						$("#nickname").removeClass("invalid");
						$("#nickname").addClass("valid");
					}
					});
				}
			else{
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});

		$("#password").keyup(function(){
			var value = $(this).val();
			if(value != ""){
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else{
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}

			var password = $("#password-confirm").val();

			if( value != password){
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password-confirm").removeClass("valid");
				$("#password-confirm").addClass("invalid");
			}
			 else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password-confirm").removeClass("invalid");
				$("#password-confirm").addClass("valid");
			}
		});

		$("#password-confirm").keyup(function(){
			var value = $(this).val();
			var password = $("#password").val();

			if( value != password){
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password").removeClass("valid");
				$("#password").addClass("invalid");
			}
			 else {
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password").removeClass("invalid");
				$("#password").addClass("valid");
			}
		});

		$("#registBtn").click(function(){

			if ( $("#email").val() == ""){
				alert("이메일을 입력하세요");
				$("#email").focus();
				$("#email").addClass("invalid");
				return false;
			}
			if( $("#email").hasClass("invalid") ) {
				alert("작성한 이메일은 사용할 수 없습니다.");
				$("#email").focus();
				return false;
			}
			else{
				$.post("<c:url value="/api/exists/email"/>", {
					email : $("#email").val()
				} , function(response) {

					console.log(response.response);

					if( response.response) {
						alert("작성한 이메일은 사용할 수 없습니다.");
						$("#email").focus();
						return false;
					}

					if($("#nickname").val()==""){
						alert("Nickname을 입력하세요!");
						$("#nickname").focus();
						$("#nickname").addClass("invalid");
						return false;
					}
					else{
					$.post("<c:url value="/api/exists/nickname"/>", {
						nickname : $("#nickname").val()
					} , function(response) {

						console.log(response.response);

						if( response.response) {
							alert("작성한 이메일은 사용할 수 없습니다.");
							$("#nickname").focus();
							return false;

					}
				}
					)};
				if($("#password").val()==""){
					alert("PassWord를 입력하세요!");
					$("#password").focus();
					$("#password").addClass("invalid");
					return false;
				}
				$("#registForm").attr({
									"method":"post",
									"action":"<c:url value="/regist"/>"
								}).submit();
						});
					}
				});
		});
</script>
</head>
<body>

	<div id="wrapper">
		<form:form modelAttribute="registForm">
			<div>
				<input type="email" id="email" placeholder="Email" name="email"
					value="${registForm.email}" />
				<div>
					<form:errors path="email" />
				</div>
			</div>
			<div>
				<input type="text" id="nickname" placeholder="Nickname"
					name="nickname" value="${registForm.nickname}" />
			</div>
			<div>
				<form:errors path="nickname" />
			</div>
			<div>
				<input type="password" id="password" name="password"
					placeholder="Password" value="${registForm.nickname}" />
				<div>
					<form:errors path="password" />
				</div>
			</div>
			<div>
				<input type="password" id="password-confirm" placeholder="Password" />
			</div>
			<div style="text-align: center;">
				<div id="registBtn" class="button">회원가입</div>
			</div>

		</form:form>

	</div>


</body>
</html>
