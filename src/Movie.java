public class Movie {

    //id's
    private int movieID;
    private String title;

    //basic constructor
    public Movie (int movieID, String title){
        this.movieID = movieID;
        this.title = title;
    }

    void printTitle(){
        System.out.println("Movie Title: " + this.title);
    }
    void printMovieID(){
        System.out.println("Movie ID: " + this.movieID);
    }

}
