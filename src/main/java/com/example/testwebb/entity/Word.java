package com.example.testwebb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Word {


    private int id;
    private String text;
    private String trueAns;
    private List<String> answers;

}
