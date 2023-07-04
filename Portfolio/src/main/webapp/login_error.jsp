<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("loginUserId") != null) {
		response.sendRedirect("menu.jsp");
	} else {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン失敗</title>
<link rel="stylesheet" href="common/css/style.css">
</head>
<body>
	<div class="header">
		<span class="big_title">honmachi</span>marutarou
	</div>
	<div class="menu">
		<div class="main_frame">
			<p>ログインに失敗しました</p>
		</div>
	</div>

	<div class="i_main_wrapper">
		<div class="a_logout_button">
			<input type="button" class="display_button" value="ログイン画面に戻る"
				onclick="location.href='login.jsp';">
		</div>
	</div>
	<div class="footer_top">
		<table class="table_format">
			<tr>
				<th>管理者情報</th>
			</tr>
			<tr>
				<td class="cel">会社名</td>
				<td>&nbsp;</td>
				<td>株式会社 honmachimarutarou</td>
			</tr>
			<tr>
				<td class="cel">Tell</td>
				<td>&nbsp;</td>
				<td>03-3333-3333</td>
			<tr>
				<td class="cel">Email</td>
				<td>&nbsp;</td>
				<td>honmachi_maru@freemail.com</td>
			</tr>
		</table>
	</div>

	<div class="footer_design">
		<footer>
			<small>© 2023 honmachimarutarou.</small>
		</footer>
	</div>
</body>
</html>

<%
	}
%>
