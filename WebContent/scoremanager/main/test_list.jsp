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
					<option value="${num}">
				</c:forEach>
			</select>

			<label>科目</label>
			<select name="f3">
				<option value="0">------</option>
				<c:forEach var="subject" items="${subjects}">
					<option value="${subject.name}">
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

	<div>科目:${subjectName}</div>
	<table class="table table-hover">
			<tr>
				<th>入学年度</th>
				<th>クラス</th>
				<th>学生番号</th>
				<th>氏名</th>
				<th>１回</th>
				<th>２回</th>
			</tr>
			<c:forEach var="subject" items="${subjectTests}">
				<tr>
					<td>${subject.cd}</td>
					<td>${subject.name}</td>
					<td><a href="SubjectUpdate.action?cd=${subject.cd}">変更</a></td>
					<td><a href="SubjectDelete.action?cd=${subject.cd}">削除</a>
				</tr>
			</c:forEach>
		</table>




	<a href="StudentList.action">戻る</a>

</body>
</html>