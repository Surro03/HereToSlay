package it.univaq.config;

import it.univaq.controller.HereToSlay;
import it.univaq.entity.*;
import it.univaq.technical.GeneratoreDiEventi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HereToSlaySetup {

    // 1. Costruttore privato: impedisce l'istanziazione.
    private HereToSlaySetup() {
        throw new IllegalStateException("Classe di Bootstrap - Non istanziabile");
    }

    public static HereToSlay setupGioco() {

        //TODO aggiungere la possibilità di inserire numero di giocatori, ecc.

        // Chiamata diretta al metodo statico (niente più "this.")
        List<Player> playerList = setupPlayers(2, null);

        GeneratoreDiEventi generatoreDiEventi = new GeneratoreDiEventi(15);

        MazzoPesca mazzoPesca = new MazzoPesca();
        mazzoPesca.mischia();

        MazzoMostri mazzoMostri = new MazzoMostri();
        mazzoMostri.mischia();

        Tavolo tavolo = new Tavolo(playerList, mazzoPesca, mazzoMostri);

        HereToSlay controller = new HereToSlay(playerList, generatoreDiEventi, tavolo);

        generatoreDiEventi.addObserver(controller);

        return controller;
    }

    // 3. Aggiunto "private static". Diventa privato perché serve solo internamente al setupGioco.
    private static List<Player> setupPlayers(int numPlayers, List<Carta> mazzoMischiato){

        List<Carta> manoGiocatore1 = new ArrayList<>(Arrays.asList(
                new CartaEroe(0, 6, "Ascia Sfascia", ClasseEroe.GUERRIERO, new EffettoPescaLeaf(1)),
                new CartaModificatore(1, -1, "Aggiungi +1 o sottrai -1 al tiro"),
                new CartaSfida(),
                new CartaEroe(0, 7, "Ezio Miaoditore", ClasseEroe.LADRO, new EffettoRubaLeaf(2) ),
                new CartaModificatore(2, "Aggiungi +2 al tuo tiro")
        ));

        List<Carta> manoGiocatore2 = new ArrayList<>(Arrays.asList(
                new CartaEroe(0, 5, "Gatto Ladro", ClasseEroe.LADRO,  new EffettoRubaLeaf(1)),
                new CartaSfida(2),
                new CartaModificatore(3, -3, "Aggiungi +3 o sottrai -3 al tiro"),
                new CartaSfida(),
                new CartaEroe(0, 8, "Fenicottero Bardo", ClasseEroe.BARDO, new EffettoRubaLeaf(2))
        ));

        Mano manoP1 = new Mano(manoGiocatore1);
        Mano manoP2 = new Mano(manoGiocatore2);

        Player p1 = new Player(1, "Luca Avenia", manoP1);
        Player p2 = new Player(2, "Alessandro Salvitti", manoP2);

        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);

        return players;
    }
}