package it.univaq.technical;
import it.univaq.entity.CartaSfida;
import it.univaq.entity.Player;

public record GiocataSfida(CartaSfida carta, Player sfidante) {}