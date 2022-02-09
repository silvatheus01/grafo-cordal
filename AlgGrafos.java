import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
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

            for (String str : lista){
                Vertice w = new Vertice(Integer.parseInt(str));
                v.addVizinho(w);
            }            

            grafo.addVertice(v);

        }

        //System.out.println(grafo);
        System.out.println(lexBFS(grafo));
       
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
    
}