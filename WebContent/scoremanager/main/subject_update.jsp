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

	<h2>科目情報更新</h2>

	<form action = "SubjectUpdateExecute.action" method="post">
		<label>科目コード</label>
		<%-- 科目コードは読み取り専用 --%>
		<input type="text" name="code" value="${subject.cd}" readonly />

		<%-- 変更する科目コードが存在しない場合のエラー表示 --%>
		<div>${error.get("error")}</div>

		<label>科目名</label>
		<%-- 科目名の属性名はname --%>
		<input type="text" name="name" value="${subject.name}" maxlength="20" required />

		<input type="submit" value="変更">
	</form>

	<a href="SubjectList.action">戻る</a>

<%@ include file="footer.jsp" %>

</body>
</html>