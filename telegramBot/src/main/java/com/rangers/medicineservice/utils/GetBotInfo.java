package com.rangers.medicineservice.utils;
/**
 * The {@code GetBotInfo} class provides a static method to retrieve information about the MedicineBot.
 * <p>
 * This information includes an overview of the bot's features, such as scheduling appointments with doctors,
 * accessing the pharmacy, asking questions to the AI, and managing personal accounts.
 * Additionally, it provides links to the development team's LinkedIn profiles and the project curator.
 * </p>
 * <p>
 * Usage:
 * <pre>
 * {@code
 * String botInfo = GetBotInfo.getBotInfo();
 * System.out.println(botInfo);
 * }
 * </pre>
 * </p>
 *
 * <p>
 * This class is intended to be used as a utility class, so it does not require instantiation.
 * </p>
 *
 * @author Oleksii Chilibiiskyi
 */
public class GetBotInfo {
    public static String getBotInfo() {
        return """
                                *About the MedicineBot*

                                *Welcome to our Telegram bot\\!*
                                This bot is designed for your convenience and offers a wide range of healthcare services\\. Below you will find a description of all available features:

                                *Schedule an appointment with a doctor*
                                • You can schedule an appointment with any doctor at our clinic\\.\s
                                • Appointments are available both online and offline\\.
                               \s
                                *Pharmacy*\s
                                • After visiting the doctor, you will receive a prescription that can be filled at our pharmacy\\.\s
                                • You can also order the medications you need without a prescription\\.\s
                                • Function to search for the nearest pharmacies by your geolocation\\.
                               \s
                                *Ask a question to the artificial intelligence*\s
                                • The user can ask questions and get answers from our AI, which will help them make decisions about their health\\.
                               \s
                                *Personal account*\s
                                • View all your appointments with doctors and prescriptions that have been issued to you, view/change personal data\\.
                               \s
                                *Development team:*
                                [Volha](https://www.linkedin.com/in/volha-zadziarkouskaya-84a1292b7/)
                                [Maksym](https://www.linkedin.com/in/maksym-bondarenko-8a6ba0280/?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app)
                                [Oleksandr](https://www.linkedin.com/in/oleksandr-harbuz-1b9b41300)
                                [Oleksii](https://www.linkedin.com/in/oleksii-chilibiiskyi/)
                                [Viktor](https://www.linkedin.com/in/viktor-bulatov-46a54b30b/)
                               \s
                                *Project curator:*
                                [Mikhail](https://www.linkedin.com/in/mikhail-egorov-54715917b/)
                               \s
                                *We are constantly working to improve our bot and will be happy to receive your feedback and suggestions\\.*""";
    }
}
