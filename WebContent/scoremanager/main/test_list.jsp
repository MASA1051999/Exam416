<%-- 成績参照JSP --%>
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
	<%-- ここから検索条件入力 --%>
	<form action = "TestListSubjectExecute.action" method="post">
		<div>科目情報</div>
			<label>入学年度</label>
			<select name="f1">
				<option value="0">------</option>
				<c:forEach var="year" items="${ent_year}">
					<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
				</c:forEach>
			</select>

			<label>クラス</label>
			<select name="f2">
				<option value="0">------</option>
				<c:forEach var="num" items="${classNum}">
					<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
				</c:forEach>
			</select>

			<label>科目</label>
			<select name="f3">
				<option value="0">------</option>
				<c:forEach var="subject" items="${subjects}">
					<option value="${subject.name}" <c:if test="${subject==f3}">selected</c:if>>${subject.name}</option>
				</c:forEach>
			</select>


		<button>検索</button>
	</form>

	<form action="TestListStudentExecute.action">

		<p>学生情報</p>
			<label>学生番号</label>
			<input type="text" name="f4" maxlength="10" placeholder="学生番号を入力してください"
			style="ime-mode: disabled" value="${f4}" required>

			<%-- 科目情報識別コード --%>
			<input type="hidden" name="f" value="sj">

			<%-- 学生情報識別コード --%>
			<input type="hidden" name="f" value="st">
		<button>検索</button>
	</form>

	<p>科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>

</body>
</html>