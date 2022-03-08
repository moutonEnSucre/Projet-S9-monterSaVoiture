package puzzle;

public class Agent {
    private static int agent_last_id = 0;
    public int id;

    public Agent() {
        this.id = agent_last_id++;
    }

}
