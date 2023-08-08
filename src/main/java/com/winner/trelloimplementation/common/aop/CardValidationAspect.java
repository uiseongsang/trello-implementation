package com.winner.trelloimplementation.common.aop;

import com.winner.trelloimplementation.card.entity.Card;
import com.winner.trelloimplementation.card.service.CardService;
import com.winner.trelloimplementation.common.security.UserDetailsImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CardValidationAspect {
    private final CardService cardService;

    public CardValidationAspect(CardService cardService) {
        this.cardService = cardService;
    }
    @Pointcut("execution(public * com.winner.trelloimplementation.card.controller.CardController.deleteCard(..)) || " +
            "execution(public * com.winner.trelloimplementation.card.controller.CardController.updateDescription(..)) || " + "" +
            "execution(public * com.winner.trelloimplementation.card.controller.CardController.updateDeadline(..))")
    public void cardMethods() {
    }

    @Before("cardMethods() && args(cardNo, user, ..)")
    public void validateCardOwner(UserDetailsImpl user, Long cardNo) {
        Card card = cardService.findCard(cardNo);

        if (!card.getUser().getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("카드를 작성한 회원이 아닙니다.");
        }
    }
}
