import sma.agent.AgentManager;
import sma.world.World;

public class Main {

    public static void main(String[] args) {
        World world = new World(5);
        AgentManager agentManager = new AgentManager(5, world);
        agentManager.update();
    }
}
