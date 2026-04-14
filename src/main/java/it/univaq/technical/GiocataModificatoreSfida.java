package it.univaq.technical;
import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Player;

// Simile all'altro, ma specifica CHI è il bersaglio del modificatore (+ a me, o - a te?)
public record GiocataModificatoreSfida(CartaModificatore carta, boolean usaPositivo, Player bersaglio) {}