package com.moss.poke.scraper;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PokeFromSite extends GetPage {

    private String pokemon;
    private Element pokeTable;
    private Element pokeEvoTable;

    public PokeFromSite(String pokemon) throws Exception {
        super("https://bulbapedia.bulbagarden.net/wiki/" + URLEncoder.encode((pokemon.replace(" ", "_")), "UTF-8") + "_(Pok%C3%A9mon)");
        this.pokemon = pokemon;

        getBaseComponents();
    }

    private void getBaseComponents() {
        // System.out.println(page.getByXPath("//span[@class='mw-headline']"));
        Element mainSection = super.page.select("html body div#globalWrapper div#column-content div#content div#outercontentbox div#bodyContent div#mw-content-text").first();
        // Element pokeTable = mainSection.child(1);
        this.pokeTable = mainSection.children().select("table.roundy").first();

        this.pokeEvoTable = null;
        Elements evoHeader = super.page.select("span#Evolution");

        if (!evoHeader.isEmpty())
            this.pokeEvoTable = evoHeader.first().parent().nextElementSiblings().select("table").first();

        // TODO:
        // Remove array index from xpaths and replace with classes, ids and others
        // Break down the 2 FromSite classes with 'interfaces'
    }

    public Set<String> getNextEvo() {
        if (this.pokeEvoTable ==  null)
            return null;
        Elements evoRows = pokeEvoTable.select("tbody").first().children();

        if (evoRows.first().children().size() == 1)
            return null;

        // System.out.println("numb of cols: " + evoRows.size());
        List<List<Element>> evoLinesCols = new ArrayList<>(evoRows.size());

        //Init list
        for (int i = 0; i < evoRows.size(); i++) {
            List<Element> al = new ArrayList<>();
            evoLinesCols.add(al);
        }

        for (int i = 1; i < evoRows.size() + 1; i++) {
            Element row = evoRows.get(i-1);
            List<Element> mainList = evoLinesCols.get(i-1);
            for (int k = 0; k < row.children().size(); k++) {
                Element col = row.children().get(k);
                String s = col.attr("rowspan");
                if (s != "" && evoRows.size() != 1) {
                    Integer rowSpanNumb = Integer.parseInt(s);
                    for (int j = i+1; j < rowSpanNumb+1; j++) {
                        List<Element> curEvoColList = evoLinesCols.get(j-1);
                        for (int l = k; l < row.children().size() && isTablePoke(row.children().get(l)); l++) {
                            // System.out.println(row.children().get(l).text());
                            if (isTablePoke(row.children().get(l))) {
                                curEvoColList.add(row.children().get(l));
                            }
                        }
                    }
                }
                if (isTablePoke(col))
                    mainList.add(col);
            }
        }

        Set<String> nextPokeEvo = new HashSet<>();

        for (List<Element> al : evoLinesCols) {
            for (int i = 0; i < al.size(); i++) {
                Element e = al.get(i);
                if (e.select("tbody tr").last().select("span").first().ownText().equals(this.pokemon) && i+1 < al.size())
                    nextPokeEvo.add(al.get(i+1).select("tbody tr").last().select("span").first().ownText());
            }
        }

        return nextPokeEvo;
    }

    //Should be given a 'td' html element
    private Boolean isTablePoke(Element e) {
        if (e.children().first().tagName().equals("table"))
            return true;
        return false;
    }

    // public String getNextEvo() {
    //     if (this.pokeEvoTable ==  null)
    //         return null;
    //     Elements evoCol = pokeEvoTable.select("tbody tr td table");
    //     // System.out.print(evoCol.size());
    //     // System.out.print(" ");

    //     List<String> allEvos = new ArrayList<>();

    //     for (Element e : evoCol) {
    //         allEvos.add(e.select("tbody tr").last().select("span").first().ownText());
    //     }

    //     // System.out.println(allEvos.indexOf(this.pokemon));
    //     if (allEvos.indexOf(this.pokemon)+1 <= allEvos.size()-1)
    //         return allEvos.get(allEvos.indexOf(this.pokemon)+1);
    //     return null;
    // }

    public String getPokemon() {
        return this.pokemon;
    }

    public Integer getDexNumb() {
        Element dexNumbHeader = this.pokeTable.select("a[title*=List of Pokémon by National Pokédex number]").first();
        String dexNumb = dexNumbHeader.select("span").first().ownText();

        String dexNumbString = dexNumb.replaceAll("\\D+","");

        if (dexNumbString.length() != 0)
            return Integer.parseInt(dexNumbString);

        return null;
    }

    public Integer getBaseFriendship() {
        Element baseFriendshipHeader = this.pokeTable.select("a[title*=List of Pokémon by base friendship]").first();
        String baseFriendship = baseFriendshipHeader.parent().parent().select("table tr td").first().ownText();

        if (!baseFriendship.equals("Unknown"))
            return Integer.parseInt(baseFriendship);
        return null;
    }

    public String getDexColor() {
        Element dexColorHeader = this.pokeTable.select("a[title*=List of Pokémon by color]").first();
        String dexColor = dexColorHeader.parent().parent().select("table tr td").first().ownText();

        return dexColor;
    }

    public List<Integer> getEVYield() {
        Element evYieldHeader = this.pokeTable.select("a[title*=List of Pokémon by effort value yield]").first();
        Elements evYieldSet = evYieldHeader.parent().parent().select("table tr").get(2).select("td");

        List<Integer> evYield = new ArrayList<>(6);
        // System.out.println(evYieldSet.text());

        try {
            for (Element e : evYieldSet) {
                if (!e.ownText().isEmpty())
                    evYield.add(Integer.parseInt(e.ownText()));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        if (evYield.isEmpty())
            return null;
        return evYield;
    }

    public String getLevelingRate() {
        Element levelingRateHeader = this.pokeTable.select("a[title*=Experience]").get(1);
        Element levelingRate = levelingRateHeader.parent().parent().select("table tr td").first();

        return levelingRate.ownText();
	}

	public List<Integer> getBaseExpYield() {
        Element baseExpYieldHeader = this.pokeTable.select("a[title*=Experience]").first();
        Elements baseExpYieldSet = baseExpYieldHeader.parent().parent().select("table tr td");

        List<Integer> baseExpYields = new ArrayList<>();

        for (Element e : baseExpYieldSet) {
            if (!e.ownText().equals("Unknown"))
                baseExpYields.add(Integer.parseInt(e.ownText()));
        }

        return baseExpYields;
    }

    public Double getWeight() {
        Element weightHeader = this.pokeTable.select("a[title*=Weight]").first();
        Elements weightSet = weightHeader.parent().parent().select("table tr td");

        for (Element e : weightSet) {
            if (e.text().contains("kg")) {
                return Double.parseDouble(e.text().replaceAll("[^0-9\\.]",""));
            }
        }

        return null;
    }

    public Double getHeight() {
        Element heightHeader = this.pokeTable.select("a[title*=List of Pokémon by height]").first();
        Elements heightSet = heightHeader.parent().parent().select("table tr td");

        for (Element e : heightSet) {
            if (e.text().contains("m")) {
                return Double.parseDouble(e.text().replaceAll("[^0-9\\.]",""));
            }
        }

        return null;
    }

    public Integer getHatchTime() {
        Element hatchTimeHeader = this.pokeTable.select("a[title*=Egg cycle]").first();
        Elements hatchTimeSet = hatchTimeHeader.parent().parent().select("table tr td");

        String[] splitHatchTimes = hatchTimeSet.text().split(" - ");

        if (splitHatchTimes.length > 1)
            return Integer.parseInt(hatchTimeSet.text().split(" - ")[1].replaceAll("\\D+",""));
        return null;
    }

    public List<String> getEggGroup() {
        Element eggGroupsHeader = this.pokeTable.select("a[title*=Egg Group]").first();
        Elements eggGroupsSet = eggGroupsHeader.parent().parent().select("table tr td a[title*=(Egg Group)] span");

        List<String> eggGroups = new ArrayList<>();
        for (Element e : eggGroupsSet) {
            eggGroups.add(e.text());
        }

        return eggGroups;
    }

    public Integer getCatchRate() {
        Element catchRateHeader = this.pokeTable.select("a[title*=Catch rate]").first();
        Elements catchRateSet = catchRateHeader.parent().parent().select("table tr td");

        String catchRate = catchRateSet.text().split(" ")[0];

        if (!catchRate.equals("Unknown"))
            return Integer.parseInt(catchRate);
        return null;
    }

    public Double getGenderRatio() {
        Element genderRatioHeader = this.pokeTable.select("a[title*=List of Pokémon by gender ratio]").first();
        Elements genderRatioSet = genderRatioHeader.parent().parent().select("table tr span");

        for (Element e : genderRatioSet) {
            if (e.text().contains("%")) {
                if (e.text().contains("female")) {
                    return 100.0 - Double.parseDouble(e.text().replaceAll("[^0-9\\.]",""));
                }
            }
        }
        return null;
    }

    public List<List<String>> getAbilities() {
        Elements abilitySets = this.pokeTable.select("tbody tr td.roundy table.roundy tbody tr a[href*=(Ability)]");

        List<String> abilities = new ArrayList<>();
        List<String> hiddenAbilities = new ArrayList<>();

        for (Element e : abilitySets) {
            if (!e.text().equals("Cacophony")) {
                String subText = e.parent().select("small").text();
                if (subText.length() == 0 || subText.equals(this.pokemon)) {
                    abilities.add(e.text());
                } else if (subText.equals("Hidden Ability")) {
                    hiddenAbilities.add(e.text());
                }
            }
        }

        List<List<String>> allAbilities = new ArrayList<>();
        allAbilities.add(abilities);
        allAbilities.add(hiddenAbilities);

        return allAbilities;
    }


    public List<String> getTypes() {
        Element typeHeader = this.pokeTable.select("a[title=Type]").first();
        Element typeTable = typeHeader.parent().parent().select("table tbody tr td").get(0);
        // System.out.println(typeTable);
        Elements typeSet = typeTable.select("b");
        
        List<String> types = new ArrayList<>();

        for (Element type : typeSet) {
            String t = type.ownText();
            // System.out.println("-"+t+"-");
            if (!t.equals("Unknown"))
                types.add(t);
        }

        return types;
    }
}