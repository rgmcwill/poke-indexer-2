package com.moss.poke.scraper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetPage {
    protected Document page = null;
    private String fileName = null;
    private String loc = "./cache/";
    private File file = null;

    public GetPage(String searchUrl) {
        this.fileName = Integer.toString(searchUrl.hashCode());
        this.file = new File(this.loc + this.fileName);

        try {
            if (file.exists()) {
                System.out.println("Cached");
                this.page = Jsoup.parse(this.file, "UTF-8", searchUrl);
            } else {
                System.out.println("Downloaded");
                this.page = Jsoup.connect(searchUrl).get();
                docWriter();
            }
        } catch(Exception e) {

        }
    }

    private File docWriter() {
        BufferedWriter  writer = null;
        try {
            this.file.createNewFile();
            writer = new BufferedWriter( new FileWriter(this.file.getAbsolutePath()));
            writer.write(this.page.toString());
            writer.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return this.file;
    }
}