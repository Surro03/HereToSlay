package it.univaq.technical;


public record GiocataSceltaModificatoreSfida(
        BersaglioModificatore bersaglio,
        TipoEffetto tipoEffetto
)
        implements SceltaModificatori
{}