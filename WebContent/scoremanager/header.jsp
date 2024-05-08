<%-- ヘッダー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>得点管理システム</h1>

<c:if test="${user != null}">
	<span>${user.name}様</span>
	<a href="../Logout.action">ログアウト</a>
</c:if>