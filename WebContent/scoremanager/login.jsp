<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./main/style.css"/>
<title>得点管理システム</title>
</head>

<body>

<c:import url="main/header.jsp"></c:import>

<form action = "LoginExecute.action" method="post">

<!--
	autocomplete
	on/off:自動補完の制御

	ime-mode
	active:漢字（全角）モードにします
	disabled:日本語入力機能(IME)そのものを使用不可能

	required:input要素を入力必須にする属性
 -->

	<h2>ログイン</h2>

	<%-- エラーメッセージがあるとき、リストを表示 --%>
	<c:if test="${!empty errors}">
		<ul>
			<li>${errors.error}</li>
		</ul>
	</c:if>

	<!-- ＩＤ 未入力を許可しない-->
	<label>ＩＤ</label>
	<input type="text" name="id" maxlength="20" placeholder="半角でご入力下さい"
	 autocomplete="off" style="ime-mode: disabled" value="${id}" required/>

	<!-- パスワード 未入力を許可しない-->
	<label>パスワード</label>
	<input type="password" name="password" id="password" value="${password}" required placeholder="20文字以内の半角英数字でご入力下さい">

	<input type="checkbox" name="chk_d_ps" id="check" onchange="checkfunction()">
	<label for="check">パスワードを表示</label>

	<!-- ログイン用ボタン -->
	<input type="submit" name="login" value="ログイン"/>

</form>

<%@ include file="main/footer.jsp" %>

<script type="text/javascript">
	function checkfunction() {
		  var check = document.getElementById('check');
		  var password = document.getElementById('password');
		  if (check.checked == true) {
		    password.type = 'text';
		  } else {
		    password.type = 'password';
		  }
		}
</script>

</body>
</html>