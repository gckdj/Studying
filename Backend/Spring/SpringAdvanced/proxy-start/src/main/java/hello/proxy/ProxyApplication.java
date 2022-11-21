package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

// @Import(AppV1Config.class)
@Import({AppV1Config.class, AppV2Config.class})
// 컴포넌트스캔 범위 설정 -> app 패키지 이하
// -> 서로 다른 방식의 클래스가 담긴 config 패키지가 스캔대상이 되어서는 안됨(학습과정 고려)
@SpringBootApplication(scanBasePackages = "hello.proxy.app")
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
