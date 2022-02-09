import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class AlgGrafos{
    
    // Grafo deve ser conexo
    ArrayList<Vertice> lexBFS(Grafo grafo){
        int n = grafo.getNumeroVertices();
        Set<Vertice> vertices = grafo.getVertices();
        Vertice v;
        ArrayList<Vertice> lista = new ArrayList<Vertice>();        

        // Vizinhos w de um vertice v
        HashSet<Vertice> vizinhos;

        for(int j = n; j > 0; j--){
            
            v = rLexMax(vertices);
            v.marcaVertice();
            lista.add(v);
            
            vizinhos = v.getVizinhos();
            for(Vertice w : vizinhos){
                if(w.isMarcado() == false){
                    w.setR(j);
                }
            }
        }

        return lista;

    }


    Vertice rLexMax(Set<Vertice> vertices){
        // Proximo vertice
        // Suponhamos que menorR é 0 no começo
        int maiorR = 0;     
        // R atual visualizado
        int atualR; 

        // Vertice que será retornado
        // Iniciamos que um valor arbitrário
        Vertice vertice = new Vertice(0);

        for(Vertice v : vertices){
            atualR = v.getR();
            if(atualR >= maiorR && v.isMarcado() == false){
                maiorR = atualR;
                vertice = v;
            }
        }

       return vertice;
    }
    
}