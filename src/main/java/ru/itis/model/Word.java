package ru.itis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Word {

    private Long id;
    private String word;
    private int count;

    public Word(String word, int count) {
        this.word = word;
        this.count = count;
    }
}
