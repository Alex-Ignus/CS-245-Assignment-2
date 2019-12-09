import java.util.*;

public class GraphAdj {
    // Each node maps to a list of all his neighbors
    private HashMap<Node, LinkedList<Node>> adjacencyMap;
    private HashMap<String, Node> lookupMap = new HashMap<>();

    public GraphAdj() {
        adjacencyMap = new HashMap<>();
    }

    public class Node {
        String name;
        Node parent;
        boolean visited;

        Node(String name){
            this.name = name;
            visited = false;
        }
        void visit() {
            visited = true;
        }

        void unvisit() {
            visited = false;
        }

        public boolean isVisited() {
            return this.visited;
        }
        public void setParent(Node parent){
            this.parent = parent;
        }
        public Node getParent(Node dest){
           return  dest.parent;
        }
        public boolean hasParent(Node child){
           return this.parent != null;
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


        if(!adjacencyMap.isEmpty()){
            if (!adjacencyMap.keySet().contains(source))
                adjacencyMap.put(source, null);

            if (!adjacencyMap.keySet().contains(destination))
                adjacencyMap.put(destination, null);
        }

        addEdgeHelper(source, destination);
        addEdgeHelper(destination, source);


    }

    public void add(String actor){
        lookupMap.putIfAbsent(actor, new Node(actor));
    }
    public Node addthenGet(String actor){
        lookupMap.putIfAbsent(actor, new Node(actor));
        return lookupMap.get(actor);
    }

    public void addAll(Collection<String> actors){
        for(String actor: actors){
            for(String castMember: actors){
                if(!actor.equals(castMember)){
                    addEdge(lookupMap.getOrDefault(actor, addthenGet(actor)), lookupMap.getOrDefault(castMember, addthenGet(castMember)));
                }
            }
        }
    }

    public void printEdges() {
        for (Node node : adjacencyMap.keySet()) {
            System.out.print("The " + node.name + " has an edge towards: ");
            for (Node neighbor : adjacencyMap.get(node)) {
                System.out.print(neighbor.name + " ");
            }
            System.out.println();
        }
    }


    public boolean hasEdge(Node source, Node destination) {
        return adjacencyMap.containsKey(source) && adjacencyMap.get(source).contains(destination);
    }



    public StringBuilder getPath(Node source, Node destination){
        StringBuilder path2dest = new StringBuilder();

        while(destination.hasParent(destination)){
            path2dest.append(destination.name + " <--> ");
            destination = destination.getParent(destination);
        }
       return path2dest.append(source.name);

    }


    public void search(String actorA, String actorB){
        Node source;
        Node destination;
        StringBuilder results;
        if(lookupMap.containsKey(actorA) && lookupMap.containsKey(actorB)){
            source = lookupMap.get(actorA);
            destination = lookupMap.get(actorB);
            if(hasEdge(source, destination)){
                System.out.println(actorA + " <--> " + actorB);
            }else {
               results = breadthFirstSearch( source,  destination);
               if(results != null){
                   System.out.println(results);
               }else{
                   System.out.println("No path found");
               }
            }
        }
        else if(!lookupMap.containsKey(actorA)){
            System.out.println("Actor Not Found" + actorA);

        }else{
            System.out.println("Actor Not Found" + actorB);
        }

    }

    public StringBuilder breadthFirstSearch(Node source, Node destination) {
        HashMap<Node, LinkedList<Node>> tmpMap = new HashMap<>(adjacencyMap);

        if (source == null) {
            return null;
        }

        LinkedList<Node> queue = new LinkedList<>();

        queue.add(source);

        while (!queue.isEmpty()) {
            Node currentFirst = queue.removeFirst();

            if (currentFirst.isVisited()){
                continue;
            }

            currentFirst.visit();

            if (tmpMap.get(currentFirst) == null){
                continue;

            }else{

                for (Node neighbor : tmpMap.get(currentFirst)) {

                    if(tmpMap.get(neighbor).contains(destination) && !destination.isVisited()){
                        neighbor.setParent(currentFirst);
                        destination.setParent(neighbor);
                        destination.isVisited();
                        return getPath(source, destination);

                    }
                    else if (!neighbor.isVisited()) {
                        neighbor.setParent(currentFirst);
                        queue.add(neighbor);
                    }
                }

            }
            System.out.println();


        }


        return null;

    }



}
