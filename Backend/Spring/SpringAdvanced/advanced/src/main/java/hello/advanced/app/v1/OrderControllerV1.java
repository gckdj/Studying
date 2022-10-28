package hello.advanced.app.v1;

import hello.advanced.trace.TraceStatus;
import hello.advanced.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {

    private final OrderServiceV1 orderService;
    private final HelloTraceV1 trace;

    @GetMapping("/v1/request")
    public String request(String itemId) {

        TraceStatus status = null;

        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
            trace.exception(status, e);
            // 에외를 꼭 다시 던져주기, 로그처리문제로 예외 먹어버리면 예외 안뜸.
            throw e;

            //2022-10-28 19:26:14.475  INFO 2114 --- [nio-8080-exec-6] h.a.trace.hellotrace.HelloTraceV1        : [cdbe0c3f] OrderController.request()
            //2022-10-28 19:26:14.475  INFO 2114 --- [nio-8080-exec-6] h.a.trace.hellotrace.HelloTraceV1        : [cdbe0c3f] OrderController.request() time=0ms ex=java.lang.IllegalStateException: 예외발생!
            //2022-10-28 19:26:14.482 ERROR 2114 --- [nio-8080-exec-6] o.a.c.c.C.[.[.[/].[dispatcherServlet]    : Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is java.lang.IllegalStateException: 예외발생!] with root cause
        }
    }
}
