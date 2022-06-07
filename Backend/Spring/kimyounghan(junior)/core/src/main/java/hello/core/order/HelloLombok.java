package hello.core.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// lombok 사용 (실무에서 많이 사용함)
@Getter
@Setter
// ToString 자동생성
@ToString
public class HelloLombok {
    private String name;
    private int age;

    public static void main(String[] args) {
        HelloLombok helloLombok = new HelloLombok();
        helloLombok.setName("Lombok GetSet Test");

        String name = helloLombok.name;
        // GetterSetter 코드 설정 필요없어짐
        // 결과 : name = Lombok GetSet Test
        System.out.println("name = " + name);
    }
}
