package ru.itis.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.itis.controller.WordController;
import ru.itis.lemmatizer.StanfordLemmatizer;
import ru.itis.model.Word;
import ru.itis.service.WordService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WordServiceImpl implements WordService {

    private static final Logger logger = LoggerFactory.getLogger(WordController.class);

    @Override
    public List<String> getWordsList(String url) {
        List<String> words = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("downloads/htmlWithoutTags" + DownloadServiceImpl.count + ".html"))) {
            String inputString = bufferedReader.readLine();
            words = Arrays.asList(inputString.split("[' ', ',', '.', '!', '?','\"', ';', ':', '[', ']', '(', ')', '\\n', '\\r', '\\t']"));
            words.remove(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("Url: {}", url);
        return words;
    }

    @Override
    public List<Word> getStatistics(String url) {
        List<String> words = getWordsList(url);
        HashMap<String, Integer> wordMap = countWords(words);
        List<Word> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            result.add(new Word(entry.getKey(), entry.getValue()));
//            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        logger.info("Counted words for url {}", url);
        return result;
    }

    // Лемматизация — это процесс преобразования слова в его базовую форму
    @Override
    public List<String> lemmatization(String url) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("downloads/htmlWithoutTags" + DownloadServiceImpl.count + ".html"))) {
            builder.append(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        StanfordLemmatizer stanfordLemmatizer = new StanfordLemmatizer();
        stanfordLemmatizer.lemmatize(builder.toString());
        return null;
    }

    private HashMap<String, Integer> countWords(List<String> words) {
        HashMap<String, Integer> wordMap = new HashMap<>();
        toUpperCase(words);
        for (String word : words) {
            if (word.matches("^[A-Z,a-z,а-я,А-Я]+$")) {
                wordMap.put(word, Collections.frequency(words, word));
            }
        }
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        return wordMap;
    }

    private void toUpperCase(List<String> words) {
        for (int i = 0; i < words.size(); i++) {
            words.set(i, words.get(i).toUpperCase());
        }
    }

}
