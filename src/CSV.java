import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.org.json.simple.JSONObject;
import java.org.json.simple.parser.JSONParser;
import java.org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {
    private JsonHealper jsonText;

  public List<List<Object>> csvReader(){
      List<List<Object>> records = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader("/Users/awojcik/Documents/GitHub/CS 245 - Assignment 2/CS-245-Assignment-2/src/tmdb_5000_credits.csv"))) {
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
      for (List<Object> internal: records) {
          for(int i = 0; i < internal.size(); i++){
              if(internal.get(i).toString().contains("name")){
                  System.out.println(internal.get(i));
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
