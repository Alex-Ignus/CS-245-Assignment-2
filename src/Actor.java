public class Actor extends Movie {

    //numeric IDs
    private int  castID;
    private String creditID;

    //titles and names
    private String characterName;
    private String actorName;

    public Actor(int movieID, String title) {
        super(movieID, title);
    }
    public Actor(int movieID, String title, int castID, String creditID, String characterName, String actorName) {
        super(movieID, title);
        this.castID = castID;
        this.creditID = creditID;
        this.characterName = characterName;
        this.actorName = actorName;

    }

    void print(){
        super.printTitle();
        System.out.println("Actors' Name: " + this.actorName);
        System.out.println("Characters' Name: " + this.characterName);
    }

}
