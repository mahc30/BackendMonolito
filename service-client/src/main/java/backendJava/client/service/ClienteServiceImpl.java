package backendJava.client.service;

import backendJava.client.dto.ClienteDTO;
import backendJava.client.dto.ClienteMapper;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.exception.Cliente.ClienteAlreadyExistsException;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
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
    public List<ClienteDTO> listAllCliente() {
        return ClienteMapper.INSTANCE.mapEntityListToDtoList(clienteRepository.findAll());
    }

    @Override
    public ClienteDTO getCliente(Long id) {
        return ClienteMapper.INSTANCE.clienteToClienteDto(clienteRepository.findById(id).orElse(null));
    }

    @Override
    public ClienteDTO createCliente(Cliente cliente) {
        Cliente clienteDB = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());
        if(clienteDB != null) throw new ClienteAlreadyExistsException(clienteDB.getTipoIdentificacion(), clienteDB.getNumeroIdentificacion());
        return ClienteMapper.INSTANCE.clienteToClienteDto(clienteRepository.save(cliente));
    }

    @Override
    public ClienteDTO updateCliente(Cliente cliente) {

        Cliente clienteDB = clienteRepository.findById(cliente.getId()).orElse(null);
        if(clienteDB == null) throw new ClienteNotFoundException(cliente.getTipoIdentificacion(), cliente.getNumeroIdentificacion());

        clienteDB.setNombres(cliente.getNombres());
        clienteDB.setApellidos(cliente.getApellidos());
        clienteDB.setEdad(cliente.getEdad());
        clienteDB.setCiudad(cliente.getCiudad());
        clienteDB.setTipoIdentificacion(cliente.getTipoIdentificacion());
        clienteDB.setNumeroIdentificacion(cliente.getNumeroIdentificacion());
        clienteDB.setFotoMongoId(cliente.getFotoMongoId());

        return ClienteMapper.INSTANCE.clienteToClienteDto(clienteRepository.save(clienteDB));
    }

    @Override
    public void deleteCliente(TipoIdentificacion tipoIdentificacion, String numeroIdentificacion) {

        Cliente clienteDB = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion);

        if(clienteDB == null) throw new ClienteNotFoundException(tipoIdentificacion, numeroIdentificacion);

        fotoRepository.deleteById(clienteDB.getFotoMongoId());
        clienteRepository.deleteById(clienteDB.getId());
    }

    @Override
    public ClienteDTO findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoId, String numeroId) {
        return ClienteMapper.INSTANCE.clienteToClienteDto(
                clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroId)
        );
    }

    @Override
    public List<ClienteDTO> findByEdadGreaterThanEqual(int edad) {
        return ClienteMapper.INSTANCE.mapEntityListToDtoList(clienteRepository.findByEdadGreaterThanEqual(edad));
    }
}
