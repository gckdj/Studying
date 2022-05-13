package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //ServletInputStream 으로 http 메시지 바디를 읽기가능(바이트코드 형식)
        ServletInputStream inputStream = req.getInputStream();

        //inputStream = org.apache.catalina.connector.CoyoteInputStream@405708bc
        System.out.println("inputStream = " + inputStream.toString());

        //바이트코드를 다시 인코딩
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        //messageBody = hello!
        System.out.println("messageBody = " + messageBody);
        resp.getWriter().write("ok");
    }
}
