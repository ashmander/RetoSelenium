package com.reto.retovivaair.stepdefinitions;

import com.reto.retovivaair.steps.ReservarVueloVivaair;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class ComprarVueloBaratoStepDefinitions {

    @Steps
    private ReservarVueloVivaair reservarVueloVivaair;

    @Given("^quiero reservar un vuelo barato$")
    public void prepararCompra() {
        reservarVueloVivaair.abrirPaginaPrincipal();
    }



    @When("^reserve el vuelo mas barato de (.*) a (.*)$")
    public void comprarVueloMasBarato(String origen, String destino) {
        reservarVueloVivaair.buscarVuelosDisponibles(origen, destino);
        reservarVueloVivaair.seleccionarVueloMasBarato();
        reservarVueloVivaair.quitarPreciosExtras();
    }



    @Then("^el precio del vuelo debe ser el mas barato$")
    public void verificarPrecio() {
        reservarVueloVivaair.verificarPrecio();
    }
}
