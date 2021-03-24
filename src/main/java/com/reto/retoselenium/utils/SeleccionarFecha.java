package com.reto.retoselenium.utils;

import java.time.LocalDate;

public class SeleccionarFecha {

    public static String sumarUnDia() {
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaHoyMasUno = fechaHoy.plusDays(1);
        return fechaHoyMasUno.toString();
    }
}
