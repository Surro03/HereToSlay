package it.univaq.entity;

public class CartaEroe extends Carta {

	private Integer numOggetti;
	private Integer requisito;
	private String nome;
	private String effetto;
	private Integer costo;
	private ClasseEroe classeEroe;

    @Override
	public void eseguiEffetto() {
		// TODO - implement Eroe.eseguiEffetto
		throw new UnsupportedOperationException();
	}

    public CartaEroe(Integer numOggetti, Integer requisito, String nome, String effetto, Integer costo, ClasseEroe classeEroe) {
        this.numOggetti = numOggetti;
        this.requisito = requisito;
        this.nome = nome;
        this.effetto = effetto;
        this.costo = costo;
        this.classeEroe = classeEroe;
    }

    public Integer getNumOggetti() {
        return numOggetti;
    }

    public void setNumOggetti(Integer numOggetti) {
        this.numOggetti = numOggetti;
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

    public String getEffetto() {
        return effetto;
    }

    public void setEffetto(String effetto) {
        this.effetto = effetto;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public ClasseEroe getClasseEroe() {
        return classeEroe;
    }

    public void setClasseEroe(ClasseEroe classeEroe) {
        this.classeEroe = classeEroe;
    }
}