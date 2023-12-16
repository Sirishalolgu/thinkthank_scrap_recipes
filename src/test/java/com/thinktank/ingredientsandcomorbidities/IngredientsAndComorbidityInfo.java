package com.thinktank.ingredientsandcomorbidities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinktank.utilities.ExcelUtilities;


public class IngredientsAndComorbidityInfo {

	static Map<String, List<String>> morbidityVsElimateIngridientlistInfo = new HashMap<>();
	
	static Map<String, List<String>> morbidityVsAddIngridientlistInfo = new HashMap<>();

	public static void init() {
		morbidityVsElimateIngridientlistInfo = ExcelUtilities.readElimatelist();
		morbidityVsAddIngridientlistInfo = ExcelUtilities.readAddlist();	
	}
	
	public static Map<String, List<String>> getMorbidityVsElimateIngridientlistInfo() {
		return morbidityVsElimateIngridientlistInfo;
	}

	public static Map<String, List<String>> getMorbidityVsAddIngridientlistInfo() {
		return morbidityVsAddIngridientlistInfo;
	}


}
