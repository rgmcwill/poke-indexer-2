package com.moss.poke.scraper;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.Node;
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
        //No evoTable due to not existing (Usally because of its a new pokemon)
        if (this.pokeEvoTable ==  null)
            return null;
        
        //Gets the rows from the table
        Elements evoRows = pokeEvoTable.select("tbody").first().children();

        //If the pokemon has no evolutions
        if (evoRows.first().children().size() == 1 && evoRows.size() == 1)
            return null;

        Element maxRow = getMaxRow(evoRows);
        int maxRowIndex = evoRows.indexOf(maxRow);

        //Will transpose table if the max col is not first
        if (maxRowIndex != 0)
            evoRows = transposeTable(evoRows);

        //A list of lists which will each contian each bit of table data
        List<List<Element>> finalList = new ArrayList<>();

        //Inits finalList
        for (int i = 0; i < evoRows.size(); i++) {
            // System.out.println("Initing Arrays " + i);
            finalList.add(new ArrayList<Element>());
        }

        //First pass for first row to span the rowspan across the lists
        Element firstRow = evoRows.first();
        for (Element tableData : firstRow.children()) {
            //Gets the numb of rowspan
            String rowspanString = tableData.attr("rowspan");
            Integer rowspanValue = null;
            if (rowspanString != "")
                rowspanValue = Integer.parseInt(rowspanString);
            //checks if the rowspan attr has a value or number in it.
            if (rowspanValue != null && (rowspanValue > 1 && rowspanValue <= evoRows.size())) {
                for (int finalListIndex = 0; finalListIndex < rowspanValue; finalListIndex++) {
                    List<Element> currEvoLine = finalList.get(finalListIndex);
                    currEvoLine.add(tableData);
                }
            } else {
                List<Element> firstEvoLine = finalList.get(0);
                firstEvoLine.add(tableData);
            }
        }

        //Adds the remaining rows to the finalList
        for (int i = 1 ; i < evoRows.size(); i++) {
            Element row = evoRows.get(i);
            for (Element tableData : row.children()) {
                List<Element> listAtRowIndex = finalList.get(i);
                listAtRowIndex.add(tableData);
            }
        }

        for (List<Element> e : finalList) {
            for (Element f : e) {
                if (isTablePoke(f)) {
                    System.out.print(getNameFromtd(f) + " | ");
                } else {
                    getEvoRequirments(f);
                }
            }
            System.out.println("");
        }

        // System.out.println(evoRows.html());

        return null;
    }

    //Get pokemon name from tableData
    private String getNameFromtd(Element td) {
        return td.select("tbody tr").last().select("span").first().ownText();
    }

    //Gets Evo requirments from tableData in a ArrayList [Level, Item Name, Descritption]
    private ArrayList<String> getEvoRequirments(Element td) {
        ArrayList<String> finalList = new ArrayList<>();

        // System.out.print(" [ ");

        //Get Level if applicable
        Elements level = td.select("a[title*=Level]");
        if (level.size() > 0) {
            finalList.add(0, level.first().text());
            // System.out.print(level.first().text() + " | ");
        } else if (td.ownText().contains("Level")) {
            Pattern p = Pattern.compile("(?=((Level )([0-9]{1,2})))");
            Matcher matcher = p.matcher(td.ownText());
            matcher.find();
            String test = matcher.group(1);
            // System.out.print(test + " | ");
        }

        // HashMap<String, Integer> counting = new HashMap<>();

        //Get items if applicable
        //This code currently gets

        /*[Ice Stone, Luck Incense, Razor Fang, Shiny Stone, Reaper Cloth, N-Solarizer, Odd Incense, Magmarizer,
        TM Fairy, Upgrade, Thunder Stone, Pure Incense, Trade, Metal Coat, Water Stone, Statistic, Poké Ball,
        Oval Stone, Whipped Dream, Leaf Stone, Electirizer, Fire Stone, Full Incense, Dusk Stone, Affection,
        Deep Sea Scale, Tart Apple, Rock Incense, Lax Incense, Moon Stone, Dragon Scale, Razor Claw, Galar,
        Dawn Stone, Wave Incense, King's Rock, Cracked Pot, Sea Incense, Rose Incense, Sun Stone, Deep Sea Tooth,
        Sweet Apple, Protector, Alola]
        */
        // for (Element e : td.children()) {
        //     if (e.attr("title") != "") {
        //         String s = e.attr("title");
        //         if (counting.get(s) != null) {
        //             Integer i = counting.get(s);
        //             counting.put(s, i+1);
        //         } else {
        //             counting.put(s, 1);
        //         }
        //     }
        // }

        // Map.Entry<String, Integer> maxEntry = null;
        // for (Map.Entry<String, Integer> e : counting.entrySet()) {
        //     if (maxEntry == null || e.getValue().compareTo(maxEntry.getValue()) > 0) {
        //         maxEntry = e;
        //     }
        // }

        // Map<String, Integer> allMax = new HashMap<>();
        // for (Map.Entry<String, Integer> e : counting.entrySet()) {
        //     if (e.getValue() == maxEntry.getValue() && e.getValue() >= 2) {
        //         allMax.put(e.getKey(), e.getValue());
        //     }
        // }

        // if (maxEntry.getValue() >= 2) {
        //     // return new ArrayList<>(allMax.keySet());
        //     System.out.print(allMax.keySet() + " | ");
        // }

        //Trying to seperate the tableData with by the arrows and the hr tag
        Elements currSec = new Elements();
        // System.out.print("Size: " + td.children().size() + " ");
        for (Node child : td.childNodes()) {
            // String childText = child.text();

            System.out.print(child.toString() + " | ");

            // if (child.tagName().equals("hr")) {
            //     System.out.print("Found ----hr---- | ");
            // } else if (childText.contains("→") || childText.contains("↓")) {
            //     System.out.print("Found -> | ");
            // } else if (childText.contains("←")) {
            //     System.out.print("Found <- | ");
            // } else {
            //     System.out.print("Found Nothing | ");
            //     currSec.add(child);
            // }
        }

        //Get Other/description if applicable


        // System.out.print(" ] ");
        // return finalList;
        return null;
    }

    //Will transpose a tbodys
    private Elements transposeTable(Elements tableRows) {
        int numbOfRows = getMaxRow(tableRows).children().size();
        Elements newRows = new Elements();

        for (int newRowIndex = 0; newRowIndex < numbOfRows; newRowIndex++) {
            Element aRow = new Element("tr");
            newRows.add(aRow);
        }

        //For Eevee there are now 8 tr instead of 3 tr
        for (Element row : tableRows) { //goes 3 time for the inital amount of rows
            for (int i = 0; i < row.children().size(); i++) { //goes 1 once then 8 then 8
                Element temp = newRows.get(i);
                Element tableData = row.children().get(i).clone();
                if (tableData.attr("colspan") != "") {
                    tableData.attr("rowspan", tableData.attr("colspan"));
                    tableData.removeAttr("colspan");
                }
                temp.appendChild(tableData);
            }
        }

        // newRows.html(newRows.html().replaceAll("↓", "→"));

        return newRows;
    }

    //Get the Row with the most col or children
    private Element getMaxRow(Elements tableRows) {
        Element maxRow = tableRows.first();
        for (Element row : tableRows) {
            if (row.children().size() > maxRow.children().size()) {
                maxRow = row;
            }
        }
        return maxRow;
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