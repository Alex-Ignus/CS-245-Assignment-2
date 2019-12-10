import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
/**
 * Main A2 Class to run and manage code execution and user interaction
 *
 * @author Alexander Wojcik
 * @author University of San Francisco
 * @version Fall 2019
 */
class A2 {
    public static void main ( String[] args ) throws IOException {


        //to store flag/value pairs for FILE io
        HashMap < String, Path > argumentMap = new HashMap <> ( );
        //For reading in lines
        TreeSet < Path > files;

        try {
            files = FileFinder.fileFinder ( Paths.get ( System.getProperty ( "user.dir" ) ) , ".csv" );
            if ( files != null ) {
                for ( Path file : files ) {
                    //noinspection SpellCheckingInspection
                    if ( file.toString ( ).contains ( "tmdb_5000_credits.csv" ) ) {
                        argumentMap.put ( "input" , file );
                    }
                }
            }
        }
        catch ( IOException e ) {
            e.printStackTrace ( );
        }
        if ( argumentMap.containsKey ( "input" ) ) {

            CSV csv = new CSV ( );
            HashMap < String, HashSet < String > > movieCast = new HashMap <> ( csv.csvReader ( argumentMap.get ( "input" ) ) );

            GraphAdj graph = new GraphAdj ( );

            //builds the graph from a set of actors for a given movie
            if ( movieCast.keySet ( ).size ( ) > 0 ) {
                for ( String movie : movieCast.keySet ( ) ) {
                    if ( ! movieCast.get ( movie ).equals ( null ) ) {
                        graph.addAll ( movieCast.get ( movie ) );
                    }
                }
            }

            //use to get a list of all actors
            //print(graph.getLookUpMap());

            BufferedReader br = new BufferedReader ( new InputStreamReader ( System.in ) );
            String actorA;
            String actorB;



                    System.out.println ( "Actor 1 name: " );
                    actorA = br.readLine ( );
                    System.out.println ( "Actor 2 name: " );
                    actorB = br.readLine ( );
                    graph.search ( actorA.toLowerCase ( ).strip () , actorB.toLowerCase ( ).strip () );



//                searchTimer.startTimer();
//                graph.search("Brad Pitt", "Joe Nunez" );
//                graph.search("Asa Butterfield", "Paul Dano" );
//                searchTimer.stopTimer();
//                searchTimer.printTimerName();


            }





    }
    //helper functions to output a text file of all actors
    private static void print ( HashMap < String, GraphAdj.Actor > output ) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter (
                Paths.get ( System.getProperty ( "user.dir" ) + "/output.text" ) ,
                StandardCharsets.UTF_8
                                                        );
        for ( String word : output.keySet ( ) ) {
            writer.write ( output.get ( word ).name );
            writer.write ( "\r\n" );
        }
        writer.close ( );
    }

}
