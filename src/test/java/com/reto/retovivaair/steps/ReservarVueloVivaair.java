package com.reto.retovivaair.steps;

import com.reto.retoselenium.interactions.JavaScriptScroll;
import com.reto.retovivaair.userinterface.QuitarExtrasYCompararPrecioVivaairPage;
import com.reto.retovivaair.userinterface.ReservarVueloVivaairPage;
import com.reto.retovivaair.userinterface.SeleccionarVueloVivaairPage;
import net.thucydides.core.annotations.Step;

public class ReservarVueloVivaair {

    private ReservarVueloVivaairPage reservarVueloVivaair;
    private SeleccionarVueloVivaairPage seleccionarVueloVivaair;
    private QuitarExtrasYCompararPrecioVivaairPage quitarExtrasYCompararPrecioVivaair;

    @Step
    public void abrirPaginaPrincipal() {
        reservarVueloVivaair.open();
        JavaScriptScroll.jsScroll();
        reservarVueloVivaair.cerrarNotificacion();
    }

    @Step
    public void buscarVuelosDisponibles(String ciudadOrigen, String ciudadDestino) {
        reservarVueloVivaair.verificarCheckbox();
        reservarVueloVivaair.seleccionarCiudadOrigen(ciudadOrigen);
        reservarVueloVivaair.seleccionarCiudadDestino(ciudadDestino);
        reservarVueloVivaair.seleccionarFechaVuelo();
        reservarVueloVivaair.buscarVuelos();
    }

    @Step
    public void seleccionarVueloMasBarato() {
        seleccionarVueloVivaair.seleccionarVueloMasBaratoYContinuar(
                seleccionarVueloVivaair.obtenerMenorPrecio(
                        seleccionarVueloVivaair.obtenerPreciosVuelos()
                )
        );
    }

    @Step
    public void quitarPreciosExtras() {
        quitarExtrasYCompararPrecioVivaair.noSeleccionarAsiento();
        quitarExtrasYCompararPrecioVivaair.noSeleccionarEquipaje();
        quitarExtrasYCompararPrecioVivaair.quitarExtras();
        quitarExtrasYCompararPrecioVivaair.noComprarSeguro();
    }

    @Step
    public void verificarPrecio() {
        quitarExtrasYCompararPrecioVivaair.compararPrecios(seleccionarVueloVivaair.getPrecioVuelo());
    }
}
