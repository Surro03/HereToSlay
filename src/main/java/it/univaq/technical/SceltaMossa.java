package it.univaq.technical;

public interface SceltaMossa extends GiocataGiocatore {

    boolean isDisponibile(Turno turno);

    Fase eseguiMossa(Turno turno);
    //per la UI:
    String getNomeAzione();

    int getCostoPA();

}
