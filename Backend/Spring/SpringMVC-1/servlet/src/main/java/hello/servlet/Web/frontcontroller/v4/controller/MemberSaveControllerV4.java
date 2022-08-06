package hello.servlet.Web.frontcontroller.v4.controller;

import hello.servlet.Web.frontcontroller.v4.ControllerV4;
import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        // model 자체를 파라미터로 주고받고 있기 때문에 ModelView를 생성 및 반환할 소요가 줄어든다.
        model.put("member", member);
        return "save-result";
    }
}
