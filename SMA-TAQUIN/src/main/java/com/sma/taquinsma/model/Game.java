package com.sma.taquinsma.model;

import com.sma.taquinsma.model.sma.agent.Agent;
import com.sma.taquinsma.model.sma.agent.PieceAgent;
import com.sma.taquinsma.model.sma.behavior.GoToTargetPosition;
import com.sma.taquinsma.model.sma.perception.Perception;
import com.sma.taquinsma.model.sma.world.World;
import com.sma.taquinsma.model.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Game extends Observable implements Runnable {
    private final Thread th;
    private boolean isRunning;

    private final List<PieceAgent> agents = new ArrayList<>();
    public boolean isDone = false;
    private final World world;

    public World getWorld() {
        return world;
    }

    public List<PieceAgent> getAgents() {
        return this.agents;
    }

    public Game(int nbAgent, World world) {
        this.world = world;
        for(int i = 0; i < nbAgent; i++) {
            agents.add(new PieceAgent());
        }

        for(PieceAgent a : agents) {
            Position initPos =  world.setCase(a);
            Position targetPos = world.getTargetPositionFromIdAgent(a.id);
            Perception perception = new Perception(world, a, initPos, targetPos);
            a.onInit(perception);
        }

        Agent nextAgentToMove = getAgentWithSmallestIdAndWithBadCurrentPosition();
        if(nextAgentToMove != null)
        {
            nextAgentToMove.behaviorList.clear();
            nextAgentToMove.addBehavior(new GoToTargetPosition(nextAgentToMove.perception));
        }

        this.th = new Thread(this);
        this.th.setDaemon(true);
    }


    @Override
    public void run() {
        while(!isDone && isRunning) {
            isDone = true;
            //Mettre à jour de tout les agents
            for(Agent a : agents) {
                //System.out.println(a.id);
                //System.out.println(a.behaviorList);

                a.update();

                if (!a.perception.isAtRightPlace())
                    isDone = false;

            }
            System.out.println(world);
            System.out.println("");

            setChanged();
            notifyObservers(); // notification de l'observer

            try {
                Thread.sleep(500); // pause
            } catch (InterruptedException ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public void start() {
        System.out.println("Game started");
        this.th.start();
        this.isRunning = true;
    }

    public void stop() {
        System.out.println("Game stoped");
        this.th.stop();
        this.isRunning = false;
    }

    public PieceAgent getAgentWithSmallestIdAndWithBadCurrentPosition() {
        for(PieceAgent agent : agents){
            if(!agent.perception.isAtRightPlace())
                return agent;
        }
        return null;
    }
}
