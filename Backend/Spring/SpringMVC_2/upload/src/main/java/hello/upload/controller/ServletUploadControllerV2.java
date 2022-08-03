package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV2(HttpServletRequest request) throws ServletException, IOException {
        log.info("request = {}", request);

        String itemName = request.getParameter("itemName");
        log.info("itemName = {}", itemName);

        Collection<Part> parts = request.getParts();
        log.info("parts = {}", parts);

        for (Part part : parts) {
            log.info("==== PART ====");
            log.info("name = {}", part.getName());
            Collection<String> headerNames = part.getHeaderNames();
            for (String headerName : headerNames) {
                log.info("header {} : {}", headerName, part.getHeader(headerName));
            }
            // 편의메서드
            // content-disposition; filename
            log.info("submittedFilename={}", part.getSubmittedFileName());
            log.info("size = {}", part.getSize()); // part body size

            // 데이터 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            // log.info("body = {}", body);

            // 파일에 저장하기
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 fullPath = {}", fullPath);

                // 서블릿 3.0부터 지원하는 기능
                part.write(fullPath);
            }
            //2022-08-03 20:32:38.079  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : request = org.springframework.web.multipart.support.StandardMultipartHttpServletRequest@770660f9
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : itemName = 3333
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : parts = [org.apache.catalina.core.ApplicationPart@69189d6e, org.apache.catalina.core.ApplicationPart@5997b8aa]
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : ==== PART ====
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : name = itemName
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : header content-disposition : form-data; name="itemName"
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : submittedFilename=null
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : size = 4
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : ==== PART ====
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : name = file
            //2022-08-03 20:32:38.080  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : header content-disposition : form-data; name="file"; filename="rufous-gefdcd0391_1920.jpg"
            //2022-08-03 20:32:38.081  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : header content-type : image/jpeg
            //2022-08-03 20:32:38.081  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : submittedFilename=rufous-gefdcd0391_1920.jpg
            //2022-08-03 20:32:38.081  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : size = 310860
            //2022-08-03 20:32:38.093  INFO 26404 --- [nio-8080-exec-1] h.u.c.ServletUploadControllerV2          : 파일 저장 fullPath = C:/data/rufous-gefdcd0391_1920.jpg
        }
        return "upload-form";

        //------WebKitFormBoundaryOv0ajKIB8qAAFk8k
        //Content-Disposition: form-data; name="itemName"
        //
        //dfddfdf
        //------WebKitFormBoundaryOv0ajKIB8qAAFk8k
        //Content-Disposition: form-data; name="file"; filename="lighthouse-g7015c8809_1920.jpg"
        //Content-Type: image/jpeg
    }
}
