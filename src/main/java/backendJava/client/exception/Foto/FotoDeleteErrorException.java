package backendJava.client.exception.Foto;

public class FotoDeleteErrorException extends RuntimeException{
    public FotoDeleteErrorException(String id){
        super(String.format("Ocurrió un error al eliminar la foto con id %s El archivo no se ha eliminado", id));
    }
}
