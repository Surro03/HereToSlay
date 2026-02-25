package it.univaq.ui;

public class Player {

	/**
	 * æ
	 */
	private Integer Id;
	private String Nome;

    public Player(Integer id, String nome) {
        Id = id;
        Nome = nome;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }
}