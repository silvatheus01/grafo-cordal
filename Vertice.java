import java.util.HashSet;
import java.util.Set;

public class Vertice {
    private int id;
    private HashSet<Vertice> vizinhos;
    private boolean marcado;
    private int r;

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

    /*public void marcaVertice(){
        this.marcado = true;
    }

    public boolean isMarcado(){
        return this.marcado;
    }

    public int getR(){
        return this.r;
    }

    public void setR(int valor){
        // Ex: 10 e 3 -> 103: 10*10 + 3 = 103
        this.r = this.r*10 + valor;
    }*/

    @Override
    public String toString(){
        return Integer.toString(this.id);
    }

    @Override
    public int hashCode() {
        //deve ser o mesmo resultado para um mesmo objeto, não pode ser aleatório
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        //se nao forem objetos da mesma classe sao objetos diferentes
        if(!(obj instanceof Vertice)) return false; 

        //se forem o mesmo objeto, retorna true
        if(obj == this) return true;

        // aqui o cast é seguro por causa do teste feito acima
        Vertice vertice = (Vertice) obj; 

        //aqui você compara a seu gosto, o ideal é comparar atributo por atributo
        return this.id == vertice.getId() /*&&
                this.r == vertice.getR() &&
                this.marcado == vertice.isMarcado()*/;
    }  

}
