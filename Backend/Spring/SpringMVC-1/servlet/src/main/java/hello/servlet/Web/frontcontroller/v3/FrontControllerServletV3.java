package hello.servlet.Web.frontcontroller.v3;

import hello.servlet.Web.frontcontroller.ModelView;
import hello.servlet.Web.frontcontroller.MyView;
import hello.servlet.Web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.Web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.Web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 파라미터의 각 이름을 순회하며 맵에 담는다.
        Map<String, String> paramMap = createParamMap(request);

        // 최초 회원가입시 key를 "/front-controller/v3/members/new-form" 로 받고 생성된 MemberFormControllerV3 객체 호출
        // 이후 반환되는 값은 ModelView 객체에 "new-form"이 담겨있다.
        ModelView mv = controller.process(paramMap);

        // viewName에는 "new-form"이 담기고
        String viewName = mv.getViewName();

        // 논리이름 전후로 문자열을 붙이고 매개변수 삼아 MyView 객체 생성
        MyView view = viewResolver(viewName);

        // view 내의 메서드 호출하며 페이지이동
        view.render(mv.getModel(), request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        // request 내의 파라미터를 순회하고 map 형태로 반환
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
    
    // 각 컨트롤러마다 서블릿 정보를 호출, 생성하지 않고 프론트컨트롤러에서만 사용
    // 서블릿 코드들을 사용하지 않으면서 실제 구현하는 컨트롤러 코드는 깔끔해진다.
    // 논리경로를 물리경로로 반환하는 viewResolver 의 기능을 하는 메서드를 만들면 좋은 점은
    // 물리경로가 변한다하더라도 viewResolver에 있는 문자열만 수정하면 된다. (유지보수 용이)
    // ModelView를 생성하고 반환하는 과정이 불편한 단점
    // V4에서는 ViewName만 반환한다.
}