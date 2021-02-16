package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itis.model.Word;
import ru.itis.service.DownloadService;
import ru.itis.service.WordService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/word")
public class WordController {

    private final WordService wordService;
    private final DownloadService downloadService;

    @GetMapping("/all")
    public String getWordsPage() {
        return "words";
    }

    @GetMapping("/statistics")
    public String getStatisticsPage() {
        return "statistics";
    }

    @GetMapping("/lemmatization")
    public String getLemmatizationPage() {
        return "words";
    }

    @PostMapping("/all")
    public String getAllWords(@RequestParam String url, Model model) {
        url = cleanUrl(url);
        downloadService.downloadPage(url);
        downloadService.cleanTags(url);
        List<String> words = wordService.getWordsList(url);
        model.addAttribute("words", words);
        model.addAttribute("url",url);
        return "words";
    }

    @PostMapping("/statistics")
    public String getStatistics(@RequestParam String url, Model model) {
        url = cleanUrl(url);
        downloadService.downloadPage(url);
        downloadService.cleanTags(url);
        List<Word> words = wordService.getStatistics(url);
        model.addAttribute("words", words);
        model.addAttribute("url",url);
        return "statistics";
    }

    @PostMapping("/lemmatization")
    public String lemmatization(@RequestParam String url, Model model) {
        url = cleanUrl(url);
        downloadService.downloadPage(url);
        downloadService.cleanTags(url);
        List<String> words = wordService.getWordsList(url);
        model.addAttribute("words", words);
        model.addAttribute("url",url);
        return "words";
    }

    private String cleanUrl(String url) {
        // удаляет все пробелы и невидимые символы (например, табуляцию, \n)
        return url.replaceAll("\\s+","");
    }

}

