package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV2 {

    // 스프링 컨테이너 직접 사용방법
    // private final ApplicationContext applicationContext;

    // public CallServiceV2(ApplicationContext applicationContext) {
    //        this.applicationContext = applicationContext;
    //    }

    // 스프링 지연로딩 활용
    // ApplicationContext 과다한 기능제공
    // ObjectProvider 빈 생성시점이 아닌, 실제 객체 사용지점으로 지연
    // 실제 객체가 아닌 프록시 객체가 주입되기 때문에 순환참조가 발생되지 않는다.
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public void external() {
        log.info("call external");
        // CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
