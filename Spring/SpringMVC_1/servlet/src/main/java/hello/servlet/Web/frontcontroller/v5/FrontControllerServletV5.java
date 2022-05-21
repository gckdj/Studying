package hello.servlet.Web.frontcontroller.v5;

import hello.servlet.Web.frontcontroller.ModelView;
import hello.servlet.Web.frontcontroller.MyView;
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

        // 핸들러어탭터가 모이는 리스트에 컨트롤러 V3를 생성하고 추가
        // 컨트롤러 V3에 해당하는 uri 경로와 로직매핑
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 컨트롤러 V3에 해당하는 uri로 핸들러 호출
        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // getHandlerAdapter 는 handlerAdapters 내에 있는 어댑터를 순회해서 지원하는 핸들러가 있는지 찾고 반환한다.
        // 현재는 V3에 해당하는 어댑터만 있는 상태
        // ControllerV3HandlerAdapter 클래스에서 supports 메서드는 매개변수로 입력된 handler 가 V3의 인스턴스인지 확인하고 불린값을 반환
        // true 처리되고 V3에 해당하는 어댑터 반환
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // handle 을 통해 V3의 처리로직을 적용 이후 동일
        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);

        // error:
        // Received [GET /front-controller/v5/v3/members/new-form HTTP/1.1
        // key 값으로 매핑된 uri : /front-controller/v3/members/new-form → 매핑키가 달라 컨트롤러가 동작하지 않았음.
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        // 정의되어 있지 않는 어댑터인 경우에 핸들러를 반환한다.
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
}
