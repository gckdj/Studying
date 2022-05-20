package hello.servlet.Web.frontcontroller.v5;

import hello.servlet.Web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.Web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.Web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.Web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    // 기존의 컨트롤러, 특정된 컨트롤러 버전만 지원
    // private Map<String, ControllerV4> controllerMap = new HashMap<>();

    // 컨트롤러 → 핸들러의 개념, 어떠한 컨트롤러 버전도 지원
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        // 모든 자바객체의 최상위는 'Object' 따라서 어떠한 컨트롤러 버전을 넣더라도 무관
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
