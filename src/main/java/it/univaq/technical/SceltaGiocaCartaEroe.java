package it.univaq.technical;

public record SceltaGiocaCartaEroe() implements SceltaMossa{

    @Override
    public Fase eseguiMossa(Turno turno) {
        // Consumo il punto azione
        boolean sufficienti = turno.consumaPA(1);
        if (sufficienti) {
            return new FaseGiocaCartaEroe();
        }else {
            return null;
        }
    }
}
