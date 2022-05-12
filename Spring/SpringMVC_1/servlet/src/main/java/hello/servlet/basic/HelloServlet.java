package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 이름과 패턴은 중복불가
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.service");

        // req = org.apache.catalina.connector.RequestFacade@32ed8f83
        // resp = org.apache.catalina.connector.ResponseFacade@7c015ae
        System.out.println("req = " + req);
        System.out.println("resp = " + resp);

        // url : http://localhost:8080/hello?username=kim
        String username = req.getParameter("username");
        // username = kim
        System.out.println("username = " + username);

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        resp.getWriter().write("hello " + username);

        // HttpServletRequest, Response 를 중요한 점은 요청, 응답 메시지를 편리하게 사용하도록 도와주는 객체라는 점.
        // 깊이있는 이해를 위해서는 HTTP 스펙이 제공하는 요청, 응답 메시지 자체를 이해해야함.
    }

    // HTTP 데이터 요청 3가지 방법
    // GET : URL의 쿼리 파라미터에 데이터를 포함해서 전달
    // POST : 메세지 바다에 쿼리 파라미터 형식으로 전달
    // HTTP MESSAGE BODY에 담아 요청
}
