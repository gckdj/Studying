package hello.itemservice.validation;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ObjectError;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
//        for (String messageCode : messageCodes) {
//            System.out.println("messageCodes = " + messageCode);
//        }
        //result:
        //messageCodes = required.item
        //messageCodes = required
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        // for (String messageCode : messageCodes) {
        //     System.out.println("messageCode = " + messageCode);
        // }
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );

        // rejectValue 내에서 codesResolver 호출

        // bindingResult.rejectValue("itemName", "required");
        // new FieldError("item", "itemName", null, false, MessageCodes, null, null);

        // messageCode = required.item.itemName
        // messageCode = required.itemName
        // messageCode = required.java.lang.String -> 타입
        // messageCode = required -> 제일 넓은 범위의 오류메시지

        // 가장 자세한 프로퍼티 -> 덜 자세한 프로퍼티
        // 위와 같은 메세지코드가 프로퍼티에 정의되어 있으면 가져와서 보여준다.
    }
}
