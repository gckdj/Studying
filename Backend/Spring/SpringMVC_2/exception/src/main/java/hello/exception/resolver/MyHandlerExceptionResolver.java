package hello.exception.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        try {
            // IllegalArgumentException 발생 시 sendError 호출, 상태코드 400 지정으로 모델뷰 반환
            if (ex instanceof IllegalArgumentException) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
                // 생성되는 모델뷰에 값을 넣거나, getWriter() 를 활용하면 사용자에 적합한 에러메세지를 반환할 수 있다.
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null;

        //{
        //    "timestamp": "2022-07-15T11:38:04.689+00:00",
        //    "status": 400,
        //    "error": "Bad Request",
        //    "exception": "java.lang.IllegalArgumentException",
        //    "path": "/api/members/bad"
        //}
    }
}
