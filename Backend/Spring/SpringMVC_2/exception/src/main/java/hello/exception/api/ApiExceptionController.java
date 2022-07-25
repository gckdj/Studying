package hello.exception.api;

import hello.exception.exception.BadRequestException;
import hello.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력값");
        }

        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }

        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
            //{
            //    "timestamp": "2022-07-14T13:16:47.261+00:00",
            //    "status": 500,
            //    "error": "Internal Server Error",
            //    "exception": "java.lang.IllegalArgumentException",
            //    "path": "/api/members/bad"
            //}
        }
        return new MemberDto(id, "hello" + id);
    }

    @GetMapping("/api/response-status-ex1")
    public String responseStatusEx1() {
        throw new BadRequestException();

        //{
        //    "timestamp": "2022-07-18T12:50:14.278+00:00",
        //    "status": 400,
        //    "error": "Bad Request",
        //    "exception": "hello.exception.exception.BadRequestException",
        //    "path": "/api/response-status-ex1"
        //}
    }

    @GetMapping("/api/response-status-ex2")
    public String responseStatusEx2() {
        // 예외처리하는 메서드에 404 넣고 예외발생
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "error.bad", new IllegalArgumentException());
        
        //{
        //    "timestamp": "2022-07-18T12:59:34.757+00:00",
        //    "status": 404,
        //    "error": "Not Found",
        //    "exception": "org.springframework.web.server.ResponseStatusException",
        //    "path": "/api/response-status-ex2"
        //}
    }

    @GetMapping("/api/default-handler-ex")
    public String defaultException(@RequestParam Integer data) {
        return "ok";
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
 }
