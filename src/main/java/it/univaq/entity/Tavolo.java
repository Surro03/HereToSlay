package it.univaq.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tavolo {

	private final MazzoPesca mazzoPesca;
	private final MazzoMostri mazzoMostri;
	private final PilaDegliScarti pilaScarti;
	private final List<CartaMostro> mostriAttiviInCampo;
	private final Map<Integer, Party> partyMap;
	private final Dado dado;

	// Il costruttore ora riceve i mazzi preparati dalla Factory!
	public Tavolo(List<Player> players, MazzoPesca mazzoPesca, MazzoMostri mazzoMostri) {
		this.mazzoPesca = mazzoPesca;
		this.mazzoMostri = mazzoMostri;
		this.pilaScarti = new PilaDegliScarti();
		this.mostriAttiviInCampo = new ArrayList<>();
		this.partyMap = new HashMap<>();
		this.dado = new Dado(6);

		for (Player p : players) {
			this.partyMap.put(p.getId(), new Party());
		}

		// Appena appare il tavolo, giriamo le prime 3 carte Mostro!
		this.giraCarteMostro();
	}

	// --- CONDIZIONI DI VITTORIA ---

	public record VittoriaPerClassi(Boolean vittoria, int numClassiDiverse) {}

	public VittoriaPerClassi checkVittoria(Integer playerId) {
		Party partyPlayer = partyMap.get(playerId);
		return new VittoriaPerClassi(partyPlayer.checkVittoria(), partyPlayer.numClassiDiverse());
	}

	public void giraCarteMostro() {
		// Gira automaticamente una carta mostro quando viene sconfitta
		while (this.mostriAttiviInCampo.size() < 3 && !this.mazzoMostri.isEmpty()) {
			CartaMostro nuovoMostro = this.mazzoMostri.pescaDallaCima();
			if (nuovoMostro != null) {
				this.mostriAttiviInCampo.add(nuovoMostro);
			}
		}
	}

	public void rimuoviMostroSconfitto(CartaMostro mostro) {
		this.mostriAttiviInCampo.remove(mostro);
		this.giraCarteMostro();
	}

	public List<CartaMostro> getMostriAttiviInCampo() {
		// Unmodifiable View: nessuno può fare .clear() dal di fuori!
		return Collections.unmodifiableList(this.mostriAttiviInCampo);
	}

	public void aggiungiCartaPilaScarti(Carta carta) {
		// Usiamo il metodo ereditato dalla nostra classe astratta
		this.pilaScarti.aggiungiInCima(carta);
	}

	public void aggiungiCartaParty(CartaEroe carta, int playerId) {
		this.partyMap.get(playerId).inserisciEroe(carta);
	}

	public Party getParty(Integer playerId) {
		return this.partyMap.get(playerId);
	}

	public PilaDegliScarti getPilaScarti() {
		return this.pilaScarti;
	}

	public MazzoPesca getMazzoPesca() {
		return this.mazzoPesca;
	}

	public int lanciaDadi(int numeroDiDadi) {
		int totale = 0;
		for (int i = 0; i < numeroDiDadi; i++) {
			totale += dado.tiraDado();
		}
		return totale;
	}
}