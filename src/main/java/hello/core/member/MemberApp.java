package hello.core.member;

public class MemberApp {

    // psvm : 자동으로 public static void main 생성
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        // ctrl + alt + v : 자동으로 Member member 생성
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("find member = " + findMember.getName());
    }
}
