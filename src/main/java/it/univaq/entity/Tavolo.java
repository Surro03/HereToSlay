package it.univaq.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tavolo {

    private final PilaScarti pilaScarti;
	private final Map<Integer, Party> partyMap;

	public Tavolo(List<Player> players) {
        this.pilaScarti = new PilaScarti();
        this.partyMap = new HashMap<>();

		for (Player p : players) {
			// Estraiamo l'ID e lo associamo a un nuovo Party
			this.partyMap.put(p.getId(), new Party());
		}
	}

	public record VittoriaPerClassi(Boolean vittoria, int numClassiDiverse) {}
	/**
	 *
	 * @param playerId
	 */
	public VittoriaPerClassi checkVittoria(Integer playerId) {
		Party partyPlayer = partyMap.get(playerId);
        return new VittoriaPerClassi(partyPlayer.checkVittoria(), partyPlayer.numClassiDiverse());
	}

	/**
	 * 
	 * @param carta
	 */
	public void aggiungiCartaPilaScarti(Carta carta) {
		this.pilaScarti.aggiungiCarta(carta);
	}

	public void operation() {
		// TODO - implement Tavolo.operation
		throw new UnsupportedOperationException();
	}

	public void aggiungiCartaParty(CartaEroe carta,int playerId){
			this.partyMap.get(playerId).inserisciCarta(carta);
	}

	public Party getParty(Integer playerId){ return this.partyMap.get(playerId); }
}