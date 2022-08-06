package hello.servlet.basic.response;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // setStatus(200) 과 동일한 의미
        // [status-line]
        resp.setStatus(HttpServletResponse.SC_OK);
        //resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); result: 400 error

        // [response-headers]
        resp.setHeader("Content-Type", "text/plain;charset=utf-8");
        resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("my-header", "hello");
        //result:
        //HTTP/1.1 200
        //Cache-Control: no-cache, no-store, must-revalidate
        //Pragma: no-cache
        //my-header: hello
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 4
        //Date: Sat, 14 May 2022 12:52:14 GMT
        //Keep-Alive: timeout=60
        //Connection: keep-alive

        //[Header 편의 메서드]
        //content(resp);
        //cookie(resp);
        redirect(resp);

        //[message body]
        PrintWriter writer = resp.getWriter();
        writer.println("안녕하세요.");
    }

    private void content(HttpServletResponse resp) {
        //헤더 내 타입지정을 위한 메서드 이용
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("utf-8");
        //resp.setContentLength ※ 생략시 자동생성
    }

    private void cookie(HttpServletResponse resp) {
        //resp.setHeader("Set-Cookie", "myCookie=good; Max-Age=600")
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600);
        //addCookie는 쿠키값 입력 편의메서드
        resp.addCookie(cookie);
        //result:
        //Set-Cookie: myCookie=good; Max-Age=600; Expires=Sat, 14-May-2022 13:12:39 GMT
        //Cookie: myCookie=good
    }

    // 지정된 경로로 다시 이동
    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html

        //편의메서드 사용 X
        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        
        response.sendRedirect("/basic/hello-form.html");
    }
}
