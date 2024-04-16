package com.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class HtmlDocument {
    private Document document;

    public HtmlDocument(String url) {
        try {
            this.document = Jsoup.connect(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void download(String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(this.document.outerHtml());
        } catch (IOException e) {
            System.err.println("Error occurred while saving the document: " + e.getMessage());
        }
    }

    // public String[] extractUrls() {
    // List<String> urls = new ArrayList<>();

    // for (Element link : this.document.select("a[href]")) {
    // urls.add(link.absUrl("href"));
    // }

    // return urls.toArray(new String[0]);
    // }

    public Url[] extractUrls() {
        List<Url> urls = new ArrayList<>();

        for (Element link : this.document.select("a[href]")) {
            String absUrl = link.absUrl("href");
            Url url = new Url(absUrl);
            urls.add(url);
        }

        return urls.toArray(new Url[0]);
    }

    public String hash() {
        String htmlContent = document.html();
        byte[] hash = Hash.getSHA(htmlContent);

        return Hash.toHexString(hash);
    }
}