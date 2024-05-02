<%-- 学生更新JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
	<h2>成績参照</h2>
	<form action = "TestList.action" method="post">
		<div>科目情報</div>
			<label>入学年度</label>
			<select name="f1">
				<c:forEach var="year" items="${ent_year}">
					<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
				</c:forEach>
			</select>

			<label>クラス</label>
			<select name="f2">
				<c:forEach var="num" items="${classNum}">
					<option value="${num}">
				</c:forEach>
			</select>

			<label>科目</label>
			<select name="f3">
				<c:forEach var="subject" items="${subjects}">
					<option value="${subject.name}">
				</c:forEach>
			</select>


		<button>検索</button>
	</form>

	<form action="TestListStudentExecute.action" method="post">

	</form>



	<a href="StudentList.action">戻る</a>

</body>
</html>