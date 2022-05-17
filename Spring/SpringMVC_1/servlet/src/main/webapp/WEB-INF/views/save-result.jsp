<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <%--<li>id=<%=((Member)request.getAttribute("member")).getId()%></li>
    <li>id=<%=((Member)request.getAttribute("member")).getUsername()%></li>
    <li>id=<%=((Member)request.getAttribute("member")).getAge()%></li>--%>

    <%-- JSP에서 제공하는 표현법 --%>
    <li>id=${member.id}</li>
    <li>username=${member.username}</li>
    <li>age=${member.age}</li>

    <%-- result : --%>
    <%--id=1
    username=dsds
    age=222--%>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
