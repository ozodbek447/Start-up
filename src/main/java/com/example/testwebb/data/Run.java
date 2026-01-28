package com.example.testwebb.data;

import com.example.testwebb.MyTelegramBot;
import com.example.testwebb.entity.Users;
import com.example.testwebb.server.Server;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Run  {

    private final DataBase dataBase;

    @Scheduled(cron = "0 0 1 * * *") //
    public void refreshUserFile() {

        if (dataBase.users.isEmpty()) {
            for (Users users : Server.load()) dataBase.users.add(users);
            return;
        }

        Server.save(new ArrayList<>());
        Server.save(dataBase.users);


    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
}
