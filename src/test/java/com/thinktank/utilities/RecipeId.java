package com.thinktank.utilities;

public class RecipeId {

	public static String getRecipeID(String url) {
		
		int length=url.length()-1;
		String lastpart=url.substring(length-5, length);
		//System.out.println(lastpart);
		return lastpart;
	}
	
	
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String url="https://www.tarladalal.com/desert-pizza-with-green-and-gold-kiwifruits-35156r";
//		getRecipeID(url);
//		
//		
//
//	}
	

}
