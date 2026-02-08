package com.example.testwebb.server;

import com.example.testwebb.MyTelegramBot;
import com.example.testwebb.data.DataBase;
import com.example.testwebb.entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService {

    private final DataBase dataBase;

    public Users getUserId(Long userId) {

        Optional<Users> first = dataBase.users.stream().filter(users -> users.getId().equals(userId)).findFirst();

        return first.orElse(null);

    }
    public int getUser(Users user) {

        List<Users> list = dataBase.users.stream().sorted(Comparator.comparingInt(Users::getScore)).toList();

        return list.indexOf(user)+1;
    }

    public String toString(Users user) {
        return "Ism:  "+user.getName()+"\n" +
                "Toplagan ball: "+user.getScore()+"\n" +
                "Darajasi :"+getUser(user)+"\n";
    }
}
