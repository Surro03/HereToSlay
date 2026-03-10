package it.univaq;

import it.univaq.controller.HereToSlay;
import it.univaq.controller.HereToSlaySetup;
import it.univaq.entity.*;
import it.univaq.ui.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        HereToSlaySetup hereToSlaySetup = new HereToSlaySetup();
        HereToSlay hereToSlay = hereToSlaySetup.setupGioco();
        System.out.println("=== BENVENUTO IN HERE TO SLAY ===");
        hereToSlay.iniziaPartita();
    }
}