import java.util.HashMap;
import java.util.HashSet;

public class Driver {
    public static void main(String args []) {
        // store initial start time
        Timer overall = new Timer();
        Timer fileReading = new Timer("Reading the CSV");
        Timer graphBuilding = new Timer("Building the Graph");
        overall.startTimer();

        HashMap<String, HashSet<String>> movieCast = new HashMap<>();
        CSV csv = new CSV();
        fileReading.startTimer();
        movieCast.putAll(csv.csvReader());
        fileReading.stopTimer();

        GraphAdj graph = new GraphAdj();
        graphBuilding.startTimer();

        if(movieCast.keySet().size()>0){
            for(String movie: movieCast.keySet()){
                if(!movieCast.get(movie).equals(null)){
                    graph.addAll(movieCast.get(movie));
                }



            }
        }
        graphBuilding.stopTimer();

        //graph.printEdges(); "Samuel L  Jackson"
        graph.search("Brad Pitt", "Joe Nunez" );
        graph.search("Asa Butterfield", "Paul Dano" );

        fileReading.printTimerName();
        graphBuilding.printTimerName();
        overall.stopTimer();
        overall.printTimer();

    }
}
