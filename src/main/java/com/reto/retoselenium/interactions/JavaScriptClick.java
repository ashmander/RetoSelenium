package com.reto.retoselenium.interactions;

import net.thucydides.core.webdriver.SerenityWebdriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavaScriptClick {

    public static void jsClick(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) SerenityWebdriverManager.inThisTestThread().getCurrentDriver();
        executor.executeScript("arguments[0].click();", element);
    }
}
