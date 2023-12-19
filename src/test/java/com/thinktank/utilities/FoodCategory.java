package com.thinktank.ingredientsandcomorbidities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

public class FoodCategory {
	

	public static List<String> categorizeTheRecipe(Map<String, List<String>> categoryEliminateList,
			List<String> ingredidentList) {
		List<String> targettedCategory = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : categoryEliminateList.entrySet()) {
			List<String> categoryeliminatelist = entry.getValue();
			if (CollectionUtils.containsAny(ingredidentList, categoryeliminatelist)) {
				System.out.println("ingredients part of category eliminated list");
			} else {
				targettedCategory.add(entry.getKey());
			}
		}
		return targettedCategory;
	}
	
}
