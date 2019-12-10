import java.time.Duration;
import java.time.Instant;
/**
 * Utility Class to create, start, stop and print out timers
 * Used to clock runtime for functions
 *
 * @author Alexander Wojcik
 * @author University of San Francisco
 * @version Fall 2019
 */
class Timer {
    private Instant overall;
    private double seconds;
    private String name;
    /**
     * Prints overall timer start
     */
    public Timer ( ) {
        System.out.println ( "Starting overall Program Time" );

    }
    /**
     * Prints custom timer start
     */
    public Timer ( String custom ) {
        this.name = custom;
        System.out.printf ( "Logging subroutine: %s start time.%n" , custom );
    }
    /**
     * Starts timer
     */
    public void startTimer ( ) {
        overall = Instant.now ( );
    }

    /**
     * Stops timer
     */
    public void stopTimer ( ) {
        Duration elapsed = Duration.between ( overall , Instant.now ( ) );
        seconds = (double) elapsed.toMillis ( )
                  / Duration.ofSeconds ( 1 ).toMillis ( );

    }
    /**
     * Prints Overall time
     */
    public void printTimer ( ) {
        System.out.printf ( "Overall Program Time: %f seconds%n" , seconds );
    }
    /**
     * Prints custom time
     */
    public void printTimerName ( ) {
        System.out.printf ( this.name + ": Time: %f seconds%n" , seconds );
    }
}
