package it.univaq.entity;

import java.util.Random;

public class Dado {

	private Integer valore;

	public int tiraDado() {
        Random random = new Random();
        return 1 + random.nextInt(valore);
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