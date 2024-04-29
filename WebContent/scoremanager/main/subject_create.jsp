<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
	<h2>科目情報登録</h2>

	<form action = "SubjectCreateExecute.action" method="post">
		<label>科目コード</label>
		<%-- 科目コードの属性名はsubject_cd --%>
		<input type="text" name="subject_cd"
			placeholder="科目コードを入力してください" maxlength="10" value="${subject_cd}" required />

		<%-- 科目コードが3文字で無かった場合のエラー表示 --%>
		<div>${errors.get("subject_error1")}</div>
		<%-- 科目コードが重複した場合のエラー表示 --%>
		<div>${errors.get("subject_error2") }</div>

		<label>科目名</label>
		<%-- 科目名の属性名はname --%>
		<input type="text" name="name"
			placeholder="科目名を入力してください" maxlength="10" value="${name}" required />

		<input type="submit" value="登録">
	</form>

	<a href="SubjectList.action">戻る</a>



</body>
</html>