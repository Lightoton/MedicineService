package com.rangers.medicineservice.config;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChatBot extends TelegramLongPollingBot {

    private final BotConfig config;

    public ChatBot(BotConfig config) {
        this.config = config;
    }


    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            System.out.println("chatId: " + chatId);
            String messageText = update.getMessage().getText();


            if (messageText.equals("/start")) {
                sendMenu(chatId); // Отправить меню при старте чата
            }
            else if (update.hasMessage() && update.getMessage().hasLocation()) {
                // Если пришло сообщение с местоположением
                processLocation(update);
            }else {
                // Попытаемся разделить входные данные пользователя
                String[] parts = messageText.split(" ");
                if (parts.length == 3) {
                    try {
                        double arg1 = Double.parseDouble(parts[0]);
                        double arg2 = Double.parseDouble(parts[1]);
                        String op = parts[2];

                        // Вызываем метод для обработки введенных данных
                        processInputValue(chatId, arg1, arg2, op);
                    } catch (NumberFormatException e) {
                        SendMessage errorMessage = new SendMessage(chatId, "Неверный формат ввода. Пожалуйста, введите два числа и операцию (+ или -) через пробел.");

                        try {
                            execute(errorMessage);
                        } catch (TelegramApiException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    SendMessage errorMessage = new SendMessage(chatId, "Неверное количество аргументов. Пожалуйста, введите два числа и операцию (+ или -) через пробел.");
                    try {
                        execute(errorMessage);
                    } catch (TelegramApiException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            switch(callbackData) {
                case "command1":
                    sendMsg(chatId, "Будет выполняться алгоритм записи к врачу");
                    break;
                case "command2":
                    sendMsg(chatId, "Будет выполняться алгоритм для поиска и возможной покупки лекарств в аптеке");
                    break;
                case "command3":
                    sendMsg(chatId, "Появятся 3 кнопки и одна из них активирует работу с AI чатом");
                    break;
                default:
                    break;
            }
        }
    }
    private void calcBot(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("введите два числа и операцию через пробел");
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void processInputValue(String chatId, double arg1, double arg2, String op) {
        double result = 0;
        switch (op.charAt(0)) {
            case '+' -> result = arg1 + arg2;
            case '-' -> result = arg1 - arg2;
        }
        SendMessage message = new SendMessage(chatId, "Результат = " + result);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    private void sendDataBot(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(getBotUsername());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMenu(String chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите команду:");

        InlineKeyboardMarkup markupKeyboard = new InlineKeyboardMarkup();

        // Создаем кнопки
        List<List<InlineKeyboardButton>> rowsInline = getLists();

        markupKeyboard.setKeyboard(rowsInline);
        message.setReplyMarkup(markupKeyboard);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static List<List<InlineKeyboardButton>> getLists() {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton button1 = new InlineKeyboardButton("Make an appointment with a doctor");
        InlineKeyboardButton button2 = new InlineKeyboardButton("Pharmacy");
        InlineKeyboardButton button3 = new InlineKeyboardButton("Chat");
        button1.setCallbackData("command1");
        button2.setCallbackData("command2");
        button3.setCallbackData("command3");
        rowInline.add(button1);
        rowInline.add(button2);
        rowInline.add(button3);
        rowsInline.add(rowInline);
        return rowsInline;
    }


    private void sendMsg(String chatId, String text) {
        if (text != null) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(text);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    // Пример использования метода getWeather для отправки запроса на API погоды и получения ответа
    public void sendWeatherForecast(String chatId, double latitude, double longitude) {
        String weatherData = getWeather(latitude, longitude);
        if (weatherData != null) {
            // Здесь вы можете распарсить ответ от API погоды и отправить пользователю информацию о погоде
            SendMessage message = new SendMessage(chatId, "Текущая погода: " + weatherData);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            // Обработка случая, когда не удалось получить данные о погоде
            SendMessage errorMessage = new SendMessage(chatId, "Не удалось получить данные о погоде.");
            try {
                execute(errorMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    // Код для отправки запроса на API погоды
    public String getWeather(double latitude, double longitude) {
        // Ваш API ключ для OpenWeatherMap
        String apiKey = "c4e51e97a669bbe600abd7dc64ad0920";
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;

        try {
            // Отправляем GET-запрос к API погоды
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Чтение ответа от сервера
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Возвращаем ответ от API
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Метод для обработки полученного местоположения
    private void processLocation(Update update) {
        Message message = update.getMessage();
        Location location = message.getLocation();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String chatId = String.valueOf(message.getChatId());

        // Отправляем запрос на погоду с полученными координатами
        sendWeatherForecast(chatId, latitude, longitude);
    }
}
