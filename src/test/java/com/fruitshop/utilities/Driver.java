package com.fruitshop.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;

public class Driver {

    private static final InheritableThreadLocal<WebDriver> driverThreadLocal = new InheritableThreadLocal<>();

    private Driver() {}

    public synchronized static WebDriver getDriver() {

        if (driverThreadLocal.get() == null) {

            String browser = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.getProperty("browser");
            switch (browser) {
                case "chrome" :
                    WebDriverManager.chromedriver().setup();
                    driverThreadLocal.set(new ChromeDriver());
                    break;
                case "chrome-headless" :
                    WebDriverManager.chromedriver().setup();
                    driverThreadLocal.set(new ChromeDriver(new ChromeOptions().setHeadless(true)));
                    driverThreadLocal.get().manage().window().fullscreen();
                    break;
                case "firefox" :
                    WebDriverManager.firefoxdriver().setup();
                    driverThreadLocal.set(new FirefoxDriver());
                    break;
                case "firefox-headless" :
                    WebDriverManager.firefoxdriver().setup();
                    driverThreadLocal.set(new FirefoxDriver(new FirefoxOptions().setHeadless(true)));
                    driverThreadLocal.get().manage().window().fullscreen();
                    break;
                case "ie" :
                    if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    }
                    WebDriverManager.iedriver().setup();
                    driverThreadLocal.set(new InternetExplorerDriver());
                    break;
                case "edge" :
                    if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
                        throw new WebDriverException("Your OS doesn't support Edge");
                    }
                    WebDriverManager.edgedriver().setup();
                    driverThreadLocal.set(new EdgeDriver());
                    break;
                case "safari" :
                    if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
                        throw new WebDriverException("Your OS doesn't support Safari");
                    }
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driverThreadLocal.set(new SafariDriver());
                    break;
                case "remote-chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.setCapability("platform", Platform.ANY);
                    try {
                        URL url = new URL("http://localhost:5555/wd/hub");
                        driverThreadLocal.set(new RemoteWebDriver(url,chromeOptions));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
        return driverThreadLocal.get();
    }

    public synchronized static void closeDriver() {

        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
