package it.univaq.technical; // o il package che preferisci

import it.univaq.entity.CartaModificatore;

// Crea il file GiocataModificatore.java e incollaci questo:
public record GiocataModificatore(CartaModificatore carta, boolean usaPositivo) {
    // Non devi scrivere nient'altro qui dentro! 
    // Java crea in automatico in background il costruttore e i metodi per leggere i dati.
}