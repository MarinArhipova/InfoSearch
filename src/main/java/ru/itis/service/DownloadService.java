package ru.itis.service;

public interface DownloadService {

    void downloadPage(String url);
    void cleanTags(String url);

}
