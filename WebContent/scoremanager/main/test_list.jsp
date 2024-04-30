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
	<form action = "TestRegist.action" method="post">
		<div>科目情報</div>
			<label>入学年度</label>>
			<select name="f1">
				<c:forEach var="ent_year" items="${year}">
					<option value="${year}"><%-- 入力されてた時の設定がまだ --%>
				</c:forEach>
			</select>

			<label>クラス</label>>
			<select name="f2">
				<c:forEach var="class" items="${num}">
					<option value="${num}">
				</c:forEach>
			</select>

			<label>科目</label>>
			<select name="f3">
				<c:forEach var="subject" items="${subject.name}">
					<option value="${subject.name}">
				</c:forEach>
			</select>


		<button type="submit">検索</button>
	</form>

	<form action="TestListStudentExecute.action" method="post">

	</form>



	<a href="StudentList.action">戻る</a>

</body>
</html>