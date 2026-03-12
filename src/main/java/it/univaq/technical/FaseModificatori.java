package it.univaq.technical;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Tavolo;
import it.univaq.entity.Player;
import it.univaq.ui.InterfacciaUtente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FaseModificatori extends Fase {

    private Map<Integer, Float> punteggiPlayer = new HashMap<>();


    public float calcoloPunteggio(float valoreCarta, Player target) {
        punteggiPlayer.merge(target.getId(), valoreCarta, Float::sum);
        return punteggiPlayer.get(target.getId());
    }

    public void salvaPunteggio(Integer playerId, float punteggioP) {
        punteggiPlayer.put(playerId, punteggioP);
    }

    public Float ottieniPunteggi(Integer playerId) {
        return punteggiPlayer.getOrDefault(playerId, 0f);
    }

    public Float eseguiFase(List<Player> giocatoriOriginali, Tavolo tavolo, GeneratoreDiEventi generatore, InterfacciaUtente ui, Fase fase) {
        Player playerAttivo = giocatoriOriginali.getFirst();
        Float valoreTiroFinale = this.ottieniPunteggi(playerAttivo.getId());

        int noConsecutivi = 0;
        int numeroGiocatori = giocatoriOriginali.size();

        List<Player> ordineTurno = new ArrayList<>(giocatoriOriginali);

        while (generatore.isTempoValido()) {
            Player giocatoreCorrente = ordineTurno.getFirst();

            // 1. FILTRO
            List<CartaModificatore> modificatoriDisponibili = giocatoreCorrente.getMano().getCarteMano().stream()
                    .filter(carta -> carta instanceof CartaModificatore)
                    .map(c -> (CartaModificatore) c)
                    .toList();

            boolean vuoleGiocare = false;

            // 2. SCELTA GIOCATORE
            if (modificatoriDisponibili.isEmpty()) {
                ui.mostraMessaggio("\n[" + giocatoreCorrente.getNome() + "] non ha carte Modificatore. Passa il turno automaticamente.");
            } else {
                vuoleGiocare = ui.chiediSeGiocareModificatore(giocatoreCorrente, modificatoriDisponibili.size());
            }

            // 3. CONTROLLO TEMPO
            if (!generatore.isTempoValido()) {
                ui.mostraMessaggio("\n[!] Il tempo è già scaduto, avresti dovuto rispondere prima");
                break;
            }

            // 4. APPLICAZIONE SCELTA
            if (vuoleGiocare) {
                CartaModificatore modifScelto = ui.scegliModificatoreDaGiocare(modificatoriDisponibili);
                float valoreModif = 0;

                if (modifScelto.getValorePositivo() != null && modifScelto.getValoreNegativo() != null) {
                    valoreModif = ui.scegliSegnoModificatore(modifScelto);
                } else if (modifScelto.getValorePositivo() != null) {
                    valoreModif = modifScelto.getValorePositivo();
                } else if (modifScelto.getValoreNegativo() != null) {
                    valoreModif = modifScelto.getValoreNegativo();
                }

                if (!generatore.isTempoValido()) break;

                generatore.resetTimerL(this);
                valoreTiroFinale = this.calcoloPunteggio(valoreModif, playerAttivo);

                giocatoreCorrente.getMano().getCarteMano().remove(modifScelto);
                if (tavolo != null) {
                    tavolo.scartaCarta(modifScelto);
                }

                ui.punteggioIntermedio(valoreTiroFinale);
                noConsecutivi = 0;

            } else {
                noConsecutivi++;
                generatore.resetTimerL(this);
            }

            // 5. CHIUSURA FASE (Delega conferma alla UI)
            if (noConsecutivi >= numeroGiocatori) {
                if (ui.chiediConfermaFineFase()) {
                    generatore.stopTimer(fase);
                    break;
                } else {
                    noConsecutivi = 0;
                }
            }

            Collections.rotate(ordineTurno, -1);
        }
        return valoreTiroFinale;
    }
}