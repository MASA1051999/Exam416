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

<c:import url="header.jsp"></c:import>

<body>

<%@ include file="navigation.jsp" %>

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
				<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
			</c:forEach>
		</select>

		<label>回数</label>
		<select name="f4">
			<option value="0">------</option>
			<option value="1"<c:if test="${num==1}">selected</c:if>>1</option>
			<option value="2"<c:if test="${num==2}">selected</c:if>>2</option>

		</select>

		<button>検索</button>

	</form>

	<%-- testキーに保存された内容を表示 --%>
	<div>${errors.get("test")}</div>

	<%-- test_nullキーに保存された内容を表示 --%>
	<div>${errors.get("test_null") }</div>

	<c:choose>

		<%-- テストが存在する場合 --%>
		<c:when test="${test_list.size()>0}">
			<h2>科目：${subject.name}(${num}回)</h2>
			<form action="TestRegistExecute.action">
				<%-- 0～100の範囲で入力してください、と表示する --%>
					<div>${errors.get("point")}</div>
				<%-- 値を入力してください、と表示する --%>
					<div>${errors.get("null_point")}</div>
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
									<c:if test="${test.point != -1}">
										<input type="number" name="point_${test.student.no}" value="${test.point}"/>
									</c:if>

									<c:if test="${test.point == -1}">
										<input type="number" name="point_${test.student.no}"/>
									</c:if>

								</td>
								<td>
									<c:if test="${test.point != -1}">
										<a href="TestDelete.action?studentcd=${test.student.no}&subjectcd=${subject.cd}&num=${num}&user=${user.school.cd}&point=${test.point}">削除</a>
									</c:if>
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

<%@ include file="footer.jsp" %>

</body>
</html>