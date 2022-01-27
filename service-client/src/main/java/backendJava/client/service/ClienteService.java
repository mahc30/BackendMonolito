package backendJava.client.service;

import backendJava.client.dto.ClienteDTO;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;

import java.util.List;

public interface ClienteService {
    public List<ClienteDTO> listAllCliente();
    public ClienteDTO getCliente(Long id);
    public ClienteDTO createCliente(Cliente cliente);
    public ClienteDTO updateCliente(Cliente cliente);
    public void deleteCliente(TipoIdentificacion tipoId, String numeroIdentificacion);
    public ClienteDTO findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoId, String numeroIdentificacion);
    public List<ClienteDTO> findByEdadGreaterThanEqual(int edad);

}
