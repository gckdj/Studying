package hello.servlet.Web.frontcontroller.v5.adapter;

import hello.servlet.Web.frontcontroller.ModelView;
import hello.servlet.Web.frontcontroller.v4.ControllerV4;
import hello.servlet.Web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object Handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) Handler;

        // Controller V4 에서는 어댑터에서 모델 객체를 생성하고 매개변수로 주고 받는다.
        Map<String, String> paramMap = createParamMap(request);
        HashMap<String, Object> model = new HashMap<>();

        // 기존 컨트롤러 V4와는 다르게 viewName을 반환해도 프론트 컨트롤러에서 처리 X
        // ModelView 에 담아서 반환(어댑터의 필요성)
        String viewName = controller.process(paramMap, model);
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
