package com.moss.poke.scraper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class AllPokeFromSite extends GetPage {

    private Set<String> allPokemon = new HashSet<>();

    public AllPokeFromSite(String searchUrl) {
        super(searchUrl);
        scrapeHtml();
    }

    private void scrapeHtml() {
        Elements tables = super.page.select("h3 ~ table");
        for (Element table: tables) {
            Elements allPokemon = table.select("table tbody tr td a");
            for (Element poke: allPokemon) {
                if (poke.attr("title").contains("(Pokémon)")) {
                    this.allPokemon.add(poke.text());
                }
            }
        }
        // Node table = header.previousSibling();
        // System.out.println(table.toString());
        // List<HtmlElement> headers = (List<HtmlElement>) super.page.getByXPath("//span[@class='mw-headline']");
        // List<HtmlElement> tables = (List<HtmlElement>) super.page.getByXPath("//table[preceding::h3[1]]");

        // for (int i = 0; i < headers.size(); i++) {
        //     if (!headers.get(i).getId().contains("Generation")) {
        //         headers.remove(i);
        //     }
        // }

        // for (Element t: tables) {
        //     System.out.println(t.className());
        // }

        // if(headers.isEmpty())
        //     System.out.println("No items found !");
        // else {
        //     for(HtmlElement header : headers)
        //         System.out.println(header.getId());
        // }

        // while(headers.size() < tables.size()) {
        //     tables.remove(tables.size()-1);
        // }

        // if(tables.isEmpty())
        //     System.out.println("No items found !");
        // else{
        //     for(HtmlElement table : tables){
        //         List<HtmlTableRow> rows = (List<HtmlTableRow>) table.getByXPath(".//tbody/tr");
        //         for(HtmlTableRow row : rows) {
        //             String[] splitRow = row.asText().split("\t");
        //             for(int i = 0; i < splitRow.length; i++) {
        //                 if (i == 3 && !splitRow[i].contains("Pokémon")) {
        //                     String poke = splitRow[i];
        //                     this.allPokemon.add(poke.substring(0, poke.length() - 1));
        //                 }
        //             }
        //         }
        //         // HtmlTableDataCell dataCell = table.getFirstByXPath(".//tbody/tr/td[3]");
        //         // System.out.println(dataCell.asText());
        //         // HtmlAnchor rowPokeName = ((HtmlAnchor) table.getFirstByXPath(".//tbody/tr/td[2]"));
        //         // System.out.println(rowPokeName.asText());
        //     }
        // }
    }

    public List<String> getAllPokemon() {
        return new ArrayList<>(this.allPokemon);
    }
}