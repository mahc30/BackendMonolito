package backendJava.client.exception;

public class EmptyListException extends RuntimeException{
    public EmptyListException(){
        super("No se encontraron elementos");
    }
}
