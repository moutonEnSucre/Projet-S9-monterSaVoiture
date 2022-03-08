package astar;

import utils.Position;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

    private final int id;
    private static int lastId = 0;
    private final Position pos;

    public final List<Node> neighbors = new ArrayList<>();
    public double heuristic = 0;
    public Node parent = null;

    public Node(Position position) {
        this.id = lastId++;
        this.pos = position;
    }

    public int getId() {
        return id;
    }

    public Position getPos() {
        return pos;
    }

    public double calculateHeuristic(Position target) {
        return Position.distance(pos.x, pos.y, target.x, target.y);
    }

    @Override
    public int compareTo(Node node) {
        return Double.compare(this.heuristic, node.heuristic);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass()) return false;
        if(this == obj) return true;

        Node other = (Node) obj;
        return pos.x == other.getPos().x && pos.y == other.getPos().y;
    }

    @Override
    public String toString() {
        return "(" + pos.x + ";" + pos.y + ")";
    }
}
