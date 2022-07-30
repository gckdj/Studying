package hello.typeconverter.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;

// 127.0.0.1:8080
@Getter
@EqualsAndHashCode
public class ipPort {

    private String id;
    private int port;

    public ipPort(String id, int port) {
        this.id = id;
        this.port = port;
    }
}
