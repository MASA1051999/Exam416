<%-- テスト削除完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科目管理システム</title>

<c:import url="header.jsp"></c:import>

</head>
<body>

<%@ include file="navigation.jsp" %>

	<h2>テスト削除完了</h2>
	<label>削除が完了しました</label>

	<a href="TestRegist.action">戻る</a>

<%@ include file="footer.jsp" %>

</body>
</html>