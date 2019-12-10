import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
/**
 * Reads in CSV Files
 *
 * @author Alexander Wojcik
 * @author University of San Francisco
 * @version Fall 2019
 */
class CSV {

    private final HashMap < String, HashSet < String > > movieCast = new HashMap <> ( );
    private final HashSet < String > allActors = new HashSet <> ( );

    /**
     * Reads in the CSV files at the given location
     *
     * @param path the locations of the file
     * @return HashMap < String, HashSet < String > > Map from K = Movie-UID V = Cast Members
     * @throws IOException
     */
    public HashMap < String, HashSet < String > > csvReader ( Path path ) throws IOException  {
        ArrayList<String> fileLines = new ArrayList <> ();
        try (
                BufferedReader br = Files.newBufferedReader ( ( path ) ,
                                                              StandardCharsets.UTF_8 )
        ) {
            String line;
            while ( ( line = br.readLine ( ) ) != null ) {
                line = line.replaceAll ( "\"{2}" , "\"" );
                fileLines.add ( line );
            }
        }
        fileLines.forEach ( this :: saveMovieTitle );

        return new HashMap <  >(movieCast) ;
    }
    /**
     * Prints Each movie with its caster members
     *
     * @param records the locations of the file
     */
    public void print ( List < List < Object > > records ) {
        System.out.println ( 0 );
        records.get ( 0 ).stream ( ).map ( o -> o + "," ).forEachOrdered ( System.out :: print );
        System.out.println ( 1 );
        records.get ( 1 ).stream ( ).map ( o -> o + "," ).forEachOrdered ( System.out :: print );
        System.out.println ( 2 );
        records.get ( 2 ).stream ( ).map ( o -> o + "," ).forEachOrdered ( System.out :: print );
    }


    /**
     * Saves the UID of the movie from the CSV
     * and stores the cast members of that group
     *
     * @param movieData the line from the csv
     *
     */
    private void saveMovieTitle ( String movieData ) {
        String  movie;
        Pattern pattern = Pattern.compile ( "([^\\[]*)" );
        Matcher matcher = pattern.matcher ( movieData );
        if ( matcher.find ( ) ) {
            movie = ( matcher.group ( 1 ).split ( "," ) )[ 0 ].strip ( );
            movieCast.putIfAbsent ( movie , getJson ( movieData ) );
        }
    }

    /**
     * Pulls gets the names of all cast members from a given line
     *
     * @param line line read from file
     * @return HashSet < String > set of cast member from the movie
     */
    private HashSet < String > getJson ( String line ) {
        String  s;
        String  regex   = "(\".\")";
        Pattern pattern = Pattern.compile ( "(?:[^\\[]*)" );
        Matcher matcher = pattern.matcher ( line );
        if ( matcher.find ( ) ) {
            s = matcher.replaceFirst ( "" );
            String[] jsons = s.split ( regex );
            for ( String x : jsons ) {
                if ( x.contains ( "cast" ) ) {
                    return cleanJson ( x.split ( "," ) , "name" );
                }
            }
        }
        return new HashSet <> ( );
    }

    /**
     * Pulls all values from the provided key out of the json file
     *
     * @param  json json line read from file that needs to be cleaned
     * @param  key the key to retrieve values from json
     * @return HashSet < String > Data set of value from key
     */
    private HashSet < String > cleanJson ( String[] json , String key ) {
        HashSet < String > actorName = Arrays.stream ( json ).filter ( x -> x.contains ( key ) ).map
                ( x -> ( x.replaceAll ( "\"name\"|[^\\p{IsAlphabetic}\\p{IsDigit}]" , " " ) ).strip ( ) ).collect
                ( Collectors.toCollection ( HashSet ::new ) );
        allActors.addAll ( actorName );
        return actorName;

    }
    /**
     * prints all Movies
     */
    public void printMovies ( ) {
        for ( String movie : movieCast.keySet ( ) ) {
            System.out.println ( "Movie Title: " + movie );
        }
    }
    /**
     * prints all Actors
     */
    public void printActors ( ) {
        for ( String actor : allActors ) {
            System.out.println ( "Actors' name: " + actor );
        }
    }


}


