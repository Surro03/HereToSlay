package it.univaq.entity;

import java.sql.Blob;


public abstract class Carta {

	private String Id;
	private String Descrizione;
	//private Blob Immagine;

    protected Carta(String descrizione) {
        Descrizione = descrizione;
        //Immagine = immagine;
    }

    public void eseguiEffetto(){

    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Boolean checkAttivazioneEffetto(float punteggioDefinitivo) { throw new UnsupportedOperationException();}
}