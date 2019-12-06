import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GraphImplementation{

    private int adjMatrix[][];
    private int numVertices;
    private Movie data;

    public GraphImplementation(int size) {
        this.numVertices = size;
        adjMatrix = new int[numVertices][numVertices];
    }

    public void addEdge(int x, int y) throws Exception {
        if (x <= numVertices && y <= numVertices && x >= 0 && y >= 0) {
            adjMatrix[x][y] = 1;
        } else {
            throw new Exception();
        }
    }


    private int firstFound(int[] arr) {
        return IntStream.range(0, arr.length).filter(i -> arr[i] == 0).findFirst().orElse(-1);
    }


    public List<Integer> neighbors(int vertex) throws Exception {
        if (vertex <= numVertices && vertex >= 0) {
            List<Integer> result = new ArrayList<Integer>();
            int i = 0;
            while (i < numVertices) {
                if (adjMatrix[vertex][i] == 1)
                    result.add(i);
                i++;
            }
            return result;
        } else {
            throw new Exception();
        }
    }
}