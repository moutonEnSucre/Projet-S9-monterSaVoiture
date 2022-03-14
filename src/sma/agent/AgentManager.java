package sma.agent;

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
            a.onInit(new Perception(world, initPos, world.getTargetPositionFromIdAgent(a.id)));
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
        }
    }
}
