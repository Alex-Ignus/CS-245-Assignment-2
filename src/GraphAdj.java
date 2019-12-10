import java.util.*;
/**
 * Builds an adjacencyMap data Structure of a HashMap < Actor, LinkedList < Actor > >
 *     and implements a breadthFirstSearch method.
 *
 * @author Alexander Wojcik
 * @author University of San Francisco
 * @version Fall 2019
 */
class GraphAdj {

    private final HashMap < Actor, LinkedList < Actor > > adjacencyMap;
    private final HashMap < String, Actor > lookupMap = new HashMap <> ( );

    public GraphAdj ( ) {
        adjacencyMap = new HashMap <> ( );
    }
    /**
     * Node class: Actor for purpose of a particular project
     */
    public class Actor {
        final String name; //data storage
        Actor edge; //used for searching
        boolean found; //used for searching

        //constructor sets name and found as false by default
        Actor ( String name ) {
            this.name = name;
            found = false;
        }
        //sets found to true
        void find ( ) {
            found = true;
        }
        //sets found to false
        void lose ( ) {
            found = false;
        }
        //returns boolean of nodes lost/found state
        boolean isFound ( ) {
            return this.found;
        }
        //used to set edge of node for searching
        void setEdge ( Actor edge ) {
            this.edge = edge;
        }
        //returns the next node/edge of this node
        Actor getEdge ( Actor dest ) {
            return dest.edge;
        }
        //returns if node has edge
        boolean hasEdge ( Actor child ) {
            return this.edge != null;
        }
    }

    /**
     * Helper method to ensure that Node b is added to Node a's
     * Edge list while ensuing that a exsists in the first place in
     * the graph
     *
     * @param a the Node who;s list of edges were adding to
     * @param b the Node/Edge to add to a
     */
    private void addEdgeHelper ( Actor a , Actor b ) {
        adjacencyMap.putIfAbsent ( a,new LinkedList <> (  ) );
        adjacencyMap.get ( a ).add ( b );
    }

    /**
     * Creates and edge between the source and the destination
     * by default this method creates an undirected graph for
     * the user to travers and utilize
     *
     * @param source The first Node
     * @param destination the second Node
     */
    private void addEdge ( Actor source , Actor destination ) {
        if ( ! adjacencyMap.isEmpty ( ) ) {
            if ( ! adjacencyMap.keySet ( ).contains ( source ) )
                adjacencyMap.put ( source , null );

            if ( ! adjacencyMap.keySet ( ).contains ( destination ) )
                adjacencyMap.put ( destination , null );
        }
        addEdgeHelper ( source , destination );
        addEdgeHelper ( destination , source );
    }
    /**
     * Add's the actor to the look up map
     *
     * @param actor adding the actor to the lookupMap
     */
    public void add ( String actor ) {
        lookupMap.putIfAbsent ( actor.toLowerCase ( ) , new Actor ( actor ) );
    }

    /**
     * Helper function to ensure that actor is added to the lookupMap
     * if actor is not present, used to avoid null pointer errors
     * Use only when the goal is to ensure data is in the look map
     *
     * @param actor adding the actor to the lookupMap
     */
    private Actor addthenGet ( String actor ) {
        lookupMap.putIfAbsent ( actor.toLowerCase ( ) , new Actor ( actor ) );
        return lookupMap.get ( actor.toLowerCase ( ) );
    }

    /**
     * Function to add a collection of strings as keys for node values
     * to the lookupMap
     *
     * @param actors String collection of actors
     */
    void addAll ( Collection < String > actors ) {
        actors.forEach ( actor -> actors.stream ( ).filter ( castMember -> ! actor.equals ( castMember ) ).forEach
                ( castMember -> addEdge ( lookupMap.getOrDefault ( actor.toLowerCase ( )
                , addthenGet ( actor ) ) , lookupMap.getOrDefault ( castMember.toLowerCase ( ) , addthenGet ( castMember ) ) ) ) );
    }

    /**
     * Function to print all the edges of the adjacencyMap
     */
    public void printEdges ( ) {
        adjacencyMap.keySet ( ).forEach ( actor -> {
            System.out.print ( "The " + actor.name + " has an edge towards: " );
            adjacencyMap.get ( actor ).stream ( ).map ( neighbor -> neighbor.name + " " ).forEach ( System.out :: print );
            System.out.println ( );
        } );
    }

