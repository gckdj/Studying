package hello.exception.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// http400
// reason에 들어갈 내용은 코드화해서 관리가능.
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")//"잘못된 요청 오류")
public class BadRequestException extends RuntimeException {


}
