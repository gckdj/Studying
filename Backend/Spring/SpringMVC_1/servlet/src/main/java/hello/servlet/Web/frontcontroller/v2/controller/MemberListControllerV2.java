package hello.servlet.Web.frontcontroller.v2.controller;

import hello.servlet.Web.frontcontroller.MyView;
import hello.servlet.Web.frontcontroller.v2.ControllerV2;
import hello.servlet.domain.Member;
import hello.servlet.domain.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MemberListControllerV2 implements ControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);
        return new MyView("/WEB-INF/views/members.jsp");
    }
}
