package sma.agent;

import java.util.Objects;

public class PieceAgent extends Agent {
    @Override
    protected void onBeforeMove() {

    }

    @Override
    protected void onAfterMove() {

    }

    @Override
    protected void onRemove() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceAgent agent = (PieceAgent) o;
        return id == agent.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Agent{" +
                "id=" + id +
                '}';
    }
}
