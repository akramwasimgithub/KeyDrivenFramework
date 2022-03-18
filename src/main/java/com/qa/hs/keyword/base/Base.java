package com.qa.hs.keyword.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Base {
    public WebDriver driver;
    public Properties prop;

    public WebDriver init_driver(String browserName){

        if(browserName.equals("chrome")){
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\63not\\selenium\\chromedriver_win32 (1)\\chromedriver.exe");
            if(prop.getProperty("headless").equalsIgnoreCase("yes")){
                //headless mode
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--headless");
                driver = new ChromeDriver(options);
            }else{
                driver = new ChromeDriver();
            }
        }

        return driver;

    }


    public Properties init_Properties(){
        prop = new Properties();
        try {
            FileInputStream ip = new FileInputStream("C:\\Users\\63not\\IdeaProjects\\KeyDrivenFramework\\src\\main\\java\\com\\qa\\hs\\keyword\\config\\config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }




}