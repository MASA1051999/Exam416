<%-- 成績削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>

<c:import url="header.jsp"></c:import>

</head>
<body>

<%@ include file="navigation.jsp" %>

	<h2>成績情報削除</h2>

	<form action = "TestDeleteExecute.action" method="post">
		<p>「${studentcd},${subjectcd},${num},${point}」を削除してもよろしいですか

		<input type="hidden" name="studentcd" value="${studentcd}">
		<input type="hidden" name="subjectcd" value="${subjectcd}">
		<input type="hidden" name="num" value="${num}">



		<input type="submit" value="削除">
	</form>

	<a href="TestRegist.action">戻る</a>

<%@ include file="footer.jsp" %>

</body>
</html>