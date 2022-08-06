package hello.servlet.basic.request;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        //--- REQUEST-LINE - start ---
        //request.getMethod() = GET
        //request.getProtocal() = HTTP/1.1
        //request.getScheme() = http
        //request.getRequestURL() = http://localhost:8080/request-header
        //request.getRequestURI() = /request-header
        //request.getQueryString() = null
        //request.isSecure() = false
        //--- REQUEST-LINE - end ---
        printStartLine(req);
        printHeaders(req);
        printHeaderUtils(req);
        printEtc(req);
    }

    private void printStartLine(HttpServletRequest request) {
        System.out.println("--- REQUEST-LINE - start ---");
        System.out.println("request.getMethod() = " + request.getMethod()); //GET
        System.out.println("request.getProtocal() = " + request.getProtocol()); // HTTP / 1.1
        System.out.println("request.getScheme() = " + request.getScheme()); //http
        // http://localhost:8080/request-header
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        // /request-test
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        //username=hi
        System.out.println("request.getQueryString() = " +
                request.getQueryString());
        System.out.println("request.isSecure() = " + request.isSecure()); //http 사용 유무
        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }

    //--- REQUEST-LINE - start ---
    //headerName = host
    //headerName = connection
    //headerName = cache-control
    //headerName = sec-ch-ua
    //headerName = sec-ch-ua-mobile
    //headerName = sec-ch-ua-platform
    //headerName = upgrade-insecure-requests
    //headerName = user-agent
    //headerName = accept
    //headerName = sec-fetch-site
    //headerName = sec-fetch-mode
    //headerName = sec-fetch-user
    //headerName = sec-fetch-dest
    //headerName = accept-encoding
    //headerName = accept-language
    //--- REQUEST-LINE - end ---
    private void printHeaders(HttpServletRequest req) {
        System.out.println("--- REQUEST-LINE - start ---");

        // getHeaderNames() : http의 모든 헤더정보를 가져옴
        /*Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println("headerName = " + headerName);
        }*/

        req.getHeaderNames().asIterator()
                        .forEachRemaining(headerName -> System.out.println("headerName = " + headerName));
        System.out.println("--- REQUEST-LINE - end ---");
    }


    //--- Header 편의 조회 start ---
    //[Host 편의 조회]
    //request.getServerName() = localhost
    //request.getServerPort() = 8080
    //
    //[Accept-Language 편의 조회] -> 언어사용 우선순위
    //locale = ko_KR
    //locale = ko
    //locale = en_US
    //locale = en
    //request.getLocale() = ko_KR
    //
    //[cookie 편의 조회] -> 쿠기가 없어 조회안되는 중
    //
    //[Content 편의 조회]
    //request.getContentType() = null
    //request.getContentLength() = -1
    //request.getCharacterEncoding() = UTF-8
    //--- Header 편의 조회 end ---

    //포스트맨으로 텍스트를 보냈을때 응답 :
    //[Content 편의 조회]
    //request.getContentType() = text/plain
    //request.getContentLength() = 6
    //request.getCharacterEncoding() = UTF-8
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");
        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더
        System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 헤더
        System.out.println();
        System.out.println("[Accept-Language 편의 조회]");
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();
        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();
        System.out.println("[Content 편의 조회]");
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());
        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    //--- 기타 조회 start ---
    //[Remote 정보]
    //request.getRemoteHost() = 0:0:0:0:0:0:0:1
    //request.getRemoteAddr() = 0:0:0:0:0:0:0:1
    //request.getRemotePort() = 64104
    //
    //[Local 정보]
    //request.getLocalName() = 0:0:0:0:0:0:0:1
    //request.getLocalAddr() = 0:0:0:0:0:0:0:1
    //request.getLocalPort() = 8080
    //--- 기타 조회 end ---
    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");
        System.out.println("[Remote 정보]");
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); //
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr()); //
        System.out.println("request.getRemotePort() = " + request.getRemotePort()); //
        System.out.println();
        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " + request.getLocalName()); //
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); //
        System.out.println("request.getLocalPort() = " + request.getLocalPort()); //
        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
}
