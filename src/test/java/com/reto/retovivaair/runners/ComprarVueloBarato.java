package com.reto.retovivaair.runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/comprar_vuelo_barato.feature",
        glue = "com.reto.retovivaair.stepdefinitions",
        snippets = SnippetType.CAMELCASE
)
public class ComprarVueloBarato {
}
