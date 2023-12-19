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

//	public static void main(String[] args) {
//		stripword("(maida)");
//	}

}
