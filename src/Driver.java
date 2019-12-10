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
 * Main Driver Class to run and manage code execution and user interaction
 *
 * @author Alexander Wojcik
 * @author University of San Francisco
 * @version Fall 2019
 */
class Driver {
    public static void main ( String[] args ) throws IOException {
        //Creates the main time
        Timer overall = new Timer ( );
        // store initial start time
        overall.startTimer ( );

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
            Timer fileReading = new Timer ( "Reading the CSV" );
            fileReading.startTimer ( );
            HashMap < String, HashSet < String > > movieCast = new HashMap <> ( csv.csvReader ( argumentMap.get ( "input" ) ) );
            fileReading.stopTimer ( );
            fileReading.printTimerName ();

            GraphAdj graph = new GraphAdj ( );
            Timer graphBuilding = new Timer ( "Building the Graph" );
            graphBuilding.startTimer ( );

            //builds the graph from a set of actors for a given movie
            if ( movieCast.keySet ( ).size ( ) > 0 ) {
                for ( String movie : movieCast.keySet ( ) ) {
                    if ( ! movieCast.get ( movie ).equals ( null ) ) {
                        graph.addAll ( movieCast.get ( movie ) );
                    }
                }
            }
            graphBuilding.stopTimer ( );
            graphBuilding.printTimerName ( );

            //use to get a list of all actors
            //print(graph.getLookUpMap());

            BufferedReader br = new BufferedReader ( new InputStreamReader ( System.in ) );
            String accStr;
            String actorA;
            String actorB;
            boolean play = true;
            Timer searchTimer = new Timer ( "Searching the Graph" );
            System.out.println (  );
            System.out.println ( "Would you like to play Connect the Celebrities? Y/N" );

            while ( play )//noinspection SpellCheckingInspection,SpellCheckingInspection
            {
                accStr = br.readLine ( );
                if ( accStr.toLowerCase ( ).contains ( "y" ) ) {
                    System.out.println ( "What is the name of the first Celebrity?" );
                    actorA = br.readLine ( );
                    System.out.println ( "What is the name of the Second Celebrity?" );
                    actorB = br.readLine ( );
                    searchTimer.startTimer ( );
                    graph.search ( actorA.toLowerCase ( ).strip () , actorB.toLowerCase ( ).strip () );
                    searchTimer.stopTimer ( );
                    searchTimer.printTimerName ( );
                    System.out.println ( "Would you like to search again" );
                }
                else if ( accStr.toLowerCase ( ).contains ( "n" ) ) {
                    System.out.println ( "Eh, I have other things I need to work on too -_-" );
                    play = false;
                }
                else {
                    //noinspection SpellCheckingInspection,SpellCheckingInspection
                    System.out.println ( "Hmmm invalid input...wanna try that again:" );
                }


//                searchTimer.startTimer();
//                graph.search("Brad Pitt", "Joe Nunez" );
//                graph.search("Asa Butterfield", "Paul Dano" );
//                searchTimer.stopTimer();
//                searchTimer.printTimerName();


            }

        }


        overall.stopTimer ( );
        overall.printTimer ( );

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
