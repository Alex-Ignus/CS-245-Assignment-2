import java.util.HashMap;
import java.util.HashSet;

public class Driver {
    public static void main(String args []){

        HashMap<Integer, HashSet<Actor>> actors = new HashMap<>();
        actors.putIfAbsent((19995), new HashSet<>());
        actors.get(19995).add(new Actor(19995, "Avatar", 242, "5602a8a7c3a3685532001c9a", "Jake Sully", "Sam Worthington"));
        for(Integer x: actors.keySet()){
            System.out.println(actors.get(19995));
            for(Actor actor: actors.get(x) ){
                actor.print();
            }
        }
    }
}
