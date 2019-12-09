import java.util.*;

public class Graph {

    public HashMap<String, Actor> adjacencyMap;

    public Graph() {
        adjacencyMap = new HashMap<>();
    }

    public class Actor {
        String actor;
        HashSet<String> movies = new HashSet<>();
        HashMap<String, Actor> edges = new HashMap<>();

        Actor(String name){
            this.actor = name;

        }

    }


    public void addEdgeHelper(String a, String b) {
       Actor actor = adjacencyMap.get(a);
       actor.edges.putIfAbsent(b, adjacencyMap.get(b));
    }

    public void addEdge(HashSet<String> edges, String movie) {
        HashMap<String, Actor> tmp = new HashMap<>();

        for(String actor: edges){
            tmp.putIfAbsent(actor, adjacencyMap.getOrDefault(actor,new Actor(actor)));
            tmp.get(actor).movies.add(movie);
        }

        for(String actor: tmp.keySet()){
            for(String edge: tmp.keySet()){
                if(!edge.equals(actor)){
                    tmp.get(edge).edges.putIfAbsent(actor, tmp.get(edge));
                    tmp.get(actor).edges.putIfAbsent(edge, tmp.get(edge));
                }
            }
        }

        for(String s : tmp.keySet()){
            adjacencyMap.put(s, tmp.get(s));


        }
//        if(movie.equals("411")){
//            for(String s: tmp.keySet()){
//                System.out.println("\n Cast member List: " + edges.size());
//                System.out.println("Actor: " + adjacencyMap.get(s).actor);
//                System.out.println("Cast Members: " + adjacencyMap.get(s).edges.keySet().toString());
//                System.out.println("Size of List for Cast Members: " + adjacencyMap.get(s).edges.size());
//                if(tmp.containsKey(s)){
//                    System.out.println("TMP: Actor: " + tmp.get(s).actor);
//                    System.out.println("TMP: Cast Members: " + tmp.get(s).edges.keySet().toString());
//                    System.out.println("Size of List for TMP: Cast Members: " + tmp.get(s).edges.size());
//                }
//
//            }
//        }


    }

    public boolean hasEdge(String actor, String castMember) {
        return adjacencyMap.containsKey(actor) && adjacencyMap.get(actor).edges.containsKey(castMember);
    }

    public void print(){
        String actor;
        for(String x: adjacencyMap.keySet()){
            actor = adjacencyMap.get(x).actor;
            for(String movie: adjacencyMap.get(actor).movies){
                if(true){
                    System.out.println("Actors Name: " + actor);
                    System.out.println("Movie Title: " + movie);
                    for(String castMember: adjacencyMap.get(actor).edges.keySet()){
                        if(adjacencyMap.get(castMember).movies.contains(movie)){
                            System.out.println("Cast members: " + castMember);
                        }
                    }
                    System.out.println();
                }


            }


        }
    }

}