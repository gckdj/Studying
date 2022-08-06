package hello.typeconverter.converter;

import hello.typeconverter.type.ipPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConverterTest {

    @Test
    void stringToInteger() {
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer result = converter.convert("10");
        // 문자열 10을 기대했으나, 숫자 10이 들어와서 에러
        assertThat(result).isEqualTo("10");
    }

    @Test
    void IntegerToString() {
        IntegerToStringConverter converter = new IntegerToStringConverter();
        String result = converter.convert(10);
        // 통과
        assertThat(result).isEqualTo("10");
    }

    @Test
    void stringToIpPort() {
        IpPortToStringConverter convert = new IpPortToStringConverter();
        ipPort source = new ipPort("127.0.0.1", 8080);
        String result = convert.convert(source);

        assertThat(result).isEqualTo("127.0.0.1:8080");
    }

    @Test
    void ipPortToString() {
        StringToIpPortConverter converter = new StringToIpPortConverter();
        String source = "127.0.0.1:8080";
        ipPort result = converter.convert(source);
        assertThat(result).isEqualTo(new ipPort("127.0.0.1", 8080));
    }

    // 현재 개발자가 직접 컨버팅하는 것과 다른 점이 없다.
    // 타입 컨버터를 등록하고 관리하면서 편리하게 변환 기능을 제공하는 역할을 하는 것이 필요하다.
}
