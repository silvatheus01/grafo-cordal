import java.util.HashSet;

public class Vertice {
    private int id;
    private HashSet<Vertice> vizinhos;

    public Vertice(int id){
        this.id = id;
        this.vizinhos = new HashSet<Vertice>();
    }

    public int getId(){
        return this.id;
    }

    public void addVizinho(Vertice vertice){
        this.vizinhos.add(vertice);
    }

    public boolean isVizinho(Vertice vertice){
        return this.vizinhos.contains(vertice);
    }

    public HashSet<Vertice> getVizinhos(){
        return this.vizinhos;
    }

    public int getNumeroVizinhos(){
        return this.vizinhos.size();
    }

    public void removeVizinho(Vertice vertice){
        this.vizinhos.remove(vertice);
    }

    @Override
    public String toString(){
        return Integer.toString(this.id);
    }


    @Override
    public int hashCode() {
        // Deve ser o mesmo resultado para um mesmo objeto, não pode ser aleatório
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        //Se nao forem objetos da mesma classe sao objetos diferentes
        if(!(obj instanceof Vertice)) return false; 

        //Se forem o mesmo objeto, retorna true
        if(obj == this) return true;

        // aqui o cast é seguro por causa do teste feito acima
        Vertice vertice = (Vertice) obj; 

        // Dois vértices que possuem o mesmo id são iguais
        return this.id == vertice.getId() ;
    }  

}
