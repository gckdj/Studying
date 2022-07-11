package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "javax.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "javax.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("error-page 500");
        printErrorInfo(request);
        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION = {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE = {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE = {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI = {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME = {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE = {}", request.getAttribute(ERROR_STATUS_CODE));

        log.info("dispatcherType = {}", request.getDispatcherType());

        //http://localhost:8080/error-ex
        //2022-07-11 20:17:39.520  INFO 5920 --- [nio-8080-exec-1] h.exception.servlet.ErrorPageController  : ERROR_EXCEPTION_TYPE = class java.lang.RuntimeException
        //2022-07-11 20:17:39.520  INFO 5920 --- [nio-8080-exec-1] h.exception.servlet.ErrorPageController  : ERROR_MESSAGE = Request processing failed; nested exception is java.lang.RuntimeException: 예외 발생!
        //2022-07-11 20:17:39.520  INFO 5920 --- [nio-8080-exec-1] h.exception.servlet.ErrorPageController  : ERROR_REQUEST_URI = /error-ex
        //2022-07-11 20:17:39.520  INFO 5920 --- [nio-8080-exec-1] h.exception.servlet.ErrorPageController  : ERROR_SERVLET_NAME = dispatcherServlet
        //2022-07-11 20:17:39.520  INFO 5920 --- [nio-8080-exec-1] h.exception.servlet.ErrorPageController  : ERROR_STATUS_CODE = 500
        //2022-07-11 20:17:39.520  INFO 5920 --- [nio-8080-exec-1] h.exception.servlet.ErrorPageController  : dispatcherType = ERROR

        //http://localhost:8080/error-404
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : errorPage 404
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : ERROR_EXCEPTION = null
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : ERROR_EXCEPTION_TYPE = null
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : ERROR_MESSAGE = 404 오류!
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : ERROR_REQUEST_URI = /error-404
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : ERROR_SERVLET_NAME = dispatcherServlet
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : ERROR_STATUS_CODE = 404
        //2022-07-11 20:18:04.471  INFO 5920 --- [nio-8080-exec-2] h.exception.servlet.ErrorPageController  : dispatcherType = ERROR
    }
}
