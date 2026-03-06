package it.univaq.ui;



public class Player {

	/**
	 * æ
	 */
	private Integer id;
	private String nome;
    private Mano mano;

    public Player(Integer id, String nome, Mano mano) {
        this.id = id;
        this.nome = nome;
        this.mano = mano;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Mano getMano() {
        return mano;
    }

    public void setMano(Mano mano) {
        this.mano = mano;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nome='" + nome + '\'' +
                '}';
    }
}