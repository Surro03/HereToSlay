package it.univaq.technical;

import it.univaq.ui.GameObserver;

public interface PayloadAttesa {
    //Così non uso la classe Object e so cosa sto passando dalla fase al turno
    void notificaUI(GameObserver obs);
}
