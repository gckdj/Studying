package hello.core.scan.filter;

import java.lang.annotation.*;

// Class 레벨에 대상
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
