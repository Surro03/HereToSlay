package it.univaq.entity;

public class CartaEroe extends Carta {

	private Integer NumOggetti;
	private Integer Requisito;
	private String Nome;
	private String Effetto;
	private Integer Costo;
	private ClasseEroe ClasseEroe;

	public void eseguiEffetto() {
		// TODO - implement Eroe.eseguiEffetto
		throw new UnsupportedOperationException();
	}

    public CartaEroe(Integer numOggetti, Integer requisito, String nome, String effetto, Integer costo, ClasseEroe classeEroe) {
        NumOggetti = numOggetti;
        Requisito = requisito;
        Nome = nome;
        Effetto = effetto;
        Costo = costo;
        ClasseEroe = classeEroe;
    }

    public Integer getNumOggetti() {
        return NumOggetti;
    }

    public void setNumOggetti(Integer numOggetti) {
        NumOggetti = numOggetti;
    }

    public Integer getRequisito() {
        return Requisito;
    }

    public void setRequisito(Integer requisito) {
        Requisito = requisito;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEffetto() {
        return Effetto;
    }

    public void setEffetto(String effetto) {
        Effetto = effetto;
    }

    public Integer getCosto() {
        return Costo;
    }

    public void setCosto(Integer costo) {
        Costo = costo;
    }

    public ClasseEroe getClasseEroe() {
        return ClasseEroe;
    }

    public void setClasseEroe(ClasseEroe classeEroe) {
        ClasseEroe = classeEroe;
    }
}