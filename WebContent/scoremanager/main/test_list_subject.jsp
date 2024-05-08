<%-- 科目別成績参照JSP --%>
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
					<option value="${subject.cd}" <c:if test="${subject==f3}">selected</c:if>>${subject.name}</option>
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


	<%-- ここから検索結果表示 --%>
	<c:choose>
		<%-- 検索結果が0件のとき --%>
		<c:when test="${empty subjectTests}">
			<p>学生情報が存在しませんでした</p>
		</c:when>

		<%-- 検索結果が1件以上のとき、科目別成績一覧を表示 --%>
		<c:when test="${!empty subjectTests}">
			<div>科目：${subjectName}</div>

			<table class="table table-hover">
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>１回</th>
						<th>２回</th>
					</tr>
					<%-- まだ初期値を空にしてない --%>
					<c:forEach var="test" items="${subjectTests}">
						<tr>
							<td>${test.entYear}</td>
							<td>${test.classNum}</td>
							<td>${test.studentNo}</td>
							<td>${test.studentName}</td>
							<%-- 試験回数が存在するかで分岐 --%>
							<td>
								<c:choose>
									<c:when test="${!empty test.getPoint(1)}">${test.getPoint(1)}</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							<td>
								<c:choose>
									<c:when test="${!empty test.getPoint(2)}">${test.getPoint(2)}</c:when>
									<c:otherwise>-</c:otherwise>
								</c:choose>
							</td>

						</tr>
					</c:forEach>
				</table>
			</c:when>
		</c:choose>

</body>
</html>