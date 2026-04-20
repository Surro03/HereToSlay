package it.univaq.technical;
import it.univaq.entity.CartaModificatore;

public record GiocataSceltaModificatoreNormale(
        TipoEffetto tipoEffetto
)
        implements GiocataGiocatore
{}