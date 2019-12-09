import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSV {
    private HashMap<String, HashSet<String>> movieCast = new HashMap<>();
    private HashSet<String> allActors = new HashSet<>();

    public HashMap<String, HashSet<String>> csvReader() {
        try (BufferedReader br = Files.newBufferedReader(Paths.get("C:\\Users\\axel\\Documents\\GitHub\\CS-245-Assignment-2\\src\\tmdb_5000_credits.csv"),
                StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\\\"{2}", "\"");
                cleanString(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // cleanList(records);
        return movieCast;

    }

    public void print(List<List<Object>> records) {
        for (int l = 0; l < 3; l++) {
            System.out.println(l);
            for (int i = 0; i < records.get(l).size(); i++) {
                System.out.print(records.get(l).get(i) + ",");
            }
        }
    }

    public void cleanString(String line) {
        SaveMovieTitle(line);
    }

    public void SaveMovieTitle(String line) {
        String movie = "";
        Pattern pattern = Pattern.compile("([^\\[]*)");
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            movie = (matcher.group(1).split(","))[0].strip();
            movieCast.putIfAbsent(movie, getJson(line));
        }
    }

    public HashSet<String> getJson(String line) {
            String s;
            String regex = "(\".\")";
            Pattern pattern = Pattern.compile("(?:[^\\[]*)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                s = matcher.replaceFirst("");
                String[] jsons = s.split(regex);
                for (String x : jsons) {
                    if (x.contains("cast")) {
                        return cleanJson(x.split(","), "name");
                    }
                }
            }
        return new HashSet<>();
    }

    public HashSet<String> cleanJson (String[]json, String key){
        HashSet<String> actorName = new HashSet<>();
        for (String x : json) {
            if (x.contains(key)) {
                actorName.add((x.replaceAll("\"name\"|[^\\p{IsAlphabetic}\\p{IsDigit}]", " ")).strip());
            }
        }
        allActors.addAll(actorName);
        return actorName;

    }

        public void printMovies () {
            for (String movie : movieCast.keySet()) {
                System.out.println("Movie Title: " + movie);
            }
        }

        public void printActors(){
            for (String actor :allActors) {
                System.out.println("Actors' name: " + actor);
            }
        }
        public void printMoviesandCast(){
            for (String movie : movieCast.keySet()) {

                    if(movieCast.containsKey(movie) && movie.equals("49444")){
                        System.out.println("Movie Title: " + movie + "| Movie Cast: " + movieCast.get(movie).toString());
                    }
            }
        }


    }


