package com.thinktank.ingredientsandcomorbidities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thinktank.utilities.ExcelUtilities;
import com.thinktank.utilities.Filter;
import com.thinktank.utilities.RecipeId;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Diabetes {
        static WebDriver driver;
	public static void main(String[] args) throws IOException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.tarladalal.com/");
		WebElement aTozRecipes=driver.findElement(By.xpath("//a[contains(@href,'RecipeAtoZ.aspx')]"));
		aTozRecipes.click();
		IngredientsAndComorbidityInfo.init();
		Map<String, List<String>> elimatelist = IngredientsAndComorbidityInfo.getMorbidityVsElimateIngridientlistInfo();
		Document doc;
		doc = Jsoup.connect("https://www.tarladalal.com/desert-pizza-with-green-and-gold-kiwifruits-35156r").get();
		List<String> ingredidentList = new ArrayList<>();
		exactIngredients(doc, ingredidentList);
		List<String> targettedMorbids = compareIngredientsWithMorbidExcludedList(elimatelist, ingredidentList);
		LinkedList<String> output = getOutputFromRecipe(driver, doc, targettedMorbids,ingredidentList);
		ExcelUtilities.writeOutput(output);

	}


	private static LinkedList<String> getOutputFromRecipe(WebDriver driver, Document doc, List<String> targettedMorbids,List<String> ingredidentList) {
		LinkedList<String> output = new LinkedList<>();
		//String url = driver.getCurrentUrl();
		String url = "https://www.tarladalal.com/desert-pizza-with-green-and-gold-kiwifruits-35156r";
		//System.out.println(url);
		
//		Recipe ID
//		Recipe Name
//		Recipe Category(Breakfast/lunch/snack/dinner)
//		Food Category(Veg/non-veg/vegan/Jain)
//		Ingredients
//		Preparation Time
//		Cooking Time
//		Preparation method
//		Nutrient values
//		Targetted morbid conditions (Diabeties/Hypertension/Hypothyroidism)
//		Recipe URL

		
		System.out.println("id:"+RecipeId.getRecipeID(url));
		output.add(RecipeId.getRecipeID(url));

		Element name = doc.getElementById("ctl00_cntrightpanel_lblrecipeNameH2");
		output.add(name.text());
		System.out.println(name.text());

		Elements category = doc.select("a[itemprop=recipeCategory]");
		if (!category.isEmpty()) {
            Element aElement = category.first();
            output.add(aElement.text());
            System.out.println("Found <a> element with name: " + aElement.text());
        } else {
            System.out.println("No <a> element with name found under the <span> with href");
        }
		
		//System.out.println(category.text());
		output.add("veg");
		System.out.println("veg");
		String result = String.join(", ", ingredidentList);
		output.add(result);
		Element preptime = doc.selectFirst("time[itemprop=prepTime]");
		System.out.println(preptime.text());
		output.add(preptime.text());
		Element cookTime = doc.selectFirst("time[itemprop=cookTime]");
		output.add(cookTime.text());
		System.out.println(cookTime.text());

		

		Elements method= doc.select("ol[itemprop=recipeInstructions]");
        List<String> recipeInstructions=new ArrayList<>();
        
		
		for (Element e : method) {
			recipeInstructions.add(e.text());
        }
		
		output.add(recipeInstructions.toString());
		
		Element nutrient = doc.getElementById("recipe_nutrients");
		if (nutrient!=null) {
            
            output.add(nutrient.text());
            System.out.println("Found <a> element with name: " + nutrient.text());
        } else {
        	output.add("Not found");
            System.out.println("nutrient null");
        }
		//output.add(nutrient.text());
		output.add(targettedMorbids.toString());
		output.add(url);
		System.out.println(output);
		return output;
	}

	
	private static List<String> compareIngredientsWithMorbidExcludedList(Map<String, List<String>> elimatelist,
			List<String> ingredidentList) {
		List<String> targettedMorbids = new ArrayList<>();
		for (Map.Entry<String, List<String>> entry : elimatelist.entrySet()) {
			List<String> eliminatedList = entry.getValue();
			if (CollectionUtils.containsAny(ingredidentList, eliminatedList)) {
				System.out.println("ingredients part of eliminated list");
			} else {
				targettedMorbids.add(entry.getKey());
			}
		}
		return targettedMorbids;
	}

	private static void exactIngredients(Document doc, List<String> ingredidentList) {
		Elements recipeIngredient = doc.select("span[itemprop=recipeIngredient]");
		for (Element e : recipeIngredient) {
			Elements ingredients = e.select("a");
			for (Element ingredientname : ingredients) {
				String newIngredient = Filter.stripword(ingredientname.text());
				ingredidentList.add(newIngredient);
				
				//driver.navigate().to("https://www.tarladalal.com/aam-chana-achar-rajasthani-pickle-3904r");
			}
		}
	}

}
