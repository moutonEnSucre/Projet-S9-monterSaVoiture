
public class Agent {

    public enum AgentType{
        empty,
        cross,
        star,
        timer,
        sun,
    }

    private static int agent_last_id = 0;
    private int agent_id;
    public AgentType agent_type = AgentType.empty;

    public Agent() {
        this.agent_id = agent_last_id++;
    }

}
