package backendJava.client.service;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;

import java.util.List;

public interface ClienteService {
    public List<Cliente> listAllCliente();
    public Cliente getCliente(Long id);
    public Cliente createCliente(Cliente cliente);
    public Cliente updateCliente(Cliente cliente);
    public void deleteCliente(TipoIdentificacion tipoId, String numeroIdentificacion);
    public Cliente findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoId, String numeroIdentificacion);
    public List<Cliente> findByEdadGreaterThan(int edad);

}
