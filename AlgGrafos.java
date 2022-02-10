import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class AlgGrafos{

    public static void main(String[] args) throws FileNotFoundException{

        String path = "./myfiles/grafo.txt";
        Grafo grafo = new Grafo(path);

        System.out.println(lexBFS(grafo));
        System.out.println(grafo);
        System.out.println(isCordal(grafo));
        System.out.println(grafo);
       
    }
    
    // Computa a Busca em Largura Lexicográfica
    static ArrayList<Vertice> lexBFS(Grafo grafo){
        int n = grafo.getNumeroVertices();
        Set<Vertice> vertices = grafo.getVertices();
        ArrayList<Vertice> lista = new ArrayList<Vertice>();        

        // Guarda os valores lexicográficos R[v] de cada vértice v
        HashMap<Vertice,Integer> R = iniciaR(grafo);
        // Controla a marcação dos vertices
        HashMap<Vertice,Boolean> marcacao = iniciaMarcacao(grafo);

        // Variáveis auxiliares
        boolean isMarcado;
        int atualR;
        HashSet<Vertice> vizinhos;
        Vertice v;

        for(int j = n; j > 0; j--){
            
            // Computa o maior R[v] onde v não está marcado
            v = rLexMax(vertices, R, marcacao);
            // Marca v
            marcacao.replace(v, true);
            // Coloca na lista
            lista.add(v);
            
            vizinhos = v.getVizinhos();
            for(Vertice w : vizinhos){
                isMarcado = marcacao.get(w);
                // Se o vizinho não está marcado, atualiza seu valor lexicográfico
                if(isMarcado == false){
                    atualR = R.get(w);
                    /*Regra para atualização do valor: 
                    R[w] = R[w]*10 + j
                    Ex: 10 e 3 -> 103: 10*10 + 3 = 103*/
                    R.replace(w, atualR*10 + j);
                }
            }
        }

        return lista;

    }

    // Inicia o conjunto R, ou seja, todos os vértices possui valor lexicográfico igual a 0
    static HashMap<Vertice,Integer> iniciaR(Grafo grafo){
        HashSet<Vertice> vertices = grafo.getVertices();
        HashMap<Vertice,Integer> R = new HashMap<Vertice,Integer>();
        for(Vertice v: vertices){
            R.put(v,0);
        }   
        return R;
    }

    // Inicia a marcação, ou seja, todos os vértices não estão marcados
    static HashMap<Vertice,Boolean> iniciaMarcacao(Grafo grafo){
        HashSet<Vertice> vertices = grafo.getVertices();
        HashMap<Vertice,Boolean> marcacao = new HashMap<Vertice,Boolean>();
        for(Vertice v: vertices){
            marcacao.put(v,false);
        }   
        return marcacao;
    }

    // Método auxliar para o algoritmo de lexBFS
    static Vertice rLexMax(Set<Vertice> vertices, HashMap<Vertice,Integer> R ,HashMap<Vertice,Boolean> marcacao){
        // Suponhamos que maior valor no conjunto R é 0 no começo
        int maiorR = 0;     
        // R atual visualizado
        int atualR = 0; 

        // Vertice que será retornado
        // Iniciamos com um id arbitrário
        Vertice vertice = new Vertice(0);

        // Inicia variável
        boolean isMarcado = false;

        for(Vertice v : vertices){
            // Pega o valor R[v] de um certo vértice v
            atualR = R.get(v);
            isMarcado = marcacao.get(v);
            /* Se R[v] possui o maior valor lexicografico
            e não está marcado, então é o candidato para ser colocado na lista */
            if(atualR >= maiorR && isMarcado == false){
                maiorR = atualR;
                vertice = v;
            }
        }

       return vertice;
    }


    static boolean induzClique(Vertice vertice){
        HashSet<Vertice> vizinhos = vertice.getVizinhos();
        /*Para cada vizinho do vertice que será retirado
        checamos se eles são vizinhos entre si, ou seja, existe um clique */
        for(Vertice v : vizinhos){
            for(Vertice w : vizinhos){
                // Um vértice não pode ser vizinho de si mesmo
                if(v.equals(w) == false){
                    // Se não é vizinho, já retornamos como falso
                    if(v.isVizinho(w) == false){
                        return false;
                    }
                }
            }            
        }

        return true;
    }

    // Checa se todos os vértices no EEP são simpliciais 
    static boolean checaVerticesSimpliciais(Grafo grafo, ArrayList<Vertice> lista){
        // Tomamos a ordem reversa da lista, ou seja, o possível EEP
        Collections.reverse(lista);

        for(Vertice v : lista){
            if (induzClique(v) == false){
                // Vertice removida para printar subgrafo
                grafo.removeVertice(v);
                return false;
            } 

            // Removemos o vértice posteriormente para checagem ocorra corretamente
            grafo.removeVertice(v);
        }   

        return true;

    }

    // Checa se o grafo é cordal
    static boolean isCordal(Grafo grafo){
        ArrayList<Vertice> lista = lexBFS(grafo);
        return checaVerticesSimpliciais(grafo, lista);
    }
    
}