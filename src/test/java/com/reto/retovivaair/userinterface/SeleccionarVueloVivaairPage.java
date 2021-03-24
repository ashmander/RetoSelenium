package com.reto.retovivaair.userinterface;

import com.reto.retoselenium.interactions.JavaScriptClick;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SeleccionarVueloVivaairPage extends PageObject {

    private Integer vueloMasBarato;

    public Map<String, Integer> obtenerPreciosVuelos() {
        List<WebElement> vuelos = new WebDriverWait(SerenityWebdriverManager.inThisTestThread().getCurrentDriver(), 20)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='flights']/div")));
        Map<String, Integer> precios = new HashMap<>();
        for(WebElement vuelo : vuelos) {
            String id = vuelo.getAttribute("id");
            WebElement precioElement = vuelo.findElement(By.xpath("//div[@id='"+id+"']//div[@class='segment-lowest-price']/div[@class='from-price']"));
            String precio = precioElement.getText().split(" ")[1];
            Integer precioConvertido = Integer.parseInt(precio.split(",")[0] + precio.split(",")[1]);
            precios.put(id, precioConvertido);
        }
        return precios;
    }

    public String obtenerMenorPrecio(Map<String, Integer> precios) {
        Map<String, Integer> preciosMenorAMayor = precios.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        vueloMasBarato = preciosMenorAMayor.entrySet().stream().findFirst().get().getValue();
        return preciosMenorAMayor.entrySet().stream().findFirst().get().getKey();
    }

    public void seleccionarVueloMasBaratoYContinuar(String id) {
        WebElement vueloMasBarato = SerenityWebdriverManager.inThisTestThread().getCurrentDriver().findElement(By.id(id));
        vueloMasBarato.click();
        WebElement detalleVueloMasBarato = new WebDriverWait(SerenityWebdriverManager.inThisTestThread().getCurrentDriver(), 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='viva']:not([class*='super']):not([class*='max']) button")));
        JavaScriptClick.jsClick(detalleVueloMasBarato);
        WebElement botonSiguiente = new WebDriverWait(SerenityWebdriverManager.inThisTestThread().getCurrentDriver(), 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='next-button'] button")));
        JavaScriptClick.jsClick(botonSiguiente);
    }

    public Integer getPrecioVuelo() {
        return vueloMasBarato;
    }
}
