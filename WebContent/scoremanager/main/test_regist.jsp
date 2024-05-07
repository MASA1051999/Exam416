<%-- 成績更新JSP --%>
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
	<h2>成績管理</h2>
	<form action = "TestRegist.action" method="post">

		<label>入学年度</label>
		<select name="f1">
			<option value="0">------</option>
			<c:forEach var="year" items="${ent_year}">
				<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>

		<label>クラス</label>
		<select name="f2">
		<option value="0">------</option>
			<c:forEach var="num" items="${classNum}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>


		<label>科目</label>
		<select name="f3">
			<option value="0">------</option>
			<c:forEach var="subject" items="${subjects}">
				<%-- 現在のsubject.cdと選択されていたf3が一致していた場合selectedを追記 --%>
				<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.cd}</option>
			</c:forEach>
		</select>

		<label>回数</label>
		<select name="f4">
			<option value="0">------</option>
			<c:forEach var="no" items="${numList}">
				<%-- 現在のnoと選択されていたf4が一致していた場合selectedを追記 --%>
				<option value="${no}" <c:if test="${no==f4}">selected</c:if>>${no}</option>
			</c:forEach>
		</select>

		<button>検索</button>

	</form>

	<%-- testキーに保存された内容を表示 --%>
	<div>${errors.get("test")}</div>

	<c:choose>
		<c:when test="${test_list.size()>0}">
			<h2>科目：${subjectName}(${num}回)</h2>
			<form action="TestRegistExecute.action">
				<table class="table table-hover">
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>点数</th>
					</tr>
					<%-- 取得したテスト結果の表示 --%>
						<c:forEach var="test" items="${test_list}">
							<tr>
								<td>${test.student.entYear}</td>
								<td>${test.classNum}</td>
								<td>${test.student.no}</td>
								<td>${test.student.name}</td>
								<td>
									<input type="text" name="point_${test.student.no}" value="${test.point}"/>
									<%-- 0～100の範囲で入力してください、と表示する --%>
									<div>${errors.get("test_error")}</div>
								</td>
							</tr>
						</c:forEach>
				</table>

			<%-- 条件を付与して送信 --%>
			<input type="hidden" name="f1" value="${f1}">
			<input type="hidden" name="f2" value="${f2}">
			<input type="hidden" name="f3" value="${f3}">
			<input type="hidden" name="f4" value="${f4}">

			<input type="submit" value="登録して終了"></form>
		</c:when>
	</c:choose>

</body>
</html>