package it.univaq.technical;

public record SceltaGiocaCartaEroe() implements SceltaMossa{

    @Override
    public Fase eseguiMossa(Turno turno) {
        return turno.aggiungiFaseInCima(new FaseGiocaCartaEroe());
    }
}
