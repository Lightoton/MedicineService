package com.rangers.medicineservice.config;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for configuring bot properties.
 *
 * @author Viktor
 */
@Configuration
@Data
public class BotConfig {
    Dotenv dotenv = Dotenv.load();

    String botName = dotenv.get("BOT_NAME");

    String token = dotenv.get("BOT_TOKEN");
}