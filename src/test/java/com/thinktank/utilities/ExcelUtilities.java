package com.thinktank.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtilities {

	private static final String RECIPE_DATA_EXPECTED_OUTPUT_TO_LOOK_LIKE = "Recipe-Data (Expected output fo";
	static String FILE_PATH = System.getProperty("user.dir")
			+ "/src/test/resources/TestData/IngredientsAndComorbidities-ScrapperHackathon.xlsx";
	static final String DIABETES_HYPOTHYROIDISM_HYPERTE = "Diabetes-Hypothyroidism-Hyperte";
	static final String FoodCategory = "FoodCategory";

	static String FILE_PATH_WRITE = System.getProperty("user.dir")
			+ "/src/test/resources/TestData/Recipe-filters-ScrapperHackathon.xlsx";

//	public static void write(List<String> output, String sheetname) throws Exception {
//		XSSFWorkbook workbook = new XSSFWorkbook();
//		XSSFSheet worksheet = workbook.createSheet("Sheet 1");
//		int rowNum = 0;
//		for (int i = 0; i <= 10; i++) {
//			Row row = worksheet.createRow(rowNum++);
//			int colNum = 0;
//
//			for (int j = 0; j <= 10; j++) {
//				// Column col=worksheet.createcol
//				// Cell cell = row.createCell(colNum++);
//				// cell.setCellValue("Row " + i + "Column ");
//			}
//		}
//
//		// writing data Fileoutput stream
//		// reading datab Fileinput stream
//		String path = System.getProperty("user.dir")
//				+ "/src/test/resources/TestData/Recipe-filters-ScrapperHackathon.xlsx";
//		File Excelfile = new File(path);
//		FileOutputStream Fos = null;
//
//		try {
//			Fos = new FileOutputStream(Excelfile);
//			workbook.write(Fos);
//			workbook.close();
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//
//			Fos.close();
//		}
//
//	}
//
	public static List<String> readExcelSheet(int coln, String sheetname) throws IOException {
		String path = System.getProperty("user.dir")
				+ "/src/test/resources/TestData/IngredientsAndComorbidities-ScrapperHackathon.xlsx";
		File Excelfile = new File(path);
		FileInputStream Fis = new FileInputStream(Excelfile);

		XSSFWorkbook workbook = new XSSFWorkbook(Fis);
		XSSFSheet sheet = workbook.getSheet(sheetname);

		List<String> columndata = new ArrayList<String>();
		// Map<String,List<String>> columndata= new HashMap<String,List<String>>();

		Iterator<Row> row = sheet.rowIterator();
//				do{
//					Row currRow=row.next();
//			
//			 Iterator<Cell> cellIterator = currRow.cellIterator();
//		        while (cellIterator.hasNext()) {
//		          Cell cell = cellIterator.next();
//		        System.out.println(cell.getStringCellValue());
//		          
//		        }
//			
////			Iterator<Cell> cell=currRow.cellIterator();
////			while(cell.hasNext())
////			{
////				
////				Cell currcell=currRow.getCell(coln);
////				columndata.add(currcell.getStringCellValue());
////				System.out.println(currcell.getStringCellValue());
////			//	Cell currcell=cell.next();
////				//System.out.println(currcell.getStringCellValue());
////			}
//			System.out.println();
//		}while(row.hasNext());
//				return columndata;

		do {
			Row currRow = row.next();
			// Cell cellcategory=currRow.getCell(0);
			// Cell itemcell=currRow.getCell(2)

			Cell cell = currRow.getCell(coln);
			// currRow=row.next();
			if (cell != null) {
				cell.setBlank();
				columndata.add(cell.getStringCellValue());
				System.out.println(cell.getStringCellValue());
			}

		} while (row.hasNext());

//			for(Row row1: sheet)	{
//				
//				Cell categoryCell = row1.getCell(coln); 
//				String category = categoryCell.getStringCellValue().trim(); 
//				
//			}

		return columndata;
	}

	public static Map<String, List<String>> read(int coln, String sheetname) {
		String path = System.getProperty("user.dir")
				+ "/src/test/resources/TestData/IngredientsAndComorbidities-ScrapperHackathon.xlsx";

		Map<String, List<String>> columndata = new HashMap<String, List<String>>();

		try {
			File Excelfile = new File(path);
			FileInputStream Fis = new FileInputStream(Excelfile);

			XSSFWorkbook workbook = new XSSFWorkbook(Fis);
			XSSFSheet sheet = workbook.getSheet(sheetname);

			Row row = sheet.getRow(0);

			Cell categoryCell = row.getCell(coln);
			String category = categoryCell.getStringCellValue().trim();

			for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				row = sheet.getRow(rowIndex);
				if (row != null) {
					Cell cell = row.getCell(coln);

					if (cell != null) {
						// Get the value from the cell and add it to the map
						String item = cell.getStringCellValue().toLowerCase().trim();
						columndata.computeIfAbsent(category, k -> new ArrayList<String>()).add(item);
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle
		}
		return columndata;

	}

	public static XSSFWorkbook readExcel(String filePath) {
		try {
			File Excelfile = new File(filePath);
			FileInputStream Fis = new FileInputStream(Excelfile);
			XSSFWorkbook workbook = new XSSFWorkbook(Fis);
			return workbook;
		} catch (Exception e) {

		}
		return null;
	}

	public static void writeOutput(LinkedList<String> output) {
		Workbook workbook = readExcel(FILE_PATH_WRITE);
		Sheet sheet = workbook.getSheet(RECIPE_DATA_EXPECTED_OUTPUT_TO_LOOK_LIKE);
		int rowcount = 2;
		// Iterator<Cell> cellIterator = row.cellIterator();
		int columnCount;
		int emptyCell;
		for (String data : output) {
			
			Row row = sheet.getRow(rowcount++);
			System.out.println("Row:" + rowcount);
			//row.getCell(2).setCellValue(data);
			emptyCell = getEmptyCell(row);
			Cell cell = row.getCell(emptyCell);
			if(cell == null || cell.getCellType() == CellType.BLANK) {
				cell = row.createCell(emptyCell, CellType.STRING);
			}
			System.out.println(emptyCell);
			cell.setCellValue(data);
			System.out.println(data);
		}

		// Write the changes back to the file
		try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH_WRITE)) {
			workbook.write(outputStream);
			System.out.println("Data has been written to the existing Excel file!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private static int getEmptyCell(Row row) {
		int firstEmptyCellIndex = -1;

		for (int i = 2; i <= row.getLastCellNum(); i++) {
		    Cell cell = row.getCell(i);
		    if (cell == null || cell.getCellType() == CellType.BLANK) {
		        firstEmptyCellIndex = i; // Found an empty cell
		        break;
		    }
		}
		if(firstEmptyCellIndex == -1) {
			firstEmptyCellIndex = row.getLastCellNum();
			System.out.println("getLastCellNum -" + row.getLastCellNum());
		}
		return firstEmptyCellIndex;
	}

	public static Map<String, List<String>> readElimatelist() {
		int eliminateColumn = 0;
		Map<String, List<String>> columndata = readColumnData(eliminateColumn, DIABETES_HYPOTHYROIDISM_HYPERTE);
		return columndata;

	}

	public static Map<String, List<String>> readAddlist() {
		Map<String, List<String>> columndata = readColumnData(1, DIABETES_HYPOTHYROIDISM_HYPERTE);
		return columndata;

	}
	
	
	public static Map<String,List<String>> readCategorylist(){
		int eliminateColumn = 0;
		Map<String, List<String>> columndata = readColumnData(eliminateColumn, FoodCategory);
		return columndata;

	}

	private static Map<String, List<String>> readColumnData(int eliminateColumn, String sheetName) {
		Map<String, List<String>> columndata = new HashMap<String, List<String>>();
		try {
			XSSFWorkbook workbook = readExcel(FILE_PATH);
			XSSFSheet sheet = workbook.getSheet(sheetName);

			Row row = sheet.getRow(0);
			Iterator<Cell> cellIterator = row.cellIterator();

			do {
				Cell currcell = cellIterator.next();
				String categoryName = currcell.getStringCellValue();
				if (categoryName != null && !categoryName.isEmpty()) {
					System.out.println(currcell.getStringCellValue());
					for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
						row = sheet.getRow(rowIndex);
						if (row != null) {
							Cell cell = row.getCell(eliminateColumn);

							if (cell != null) {
								// Get the value from the cell and add it to the map
								String item = cell.getStringCellValue().toLowerCase().trim();
								columndata.computeIfAbsent(categoryName, k -> new ArrayList<String>()).add(item);
							}
						}
					}
					eliminateColumn += 2;
				}
			} while (cellIterator.hasNext());

		} catch (Exception e) {
			// TODO: handle
		}
		return columndata;
	}

//	public static void main(String[] args) {
//		// readElimatelist();
//		
//		
//		 //writeOutput(list);
//	}

}
