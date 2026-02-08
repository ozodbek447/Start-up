package com.example.testwebb.data;

import com.example.testwebb.entity.Users;
import com.example.testwebb.entity.Word;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataBase {

    public List<Users> users=new ArrayList<>();

    public Map<Long,List<Word>> userWords = new HashMap<>();
}
