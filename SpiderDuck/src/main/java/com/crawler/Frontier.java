package com.crawler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

public class Frontier {
    private Queue<Url> urlQueue;
    private int count;
    private HashMap<String, Queue<Url>> urlMap;

    public Frontier() {
        urlQueue = new LinkedList<Url>();
        count = 0;
    }

    public void readSeed(String seedPath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(seedPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Url url;
                try {
                    url = new Url(line);
                } catch (URISyntaxException e) {
                    continue;
                }
                addurl(url);
            }
        } catch (IOException e) {
            return;
        }
    }

    public void addurl(Url url) {
        urlQueue.offer(url);
        count++;
    }

    public Url getNexturl() {
        if (count > 0)
            count--;
        return urlQueue.poll();
    }
  
    public boolean isEmpty() {
        return count == 0;
    }

}

