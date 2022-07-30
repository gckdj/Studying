package hello.typeconverter.converter;

import hello.typeconverter.type.ipPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

@Slf4j
public class StringToIpPortConverter implements Converter<String, ipPort> {

    @Override
    public ipPort convert(String source) {
        log.info("convert source = {}", source);

        String[] split = source.split(":");
        String ip = split[0];
        int port = Integer.parseInt(split[1]);

        return new ipPort(ip, port);
    }
}
