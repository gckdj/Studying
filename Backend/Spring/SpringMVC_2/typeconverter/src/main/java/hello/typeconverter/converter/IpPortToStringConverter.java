package hello.typeconverter.converter;

import hello.typeconverter.type.ipPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class IpPortToStringConverter implements Converter<ipPort, String> {

    @Override
    public String convert(ipPort source) {
        log.info("convert source = {}", source);
        return source.getId() + ":" + source.getPort();
    }
}
