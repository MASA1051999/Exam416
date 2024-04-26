
<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科目管理システム</title>
</head>
<body>

	<h2>科目管理</h2>
	<a href="SubjectCreate.action">新規登録</a>


		<table class="table table-hover">
			<tr>
				<th>科目コード</th>
				<th>科目名</th>
				<th></th>
				<th></th>
			</tr>
			<c:forEach var="subject" items="${subjects}">
				<tr>
					<td>${subject.cd}</td>
					<td>${subject.name}</td>
					<td><a href="SubjectUpdate.action?cd=${subject.cd}">変更</a></td>
					<td><a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
				</tr>
			</c:forEach>
		</table>


</body>
</html>