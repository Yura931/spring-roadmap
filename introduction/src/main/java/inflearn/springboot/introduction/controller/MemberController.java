package inflearn.springboot.introduction.controller;

import inflearn.springboot.introduction.domain.Member;
import inflearn.springboot.introduction.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
        /*
            스프링 컨테이너가 AOP가 적용되면 Proxy 가짜를!
            memberService = class inflearn.springboot.introduction.service.MemberService$$EnhancerBySpringCGLIB$$3f929c38
            EnhancerBySpringCGLIB
            CGLibrary 멤버 서비스를 복제해서 코드를 조작하는 기술
        */
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
