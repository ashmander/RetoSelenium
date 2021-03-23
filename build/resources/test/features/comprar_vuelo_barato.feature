Feature: Comprar vuelo
  Yo como viajero
  Quiero comprar un vuelo
  Para poder viajar

  Scenario: Comprar vuelo más barato en Vivaair
    Given quiero comprar un vuelo barato
    When compre el vuelo mas barato de MDE a BOG
    Then el precio del vuelo debe ser el mas barato