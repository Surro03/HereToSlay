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

    public Boolean checkAttivazioneEffetto(int punteggioDefinitivo) { throw new UnsupportedOperationException();}
}