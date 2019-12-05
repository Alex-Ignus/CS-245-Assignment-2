import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSV {

  public List<List<String>> csvReader(){
      List<List<String>> records = new ArrayList<>();
      try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\axel\\Documents\\GitHub\\CS-245-Assignment-2\\src\\tmdb_5000_credits.csv"))) {
          String line;
          while ((line = br.readLine()) != null) {
              String[] values = line.split(",");
              records.add(Arrays.asList(values));
          }
      } catch (IOException e) {
          e.printStackTrace();
      }

      for(int l = 0; l < 3; l++){
          System.out.println(l);
         for(int i = 0; i < records.get(l).size(); i++){
             System.out.print(records.get(l).get(i) + ", ");
         }
      }
      return records;

  }

  public void print(){

  }
}
