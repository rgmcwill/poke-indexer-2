package com.moss.poke.scraper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PokeFromSite extends GetPage {

    String pokemon;

    public PokeFromSite(String searchUrl, String pokemon) {
        super(searchUrl);
        this.pokemon = pokemon;
        scrapeHtml();
    }

    private void scrapeHtml() {
        // System.out.println(page.getByXPath("//span[@class='mw-headline']"));
        Element mainSection = super.page.select("html body div#globalWrapper div#column-content div#content div#outercontentbox div#bodyContent div#mw-content-text").first();
        Element pokeTable = mainSection.child(1);

        System.out.println(getTypes(pokeTable));
        System.out.println(getAbilities(pokeTable));
        System.out.println(getGenderRatio(pokeTable));
        System.out.println(getCatchRate(pokeTable));
        System.out.println(getEggGroup(pokeTable));
        System.out.println(getHatchTime(pokeTable));
        System.out.println(getHeight(pokeTable));
        System.out.println(getWeight(pokeTable));
        System.out.println(getBaseExpYield(pokeTable));
        System.out.println(getLevelingRate(pokeTable));
        // System.out.println(getEVYield(pokeTable));
        // System.out.println(getDexColor(pokeTable));
        // System.out.println(getBaseFriendship(pokeTable));

        // TODO:
        // Remove array index from xpaths and replace with classes, ids and others
        // Break down the 2 FromSite classes with 'interfaces'
    }

    private Object getBaseFriendship(Element table) {
        return null;
    }

    private Object getDexColor(Element table) {
        return null;
    }

    private Object getEVYield(Element table) {
        return null;
    }

    private String getLevelingRate(Element table) {
        Element levelingRateHeader = table.select("a[title*=Experience]").first();
        Element levelingRate = levelingRateHeader.parent().parent().select("table tr td").first();

        return levelingRate.ownText();
	}

	private List<Integer> getBaseExpYield(Element table) {
        Element baseExpYieldHeader = table.select("a[title*=Experience]").first();
        Elements baseExpYieldSet = baseExpYieldHeader.parent().parent().select("table tr td");

        List<Integer> baseExpYields = new ArrayList<>();

        for (Element e : baseExpYieldSet) {
            if (!e.ownText().equals("Unknown"))
                baseExpYields.add(Integer.parseInt(e.ownText()));
        }

        return baseExpYields;
    }

    private Double getWeight(Element table) {
        Element weightHeader = table.select("a[title*=Weight]").first();
        Elements weightSet = weightHeader.parent().parent().select("table tr td");

        for (Element e : weightSet) {
            if (e.text().contains("kg")) {
                return Double.parseDouble(e.text().replaceAll("[^0-9\\.]",""));
            }
        }

        return null;
    }

    private Double getHeight(Element table) {
        Element heightHeader = table.select("a[title*=List of Pokémon by height]").first();
        Elements heightSet = heightHeader.parent().parent().select("table tr td");

        for (Element e : heightSet) {
            if (e.text().contains("m")) {
                return Double.parseDouble(e.text().replaceAll("[^0-9\\.]",""));
            }
        }

        return null;
    }

    private Integer getHatchTime(Element table) {
        Element hatchTimeHeader = table.select("a[title*=Egg cycle]").first();
        Elements hatchTimeSet = hatchTimeHeader.parent().parent().select("table tr td");

        return Integer.parseInt(hatchTimeSet.text().split(" - ")[1].replaceAll("\\D+",""));
    }

    private List<String> getEggGroup(Element table) {
        Element eggGroupsHeader = table.select("a[title*=Egg Group]").first();
        Elements eggGroupsSet = eggGroupsHeader.parent().parent().select("table tr td a[title*=(Egg Group)] span");

        List<String> eggGroups = new ArrayList<>();
        for (Element e : eggGroupsSet) {
            eggGroups.add(e.text());
        }

        return eggGroups;
    }

    private Integer getCatchRate(Element table) {
        Element catchRateHeader = table.select("a[title*=Catch rate]").first();
        Elements catchRateSet = catchRateHeader.parent().parent().select("table tr td");

        return Integer.parseInt(catchRateSet.text().split(" ")[0]);
    }

    private Double getGenderRatio(Element table) {
        Element genderRatioHeader = table.select("a[title*=List of Pokémon by gender ratio]").first();
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

    private List<List<String>> getAbilities(Element table) {
        Elements abilitySets = table.select("tbody tr td.roundy table.roundy tbody tr a[href*=(Ability)]");

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


    private List<String> getTypes(Element table) {
        Elements typeSets = table.select("tbody tr td.roundy table.roundy tbody tr tr");

        List<String> types = new ArrayList<>();
        Elements firstTypeSet = typeSets.first().select("td");

        for (Element type : firstTypeSet) {
            String t = type.text();
            // System.out.println("-"+t+"-");
            if (!t.equals("Unknown"))
                types.add(t);
        }

        return types;
    }
}