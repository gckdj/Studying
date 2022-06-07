package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 탐색할 패키지의 시작위치 지정
        basePackages = "hello.core.member",
        // 탐색할 클래스 시작위치 지정 ※ 지정하지 않을경우 @ComponentScan 이 붙은 설정정보 클래스의 패키지가 시작위치가 된다.
        // 프로젝트의 최상단에 두는 것이 관례
        basePackageClasses = AutoAppConfig.class,
        // 예제에서 사용하던 Configuration 제외
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
    // 가급적이면 수동 등록은 자제
    // 스프링 자체의 기본세팅 사용 권장
//    @Bean
//    MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//    }
}
