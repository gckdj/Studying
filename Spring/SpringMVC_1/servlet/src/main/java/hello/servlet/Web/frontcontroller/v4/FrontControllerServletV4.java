package hello.servlet.Web.frontcontroller.v4;

import hello.servlet.Web.frontcontroller.MyView;
import hello.servlet.Web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.Web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.Web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(request);
        // 프론트컨트롤러에서 생성한 model를 파라미터로 넘기고 주고 받는다.
        Map<String, Object> model = new HashMap<>();
        // ModelView에서 값을 받고 객체 내에서 viewName을 가져올 필요가 없어졌으니,
        // 바로 문자열 변수에 viewName을 받으면 된다.
        String viewName = controller.process(paramMap, model);

        MyView view = viewResolver(viewName);
        view.render(model, request, response);
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

    // result:
    //id username age
    //1	kdj2	11
    //2	kim	222

    // 지금까지의 발전과정은 한번에 이루어진 것이 아니라, 점진적으로 발전해온 과정이다.
    // '프레임워크나 공통 기능이 수고로워야 사용하는 개발자가 편리해진다.'

    // 현재까지 만들어지는 컨트롤러는 인터페이스에 의존하고 있어 하나의 프로젝트에서 다양한 방식의 컨트롤러 구현에 제한이 있다.
    // → 유연한 컨트롤러의 필요성(어댑터)
}