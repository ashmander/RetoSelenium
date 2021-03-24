package com.reto.retovivaair.userinterface;

import com.reto.retoselenium.interactions.JavaScriptClick;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class QuitarExtrasYCompararPrecioVivaairPage extends PageObject {

    public void noSeleccionarAsiento() {
        WebElement element = new WebDriverWait(SerenityWebdriverManager.inThisTestThread().getCurrentDriver(), 30)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-v-48db7256][data-v-54f6a7bc] button[style*='border: none']")));
        element.click();
        WebElement confirmarNoSeleccionDeAsiento = SerenityWebdriverManager.inThisTestThread().getCurrentDriver().findElement(By.cssSelector("button[class='vue-dialog-button']"));
        JavaScriptClick.jsClick(confirmarNoSeleccionDeAsiento);
    }

    public void noSeleccionarEquipaje() {
        WebElement element = new WebDriverWait(SerenityWebdriverManager.inThisTestThread().getCurrentDriver(), 30)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='bag-service-link'] button[data-v-48db7256]")));
        element.click();
    }

    public void quitarExtras() {
        List<WebElement> extras = SerenityWebdriverManager.inThisTestThread().getCurrentDriver().findElements(By.cssSelector("div[class*='service-toggle-switch']"));
        for(WebElement extra : extras) {
            extra.click();
        }
    }

    public void noComprarSeguro() {
            WebElement checkbox = SerenityWebdriverManager.inThisTestThread().getCurrentDriver().findElement(By.cssSelector("div[class='chubb-single-offer'] label span.box"));
            JavaScriptClick.jsClick(checkbox);
    }

    public Integer obtenerPrecioVueloFinal() {
        WebElement icono = SerenityWebdriverManager.inThisTestThread().getCurrentDriver().findElement(By.id("basket-icon"));
        icono.click();
        WebElement precioElemento = SerenityWebdriverManager.inThisTestThread().getCurrentDriver().findElement(By.cssSelector("div.price"));
        String precio = precioElemento.getText().split(" ")[1];
        Integer price = Integer.parseInt(precio.split(",")[0] + precio.split(",")[1]);
        return price;
    }

    public void compararPrecios(Integer precioInicial) {
        Assert.assertEquals(precioInicial, obtenerPrecioVueloFinal());
    }
}
