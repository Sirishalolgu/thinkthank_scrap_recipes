package com.thinktank.utilities;

public class Filter {
	
	public static String stripword(String ingrientname) {
		String [] wordstoRemove={"powdered","\\(",")//"};	
		for (String word:wordstoRemove) {
			if(ingrientname.contains(word)) {
				ingrientname=ingrientname.replace(word, "");
				ingrientname=ingrientname.toLowerCase().trim();
			}
		}
		
		System.out.println(ingrientname);
		return ingrientname;
		
	}
	public static void main(String[] args) {
		stripword("(maida)");
	}

}
