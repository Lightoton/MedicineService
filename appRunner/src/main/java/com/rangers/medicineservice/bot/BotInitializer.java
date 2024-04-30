package com.rangers.medicineservice.bot;

import com.rangers.medicineservice.config.ChatBot;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotInitializer {
    final ChatBot chatBot;

    public BotInitializer(ChatBot chatBot) {
        this.chatBot = chatBot;
    }

    @EventListener({ApplicationReadyEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            telegramBotsApi.registerBot(chatBot);
        } catch (TelegramApiException e) {
            throw new RuntimeException();
        }
    }

}
