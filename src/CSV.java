import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.org.json.simple.JSONObject;
import java.org.json.simple.parser.JSONParser;
import java.org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSV {
    private JsonHealper jsonText;

  public List<List<Object>> csvReader(){
      List<List<Object>> records = new ArrayList<>();
          try (BufferedReader br = Files.newBufferedReader(Paths.get("/Users/awojcik/Documents/GitHub/CS 245 - Assignment 2/CS-245-Assignment-2/src/tmdb_5000_credits.csv"),
                  StandardCharsets.UTF_8)) {
          Object line;
          while ((line = br.readLine()) != null) {
              Object[] values = ((String) line).split(",");
              records.add(Arrays.asList(values));
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
      cleanList(records);

      return records;

  }

  public void print(List<List<Object>> records){
      for(int l = 0; l < 3; l++){
          System.out.println(l);
          for(int i = 0; i < records.get(l).size(); i++){
              System.out.print(records.get(l).get(i) + ", ");
          }
      }
  }

  public void cleanList(List<List<Object>> records) {
      String s = "";
      String regex ="\\\"([^\\\"]*)\\\"\\W*";
      for (List<Object> internal: records) {
          for(int i = 0; i < internal.size(); i++){
              if(internal.get(i).toString().contains("name")){
                  s = internal.get(i).toString().replaceAll(regex," ");
                  System.out.println( s + " | my index: " + i);
                  
              }
          }
      }
  }
  public void cleanJson(){

  }
    public class JsonHealper {
        Object json;
        JSONObject objJSON;

        public JsonHealper(String json) throws ParseException {
            this.json = new JSONParser().parse(json);
            this.objJSON = (JSONObject) this.json;
        }

        public String getJSON(String key) {
            return (String) objJSON.get(key);
        }

        public Integer getJSON(Integer key) {
            return (Integer) objJSON.get(key);
        }

    }
}
