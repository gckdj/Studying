package hello.upload.domain;


import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;
    // UUID 등과 같이 서로 겹치지 않을 새로운 저장명
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
