package com.sma.taquinsma.model.sma.behavior;

import com.sma.taquinsma.model.sma.agent.Agent;
import com.sma.taquinsma.model.sma.perception.Perception;
import com.sma.taquinsma.model.utils.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Comportement obligeant l'agent à devoir se déplacer
public class NeedToMove implements Behavior {
    private final Perception perception;
    private final Agent agentWhoContactMe;
    private boolean isDone = false;

    public NeedToMove(Perception perception, Agent agentWhoContactMe) {
        this.agentWhoContactMe = agentWhoContactMe;
        this.perception = perception;
    }

    @Override
    public String toString() {
        return "NeedToMove: " + perception.currentPos.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o instanceof NeedToMove;
    }

    @Override
    public boolean done() {
        return isDone;
    }

    @Override
    public void action() {
        //On essaye de trouver une case valide autour de l'agent
        List<Position> validPositions = getValidPosition();
        if(!validPositions.isEmpty()) {
            //Si on la trouve, on se déplace
            Position nextPos = validPositions.get(0);
            perception.world.moveAgent(perception.parent, nextPos);
        }
        else {
            //Sinon on demande à nos voisins de se déplacer
            contactValidNeighborToMove();
        }

        isDone = true;
    }

    private void contactValidNeighborToMove() {
        List<Position> offset = new ArrayList<>() {
            {
                add(new Position(-1, 0));
                add(new Position(1, 0));
                add(new Position(0, -1));
                add(new Position(0, 1));
            }
        };

        Position currentPos = perception.currentPos;

        List<Agent> neightborToMove = new ArrayList<>();

        //On récupère les voisins qui ne sont pas ceux qui nous ont contactés et qui ne sont pas à leur place finale
        for(Position o : offset) {
            Position testPos = new Position(currentPos.x + o.x, currentPos.y + o.y);
            if(testPos.x >= 0 && testPos.x < perception.world.size && testPos.y >= 0 && testPos.y < perception.world.size) {

                Agent agentCaseTested = perception.world.getAgent(testPos);
                if(agentCaseTested != null && agentCaseTested != agentWhoContactMe && !agentCaseTested.perception.isAtRightPlace()){// && !agentCaseTested.perception.isAtRightPlace()) {
                    neightborToMove.add(agentCaseTested);
                }
            }
        }

        //Si on n'en trouve pas, on refzit la procédure mais nous pouvons désormais contacter les agents qui sont sur
        //leur position finale
        if(neightborToMove.isEmpty()) {
           /* perception.parent.sendMessage(agentWhoContactMe, "MOVE");*/

            for(Position o : offset) {
                Position testPos = new Position(currentPos.x + o.x, currentPos.y + o.y);
                if(testPos.x >= 0 && testPos.x < perception.world.size && testPos.y >= 0 && testPos.y < perception.world.size) {

                    Agent agentCaseTested = perception.world.getAgent(testPos);
                    if(agentCaseTested != null && agentCaseTested != agentWhoContactMe) {// && !agentCaseTested.perception.isAtRightPlace()) {
                        neightborToMove.add(agentCaseTested);
                    }
                }
            }

            //On contact l'un de nos voisin afin de lui demander de bouger
            if(!neightborToMove.isEmpty()) {
                perception.parent.sendMessage(neightborToMove.get(new Random().nextInt(neightborToMove.size())), "MOVE");
            }
        }
        else {
            //On demande à nos voisin de se déplacer
            for(Agent a : neightborToMove) {
                perception.parent.sendMessage(a, "MOVE");
            }
        }
    }

    private List<Position> getValidPosition() {
        List<Position> validPosition = new ArrayList<>();

        List<Position> offset = new ArrayList<>() {
            {
                add(new Position(-1, 0));
                add(new Position(1, 0));
                add(new Position(0, -1));
                add(new Position(0, 1));
            }
        };

        Position currentPos = perception.currentPos;

        for(Position o : offset) {
            Position testPos = new Position(currentPos.x + o.x, currentPos.y + o.y);
            if(testPos.x >= 0 && testPos.x < perception.world.size && testPos.y >= 0 && testPos.y < perception.world.size) {
                Agent agentCaseTested = perception.world.getAgent(testPos);
                if(agentCaseTested == null) {
                    validPosition.add(testPos);
                }
            }
        }
        return validPosition;
    }
}
