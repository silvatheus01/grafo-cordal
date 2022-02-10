public class GrafoInvalidoException extends Exception{
    public GrafoInvalidoException(String mensagem){
        super("O grafo é inválido: " + mensagem);
    }
}
