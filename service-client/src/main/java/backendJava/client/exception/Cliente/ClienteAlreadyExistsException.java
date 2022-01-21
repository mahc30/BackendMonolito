package backendJava.client.exception.Cliente;

import backendJava.client.entity.TipoIdentificacion;

public class ClienteAlreadyExistsException extends RuntimeException{
    public ClienteAlreadyExistsException(TipoIdentificacion tipoId, String numeroId) {
        super(String.format("Client with TypeId: %s number: %s Already Exists", tipoId, numeroId));
    }
}
