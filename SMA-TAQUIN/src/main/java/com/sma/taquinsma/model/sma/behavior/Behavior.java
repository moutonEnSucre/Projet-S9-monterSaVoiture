package com.sma.taquinsma.model.sma.behavior;

public interface Behavior {
    //Permet de tester si le comportement à fini sa tâche
    boolean done();
    //Permet d'update le comportement
    void action();
}
