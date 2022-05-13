package hello.servlet.basic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HelloData {

    private String username;
    private int age;

    // 롬복 사용시 필요 X
    //    public int getAge() {
    //        return age;
    //    }
    //
    //    public void setAge(int age) {
    //        this.age = age;
    //    }
    //
    //    public String getUsername() {
    //        return username;
    //    }
    //
    //    public void setUsername(String username) {
    //        this.username = username;
    //    }
}