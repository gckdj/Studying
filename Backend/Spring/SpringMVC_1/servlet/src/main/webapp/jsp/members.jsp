<%@ page import="java.util.List" %>
<%@ page import="hello.servlet.domain.MemberRepository" %>
<%@ page import="hello.servlet.domain.Member" %>
<%@ page import="hello.servlet.domain.MemberRepository" %>
<%@ page import="hello.servlet.domain.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
    <%
        for (Member member : members) {
            out.write(" <tr>");
            out.write(" <td>" + member.getId() + "</td>");
            out.write(" <td>" + member.getUsername() + "</td>");
            out.write(" <td>" + member.getAge() + "</td>");
            out.write(" </tr>");
        }
        // jsp 내에서 자바코드를 작성하고 동일한 결과를 얻었다.
        // result:
        //  id	username	age
        //  1	kdj1	11
        //  2	kdj2	22

        // 서블릿/JSP의 한계점:
        // 서블릿만 활용하는 방식에서는 자바단에서 자바코드로 html 요소를 생성했기 때문에,
        // 기존 로직과 뒤엉켜 가독성이 저해되고, 개발자의 실수를 유발함.
        // JSP에서는 html 내에서 코딩함으로 가독성이 저해되는 부분은 해결했지만,
        // 모든 역할의 코드들이 jsp 파일 내에서 모여, 프로젝트가 커질수록 유지보수의 어려움 증대
        // → MVC패턴 등장
    %>
    </tbody>
</table>
</body>
</html>