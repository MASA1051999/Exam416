<%-- 科目変更完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科目管理システム</title>
</head>

<c:import url="header.jsp"></c:import>

<body>

<%@ include file="navigation.jsp" %>

	<h2>科目情報変更</h2>
	<label>変更が完了しました</label>

	<a href="SubjectList.action">戻る</a>

<%@ include file="footer.jsp" %>

</body>
</html>