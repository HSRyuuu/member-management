package hello.membermanagement.web;

import hello.membermanagement.domain.member.Member;
import hello.membermanagement.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/basic/members")
@RequiredArgsConstructor
public class BasicMemberController {
    private final MemberRepository memberRepository;

    /**
     * 맨 처음에 전체 멤버 목록 폼을 뿌려준다.
     * @param model
     * @return members.html 멤버 목록 폼
     */
    @GetMapping
    public String members(Model model){
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members",members);
        return "basic/members";
    }

    /**
     * members.html에서 특정 멤버의 id 또는 이름을 누르면 멤버의 상세 폼으로 이동한다.
     * uri는 /basic/members/{memberId} 이다.
     * @param memberId -> @PathVariable
     * @param model
     * @return member.html 멤버 상세 폼
     */
    @GetMapping("/{memberId}")
    public String member(@PathVariable long memberId, Model model){
        Member member = memberRepository.findById(memberId);
        model.addAttribute("member",member);
        return "basic/member";
    }

    /**
     * /basic/members에서 '멤버 등록' 버튼을 누르면 멤버 등록 폼으로 이동 ->  /basic/members/add
     * @return addForm.html 멤버 등록 폼
     */
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

    /**
     * addForm에서 정보를 입력한 후 '멤버 등록' 버튼을 누르면 Post방식으로 submit 된다.
     * @param member -> @ModelAttribute로 정보를 바탕으로 Member 객체를 생성한다.
     * @param redirectAttributes -> status=true 쿼리가 들어오면 상단의 텍스트를 변경하도록 member.html에 설정되어있다.
     * @return redirect 방식으로 /basic/members/{memberId}
     */
    @PostMapping("/add")
    public String addMember(@ModelAttribute Member member, RedirectAttributes redirectAttributes){
        Member savedMember = memberRepository.save(member);
        redirectAttributes.addAttribute("memberId",savedMember.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/members/{memberId}";
    }

    /**
     * member.html에서 '정보 수정' 버튼을 누르면 editForm을 뿌려준다.
     * @param memberId
     * @param model
     * @return editForm.html 멤버 수정 폼
     */
    @GetMapping("/{memberId}/edit")
    public String editForm(@PathVariable Long memberId, Model model){
        Member member = memberRepository.findById(memberId);
        model.addAttribute("member",member);
        return "basic/editForm";
    }

    /**
     * editForm에서 저장 버튼을 누르면 Post 방식으로 submit 된다.
     * @param memberId
     * @param member -> 수정된 멤버 정보를 받아서 member 객체로 받아온다.
     * @return redirect 방식으로 수정된 정보가 적용된 /members/{memberId} 로 돌아간다.
     */
    @PostMapping("/{memberId}/edit")
    public String edit(@PathVariable Long memberId, @ModelAttribute Member member){
        memberRepository.updateMember(memberId,member);
        return "redirect:/basic/members/{memberId}";
    }

    /**
     * members.html에서 멤버 삭제 버튼을 누르면 /basic/members/delete로 가도록 설정되어 있다.
     * @return deleteForm을 뿌려준다.
     */
    @GetMapping("/delete")
    public String deleteForm(){
        return "basic/deleteForm";
    }

    /**
     * deleteForm 에서 '멤버 삭제' 버튼을 누르면 입력한 Id를 Post형식으로 받는다.
     * memberRepository에서 삭제하고, 삭제되었다는 메시지를 위해 상단의 텍스트가 '삭제완료' 로 변경된다.
     * @param memberId
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/delete")
    public String deleteItem(Long memberId, RedirectAttributes redirectAttributes){
        memberRepository.deleteMember(memberId);
        redirectAttributes.addAttribute("status",true);
        return "redirect:/basic/members/delete";
    }


    /**
     * 초기 데이터
     */
    @PostConstruct
    public void init(){
        memberRepository.save(new Member("memberA", 20, "19981212", "01023234545","abc123@naver.com"));
        memberRepository.save(new Member("memberB", 30, "19941125", "01026734812","hello@gmail.com"));
    }

}
