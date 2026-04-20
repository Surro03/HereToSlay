package it.univaq.technical;

import it.univaq.ui.GameObserver;

public interface ContestoAttesa {
    //Così non uso la classe Object e so cosa sto passando dalla fase al turno
    void notificaUI(GameObserver obs);
}
