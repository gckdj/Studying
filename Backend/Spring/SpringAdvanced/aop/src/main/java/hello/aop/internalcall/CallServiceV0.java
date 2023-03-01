package hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    public void external() {
        log.info("call external");
        internal(); // 내부 메서드 호출(this.internal()) 자바에서는 대상이 없는 메서드를 호출하면 this의 메서드를 호출
    }

    public void internal() {
        log.info("call internal");
    }
}
