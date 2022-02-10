import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;

public class AlgGrafos{

    public static void main(String[] args) throws FileNotFoundException, GrafoInvalidoException{

        String path = "./myfiles/grafo.txt";
        Grafo grafo = new Grafo(path);

        int resultado = isCordal(grafo);
        if(resultado == -1){
            System.out.println("O grafo é cordal.");
        }else{
            System.out.println("O grafo não é cordal pois o vértice "
             + resultado + " não é simplicial no possível EEP.\nSubgrafo restante:\n" 
             + grafo.toString());                      
        } 
    }
    
    // Computa a Busca em Largura Lexicográfica (LexBFS)
    static ArrayList<Vertice> lexBFS(Grafo grafo) throws GrafoInvalidoException{

        // Testa se o grafo é conexo, pois estamos supondo que a entrada deve ser um grafo conexo.
        if(isConexo(grafo) == false){
            throw new GrafoInvalidoException("O grafo não é conexo.");
        }

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
                // Tomamos que um vértice não pode ser vizinho de si mesmo
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

    // Checa se todos os vértices no possível EEP são simpliciais 
    static int checaVerticesSimpliciais(Grafo grafo, ArrayList<Vertice> lista){
        // Tomamos a ordem reversa da lista, ou seja, o possível EEP
        Collections.reverse(lista);

        for(Vertice v : lista){
            if (induzClique(v) == false){
                // Se o grafo não é cordal, retornamos o vértice que não é simplicial.
                return v.getId();
            } 

            // Removemos o vértice posteriormente para checagem ocorra corretamente
            grafo.removeVertice(v);
        }   
        
        // Retorna -1 se o grafo é cordal
        return -1;

    }

    /* Checa se o grafo é cordal. Se for, retornamos -1.
    Senão, retornamos o id do vértice que não é simplicial.*/
    static int isCordal(Grafo grafo) throws GrafoInvalidoException{
        ArrayList<Vertice> lista = lexBFS(grafo);
        return checaVerticesSimpliciais(grafo, lista);
    }

    // Testa se o grafo é conexo através de um algoritmo de BFS (Busca em Largura) modificado. 
    static boolean isConexo(Grafo grafo){
        // Responsável por guardar o número de vértices alcançados
        int contador = 0;
        // Guarda as cores de cada vértice.
        HashMap<Vertice,Cor> verticesCores = new  HashMap<Vertice,Cor>();
        HashSet<Vertice> vertices = grafo.getVertices();

        // Pinta todos os vértices de branco
        for(Vertice v: vertices){
            verticesCores.put(v,Cor.BRANCO);
        }

         // Pega um vertice arbitrário no conjunto de todos os vértices do grafo.
        ArrayList<Vertice> aux = new ArrayList<Vertice>(vertices);
        Vertice s = aux.get(0);
        
        verticesCores.replace(s, Cor.CINZENTO);

        Queue<Vertice> fila = new ArrayDeque<Vertice>();
        fila.add(s);

        // Varuáveis auxiliares
        Vertice u;
        HashSet<Vertice> vizinhos;


        while(fila.isEmpty() == false){
            u = fila.remove();
            vizinhos = u.getVizinhos();
            for(Vertice w : vizinhos){
                if(verticesCores.get(w) == Cor.BRANCO){
                    verticesCores.replace(w, Cor.CINZENTO);
                    fila.add(w);
                }
            }
            verticesCores.replace(u, Cor.PRETO);
            // Incrementa o número de vértices alcançados
            contador++;
        }
        
        /* Se o número de vértices alcançados for o mesmo número de vértices do grafo,
        o grafo é conexo, senão, não é.
        */
        return grafo.getNumeroVertices() == contador;
    }
}