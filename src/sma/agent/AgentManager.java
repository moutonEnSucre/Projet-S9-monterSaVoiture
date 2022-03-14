package sma.agent;

import sma.behavior.GoToTargetPosition;
import sma.perception.Perception;
import sma.world.World;
import utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AgentManager {
    private final List<PieceAgent> agents = new ArrayList<>();
    public boolean isAtRightPlace = false;
    private World world;

    public AgentManager(int nbAgent, World world) {
        this.world = world;
        for(int i = 0; i < nbAgent; i++) {
            agents.add(new PieceAgent());
        }

        for(PieceAgent a : agents) {
            Position initPos =  world.setCase(a);
            Position targetPos = world.getTargetPositionFromIdAgent(a.id);
            Perception perception = new Perception(world, initPos, targetPos);

            a.behaviorList.add(new GoToTargetPosition(perception));
            a.onInit(perception);
        }
    }

    public void update() {
        while(!isAtRightPlace) {
            isAtRightPlace = true;
            for(Agent a : agents) {
                if (!a.perception.isAtRightPlace()) {
                    a.update();
                    if (!a.perception.isAtRightPlace())
                        isAtRightPlace = false;
                }
            }
            System.out.println(world);
            System.out.println("");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
