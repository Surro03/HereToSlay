package it.univaq.entity;

public class Dado {

	private Integer Valore;

	public void tiraDado() {
		// TODO - implement Dado.tiraDado
		throw new UnsupportedOperationException();
	}

    public Dado(Integer valore) {
        Valore = valore;
    }

    public Integer getValore() {
        return Valore;
    }

    public void setValore(Integer valore) {
        Valore = valore;
    }
}