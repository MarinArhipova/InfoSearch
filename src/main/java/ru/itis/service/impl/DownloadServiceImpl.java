package ru.itis.service.impl;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import ru.itis.service.DownloadService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class DownloadServiceImpl implements DownloadService {

    public static int count = 0;
    private File fileIndexTxt = new File("index.txt");

    @Override
    public void downloadPage(String url) {
        try {
            count++;
            Document doc = Jsoup.connect(url)
                    .userAgent("Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();
            File file = new File("downloads/downloadedHtml-" + count + ".html");
            FileUtils.writeStringToFile(file, doc.outerHtml());

            FileUtils.writeStringToFile(fileIndexTxt, count + ". " + url + "/n");
        } catch (MalformedURLException e) {
            System.out.println("BAD LINK!");
        } catch (IOException e) {
            System.out.println("BAD SOMETHING!");
        }
    }

    @Override
    public void cleanTags(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            File file = new File("downloads/htmlWithoutTags" + count + ".html");
            FileUtils.writeStringToFile(file, document.text().replaceAll("\\<.*?\\>", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
