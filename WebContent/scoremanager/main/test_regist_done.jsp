<%-- 成績登録完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>

<c:import url="header.jsp"></c:import>

<body>

<%@ include file="navigation.jsp" %>

	<h2>成績管理</h2>
		<p>登録が完了しました</p>
	<a href="TestRegist.action">戻る</a>
	<a href="TestList.action">成績参照</a>
</body>
</html>