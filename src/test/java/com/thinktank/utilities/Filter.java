package com.thinktank.utilities;

public class Filter {

    public static String stripword(String ingredientName) {
        String[] wordsToRemove = {
            "powdered", "\\(", "\\)", "black", "grated mozzarella", "sea",
            "grated", "grated processed", "chopped", "diced mozzarella", "icing",
            "sweet and sour", "red chilli", "chilli garlic", "baby","powder"
        };

        for (String word : wordsToRemove) {
            if (ingredientName.contains(word)) {
                ingredientName = ingredientName.replace(word, "");
            }
        }

        ingredientName = ingredientName.toLowerCase().trim();
        System.out.println(ingredientName);
        return ingredientName;
    }

    public static void main(String[] args) {
        stripword("(maida)");
        stripword("powdered sugar");
        stripword("black salt");
        stripword("grated mozzarella cheese");
        stripword("sea salt");
        stripword("grated processed cheese");
        stripword("chopped spinach");
        stripword("diced mozzarella cheese");
        stripword("icing sugar");
        stripword("sweet and sour sauce");
        stripword("red chilli sauce");
        stripword("chilli garlic sauce");
        stripword("baby corn");
        stripword("coffee powder");
    }
}