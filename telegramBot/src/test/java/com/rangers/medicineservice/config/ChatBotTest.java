package com.rangers.medicineservice.config;

import com.rangers.medicineservice.mapper.ScheduleMapper;
import com.rangers.medicineservice.service.impl.ScheduleServiceImpl;
import com.rangers.medicineservice.service.impl.UserServiceImpl;
import com.rangers.medicineservice.utils.RegistrationUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChatBotTest {

    @Mock
    private AbsSender absSender;

    @Mock
    private BotConfig config;

    @InjectMocks
    private ChatBot chatBot;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getBotUsernameTest() {
        String expectedBotName = "TestBot";
        when(config.getBotName()).thenReturn(expectedBotName);

        String actualBotName = chatBot.getBotUsername();
        assertEquals(expectedBotName, actualBotName);
    }

    @Test
    public void testOnUpdateReceivedWithTextMessage() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(update.hasMessage()).thenReturn(true);
        when(update.getMessage()).thenReturn(message);
        when(message.hasText()).thenReturn(true);
        when(message.getChatId()).thenReturn(123456L);
        when(message.getText()).thenReturn("/start");

        chatBot.onUpdateReceived(update);

        verify(update, times(1)).hasMessage();
        verify(update, times(3)).getMessage();
        verify(message, times(1)).hasText();
    }
    @Test
    public void testOnUpdateReceivedWithCallbackQuery() throws TelegramApiException {
        Update update = mock(Update.class);
        CallbackQuery callbackQuery = mock(CallbackQuery.class);
        Message message = mock(Message.class);

        when(update.hasCallbackQuery()).thenReturn(true);
        when(update.getCallbackQuery()).thenReturn(callbackQuery);
        when(callbackQuery.getMessage()).thenReturn(message);
        when(callbackQuery.getData()).thenReturn(null); // Testing null callback data

        doAnswer(invocation -> null).when(absSender).execute(any(SendMessage.class));

        chatBot.onUpdateReceived(update);

        verify(update, times(1)).hasCallbackQuery();
        verify(update, times(2)).getCallbackQuery();
        verify(callbackQuery, times(1)).getData();
    }
}