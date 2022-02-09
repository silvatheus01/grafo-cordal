import java.util.HashSet;
import java.util.Set;

public class Grafo {
    private Set<Vertice> vertices;

    public Grafo(){
        this.vertices = new HashSet<Vertice>();
    }

    public void addVertice(Vertice vertice){
        this.vertices.add(vertice);
    }


    public Set<Vertice> getVertices(){
        return this.vertices;
    }

    public int getNumeroVertices(){
        return this.vertices.size();
    }

    
    // Checa se um vértice está no grafo
    /*public boolean contemVertice(int id){
        Vertice vertice_aux = new Vertice(id);
        if(this.vertices.contains(vertice_aux)) return true;
        else return false;

    }*/


}
