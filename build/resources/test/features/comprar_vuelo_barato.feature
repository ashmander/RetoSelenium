Feature: Reservar vuelo
  Yo como viajero
  Quiero reservar un vuelo
  Para poder viajar

  Scenario: Reservar vuelo más barato en Vivaair
    Given quiero reservar un vuelo barato
    When reserve el vuelo mas barato de MDE a BOG
    Then el precio del vuelo debe ser el mas barato