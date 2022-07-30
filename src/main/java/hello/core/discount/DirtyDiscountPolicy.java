package hello.core.discount;

import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
public class DirtyDiscountPolicy implements DiscountPolicy {

    @Override
    public int discount(Member member, int price) {
        return 0;
    }
}
