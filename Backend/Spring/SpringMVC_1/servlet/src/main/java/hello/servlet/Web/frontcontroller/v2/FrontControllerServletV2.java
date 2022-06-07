package hello.servlet.Web.frontcontroller.v2;

import hello.servlet.Web.frontcontroller.MyView;
import hello.servlet.Web.frontcontroller.v1.ControllerV1;
import hello.servlet.Web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.Web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.Web.frontcontroller.v2.controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // 매핑된 경로를 찾아 컨트롤러 객체를 생성한다.
        ControllerV2 controller = controllerMap.get(requestURI);

        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 컨트롤러의 process 메서드를 호출하면 MyView가 반환된다.
        MyView view = controller.process(response, request);

        // 반환된 MyView에서 render 메서드를 호출한다.
        view.render(request, response);

        // result :
        // id	username	age
        // 1	kdj2	11
        // 2	2232	333
    }
}