<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="./style.css"/>
<title>得点管理システム</title>
</head>

<c:import url="header.jsp"></c:import>

<body>

<div id="content">

	<div id="nav">
		<%@ include file="navigation.jsp" %>
	</div>

	<div id="main">
		<h2>メニュー</h2>

		<a href="StudentList.action">学生管理</a>
		<div>成績管理</div>
		<a href="TestRegist.action">成績登録</a>
		<a href="TestList.action">成績参照</a>
		<a href="SubjectList.action">科目管理</a>
	</div>

</div>

<%@ include file="footer.jsp" %>

</body>
</html>