<%-- 科目更新JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>

<c:import url="header.jsp"></c:import>

<body>

<%@ include file="navigation.jsp" %>

	<h2>科目情報削除</h2>

	<form action = "SubjectDeleteExecute.action" method="post">
		<p>「${subject.name}(${subject.cd})」を削除してもよろしいですか

		<input type="hidden" name="code" value="${subject.cd}">

		<input type="submit" value="削除">
	</form>

	<a href="SubjectList.action">戻る</a>



</body>
</html>