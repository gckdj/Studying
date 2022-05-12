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
    }
}
