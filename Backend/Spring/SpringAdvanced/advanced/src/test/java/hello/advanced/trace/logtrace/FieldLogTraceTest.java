package hello.advanced.trace.logtrace;

import hello.advanced.trace.TraceStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldLogTraceTest {

    FieldLogTrace trace = new FieldLogTrace();

    @Test
    void begin_end_level2() {
        TraceStatus status1 = trace.begin("Hello1");
        TraceStatus status2 = trace.begin("Hello2");
        trace.end(status2);
        trace.end(status1);
    }

    @Test
    void begin_exception_level2() {
        TraceStatus status1 = trace.begin("hello1");
        TraceStatus status2 = trace.begin("hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status1, new IllegalStateException());
    }

    //22:28:15.261 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [92f72fa5] Hello1
    //22:28:15.263 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [92f72fa5] |-->Hello2
    //22:28:15.263 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [92f72fa5] |<--Hello2 time=0ms
    //22:28:15.263 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [92f72fa5] Hello1 time=2ms
    //22:28:15.270 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [0844d06e] hello1
    //22:28:15.270 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [0844d06e] |-->hello2
    //22:28:15.270 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [0844d06e] |<X-hello2 time=0ms ex=java.lang.IllegalStateException
    //22:28:15.270 [main] INFO hello.advanced.trace.logtrace.FieldLogTrace - [0844d06e] hello1 time=0ms ex=java.lang.IllegalStateException
}