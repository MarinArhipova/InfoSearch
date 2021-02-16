package ru.itis.service;

import ru.itis.model.Word;

import java.util.List;

public interface WordService {

    List<String> getWordsList(String url);
    List<Word> getStatistics(String url);
    List<String> lemmatization(String url);

}
