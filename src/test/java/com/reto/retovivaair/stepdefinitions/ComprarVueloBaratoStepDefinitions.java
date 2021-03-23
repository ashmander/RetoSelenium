package com.reto.retovivaair.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ComprarVueloBaratoStepDefinitions {

    private WebDriver driver;
    private int vueloMasBarato;

    @Given("^quiero comprar un vuelo barato$")
    public void prepararCompra() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.vivaair.com/co/es");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");
        cerrarNotificacion();
    }

    public void cerrarNotificacion() {
        try {
            WebElement cancelar = driver.findElement(By.id("onesignal-slidedown-cancel-button"));
            cancelar.click();
        } catch (NoSuchElementException e) {

        }
    }

    @When("^compre el vuelo mas barato de (.*) a (.*)$")
    public void comprarVueloMasBarato(String origen, String destino) {
        verificarCheckbox();
        seleccionarCiudad(origen,"departure");
        seleccionarCiudad(destino, "destination");
        seleccionarFechaActualMasUno();
        buscarVuelos();
        seleccionarVueloMasBaratoYContinuar(
                obtenerMenorPrecio(obtenerPreciosVuelos()));
        noSeleccionarAsiento();
        noSeleccionarEquipaje();
        quitarExtras();
        noComprarSeguro();
    }

    public void seleccionarVueloMasBaratoYContinuar(String id) {
        WebElement vueloMasBarato = driver.findElement(By.id(id));
        vueloMasBarato.click();
        WebElement detalleVueloMasBarato = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='viva']:not([class*='super']):not([class*='max']) button")));
        detalleVueloMasBarato.click();
        WebElement botonSiguiente = new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='next-button'] button")));
        botonSiguiente.click();
    }

    public String obtenerMenorPrecio(Map<String, Integer> precios) {
        Map<String, Integer> preciosMenorAMayor = precios.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        vueloMasBarato = preciosMenorAMayor.entrySet().stream().findFirst().get().getValue();
        return preciosMenorAMayor.entrySet().stream().findFirst().get().getKey();
    }

    public Map<String, Integer> obtenerPreciosVuelos() {
        List<WebElement> vuelos = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='flights']/div")));
        Map<String, Integer> precios = new HashMap<>();
        for(WebElement vuelo : vuelos) {
            String id = vuelo.getAttribute("id");
            WebElement precioElement = vuelo.findElement(By.xpath("//div[@id='"+id+"']//div[@class='segment-lowest-price']/div[@class='from-price']"));
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+precioElement.getText());
            String precio = precioElement.getText().split(" ")[1];
            Integer price = Integer.parseInt(precio.split(",")[0] + precio.split(",")[1]);
            precios.put(id, price);
        }
        return precios;
    }

    public void buscarVuelos() {
        WebElement botonBuscar = driver.findElement(By.id("criteria-search-button-desktop"));
        botonBuscar.click();
    }

    public void verificarCheckbox() {
        WebElement checkBox;
        try {
            checkBox = driver.findElement(By.cssSelector("div[class='search-type-switch'] label input[aria-checked]"));
        } catch (NoSuchElementException e) {
            checkBox = driver.findElement(By.cssSelector("div[class='search-type-switch'] label"));
            checkBox.click();
        }

    }

    public void seleccionarFechaActualMasUno() {
        Calendar calendar = Calendar.getInstance();
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        dia++;
        mes++;
        String fecha = calendar.get(Calendar.YEAR) + "-"
                + (mes < 10 ? "0"+mes : mes) + "-"
                + (dia < 10 ? "0" + dia : dia);
        verifyLastDayOfYear(calendar, fecha);
    }

    public void verifyLastDayOfYear(Calendar calendar, String fecha) {
        if(calendar.get(Calendar.MONTH) == 12 && calendar.get(Calendar.DAY_OF_MONTH) == 31) {
            int year = calendar.get(Calendar.YEAR) + 1;
            fecha = year+"-01-01";
        }
        verificarFechaActualMasUnoExiste(fecha, calendar.get(Calendar.MONTH));
    }

    public void verificarFechaActualMasUnoExiste(String fecha, int mes) {
        WebElement seleccionarFecha =  driver.findElement(By.cssSelector("div[class*='date-selector-container']"));
        seleccionarFecha.click();
        WebElement diaAElegir = null;
        try {
            diaAElegir = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-date='"+fecha+"']/button")));
        } catch (NoSuchElementException e) {
            mes++;
            String fechaABuscar = fecha.split("-")[0] + "-"
                    + (mes < 10 ? "0"+mes : mes) + "-"
                    + "01";
            diaAElegir = new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[@data-date='"+fechaABuscar+"']/button")));
        } finally {
            diaAElegir.click();
        }
    }

    public void seleccionarCiudad(String ciudad, String tipo) {
        WebElement tipoElement = driver.findElement(By.xpath("//div[@id='criteria-airport-select-"+tipo+"']//input"));
        tipoElement.click();
        tipoElement.clear();
        tipoElement.sendKeys(ciudad);
        WebElement ciudadElegida = tipoElement.findElement(By.xpath("//ul[@data-v-df607e20]/li[@id='criteria-airport-select-"+tipo+"-station-"+ciudad+"']"));
        ciudadElegida.click();
    }

    public void noSeleccionarAsiento() {
        WebElement element = new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-v-48db7256][data-v-54f6a7bc] button[style*='border: none']")));
        element.click();
        WebElement confirmarNoSeleccionDeAsiento = driver.findElement(By.cssSelector("button[class='vue-dialog-button']"));
        confirmarNoSeleccionDeAsiento.click();
    }

    public void noSeleccionarEquipaje() {
        WebElement element = new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class*='bag-service-link'] button[data-v-48db7256]")));
        element.click();
    }

    public void quitarExtras() {
        List<WebElement> extras = driver.findElements(By.cssSelector("div[class*='service-toggle-switch']"));
        for(WebElement extra : extras) {
            extra.click();
        }
    }

    public void noComprarSeguro() {
        try {
            WebElement checkbox = driver.findElement(By.cssSelector("div[class='chubb-single-offer'] label span.box"));
            checkbox.click();
        } catch (NoSuchElementException e) {

        }
    }

    @Then("^el precio del vuelo debe ser el mas barato$")
    public void verificarPrecio() {
        int precioFinal = obtenerPrecioVueloFinal();
        Assert.assertEquals(vueloMasBarato, precioFinal);
    }

    public Integer obtenerPrecioVueloFinal() {
        WebElement icono = driver.findElement(By.id("basket-icon"));
        icono.click();
        WebElement precioElemento = driver.findElement(By.cssSelector("div.price"));
        String precio = precioElemento.getText().split(" ")[1];
        Integer price = Integer.parseInt(precio.split(",")[0] + precio.split(",")[1]);
        return price;
    }
}
