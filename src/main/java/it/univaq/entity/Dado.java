package it.univaq.entity;

public class Dado {

	private Integer valore;

	public void tiraDado() {
		// TODO - implement Dado.tiraDado
		throw new UnsupportedOperationException();
	}

    public Dado(Integer valore) {
        this.valore = valore;
    }

    public Integer getValore() {
        return valore;
    }

    public void setValore(Integer valore) {
        this.valore = valore;
    }
}