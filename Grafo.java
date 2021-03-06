import java.util.HashSet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Grafo {
    private HashSet<Vertice> vertices;
    private String path;

    public Grafo(String path) throws FileNotFoundException, GrafoInvalidoException{
        this.vertices = new HashSet<Vertice>();
        // Caminho do arquivo que possui os vértices e as arestas
        this.path = path;

        FileReader fr = null;

        try{  
            // O . serve pra dizer que está na mesma pasta
            fr = new FileReader(this.path);
        }catch(FileNotFoundException e){
            // Caso o arquivo não exista
            throw new FileNotFoundException("O arquivo de entrada para o grafo não existe.");
        }
        
        Scanner sc = new Scanner(fr);

        // Continua enquanto não chegar na última linha
        while(sc.hasNextLine()){
            // Linha lida até \n
            String linha = sc.nextLine();
            //Lista de números que serão os ids dos vértices
            ArrayList<String> lista = new ArrayList<String>(Arrays.asList(linha.split(" ")));
            // Remove o caractere "=" que não é importante
            lista.remove("=");

            // Vertice que terá seus vizinhos neste momento
            // Seu id é o número no começo da linha
            Vertice v;

            // O ID de um vértice precisa ser um número inteiro
            try {
                v = new Vertice(Integer.parseInt(lista.get(0)));
            } catch (NumberFormatException e) {
                throw new GrafoInvalidoException("O ID de um vértice precisa ser um número.");
            }

            // O ID de um vértice precisa ser um número inteiro maior do que 0
            if(v.getId() <= 0){
                throw new GrafoInvalidoException("O ID de um vértice precisa ser um número maior do que 0.");
            }
            
            // Remove o id do vértice atual que terá seus vizinhos computados
            lista.remove(0);

            // Checa se o vertice já existe no grafo
            if(this.contemVertice(v)){
                // Se existir, trabalhamos com a instância que já existe.
                v = this.getVertice(v);
            }else{
                this.addVertice(v);
            }
            
            // Para cada vizinho na lista
            for (String str : lista){
                Vertice w;

                // O ID de um vértice precisa ser um número inteiro
                try {
                    w = new Vertice(Integer.parseInt(str));
                } catch (NumberFormatException e) {
                    throw new GrafoInvalidoException("O ID de um vértice precisa ser um número.");
                }
    
                // O ID de um vértice precisa ser um número inteiro maior do que 0
                if(w.getId() <= 0){
                    throw new GrafoInvalidoException("O ID de um vértice precisa ser um número maior do que 0.");
                }

                // Checa se o vertice já existe no grafo
                if(this.contemVertice(w)){
                    // Se existir, trabalhamos com a instância que já existe.
                    w = this.getVertice(w);
                }else{
                    this.addVertice(w);  
                }

                v.addVizinho(w);                
            }            
        }

        sc.close();

        HashSet<Vertice> vertices = this.vertices;

        // Testa se o grafo é direcionado
        for(Vertice v : vertices){
            HashSet<Vertice> vizinhos = v.getVizinhos();
            for (Vertice w : vizinhos) {
                /*Se v "aponta" para w, w também precisa "apontar" para v
                para o grafo ser não direcionado.*/
                if(w.isVizinho(v) == false){
                    throw new GrafoInvalidoException("O grafo é direcionado.");
                }   
            }
        }

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
        // Se o número de vértices for maior que 0, imprimimos os vértices
        if(this.getNumeroVertices() > 0){
            for(Vertice v : this.vertices){
                out += v.toString() + " = " ;
                HashSet<Vertice> vizinhos = v.getVizinhos();
                for(Vertice w : vizinhos){
                    out += w.toString() + " ";
                }
    
                out += "\n";
            }
            // Retiramos a linha sem caracteres que ficaria no final devido ao "\n"
            return out.substring(0,out.length()-1);
        }else{
            return "Grafo vazio";
        }           
    }

    
    // Checa se um vértice está no grafo
    public boolean contemVertice(Vertice vertice){
        if(this.vertices.contains(vertice)) return true;
        else return false;
    }

    public void removeVertice(Vertice vertice){
        // Remove o vertice do conjunto
        this.vertices.remove(vertice);

        HashSet<Vertice> vizinhos;

        // Remove vertice dos vizinhos de todos os vertices do conjunto
        for(Vertice v : this.vertices){
            vizinhos = v.getVizinhos();
            // Testa se v possui vizinhos
            if(vizinhos.isEmpty() == false){
                Iterator<Vertice> iter = vizinhos.iterator();
                /* Enquanto possuir vizinhos para serem analisados,
                checamos se o vertice que será removido é um deles
                */
                while(iter.hasNext()){
                    if(iter.next().equals(vertice)){
                        iter.remove();
                    }
                }                
            }   
        }
    }

}
