package it.univaq.technical;


public record GiocataSceltaModificatore(
        BersaglioModificatore bersaglio,
        TipoEffetto tipoEffetto
)
        implements GiocataGiocatore
{}