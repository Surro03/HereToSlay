package it.univaq.technical;

public record SceltaScartaManoPescaCinque() implements SceltaMossa{
    @Override
    public boolean isDisponibile(Turno turno) {
        //return turno.getPaRimasti() >= 3;
        return false;
    }

    @Override
    public Fase eseguiMossa(Turno turno) {
        return null;
    }

    @Override
    public String getNomeAzione() {
        return "Scarta Mano e Pesca 5 Carte";
    }

    @Override
    public int getCostoPA() {
        return 3;
    }
}
