package it.univaq.entity;

import it.univaq.entity.Tavolo;

public class EffettoRubaLeaf implements EffettoStrategyComponent {

    private final int numeroCarteDaRubare;

    public EffettoRubaLeaf(int numeroCarteDaRubare) {
        this.numeroCarteDaRubare = numeroCarteDaRubare;
    }

    @Override
    public void risolvi(Tavolo tavolo) {
        System.out.println("-> Risoluzione Effetto: Il giocatore deve rubare " + numeroCarteDaRubare + " carta/e.");

        // Siccome per rubare devo chiedere alla UI *chi* è il bersaglio, 
        // non posso risolvere la logica istantaneamente. 
        // DEVO METTERE IL TURNO IN ATTESA!
        
        /* * ESEMPIO DI LOGICA REALE:
         * 1. Recupero chi sta giocando
         * 2. Recupero la lista dei possibili bersagli (gli altri giocatori)
         * 3. Creo il ContestoAttesa (Il tuo Command in uscita!)
         * 4. Lo passo al mediatore (Turno)
         */
         
         // Player giocatoreCorrente = turno.getGiocatoreCorrente();
         // List<Player> avversari = tavolo.getAvversariDi(giocatoreCorrente);
         
         // turno.setContestoAttesa(
         //     new ContestoAttesaRubaCarta(giocatoreCorrente, avversari, numeroCarteDaRubare)
         // );
         
         // Quando la UI risponderà, invierà un nuovo "Command Message" in ingresso
         // (es. SceltaAvversarioRubaRecord) e il Turno applicherà lo scambio di carte!
    }
}
