package it.univaq.entity;

public class CartaMostro extends Carta {

	private Integer condizioniVittoria;
	private String descrizione;
	private Integer requisito;
	private String nome;

    public CartaMostro(Integer condizioniVittoria, String descrizione, Integer requisito, String nome) {
        super(descrizione, nome);
        this.condizioniVittoria = condizioniVittoria;
        this.requisito = requisito;
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