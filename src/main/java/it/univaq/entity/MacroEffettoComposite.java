package it.univaq.entity;

import it.univaq.entity.Tavolo;
import java.util.List;

public class MacroEffettoComposite implements EffettoStrategyComponent {

    // Il Composite contiene una lista di componenti dello STESSO tipo dell'interfaccia
    private final List<EffettoStrategyComponent> sottoEffetti;

    /**
     * Il costruttore accetta la lista di effetti che compongono la "combo".
     * @param sottoEffetti Lista di effetti (Leaf o altri Composite) da eseguire in sequenza.
     */
    public MacroEffettoComposite(List<EffettoStrategyComponent> sottoEffetti) {
        this.sottoEffetti = sottoEffetti;
    }

    @Override
    public void risolvi(Tavolo tavolo) {
        System.out.println("[Composite] Inizio risoluzione del Macro-Effetto combinato...");

        // Il Composite delega il lavoro a ciascuno dei suoi figli in modo polimorfico
        for (EffettoStrategyComponent effetto : sottoEffetti) {
            effetto.risolvi(tavolo);
        }

        //System.out.println("[Composite] Fine risoluzione del Macro-Effetto.");
    }
}