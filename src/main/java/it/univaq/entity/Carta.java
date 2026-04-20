package it.univaq.entity;

import java.sql.Blob;


public abstract class Carta {

	protected String id;
	protected String descrizione;
    protected String nome;
	//private Blob Immagine;

    protected Carta(String descrizione, String nome) {
        this.descrizione = descrizione;
        this.nome = nome;
        //Immagine = immagine;
    }

    public void eseguiEffetto(){

    }

    public String getNome(){
        return nome;
    }

    public Boolean checkAttivazioneEffetto(float punteggioDefinitivo) {
        throw new UnsupportedOperationException();
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}