import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;

/**
 * Finds all files from a given file path
 *
 * @author Alexander Wojcik
 * @author University of San Francisco
 * @version Spring 2019
 */
class FileFinder {

    private static String uniqueEx;

    /**
     * reads the flag file path location pass path to fileFinder
     *
     * @param path the locations of the file
     * @return inputFile the set of files found in given location
     * @throws IOException
     */
    public static TreeSet < Path > fileFinder ( Path path , String extension ) throws IOException {
        final TreeSet < Path > inputFile = new TreeSet <> ( );
        uniqueEx = extension;
        fileFinder ( path , inputFile );
        return inputFile;
    }

    /**
     * reads the flag file path location pass path to fileFinder
     *
     * @param path the locations of the file
     * @return inputFile the set of files found in given location
     * @throws IOException
     */
    public static String fileFinder ( Path path ) throws IOException {
        String inputFile = null;
        fileFinder ( path , inputFile );
        return inputFile;
    }

    /**
     * reads files/ each file of a directory and adds those file paths to an
     * ArrayList of the class to pass to outputFiles
     *
     * @param path      the path to be checked
     * @param inputFile the masterList data structure
     * @throws IOException
     * @paramMap<String, String> argsMap: Map of flag/value pairs
     */
    private static void fileFinder ( Path path , Set < Path > inputFile )
            throws IOException {

        if ( isTextFile ( path ) && ! path.toString ( ).contains ( "out/production" ) ) {
            inputFile.add ( path );
        }
        else if ( Files.isDirectory ( path ) ) {
            try (
                    DirectoryStream < Path > listing = Files
                            .newDirectoryStream ( path )
            ) {
                for ( final Path file : listing ) {
                    fileFinder ( file , inputFile );
                }
            }
        }
    }


    /**
     * Checks if path is a file
     *
     * @param path the path to be checked
     * @return boolean if path is file or not
     **/
    private static boolean isTextFile ( Path path ) {
        String lower = path.toString ( ).toLowerCase ( );
        return (
                ! Files.isDirectory ( path ) && lower.endsWith ( ".txt" )
                || lower.endsWith ( ".text" ) || lower.endsWith ( getUniqueEx ( ) )
        );
    }

    private static String getUniqueEx ( ) {
        return uniqueEx;
    }

    public static void setUniqueEx ( String uniqueEx ) {
        FileFinder.uniqueEx = uniqueEx;
    }
}