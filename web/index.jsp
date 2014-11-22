<%@page import="com.sys.bean.AreaBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<script>
function setCookie(c_name,value,expiredays)
{
	var exdate=new Date()
	exdate.setDate(exdate.getDate()+expiredays)
	document.cookie=c_name+ "=" +escape(value)+
	((expiredays==null) ? "" : ";expires="+exdate.toGMTString())
}
</script>
</head>
<body>
史衍盛123


<form action="<%=path%>/servlet/com.sys.servlet.TestServlet" method="post" name="form1">

<input type="button" name="Submit" value="设置cookie" onclick="setCookie('sessionId','<%=request.getSession().getId()%>',1);">


<input type="submit" name="Submit" value="upload">
</form>
</body>
</html>