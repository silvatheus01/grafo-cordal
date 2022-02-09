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

        System.out.println(grafo);
       
    }
    
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