import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class GraphImplementation{

    private HashMap<Node, LinkedList<Node>> adjacencyMap;

    public GraphImplementation() {
        adjacencyMap = new HashMap<>();
    }

    public class Node  {
        int weight;
        String actor;
        HashSet<Node> edges = new HashSet<>();

        Node(int weight, String name){
            this.weight = weight;
            this.actor = name;
        }

        public void addEdge(Node node) {
            if (!edges.contains(node))
                edges.add(node);
        }

        public void addEdges(Collection<Object> edges){
            for(Object edge: edges){
                if (!edges.contains(edge))
                    edges.add(edge);
            }

        }

    }



    public void addEdgeHelper(Node a, Node b) {
        LinkedList<Node> tmp = adjacencyMap.get(a);

        if (tmp != null) {
            tmp.remove(b);
        }
        else tmp = new LinkedList<>();
        tmp.add(b);
        adjacencyMap.put(a,tmp);
    }

    public void addEdge(Node source, Node destination) {

        // We make sure that every used node shows up in our .keySet()
        if (!adjacencyMap.keySet().contains(source))
            adjacencyMap.put(source, null);

        if (!adjacencyMap.keySet().contains(destination))
            adjacencyMap.put(destination, null);

        addEdgeHelper(source, destination);

    }

    public void printEdges() {
        for (Node node : adjacencyMap.keySet()) {
            System.out.print("The " + node.actor + " has an edge towards: ");
            for (Node neighbor : adjacencyMap.get(node)) {
                System.out.print(neighbor.actor + " ");
            }
            System.out.println();
        }
    }

    public boolean hasEdge(Node source, Node destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
    }

}