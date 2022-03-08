package puzzle;

import utils.Position;

import java.util.Objects;

public class Agent {
    private static int agent_last_id = 0;
    public int id;

    public Position targetPos;
    public Position currentPosition;

    public Agent() {
        this.id = agent_last_id++;
    }

    public boolean rightPosition(){
        return currentPosition == targetPos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agent agent = (Agent) o;
        return id == agent.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
