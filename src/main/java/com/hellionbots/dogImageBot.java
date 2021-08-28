package com.hellionbots;

import com.hellionbots.Services.serviceManager;
import com.hellionbots.configuration.cfg;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class dogImageBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        String cmd = update.getMessage().getText();
        GetChatMember getChatMember = new GetChatMember("@HellionBotSupport", update.getMessage().getFrom().getId());
        GetChatMember getChatMember2 = new GetChatMember("@HellionBots", update.getMessage().getFrom().getId());
        try {
            ChatMember c = execute(getChatMember);
            ChatMember c2 = execute(getChatMember2);
            if (!c.getStatus().equals("left") && !c2.getStatus().equals("left")) {
                if (cmd.startsWith(handler())) {
                    if (update.getMessage().getText().equalsIgnoreCase(handler() + "start")) {
                        sendMessage(update, "Hello, " + update.getMessage().getFrom().getFirstName()
                                + "\n\nI can Help you getting Random Dog Images\nType /dimage to get one Dog Image"
                                + "\n\nJoin @HellionBots for more Updates & Bot's like me"
                                + "\nJoin @HellionBotSupport for any Help!");
                    }
                    if (update.getMessage().getText().equalsIgnoreCase(handler() + "dimage")) {
                        String imageURL = new serviceManager().getImageURL();
                        String[] arr = imageURL.split("/");
                        SendPhoto photo = new SendPhoto(update.getMessage().getChatId().toString(),
                                new InputFile(imageURL));
                        photo.setCaption("Breed : " + arr[4]);
                        try {
                            execute(photo);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                sendMessage(update, "Join @HellionBots\nJoin @HellionBotSupport\n\nIn order to use me :)");
            }
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }

    public Message sendMessage(Update update, String text) {
        Message m;

        try {
            SendMessage message = new SendMessage(update.getMessage().getChatId().toString(), text);
            // message.enableMarkdownV2(true);
            m = execute(message);
            return m;
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public String handler() {
        return cfg.handler;
    }

    @Override
    public String getBotUsername() {
        return cfg.botUsername;
    }

    @Override
    public String getBotToken() {
        return cfg.botToken;
    }

}