    /**
     * boolean function check if a Node has an edge to another Node
     *
     * @param source The node whose edges that are being checked for destination
     * @param destination the node we are looking for
     *
     * @return boolean if source has an edge to destination
     */
    private boolean hasEdge ( Actor source , Actor destination ) {
        return adjacencyMap.containsKey ( source ) && adjacencyMap.get ( source ).contains ( destination );
    }

    /**
     * Finds the existing path between two Nodes source, destination
     * and creates a StringBuilder to output the Path
     *
     * @param source The node whose edges that are being checked for destination
     * @param destination the node we are looking for
     *
     * @return StringBuilder the string of the path between two Nodes
     */
    private StringBuilder getPath ( Actor source , Actor destination ) {
        ArrayList<String> path2dest = new ArrayList < String > ( );
        while ( destination.hasEdge ( destination ) ) {
            path2dest.add ( destination.name );
            destination = destination.getEdge ( destination );
        }
        path2dest.add ( source.name );
        return printPath(path2dest);

    }

    public StringBuilder printPath ( ArrayList < String > path ){
        StringBuilder results = new StringBuilder (  );
        for( int i = path.size () - 1; i > 0; i-- ){
            results.append ( path.get ( i ) + " --> " );
        }
        results.append ( path.get ( 0 ) );
        return results;
    }
    /**
     * Finds if there is an existing path between two actorA , actorB
     * and finds the shortest path if one exists.
     *
     * @param actorA the starting point
     * @param actorB the destination
     *
     */
    public void search ( String actorA , String actorB ) {
        Actor source;
        Actor destination;
        StringBuilder results;
        if ( lookupMap.containsKey ( actorA ) && lookupMap.containsKey ( actorB ) ) {
            source = lookupMap.get ( actorA );
            destination = lookupMap.get ( actorB );
            if ( hasEdge ( source , destination ) ) {
                System.out.println ("Path between " + lookupMap.get(actorA).name +
                                    " and " + lookupMap.get(actorB).name + " "
                                    +  lookupMap.get(actorA).name + " --> " + lookupMap.get(actorB).name );
            }
            else {
                results = breadthFirstSearch ( source , destination );
                if ( results != null ) {
                    System.out.println ("Path between " + lookupMap.get(actorA).name +
                                        " and " + lookupMap.get(actorB).name + " " +  results );
                }
                else {
                    System.out.println ( "No path was found between " + lookupMap.get(actorA).name +
                                         " and " + lookupMap.get(actorB).name );
                }
            }
        }
        else if ( ! lookupMap.containsKey ( actorA ) ) {
            System.out.println ( "Actor Not Found: " + actorA );
        }
        else {
            System.out.println ( "Actor Not Found: " + actorB );
        }
    }

    /**
     * Finds if there is an existing path between two actorA , actorB
     * and finds the shortest path if one exists, by checking each
     * row of edges instead of depth
     *
     * @param source The root Node/starting point
     * @param destination the end Node/destination
     *
     * @return StringBuilder the string of the
     *           path between two Nodes, or null if no path is present
     */
    private StringBuilder breadthFirstSearch ( Actor source , Actor destination ) {
        HashMap < Actor, LinkedList < Actor > > tmpMap = new HashMap <> ( adjacencyMap );

        if ( source == null ) {
            return null;
        }

        LinkedList < Actor > queue = new LinkedList <> ( );
        queue.add ( source );

        while ( ! queue.isEmpty ( ) ) {

            Actor currentFirst = queue.removeFirst ( );
            if ( currentFirst.isFound ( ) ) {
                continue;
            }

            currentFirst.find ( );

            if ( tmpMap.get ( currentFirst ) == null ) {
                continue;
            }
            else {
                for ( Actor neighbor : tmpMap.get ( currentFirst ) ) {
                    if ( tmpMap.get ( neighbor ).contains ( destination ) && ! destination.isFound ( ) ) {
                        neighbor.setEdge ( currentFirst );
                        destination.setEdge ( neighbor );
                        return getPath ( source , destination );
                    }
                    else if ( ! neighbor.isFound ( ) ) {
                        neighbor.setEdge ( currentFirst );
                        queue.add ( neighbor );
                    }
                }
            }
            System.out.println ( );
        }
        return null;
    }
    /**
     * Helper Function to return a copy of the lookupMap
     *
     * @return HashMap copy of the loopupMap
     */
    public HashMap < String, Actor > getLookUpMap ( ) {
        return new  HashMap <  >(this.lookupMap);
    }


}
