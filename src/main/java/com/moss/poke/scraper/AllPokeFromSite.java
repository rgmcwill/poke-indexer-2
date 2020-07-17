package com.moss.poke.scraper;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class AllPokeFromSite {

    private HtmlPage page = null;
    private List<String> allPokemon = new ArrayList<>();

    public AllPokeFromSite(String searchUrl) {
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
        List<HtmlElement> headers = (List<HtmlElement>) this.page.getByXPath("//span[@class='mw-headline']");
        List<HtmlElement> tables = (List<HtmlElement>) this.page.getByXPath("//table[preceding::h3[1]]");

        for (int i = 0; i < headers.size(); i++) {
            if (!headers.get(i).getId().contains("Generation")) {
                headers.remove(i);
            }
        }

        if(headers.isEmpty())
            System.out.println("No items found !");
        else {
            for(HtmlElement header : headers)
                System.out.println(header.getId());
        }

        while(headers.size() < tables.size()) {
            tables.remove(tables.size()-1);
        }

        if(tables.isEmpty())
            System.out.println("No items found !");
        else{
            for(HtmlElement table : tables){
                List<HtmlTableRow> rows = (List<HtmlTableRow>) table.getByXPath(".//tbody/tr");
                for(HtmlTableRow row : rows) {
                    String[] splitRow = row.asText().split("\t");
                    for(int i = 0; i < splitRow.length; i++) {
                        if (i == 3 && !splitRow[i].contains("Pokémon")) {
                            String poke = splitRow[i];
                            this.allPokemon.add(poke.substring(0, poke.length() - 1));
                        }
                    }
                }
                // HtmlTableDataCell dataCell = table.getFirstByXPath(".//tbody/tr/td[3]");
                // System.out.println(dataCell.asText());
                // HtmlAnchor rowPokeName = ((HtmlAnchor) table.getFirstByXPath(".//tbody/tr/td[2]"));
                // System.out.println(rowPokeName.asText());
            }
        }
    }

    public List<String> getAllPokemon() {
        return this.allPokemon;
    }
}