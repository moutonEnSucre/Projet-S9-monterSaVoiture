package astar;
import utils.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {
    private final Map<Position, Node> graph = new HashMap<>();
    private final Position size;
    private final String name;

    public Graph(Position size, String name, HashSet<Position> walls) {
        this.size = size;
        this.name = name;

        //graph build
        for(int i = 0; i < this.size.x; i++) {
            for(int j = 0; j < this.size.y; j++) {
                Position pos = new Position(j, i);
                if(!walls.contains(pos)) {
                    this.graph.put(pos, new Node(pos));
                }
            }
        }

        //neighbor build
        List<Position> offset = new ArrayList<Position>() {{
            add(new Position(-1, 0));
            add(new Position(1, 0));
            add(new Position(0, -1));
            add(new Position(0, 1));
        }};

        for(int i = 0; i < this.size.x; i++) {
            for(int j = 0; j < this.size.y; j++) {
                if(this.graph.containsKey(new Position(j, i))) {
                    Node node = this.graph.get(new Position(j, i));
                    for(Position pos : offset) {
                        Position posToCheck = new Position(node.getPos().x + pos.x, node.getPos().y + pos.y);
                        if(this.graph.containsKey(posToCheck)) {
                            node.neighbors.add(this.graph.get(posToCheck));
                        }
                    }
                }
            }
        }
    }

    public List<Position> getPath(Position start, Position target) {
        if(this.graph.containsKey(start) && this.graph.containsKey(target)) {
            Node nStart = graph.get(start);
            Node nTarget = graph.get(target);

            PriorityQueue<Node> closedList = new PriorityQueue<>();
            PriorityQueue<Node> openList = new PriorityQueue<>();

            nStart.heuristic = nStart.calculateHeuristic(target);
            nStart.parent = null;
            openList.add(nStart);

            while(!openList.isEmpty()) {
                Node n = openList.poll();

                if(n.equals(nTarget)) {
                    return this.convertNodeToVector2iList(n);
                }

                for(Node m : n.neighbors) {
                    if(!openList.contains(m) && !closedList.contains(m)) {
                        m.parent = n;
                        m.heuristic = m.calculateHeuristic(target);
                        openList.add(m);
                    }
                }

                closedList.add(n);
            }
        }

        return null;
    }

    public List<Position> convertNodeToVector2iList(Node node){
        List<Position> positions = new ArrayList<>();

        if(node==null) return positions;

        while(node.parent != null){
            positions.add(node.getPos());
            node = node.parent;
        }

        return positions;
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();

        //inverted
        value.append("<").append(this.name).append(">\n");

        //graph display
        for(int i = 0; i < this.size.x; i++) {
            for(int j = 0; j < this.size.y; j++) {
                if(this.graph.containsKey(new Position(j, i))) {
                    value.append("0 ");
                }
                else {
                    value.append("1 ");
                }

                if(j == this.size.x - 1) {
                    value.append("\n");
                }
            }
        }

        return value.toString();
    }

    public String getName() {
        return name;
    }
}
