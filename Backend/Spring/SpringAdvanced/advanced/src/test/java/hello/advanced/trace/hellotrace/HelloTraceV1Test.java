package hello.advanced.trace.hellotrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

public class HelloTraceV1Test {

    @Test
    void begin_end() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());

        //22:40:17.924 [main] INFO hello.advanced.trace.hellotrace.HelloTraceV1 - [593cf88f] hello
        //22:40:17.926 [main] INFO hello.advanced.trace.hellotrace.HelloTraceV1 - [593cf88f] hello time=4ms ex=java.lang.IllegalStateException
    }
}
