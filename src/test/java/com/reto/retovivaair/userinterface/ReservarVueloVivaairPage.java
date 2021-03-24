package com.reto.retovivaair.userinterface;

import com.reto.retoselenium.utils.SeleccionarFecha;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@DefaultUrl("https://www.vivaair.com/co/es")
public class ReservarVueloVivaairPage extends PageObject {

    @FindBy(css = "div[class='search-type-switch'] label") private WebElement soloIda;
    @FindBy(xpath = "//div[@id='criteria-airport-select-departure']//input") private WebElement listaCiudadOrigen;
    @FindBy(xpath = "//div[@id='criteria-airport-select-destination']//input") private WebElement listaCiudadDestino;
    @FindBy(id = "criteria-search-button-desktop") private WebElement buscarVuelo;
    @FindBy(css = "div[class*='date-selector-container']") private WebElement seleccionarFecha;

    public void cerrarNotificacion() {
        SerenityWebdriverManager.inThisTestThread().getCurrentDriver().manage().window().maximize();
        try {
            WebElement cancelar = SerenityWebdriverManager.inThisTestThread().getCurrentDriver().findElement(By.id("onesignal-slidedown-cancel-button"));
            cancelar.click();
        } catch (NoSuchElementException e) {

        }
    }

    public void verificarCheckbox() {
        soloIda.click();
    }

    public void seleccionarCiudadOrigen(String ciudad) {
        listaCiudadOrigen.click();
        listaCiudadOrigen.clear();
        listaCiudadOrigen.sendKeys(ciudad);
        WebElement ciudadElegida = listaCiudadOrigen.findElement(By.xpath("//ul[@data-v-df607e20]/li[@id='criteria-airport-select-departure-station-"+ciudad+"']"));
        ciudadElegida.click();
    }

    public void seleccionarCiudadDestino(String ciudad) {
        listaCiudadDestino.click();
        listaCiudadDestino.clear();
        listaCiudadDestino.sendKeys(ciudad);
        WebElement ciudadElegida = listaCiudadDestino.findElement(By.xpath("//ul[@data-v-df607e20]/li[@id='criteria-airport-select-destination-station-"+ciudad+"']"));
        ciudadElegida.click();
    }

    public void seleccionarFechaVuelo() {
        seleccionarFecha.click();
        WebElement fechaSeleccionada = new WebDriverWait(SerenityWebdriverManager.inThisTestThread().getCurrentDriver(), 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-date='"+ SeleccionarFecha.sumarUnDia() +"']/button")));
        fechaSeleccionada.click();
    }

    public void buscarVuelos() {
        buscarVuelo.click();
    }
}
