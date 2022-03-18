package com.qa.hs.keyword.engine;

import com.qa.hs.keyword.base.Base;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openxml4j.exceptions.InvalidFormatException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KeyWordEngine {

    public WebDriver driver;
    public Properties prop;
    public Base basePage;
    public  WebElement element;


   // public KeyWordElementActions keyWordEleActions;
    public static Workbook book;
    public static Sheet sheet;
    public static ThreadLocal<Workbook> testBook = new ThreadLocal<Workbook>();
    public static ThreadLocal<Sheet> testSheet = new ThreadLocal<Sheet>();

    public final String TESTDATA_SHEET_PATH = "C:\\Users\\63not\\IdeaProjects\\KeyDrivenFramework\\src\\main\\java\\com\\qa\\hs\\keyword\\scenarios\\hubspot_scenarios.xlsx";

    public void startExecution(String sheetName) {

        By locator;
        String locatorValue = null;
        String locatorName = null;
        FileInputStream file = null;
        try {
            file = new FileInputStream(TESTDATA_SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
       try {
           book = WorkbookFactory.create(file);
       }catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e){
           e.printStackTrace();
       }catch (IOException e){
           e.printStackTrace();
       }
        sheet = book.getSheet(sheetName);
        testSheet.set(sheet);
        int k = 0;
        for (int i = 0; i < testSheet.get().getLastRowNum(); i++) {

            try {
                String locatorColValue = testSheet.get().getRow(i + 1).getCell(k + 1).toString().trim();
                if (!locatorColValue.equalsIgnoreCase("NA")) {
                    locatorName = locatorColValue.split("=")[0].trim();
                    locatorValue = locatorColValue.split("=")[1].trim();
                }
                String action = testSheet.get().getRow(i + 1).getCell(k + 2).toString().trim();
                String value = testSheet.get().getRow(i + 1).getCell(k + 3).toString().trim();

                switch (action) {

                    case "open browser":
                        basePage = new Base();
                        prop = basePage .init_Properties();
                        if (value.isEmpty() || value.equals("NA")) {
                            driver = basePage.init_driver(prop.getProperty("browser"));
                        } else {
                            driver = basePage.init_driver(value);
                        }
                        driver= basePage.init_driver(value);
                        break;

                    case "enter url":
                        if (value.isEmpty() || value.equals("NA")) {
                           driver.get(prop.getProperty("url"));
                        } else {
                            driver.get(value);
                        }
                        break;
                    case "quit":
                        driver.quit();
                        break;
                    default:
                        System.out.println("No Action is defined");
                        break;
                }

                switch (locatorName) {
                    case "id":
                         element = driver.findElement(By.id(locatorValue));
                        if (action.equalsIgnoreCase("sendkeys")) {
                           element.clear();
                            element.sendKeys( value);
                        } else if (action.equalsIgnoreCase("click")) {
                            element.click();
                        }
                        locatorName = null;
                        break;
                    case "linkText":
                        element = driver.findElement(By.linkText(locatorValue));
                       element.click();
                        locatorName = null;
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
            }
        }

    }


}
