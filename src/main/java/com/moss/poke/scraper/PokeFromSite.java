package com.moss.poke.scraper;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class PokeFromSite {

    private HtmlPage page = null;

    public PokeFromSite(String searchUrl) {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            this.page = client.getPage(searchUrl);
            client.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        scrapeHtml();
    }

    private void scrapeHtml() {
        // System.out.println(page.getByXPath("//span[@class='mw-headline']"));
        List<HtmlElement> pokeTable = (List<HtmlElement>)this.page.getByXPath("/html/body/div[1]/div[1]/div[1]/div[5]/div[1]/div[1]/div[4]/table[2]");

        System.out.println(pokeTable);

        // TODO:
        // Remove array index from xpaths and replace with classes, ids and others
        // Break down the 2 FromSite classes with 'extends' and 'interfaces'
    }
}