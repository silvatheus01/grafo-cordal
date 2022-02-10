import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class AlgGrafos{

    public static void main(String[] args) throws FileNotFoundException{

        FileReader fr = null;

        try{  
            // O . serve pra dizer que está na mesma pasta
            fr = new FileReader("./myfiles/grafo.txt");
        }catch(FileNotFoundException e){
            throw new FileNotFoundException("Arquivo de entrada não existe.");
        }
       
        Scanner sc = new Scanner(fr);
        Grafo grafo = new Grafo();

        // Enquanto não tiver chegado ao final da linha
        while(sc.hasNextLine()){
            // Linha lida até \n
            String linha = sc.nextLine();
            //Lista de números
            ArrayList<String> lista = new ArrayList<String>(Arrays.asList(linha.split(" ")));
            // Remove caractere "="
            lista.remove("=");

            // Vertice que terá seus vizinhos neste momento
            Vertice v = new Vertice(Integer.parseInt(lista.get(0)));
            lista.remove(0);

            // Caso o vertice já exista
            if(grafo.contemVertice(v)){
                // Trabalhamos com a instância que já existe
                v = grafo.getVertice(v);
            }else{
                grafo.addVertice(v);
            }
            

            for (String str : lista){
                Vertice w = new Vertice(Integer.parseInt(str));
                // Caso o vertice já exista
                if(grafo.contemVertice(w)){
                    // Trabalhamos com a instância que já existe
                    w = grafo.getVertice(w);
                }else{
                    grafo.addVertice(w);  
                }
                // O grafo é não direcionado
                v.addVizinho(w);  
                w.addVizinho(v); 
            }            

        }

        //System.out.println(grafo);
        System.out.println(lexBFS(grafo));
        System.out.println(grafo);
        System.out.println(isCordal(grafo));
        System.out.println(grafo);
       
    }
    
    // Grafo deve ser conexo
    static ArrayList<Vertice> lexBFS(Grafo grafo){
        int n = grafo.getNumeroVertices();
        Set<Vertice> vertices = grafo.getVertices();
        Vertice v;
        ArrayList<Vertice> lista = new ArrayList<Vertice>();        

        // Vizinhos w de um vertice v
        HashSet<Vertice> vizinhos;

        HashMap<Vertice,Integer> R = iniciaR(grafo);
        HashMap<Vertice,Boolean> marcacao = iniciaMarcacao(grafo);

        // Inicializa a variável
        boolean isMarcado;
        int atualR;

        for(int j = n; j > 0; j--){
            
            v = rLexMax(vertices, R, marcacao);
            // Marca v
            marcacao.replace(v, true);
            lista.add(v);
            
            vizinhos = v.getVizinhos();
            for(Vertice w : vizinhos){
                isMarcado = marcacao.get(w);
                if(isMarcado == false){
                    atualR = R.get(w);
                    R.replace(w, atualR*10 + j);
                }
            }
        }

        return lista;

    }

    static HashMap<Vertice,Integer> iniciaR(Grafo grafo){
        HashSet<Vertice> vertices = grafo.getVertices();
        HashMap<Vertice,Integer> R = new HashMap<Vertice,Integer>();
        for(Vertice v: vertices){
            R.put(v,0);
        }   
        return R;
    }

    // Inicia a marcação
    static HashMap<Vertice,Boolean> iniciaMarcacao(Grafo grafo){
        HashSet<Vertice> vertices = grafo.getVertices();
        HashMap<Vertice,Boolean> marcacao = new HashMap<Vertice,Boolean>();
        for(Vertice v: vertices){
            marcacao.put(v,false);
        }   
        return marcacao;
    }


    static Vertice rLexMax(Set<Vertice> vertices, HashMap<Vertice,Integer> R ,HashMap<Vertice,Boolean> marcacao){
        // Proximo vertice
        // Suponhamos que menorR é 0 no começo
        int maiorR = 0;     
        // R atual visualizado
        int atualR = 0; 

        // Vertice que será retornado
        // Iniciamos que um valor arbitrário
        Vertice vertice = new Vertice(0);

        // Inicia variável
        boolean isMarcado = false;

        for(Vertice v : vertices){
            // Pega o valor no conjunto R de um certo vértice v
            atualR = R.get(v);
            isMarcado = marcacao.get(v);
            if(atualR >= maiorR && isMarcado == false){
                maiorR = atualR;
                vertice = v;
            }
        }

       return vertice;
    }

    static boolean induzClique(Vertice vertice){
        HashSet<Vertice> vizinhos = vertice.getVizinhos();
        HashSet<Vertice> vv;
        for(Vertice v : vizinhos){
            for(Vertice w : vizinhos){
                if(v.equals(w) == false){
                    if(v.isVizinho(w) == false){
                        return false;
                    }
                }
            }

            /*// Vizinhos dos vizinhos
            vv = v.getVizinhos();
            for(Vertice w : vv){
                if(w.isVizinho(vertice) == false){
                    return false;
                }
            }*/
            
        }

        return true;
    }

    static boolean testaVerticesSimpliciais(Grafo grafo, ArrayList<Vertice> lista){
        // Tomamos a ordem reversa da lista
        Collections.reverse(lista);

        for(Vertice v : lista){
            
            if (induzClique(v) == false){
                grafo.removeVertice(v);
                return false;
            } 

            grafo.removeVertice(v);
        }   

        return true;


    }

    static boolean isCordal(Grafo grafo){
        ArrayList<Vertice> lista = lexBFS(grafo);
        return testaVerticesSimpliciais(grafo, lista);
    }
    
}