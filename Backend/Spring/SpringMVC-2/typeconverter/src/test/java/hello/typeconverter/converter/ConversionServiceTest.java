package hello.typeconverter.converter;

import hello.typeconverter.type.ipPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class ConversionServiceTest {

    @Test
    void conversionService() {
        // 등록
        DefaultConversionService conversionService = new DefaultConversionService();

        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 사용
        //Integer result = conversionService.convert("10", Integer.class);

        // 컨버터 서비스가 자동으로 변환하는 컨버터를 찾아서 적용시켜줌
        // System.out.println("result = " + result);

        assertThat(conversionService.convert("10", Integer.class)).isEqualTo(10);
        assertThat(conversionService.convert(10, String.class)).isEqualTo("10");

        ipPort result = conversionService.convert("127.0.0.1:8080", ipPort.class);
        assertThat(result).isEqualTo(new ipPort("127.0.0.1", 8080));

        String ipPortString = conversionService.convert(new ipPort("127.0.0.1", 8080), String.class);
        assertThat(ipPortString).isEqualTo("127.0.0.1:8080");

        // 컨버전서비스에서 컨버터를 등록해놓고 컨버전 서비스만 호출하면 한번에 모두 적용
    }
}

