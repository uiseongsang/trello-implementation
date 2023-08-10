package com.winner.trelloimplementation.common.cron;

import com.winner.trelloimplementation.card.service.CardServiceImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {
    private final CardServiceImpl cardService;

    public ScheduledTask(CardServiceImpl cardService) {
        this.cardService = cardService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateIsDeadline() {
        Integer count = cardService.updateIsDeadline();
        System.out.println("card 마감 " + count + "개");
    }
}
