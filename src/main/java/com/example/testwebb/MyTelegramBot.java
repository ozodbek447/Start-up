package com.example.testwebb;

import com.example.testwebb.data.DataBase;
import com.example.testwebb.entity.Users;
import com.example.testwebb.entity.Word;
import com.example.testwebb.server.Server;
import com.example.testwebb.server.UserService;
import com.example.testwebb.server.WordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MyTelegramBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final WordService wordService;
    private final DataBase dataBase;
    @Value("${telegram.bot.username}")
    String username;

    @Value("${telegram.bot.token}")
    String token;

//    private Long adminId=1817365313L;
    private Map<Long, List<Word>> usersWords = new HashMap<>();




    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()){


            Message message = update.getMessage();
            Long chatId = message.getChat().getId();
            String text = message.getText();
            Users user = userService.getUserId(chatId);

            //! yangi foydalanuvchi ruyhatdan tishi
            if (user==null){
                sendMessage("Salom bottimizga xush kelibsiz\n Hayotingizdagi yana bir tug'ri tanlovni" +
                        " amalga oshirish uchun iltimos ruyhatdan o'ting.Username kiriting ",chatId);

                Users users=new Users();
                users.setStep("ism");
                users.setId(chatId);

                dataBase.users.add(users);
                return;
            }


            //! foydalanubvchi ismini olish
            if (user.getStep().equals("ism")){
                user.setName(text);
                user.setStep("menu");
                boshlash(user.getId(), """
                       Qoyil siz ruyhatdan utishni tugatdingiz .\n 
                       Buni 50% insonlar qila olmaydi .Sizning IQ darajangiz 70 dan baland.\n
                       menu dagi battinlardan birini tanla.Eslatma malumotlar bir kunga saqlanadi
                       """);
                return;
            }
            if (user.getStep().equals("answer")){
                if (dataBase.userWords.get(chatId).get(user.getCont()).getTrueAns().equals(text)){
                    sendMessage(dataBase.userWords.get(chatId).get(user.getCont()).getText()+"-"+text+"✅",chatId);
                    user.setScore(user.getScore()+1);
                    user.setAnswers(user.getAnswers()+1);
                }else {
                    sendMessage(dataBase.userWords.get(chatId).get(user.getCont()).getText()+"-"+text+"❌",chatId);
                }
                user.setScore(user.getScore()+1);
                user.setCont(user.getCont()+1);
                user.getAnswer().add(text);
                if (user.getCont()==10){
                    if(user.getAnswers()<=3){
                        sendMessage("""
                               🤖 Savollar juda oson edi-ku…
                               😅 Hatto bot ham hayron qolmoqda
                               📊 Sen esa atigi *2 ta* to‘g‘ri topding
                               😳 Rostdan ham shunaqami?
                               Hamma kamida *5 ta* topyapti-ku
                               ━━━━━━━━━━ 🔥 Maslahat 🔥 ━━━━━━━━━━
                               📚 Savollarni diqqat bilan o‘qi
                               🧠 Shoshilmay javob ber
                               💪 Keyingi safar rekord qil!
                               😎 Bot senga ishonadi…
                               lekin hozircha ozgina mashq kerak shekilli 🙂
                               Ammo buni senga foydasi yuq chunki sen yutqazding....
                               
                               """,chatId);
                    }
                    sendMessage(userService.toString(user),chatId);
                    boshlash(chatId,"Tanla aqlli" );
                    dataBase.userWords.clear();

                }else {
                    wordBatting(dataBase.userWords.get(chatId).get(user.getCont()),chatId);

                }
                return;

            }

            if (text.equals("orqaga")){
                user.setStep("menu");
                return;
            }
            if(text.equals("Ingiliz_tili")){
                dataBase.userWords.put(chatId,wordService.getWords(wordService.words()));
                wordBatting(dataBase.userWords.get(chatId).get(user.getCont()),chatId);
                user.setStep("answer");

                return;
            }
            if(text.equals("Tarix_Savollar")){
                dataBase.userWords.put(chatId,wordService.getWords(wordService.wordTarix()));
                wordBatting(dataBase.userWords.get(chatId).get(user.getCont()),chatId);
                user.setStep("answer");

                return;
            }


            if(text.equals("AI bilan jang")){
                language(chatId,"Qaysi qurolda jang qilasan");
                return;
            }
            if (text.equals("mening malumotlarim1")){
                sendMessage(userService.toString(user),chatId);
                return;
            }
            if(user.getStep().equals("menu")){
                boshlash(chatId,"Tanla aqlli" );
                return;
            }







        }



    }
    private void  sendMessage(String text ,Long chatId){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void boshlash(Long chatId,String text) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();


        KeyboardRow row = new KeyboardRow();
        row.add("AI bilan jang");
        keyboard.add(row);

        KeyboardRow row12 = new KeyboardRow();
        row12.add("mening malumotlarim1");
        keyboard.add(row12);

        KeyboardRow row11 = new KeyboardRow();
        row11.add("orqaga1");
        keyboard.add(row11);

        replyKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void language(Long chatId,String text) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();


        KeyboardRow row = new KeyboardRow();
        row.add("Ingiliz_tili");
        keyboard.add(row);

        KeyboardRow row12 = new KeyboardRow();
        row12.add("Tarix_Savollar");
        keyboard.add(row12);

        KeyboardRow row11 = new KeyboardRow();
        row11.add("menu");
        keyboard.add(row11);

        replyKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private void wordBatting(Word word, Long chatId) {



        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        for (String answer : word.getAnswers()) {
            row.add(answer);
        }

        keyboard.add(row);

        replyKeyboardMarkup.setKeyboard(keyboard);

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("So‘z tarjimasini tanlang: \n\n👉 " + word.getText());
        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

//    public String toStringg(List<Word> words,Users users) {
//        String text = "";
//
//        text="👨‍🎓Foydalanuvchi: "+users.getName()+
//           "\n🌟Umumiy ball: "+users.getScore()+
//           "\n⚔Umumiy janglar soni: "+users.getFight()+
//           "\n📋Dataja:"+userService.getUser(users)+"\n"+"\n"+"Suzlaringiz\n\n";
//        int i=0;
//        for (Word w : words) {
//            if (users.getAnswer().get(i).equals(w.getTranslation())){
//                text=text+w.getText()+" = "+users.getAnswer().get(i)+" ✅\n";
//
//            }else {
//                text=text+w.getText()+" = "+users.getAnswer().get(i)+" ❌\n";
//            }
//            i++;
//        }
//        users.getAnswer().clear();
//        dataBase.users.remove(users);
//        dataBase.users.add(users);
//
//
//
//        return text+"\nXohlagan harfni bossang menuga tushasan";
//
//
//    }






    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
