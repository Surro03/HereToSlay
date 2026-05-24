package it.univaq.entity;



public class CartaEroe extends Carta {

    private Integer numOggetti;
    private CartaOggetto cartaOggetto;
    private Integer requisito;
    private String nome;
    private ClasseEroe classeEroe;
    private EffettoStrategyComponent logicaEffetto;


    public CartaEroe(Integer numOggetti, Integer requisito, String nome, ClasseEroe classeEroe, EffettoStrategyComponent logicaEffetto) {
        super("descrizione", "nome"); // Sostituisci pure con la chiamata corretta alla superclasse Carta
        this.numOggetti = numOggetti;
        this.requisito = requisito;
        this.nome = nome;
        this.classeEroe = classeEroe;
        this.logicaEffetto = logicaEffetto; // Strategia concreta (Leaf o Composite)
    }


    @Override
    public void eseguiEffetto(Tavolo tavolo) {
        //System.out.println("[Strategy Context] " + this.nome + " sta attivando il suo potere...");
        // La carta non sa COSA farà questo effetto, lo lancia e basta.
        this.logicaEffetto.risolvi(tavolo);
    }

    // Getti e Setter per la logica dell'effetto (Utile se un domani un oggetto modifica il potere dell'eroe!)
    public EffettoStrategyComponent getLogicaEffetto() {
        return logicaEffetto;
    }

    public void setLogicaEffetto(EffettoStrategyComponent logicaEffetto) {
        this.logicaEffetto = logicaEffetto;
    }

    // --- Sotto rimangono invariati i tuoi Getter e Setter preesistenti ---

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
        return logicaEffetto.toString();
    }

    public void setEffetto(EffettoStrategyComponent effetto) {
        this.logicaEffetto = effetto;
    }


    public ClasseEroe getClasseEroe() {
        return classeEroe;
    }

    public void setClasseEroe(ClasseEroe classeEroe) {
        this.classeEroe = classeEroe;
    }

    @Override
    public Boolean checkAttivazioneEffetto(float punteggioDefinitivo) {
        return punteggioDefinitivo >= requisito;
    }
}