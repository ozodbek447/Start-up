package com.example.testwebb.server;

import com.example.testwebb.data.DataBase;
import com.example.testwebb.entity.Users;
import com.example.testwebb.entity.Word;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WordService {


    private final Gson gson;

    public List<Word> getWords(List<Word> words) {
        try {
            Random random = new Random();
            List<Word> result = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                result.add(words.get(random.nextInt(words.size())));
            }
            return result;
        } catch (Exception e) {
            return Collections.emptyList();
        }


    }

    public List<Word> getWord(Word correctWord) {
        Random random = new Random();
        List<Word> allWords = words();

        Word wrongWord;
        do {
            wrongWord = allWords.get(random.nextInt(allWords.size()));
        } while (wrongWord.getId() == correctWord.getId());

        List<Word> result = new ArrayList<>();
        result.add(correctWord);
        result.add(wrongWord);

        Collections.shuffle(result); // ARALASHTIRISH 🔀
        return result;
    }

    public   List<Word> words() {
        try (InputStreamReader reader = new InputStreamReader(
                getClass().getResourceAsStream("/templates/word.json") // classpath ichidan oladi
        )) {
            Type type = new TypeToken<List<Word>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            return new ArrayList<>(); // fayl bo‘lmasa bo‘sh list
        }
    }
    public   List<Word> wordTarix() {
        try (InputStreamReader reader = new InputStreamReader(
                getClass().getResourceAsStream("/templates/tarix.json") // classpath ichidan oladi
        )) {
            Type type = new TypeToken<List<Word>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            return new ArrayList<>(); // fayl bo‘lmasa bo‘sh list
        }
    }

}
