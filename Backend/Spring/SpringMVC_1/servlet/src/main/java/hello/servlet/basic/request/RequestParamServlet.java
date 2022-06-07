package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
* 1. 파라미터 전송기능
* http://localhost:8080/request-param?username=hello&age=20
*/
@WebServlet(name = "RequestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        // RequestParamServlet.service
        // System.out.println("RequestParamServlet.service");

        //[전체 파라미터 조회] - start
        //username=hello
        //age=20
        //[전체 파라미터 조회] - end
        System.out.println("[전체 파라미터 조회] - start");
        req.getParameterNames().asIterator()
                        .forEachRemaining(paramName -> System.out.println(paramName + "=" + req.getParameter((paramName))));
        System.out.println("[전체 파라미터 조회] - end");

        //[단일 파라미터 조회]
        //username = hello
        //age = 20
        //[단일 파라미터 조회 끝]
        System.out.println("[단일 파라미터 조회]");
        String username = req.getParameter("username");
        String age = req.getParameter("age");
        System.out.println("username = " + username);
        System.out.println("age = " + age);
        System.out.println("[단일 파라미터 조회 끝]");

        //url : http://localhost:8080/request-param?username=hello&age=20&username=hello2
        //동일한 키로 값이 중복될 경우 getParameterValues 로 모두 꺼내올 수 있음
        //[이름이 같은 복수 파라미터 조회]
        //username = hello
        //username = hello2
        //[이름이 같은 복수 파라미터 조회 끝]
        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = req.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }
        System.out.println("[이름이 같은 복수 파라미터 조회 끝]");

        // 단수 vs 복수 파라미터
        // 단수 -> 하나의 키에 대한 중복 값을 허용 X, 복수 -> 허용 O
        // 중복 값인 경우 getParameterValue 를 호출하는 경우 가장 첫번째 값을 반환한다.
    }
}
