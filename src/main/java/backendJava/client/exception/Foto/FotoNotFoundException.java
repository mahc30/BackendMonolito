package backendJava.client.exception.Foto;

public class FotoNotFoundException extends RuntimeException{
    public FotoNotFoundException(String id) {

        super(String.format("Foto with id %s not found",id));
    }
}
