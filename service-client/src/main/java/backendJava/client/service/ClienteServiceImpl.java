package backendJava.client.service;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.exception.Cliente.ClienteAlreadyExistsException;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
import backendJava.client.exception.EmptyListException;
import backendJava.client.repository.ClienteRepository;
import backendJava.client.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{
    private final ClienteRepository clienteRepository;
    private final FotoRepository fotoRepository;

    @Override
    public List<Cliente> listAllCliente() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getCliente(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente createCliente(Cliente cliente) {
        Cliente clienteDB = this.findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
        if(clienteDB != null) throw new ClienteAlreadyExistsException(clienteDB.getTipoIdentificacion(), clienteDB.getNumeroIdentificacion());

        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {

        Cliente clienteDB = getCliente(cliente.getId());
        if(clienteDB == null) throw new ClienteNotFoundException(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());

        clienteDB.setNombres(cliente.getNombres());
        clienteDB.setApellidos(cliente.getApellidos());
        clienteDB.setEdad(cliente.getEdad());
        clienteDB.setCiudad(cliente.getCiudad());
        clienteDB.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteDB.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        clienteDB.setFotoMongoId(cliente.getFotoMongoId());

        return clienteRepository.save(clienteDB);
    }

    @Override
    public void deleteCliente(TipoIdentificacion tipoIdentificacion, String numeroIdentificacion) {

        Cliente clienteDB = this.findByTipoIdentificacionAndNumeroIdentificacion(
                tipoIdentificacion,
                numeroIdentificacion);

        if(clienteDB == null) throw new ClienteNotFoundException(tipoIdentificacion, numeroIdentificacion);

        fotoRepository.deleteById(clienteDB.getFotoMongoId());
        if(clienteDB != null) clienteRepository.deleteById(clienteDB.getId());
    }

    @Override
    public Cliente findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoId, String numeroId) {
        Cliente cliente = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroId);
        return cliente;
    }

    @Override
    public List<Cliente> findByEdadGreaterThan(int edad) {
        List<Cliente> clientes = clienteRepository.findByEdadGreaterThan(edad);
        if(clientes.isEmpty()) throw new EmptyListException();
        return clientes;
    }


}
