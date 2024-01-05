import java.util.ArrayList;

public class Node {
    ArrayList<edge> edges = new ArrayList<>();
    public void print(){
        for(int i=0; i<edges.size(); i++){
            edges.get(i).print();
        }
    }
}
