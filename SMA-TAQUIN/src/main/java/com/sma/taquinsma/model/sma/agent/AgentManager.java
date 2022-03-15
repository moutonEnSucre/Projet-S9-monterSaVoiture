package com.sma.taquinsma.model.sma.agent;

import com.sma.taquinsma.model.sma.behavior.GoToTargetPosition;
import com.sma.taquinsma.model.sma.perception.Perception;
import com.sma.taquinsma.model.sma.world.World;
import com.sma.taquinsma.model.utils.Position;

import java.util.ArrayList;
import java.util.List;

public class AgentManager {
    private final List<PieceAgent> agents = new ArrayList<>();
    public boolean isDone = false;
    private final World world;

    public AgentManager(int nbAgent, World world) {
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
    }

    public void update() {

        while(!isDone) {
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
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public PieceAgent getAgentWithSmallestIdAndWithBadCurrentPosition() {
        for(PieceAgent agent : agents){
            if(!agent.perception.isAtRightPlace())
                return agent;
        }
        return null;
    }
}
