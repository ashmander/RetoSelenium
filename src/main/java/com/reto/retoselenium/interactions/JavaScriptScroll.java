package com.reto.retoselenium.interactions;

import net.thucydides.core.webdriver.SerenityWebdriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JavaScriptScroll {

    public static void jsScroll() {
        JavascriptExecutor executor = (JavascriptExecutor) SerenityWebdriverManager.inThisTestThread().getCurrentDriver();
        executor.executeScript("window.scrollBy(0, 500)");
    }
}
