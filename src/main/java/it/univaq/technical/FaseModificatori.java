package it.univaq.technical;

import it.univaq.entity.CartaModificatore;
import it.univaq.entity.Tavolo;
import it.univaq.ui.GeneratoreDiEventi;
import it.univaq.ui.Player;

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

    public Float eseguiFase(List<Player> giocatoriOriginali, Tavolo tavolo, GeneratoreDiEventi generatore, Scanner scanner) {
        // Il giocatore attivo è SEMPRE il primo della lista originale passata dal Turno
        Player playerAttivo = giocatoriOriginali.getFirst();
        Float valoreTiroFinale = this.ottieniPunteggi(playerAttivo.getId());

        int noConsecutivi = 0;
        int numeroGiocatori = giocatoriOriginali.size();
        float valoreModif = 0;
        String scelta;

        // LA FIX: Creiamo una copia della lista solo per questo giro di modificatori.
        // Così possiamo fare 'Collections.rotate' senza sporcare l'ordine originale del gioco!
        List<Player> ordineTurno = new ArrayList<>(giocatoriOriginali);

        while (generatore.isTempoValido()) {
            // Prendiamo il primo giocatore dalla nostra lista copiata che sta ruotando
            Player giocatoreCorrente = ordineTurno.getFirst();

            // 1. Filtriamo le carte per vedere se ha dei modificatori
            List<CartaModificatore> modificatoriDisponibili = giocatoreCorrente.getMano().getCarteMano().stream()
                    .filter(carta -> carta instanceof CartaModificatore)
                    .map(c -> (CartaModificatore) c)
                    .toList();

            // 2. LOGICA ANTI-LOOP
            if (modificatoriDisponibili.isEmpty()) {
                System.out.println("\n[" + giocatoreCorrente.getNome() + "] non ha carte Modificatore. Passa il turno automaticamente.");
                scelta = "No";
            } else {
                while (true) {
                    System.out.println("\n" + giocatoreCorrente.getNome() + ", hai " + modificatoriDisponibili.size() + " modificatore/i. Vuoi giocarne uno? (Si/No)");
                    scelta = scanner.nextLine().trim();

                    if (scelta.equalsIgnoreCase("Si") || scelta.equalsIgnoreCase("No")) {
                        break;
                    }
                    System.out.println("Devi rispondere Si o No.");
                }
            }

            // Controllo del tempo prima di procedere
            if (!generatore.isTempoValido()) {
                System.out.println("\n[!] Tempo SCADUTO!");
                break;
            }

            // 3. APPLICAZIONE SCELTA
            if (scelta.equalsIgnoreCase("Si")) {
                System.out.println("\n--- SCEGLI UN MODIFICATORE DA GIOCARE ---");
                System.out.printf("%-8s | %-18s | %-10s | %-10s%n", "NUMERO", "TIPO", "VALORE +", "VALORE -");
                System.out.println("-".repeat(55));

                for (int i = 0; i < modificatoriDisponibili.size(); i++) {
                    CartaModificatore mod = modificatoriDisponibili.get(i);
                    String vPos = (mod.getValorePositivo() != null) ? String.format("%+.0f", mod.getValorePositivo()) : " / ";
                    String vNeg = (mod.getValoreNegativo() != null) ? String.format("%.0f", mod.getValoreNegativo()) : " / ";
                    System.out.printf("[%d]      | %-18s | %-10s | %-10s%n", (i + 1), "Modificatore", vPos, vNeg);
                }
                System.out.println("-".repeat(55));
                System.out.print("Digita il numero del modificatore: ");

                int sceltaCartaModif = scanner.nextInt();
                scanner.nextLine();

                CartaModificatore modifScelto = modificatoriDisponibili.get(sceltaCartaModif - 1);

                if (modifScelto.getValorePositivo() != null && modifScelto.getValoreNegativo() != null) {
                    System.out.println("Scegli il valore della carta modificatore");
                    System.out.println("1 Applica: " + modifScelto.getValorePositivo());
                    System.out.println("2 Applica: " + modifScelto.getValoreNegativo());

                    int sceltaSegno = scanner.nextInt();
                    scanner.nextLine();

                    if (!generatore.isTempoValido()) break;

                    if (sceltaSegno == 1) {
                        valoreModif = modifScelto.getValorePositivo();
                    } else if (sceltaSegno == 2) {
                        valoreModif = modifScelto.getValoreNegativo();
                    }
                } else if (modifScelto.getValorePositivo() != null) {
                    valoreModif = modifScelto.getValorePositivo();
                } else if (modifScelto.getValoreNegativo() != null) {
                    valoreModif = modifScelto.getValoreNegativo();
                }

                generatore.resetTimerL(this);
                // Il calcolo avviene SEMPRE sul 'playerAttivo' (quello che ha tirato i dadi)
                valoreTiroFinale = this.calcoloPunteggio(valoreModif, playerAttivo);

                giocatoreCorrente.getMano().getCarteMano().remove(modifScelto);
                if (tavolo != null) {
                    tavolo.scartaCarta(modifScelto);
                }

                System.out.println("Modificatore applicato! Nuovo punteggio provvisorio: " + valoreTiroFinale);
                noConsecutivi = 0;

            } else {
                noConsecutivi++;
                generatore.resetTimerL(this);
            }

            // 4. VERIFICA FINE FASE
            if (noConsecutivi >= numeroGiocatori) {
                String confermaTermina;
                while (true) {
                    System.out.println("\n[?] Tutti hanno passato. Terminare la fase modificatori? (Si/No)");
                    confermaTermina = scanner.nextLine().trim();
                    if (confermaTermina.equalsIgnoreCase("Si") || confermaTermina.equalsIgnoreCase("No")) break;
                }

                if (confermaTermina.equalsIgnoreCase("Si")) {
                    generatore.stopTimer();
                    break;
                } else {
                    noConsecutivi = 0;
                }
            }

            // Ruotiamo SOLO la nostra lista di copia, l'originale resta intatta!
            Collections.rotate(ordineTurno, -1);
        }
        return valoreTiroFinale;
    }
}