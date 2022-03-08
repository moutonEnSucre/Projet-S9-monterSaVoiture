package astar;

import utils.Position;

import java.util.*;

public class Astar {

    private Astar() {}

    private static final Astar INSTANCE = new Astar();

    public static Astar getInstance() {
        return INSTANCE;
    }

    private final HashMap<String, Graph> graphs = new HashMap<>();

    public void addGraph(Graph graph) {
        this.graphs.put(graph.getName(), graph);
    }

    public Graph getGraph(String name) {
        return graphs.get(name);
    }

    public List<Position> getPath(String graphName, Position start, Position target) {
        if(this.graphs.containsKey(graphName)) {
            return this.graphs.get(graphName).getPath(start, target);
        }
        return new ArrayList<>();
    }

    public void reset() {
        graphs.clear();
    }
}
