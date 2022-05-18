package hello.servlet.Web.frontcontroller.v1;

import hello.servlet.Web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.Web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.Web.frontcontroller.v1.controller.MemberSaveContorllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveContorllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();

        // 요쳥경로가 생성자로 매핑되어 있는게 아니면 controller는 null이 된다.
        ControllerV1 controller = controllerMap.get(requestURI);

        //  controller가 null이면, 클라이언트에 404 오류를 반환하도록 한다.
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        controller.process(response, request);
    }
}