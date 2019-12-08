import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Driver {
    public static void main(String args []) {
        // store initial start time
        final Instant start = Instant.now();
        HashMap<String, HashSet<String>> movieCast = new HashMap<>();
        CSV csv = new CSV();
        movieCast.putAll(csv.csvReader());
        Graph graph = new Graph();
        ArrayList<String> value = null;

        if(movieCast.keySet().size()>0){
            for(String movie: movieCast.keySet()){
                if(!movieCast.get(movie).equals(null)){
                    graph.addEdge(movieCast.get(movie), movie);
                }


            }




        }
        final Duration elapsed = Duration.between(start, Instant.now());
        final double seconds = (double) elapsed.toMillis()
                / Duration.ofSeconds(1).toMillis();
        System.out.printf("Elapsed: %f seconds%n", seconds);
    }
}
