import java.time.Duration;
import java.time.Instant;

public class Timer {
    private Instant overall;
    private Duration elapsed;
    private double seconds;
    private String name;


    public Timer(){
        System.out.println("Starting overall Program Time");

    }
    public Timer(String custom){
        this.name = custom;
        System.out.println("Logging subroutine: " + custom +" start time.");
    }

    public void startTimer(){
        overall = Instant.now();
    }
    public void stopTimer(){
        elapsed = Duration.between(overall, Instant.now());
        seconds = (double) elapsed.toMillis()
                / Duration.ofSeconds(1).toMillis();

    }
    public void printTimer(){
        System.out.printf("Overall Program Time: %f seconds%n", seconds);
    }

    public void printTimerName(){
        System.out.printf(this.name + ": Time: %f seconds%n", seconds);
    }
}
