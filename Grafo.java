import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class Grafo {
    private HashSet<Vertice> vertices;

    public Grafo(){
        this.vertices = new HashSet<Vertice>();
    }

    public void addVertice(Vertice vertice){
        this.vertices.add(vertice);
    }


    public Vertice getVertice(Vertice vertice){
        ArrayList<Vertice> vertices = new ArrayList<Vertice>(this.vertices);
        int index = vertices.indexOf(vertice);
        return vertices.get(index);
    }

    public HashSet<Vertice> getVertices(){
        return this.vertices;
    }

    public int getNumeroVertices(){
        return this.vertices.size();
    }



    @Override
    public String toString(){
        String out = "";
        for(Vertice v : this.vertices){
            out += v.toString() + " : " ;
            HashSet<Vertice> vizinhos = v.getVizinhos();
            for(Vertice w : vizinhos){
                out += w.toString() + " ";
            }

            out += "\n";
        }

        return out;
    }

    
    // Checa se um vértice está no grafo
    public boolean contemVertice(Vertice vertice){
        if(this.vertices.contains(vertice)) return true;
        else return false;

    }

    public void removeVertice(Vertice vertice){
        
    }


}
