package it.univaq;

import it.univaq.controller.HereToSlay;
import it.univaq.entity.*;
import it.univaq.technical.*;
import it.univaq.ui.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. INIZIALIZZAZIONE SISTEMA
        Scanner scanner = new Scanner(System.in);


        // Creazione Giocatori
        Player p1 = new Player(1, "Luca Avenia", null);
        Player p2 = new Player(2, "Alessandro Salvitti", null);
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);


        // Inizializzazione Turno con 3 Punti Azione (PA) [cite: 3, 12]
        HereToSlay controller = new HereToSlay(2, 1,4, players);

        GeneratoreDiEventi generatoreDiEventi = new GeneratoreDiEventi();
        controller.setGeneratoreDiEventi(generatoreDiEventi);

        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");
        System.out.println("Scegli il tuo Giocatore: ");
        //String sceltaGiocatore = scanner.nextLine();
        System.out.println("Turno di: " + players.getFirst() + " | PA disponibili: " + controller.getPaRimasti());

        // 2. ESECUZIONE AZIONE: Gioca Carta Eroe (Mossa 1) [cite: 9, 21]
        List<Carta> manoGiocatore1 = new ArrayList<>(Arrays.asList(
                // 1. Un Eroe dal costo 1, requisito 6
                new CartaEroe(0, 6, "Orso Guerriero", "Distruggi un eroe avversario", 1, null),

                // 2. Un Modificatore doppio (+1 / -1)
                new CartaModificatore( 1.0f, -1.0f, "Aggiungi +1 o sottrai -1 al tiro"),

                // 3. Una Carta Sfida generica
                new CartaSfida(),

                // 4. Un altro Eroe (Costo 1, requisito 7)
                new CartaEroe(0, 7, "Volpe Magica", "Pesca due carte dal mazzo", 1, null),

                // 5. Un Modificatore singolo (+2)
                new CartaModificatore(2, 2.0f, "Aggiungi +2 al tuo tiro")
        ));

        Mano manoP1 = new Mano(manoGiocatore1);

        List<Carta> manoGiocatore2 = new ArrayList<>(Arrays.asList(
                // 1. Un Eroe (Costo 1, requisito 5)
                new CartaEroe(0, 5, "Gatto Ladro", "Ruba una carta dalla mano di un giocatore", 1, null),

                // 2. Una Carta Sfida con una classe specifica (es. 2)
                new CartaSfida(2),

                // 3. Un Modificatore molto forte (+3 / -3)
                new CartaModificatore(3.0f, -3.0f, "Aggiungi +3 o sottrai -3 al tiro"),

                // 4. Un'altra Carta Sfida generica
                new CartaSfida(),

                // 5. Un Eroe (Costo 1, requisito 8)
                new CartaEroe(0, 8, "Fenicottero Bardo", "Tira di nuovo un dado", 1, null)
        ));

        Mano manoP2 = new Mano(manoGiocatore2);

        p1.setMano(manoP1);
        p2.setMano(manoP2);

        System.out.println("Questa è la tua mano: " + players.getFirst().getMano().getCarteMano());


        System.out.println("\n--- Fase 1: Richiesta Mossa ---");
        // Il controller verifica i PA e scala il punto [cite: 10, 11]


        int sceltaMossa = scanner.nextInt();
        scanner.nextLine();
        controller.richiestaMossa(sceltaMossa);

        Carta c = players.getFirst().getMano().getCarteMano().get(sceltaMossa - 1);

        if (sceltaMossa == 1) {

            // 3. SELEZIONE E GIOCATA CARTA [cite: 54]
            CartaEroe bardo = new CartaEroe(0, 7, "Bardo Canterino", "Pesca una carta", 1, ClasseEroe.BARDO);

            System.out.println("[AZIONE] " + p1.getNome() + " gioca la carta: " + ((CartaEroe) c).getNome() );
            controller.giocaCarta((CartaEroe) c);// La carta viene salvata nella FaseGiocaCarta [cite: 53, 54]
        }


        // 4. FINESTRA DI SFIDA (Simulazione Timeout) [cite: 59, 65]
        System.out.println("\n--- Fase 2: Finestra di Sfida ---");
        System.out.println("In attesa di reazioni dagli avversari...");
        // Simuliamo che nessuno giochi una CartaSfida, quindi scatta il timeout [cite: 65, 74]
        controller.timeout();

        String scelta;
        // 5. ATTIVAZIONE EFFETTO EROE [cite: 80, 114]
        System.out.println("\n--- Fase 3: Utilizzo Effetto ---");
        while (true) {
            System.out.println("Vuoi provare ad attivare l'effetto di " + ((CartaEroe) c).getNome() + "? (Si/No)");
            scelta = scanner.nextLine().trim();

            if (scelta.equalsIgnoreCase("Si") || scelta.equalsIgnoreCase("No")) {
                break; // L'input è valido, usciamo dal ciclo
            }

            System.out.println("Se c'è scritto si o no magari significa che devi mette quelli eh?");
        }


        if (scelta.equalsIgnoreCase("Si")) {
            // rispostaUtente("Si") crea la FaseEffetto e tira i dadi [cite: 132, 136]
            String risultatoLancio = controller.rispostaUtente("Si", c);
            System.out.println("[DADI] " + risultatoLancio);

            float dadiBase = Float.parseFloat(risultatoLancio);
            Player playerAttivo = players.getFirst();
            float valoreFinale = dadiBase ;
            int noConsecutivi = 0;
            int numeroGiocatori = players.size();
            float valoreModif = 0;

            while (generatoreDiEventi.isTempoValido()) {
                while (true) {
                    System.out.println(players.getFirst().getNome() +  ", Vuoi giocare un modificatore? (Si/No)");
                    scelta = scanner.nextLine();

                    if (scelta.equalsIgnoreCase("Si") || scelta.equalsIgnoreCase("No")) {
                        break; // L'input è valido, usciamo dal ciclo
                    }

                    System.out.println("Se c'è scritto si o no magari significa che devi mette quelli eh?");
                }


                if (!generatoreDiEventi.isTempoValido()) {
                    System.out.println("\n[!] Peccato! Hai premuto Invio, ma il tempo è SCADUTO proprio ora.");
                    break; // Esce immediatamente dal ciclo while
                }

                if (scelta.equalsIgnoreCase("Si")) {
                    System.out.println("\n--- Scegli un Modificatore da giocare ---");
                    List<CartaModificatore> modificatoriDisponibili = new ArrayList<>();

                    // 2. Filtriamo solo i modificatori e li stampiamo numerati
                    int visualIndex = 1;
                    for (Carta c1 : players.getFirst().getMano().getCarteMano()) {
                        if (c1 instanceof CartaModificatore mod) {
                            modificatoriDisponibili.add(mod);
                            System.out.println(visualIndex + ") " + mod + " [Valore: " + mod.getValorePositivo() + ", " + mod.getValoreNegativo() + "]");
                            visualIndex++;
                        }
                    }

                    // 3. Gestione caso "Nessuna carta trovata"
                    if (modificatoriDisponibili.isEmpty()) {
                        System.out.println("Non hai carte Modificatore in mano!");

                    } else{

                        int sceltaCartaModif = scanner.nextInt();
                        scanner.nextLine();

                        CartaModificatore modifScleto = modificatoriDisponibili.get(sceltaCartaModif - 1);




                        if (modifScleto.getValorePositivo() != null && modifScleto.getValoreNegativo() != null) {
                            System.out.println("Scegli il valore della carta modificatore");
                            System.out.println("1 Applica: " + modifScleto.getValorePositivo());
                            System.out.println("2 Applica: " + modifScleto.getValoreNegativo());

                            int sceltaSegno = scanner.nextInt();
                            scanner.nextLine();

                            if (!generatoreDiEventi.isTempoValido()) {
                                System.out.println("Tempo scaduto durante la scelta del valore!");
                                break;
                            }

                            if (sceltaSegno == 1) {
                                valoreModif = modifScleto.getValorePositivo();
                            } else if (sceltaSegno == 2) {
                                valoreModif = modifScleto.getValoreNegativo();
                            } else  {
                                System.out.println("Bastardo scegli tra 1 e due");
                            }
                        } else if (modifScleto.getValorePositivo() !=null && modifScleto.getValoreNegativo() == null) {
                            valoreModif = modifScleto.getValorePositivo();
                        } else if (modifScleto.getValoreNegativo() !=null && modifScleto.getValorePositivo() == null) {
                            valoreModif = modifScleto.getValoreNegativo();
                        }

                        valoreFinale = controller.giocoCarta(modifScleto, playerAttivo, valoreModif);
                        players.getFirst().getMano().getCarteMano().remove(modifScleto);



                        noConsecutivi = 0;
                    }


                } else {
                    noConsecutivi ++;

                    controller.resetTimer();
                }

                if (noConsecutivi >= numeroGiocatori) {
                    String confermaTermina = "";

                    while (true) {
                        System.out.println("\n[?] Entrambi avete passato. Volete terminare la fase ora senza attendere il timer? (Si/No)");
                        confermaTermina = scanner.nextLine();

                        if (scelta.equalsIgnoreCase("Si") || scelta.equalsIgnoreCase("No")) {
                            break; // L'input è valido, usciamo dal ciclo
                        }

                        System.out.println("Se c'è scritto si o no magari significa che devi mette quelli eh?");
                    }


                    if (confermaTermina.equalsIgnoreCase("Si")) {
                        generatoreDiEventi.stopTimer(); // Ferma il task del messaggio "Tempo Scaduto"
                        break; // Esci dal ciclo e vai al calcolo finale
                    } else {
                        // Se dicono No, resettiamo uno dei counter per dare un'altra chance
                        // o lasciamo che il timer scorra normalmente.
                        noConsecutivi = 0;
                    }
                }

                Collections.rotate(players, -1);
            }


            // 6. GESTIONE MODIFICATORI (Simulazione Punteggio Finale) [cite: 101, 134]
            // Supponiamo che dopo la FaseModificatori il punteggio sia 8 (Requisito era 7)
            System.out.println(valoreFinale);
            System.out.println("\n--- Fase 4: Verifica Requisiti ---");
            String esitoEffetto = controller.checkAttivazioneEffetto(valoreFinale); // [cite: 112, 146]
            System.out.println("[ESITO] " + esitoEffetto);

        }











        // 7. VERIFICA CONDIZIONI DI VITTORIA [cite: 78, 84]
        System.out.println("\n--- Fase Finale: Verifica Vittoria ---");
        controller.checkVittoria(p1); // Conta le classi uniche nel Party [cite: 84, 85]

        System.out.println("\nPA rimanenti per " + p1.getNome() + ": " + controller.getPaRimasti());
        System.out.println("=================================");
    }
}