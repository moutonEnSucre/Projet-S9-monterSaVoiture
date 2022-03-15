import sma.agent.AgentManager;
import sma.world.World;

public class Main {

    public static void main(String[] args) {
        //Création du monde
        World world = new World(5);

        //Création du container gérant les agents
        AgentManager agentManager = new AgentManager(13, world);
        agentManager.update();
    }
}
