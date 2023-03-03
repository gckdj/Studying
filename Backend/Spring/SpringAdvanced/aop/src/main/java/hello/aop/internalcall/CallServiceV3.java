package hello.aop.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CallServiceV3 {

    // 대안3: 별도 서비스 분리(내부호출 방지)
    private final InternalService internalService;

    public void external() {
        log.info("call external");
        internalService.internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
