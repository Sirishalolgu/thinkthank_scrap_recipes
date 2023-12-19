package com.thinktank.utilities;

import java.util.ArrayList;
import java.util.List;

public class Allergy {
	
	public static List<Integer> getAllergy(List<String> ingredientlist) {
		String[] allergycheck = new String[]{"Milk","Soy","Egg","Sesame","Peanuts","walnut","almond","hazelnut",
				"pecan","cashew","pistachio","Shell fish","Seafood"};
		
		List<Integer> rowsToUpdate = new ArrayList<>();
		
		for (int i= 0; i< allergycheck.length;i++) {
			String ingredient = allergycheck[i];
			if (ingredientlist.contains(ingredient)) {
				continue;
			}else {
				rowsToUpdate.add(i);
			}
		}
		return rowsToUpdate;
	}
	
	

}
