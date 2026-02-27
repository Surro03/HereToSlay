package it.univaq.entity;

public class CartaMostro {

	private Integer condizioniVittoria;
	private String descrizione;
	private Integer requisito;
	private String nome;

    public CartaMostro(Integer condizioniVittoria, String descrizione, Integer requisito, String nome) {
        this.condizioniVittoria = condizioniVittoria;
        this.descrizione = descrizione;
        this.requisito = requisito;
        this.nome = nome;
    }

    public Integer getCondizioniVittoria() {
        return condizioniVittoria;
    }

    public void setCondizioniVittoria(Integer condizioniVittoria) {
        this.condizioniVittoria = condizioniVittoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Integer getRequisito() {
        return requisito;
    }

    public void setRequisito(Integer requisito) {
        this.requisito = requisito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}