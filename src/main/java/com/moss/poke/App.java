package com.moss.poke;

import java.net.URLEncoder;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String pokemon = "Charizard" ;

        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        HtmlPage page = null;
        try {
            // String searchUrl = "https://bulbapedia.bulbagarden.net/wiki/" + URLEncoder.encode(searchQuery, "UTF-8") + "_(Pok%C3%A9mon)";
            String searchUrl = "https://bulbapedia.bulbagarden.net/wiki/List_of_Pok%C3%A9mon_by_National_Pok%C3%A9dex_number";
            page = client.getPage(searchUrl);
        }catch(Exception e){
            e.printStackTrace();
        }

        System.out.println(page.getByXPath("/html/body/div[2]"));

        List<HtmlElement> items = (List<HtmlElement>) page.getByXPath("/html/body/div[3]/div[2]/div/div[6]/div/div/div[4]/table[2]/tbody/tr") ;
        if(items.isEmpty()){
            System.out.println("No items found !");
        }else{
            for(HtmlElement item : items){
                HtmlAnchor rowPokeName = ((HtmlAnchor) item.getFirstByXPath(".//td[2]"));

                String pokeName = rowPokeName.asText();

                System.out.println( String.format("Pokemon : %s ", pokeName));
            }
        }
    }
}
