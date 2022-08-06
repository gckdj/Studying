package hello.servlet.Web.Servlet;

import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "memberSaveServlet", urlPatterns = "/servlet/members/save")
public class MemberSaveServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MemberSaveServlet.service");
        //form에서 넘긴 파라미터를 지정해서 각 변수에 담고
        String username = req.getParameter("username");
        int age = Integer.parseInt(req.getParameter("age"));

        // 해당 변수로 member 객체생성, 메모리(HashMap)에 저장(put)
        Member member = new Member(username, age);
        memberRepository.save(member);

        resp.setContentType("text/html");
        resp.setCharacterEncoding("utf-8");

        // 저장된 member 객체의 데이터로, 응답처리
        PrintWriter w = resp.getWriter();
        w.write("<html>\n" +
                "<head>\n" +
                " <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "성공\n" +
                "<ul>\n" +
                " <li>id="+member.getId()+"</li>\n" +
                " <li>username="+member.getUsername()+"</li>\n" +
                " <li>age="+member.getAge()+"</li>\n" +
                "</ul>\n" +
                "<a href=\"/index.html\">메인</a>\n" +
                "</body>\n" +
                "</html>");
        //result:
        //성공
        //id=1
        //username=kdj
        //age=29

        //한번 더 수행시
        //sequence++로 id값이 증가
        //result:
        //성공
        //id=2
        //username=kdj2
        //age=29
    }
}
