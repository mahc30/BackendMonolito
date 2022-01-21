package backendJava.client.exception.Cliente;

import backendJava.client.entity.TipoIdentificacion;

public class ClienteNotFoundException extends RuntimeException{
        public ClienteNotFoundException(TipoIdentificacion tipoId, String numeroId) {

            super(String.format("Client with TypeId: %s number: %s not found", tipoId.toString(), numeroId));
        }
}
