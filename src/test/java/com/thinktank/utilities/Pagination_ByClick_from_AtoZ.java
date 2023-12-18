package com.thinktank.utilities;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.thinktank.ingredientsandcomorbidities.IngredientsAndComorbidityInfo;
public class Pagination_ByClick_from_AtoZ {
	public static void main(String[] args) throws InterruptedException, IOException {

		WebDriver driver;

		driver = new ChromeDriver();
		driver.get("https://www.tarladalal.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.manage().deleteAllCookies();

		driver.findElement(By.xpath("//a[@title='Recipea A to Z']")).click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0, 700)");

		//List<String> allRecipeNames = new ArrayList<>();
		//List<String> allRecipeIds = new ArrayList<>();

	for (char k = 'A'; k <= 'Z'; k++) {
			int next_page = 1;
			String paginationSelector = "https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + k + "&pageindex="
					+ next_page;
			driver.get(paginationSelector);

			List<WebElement> pages_full_data = driver.findElements(By.xpath("//div[contains(text(),'Goto Page')]/a"));

			if (pages_full_data.isEmpty()) {
				System.out.println("No recipe found");
				continue;
			}

			WebElement element_1_XX = pages_full_data.get(pages_full_data.size() - 1);

			String pagenation_last_page_XX = element_1_XX.getText();
			int last_page_number = Integer.parseInt(pagenation_last_page_XX);
			System.out.println("Page Data: " + "for alphabet " + k + " " + pagenation_last_page_XX);
			
			for (int j = 0; j <= last_page_number - 1; j++) {
				paginationSelector = "https://www.tarladalal.com/RecipeAtoZ.aspx?beginswith=" + k + "&pageindex="
						+ next_page;
				driver.get(paginationSelector);
				List<WebElement> curr_page = driver.findElements(By.xpath("//a[@class='rescurrpg']"));
				WebElement element_page = curr_page.get(0);
				String pagenation_last_page = element_page.getText();
				int pagenation_last_page_integer = Integer.parseInt(pagenation_last_page);
				next_page = pagenation_last_page_integer + 1;
				System.out.println("Last Page: " + pagenation_last_page);

				List<WebElement> recipeNames = driver.findElements(By.xpath("//div//span[@class='rcc_recipename']"));
			//	List<WebElement> recipeId = driver.findElements(By.xpath("//div//span[contains(text(),'Recipe#')]"));

				for (int i = 0; i < recipeNames.size(); i++) {
					recipeNames = driver.findElements(By.xpath("//div//span[@class='rcc_recipename']"));
					WebElement element = recipeNames.get(i);
				
                    String catchUrl=driver.getCurrentUrl();
					element.click();
					
					js.executeScript("window.scrollBy(0, 700)");
					String currentUrl = driver.getCurrentUrl();
					IngredientsAndComorbidityInfo.init();
					Map<String, List<String>> elimatelist = IngredientsAndComorbidityInfo
							.getMorbidityVsElimateIngridientlistInfo();

					Document doc = Jsoup.connect(currentUrl).get();
					List<String> ingredidentList = new ArrayList<>();
					exactIngredients(doc, ingredidentList);
					List<String> targettedMorbids = compareIngredientsWithMorbidExcludedList(elimatelist,
							ingredidentList);
					LinkedList<String> output = getOutputFromRecipe(driver, doc, targettedMorbids, ingredidentList);
					ExcelUtilities.writeOutput(output);
		
					driver.navigate().to(catchUrl);
					//driver.navigate().back();

				}}}
			
		

		driver.quit();
	}

//**********************************************************************************************************//	
	
	
	private static LinkedList<String> getOutputFromRecipe(WebDriver driver, Document doc, List<String> targettedMorbids,
			List<String> ingredidentList) {
		LinkedList<String> output = new LinkedList<>();
		try {
		

		//String url = "https://www.tarladalal.com/desert-pizza-with-green-and-gold-kiwifruits-35156r";
		String url = driver.getCurrentUrl();
		System.out.println("id:" + RecipeId.getRecipeID(url));
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

		// System.out.println(category.text());
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

		Elements method = doc.select("ol[itemprop=recipeInstructions]");
		List<String> recipeInstructions = new ArrayList<>();

		for (Element e : method) {
			recipeInstructions.add(e.text());
		}

		output.add(recipeInstructions.toString());

		Element nutrient = doc.getElementById("recipe_nutrients");
		if (nutrient != null) {

			output.add(nutrient.text());
			System.out.println("Found <a> element with name: " + nutrient.text());
		} else {
			output.add("Not found");
			System.out.println("nutrient null");
		}
		// output.add(nutrient.text());
		output.add(targettedMorbids.toString());
		output.add(url);}
		catch(Exception e) {
			System.out.println(e);
		}
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
			}
		}
	}

}