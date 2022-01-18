package backendJava.client.service;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService{
    private final ClienteRepository clienteRepository;

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
        cliente.setFoto("CREATED BUT NEEDS FOTO");
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente updateCliente(Cliente cliente) {

        if(cliente == null) return null;

        cliente.setNombres(cliente.getNombres());
        cliente.setApellidos(cliente.getApellidos());
        cliente.setEdad(cliente.getEdad());
        cliente.setCiudad(cliente.getCiudad());
        cliente.setTipoIdentificacion(cliente.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(cliente.getNumeroIdentificacion());

        return clienteRepository.save(cliente);
    }

    @Override
    public void deleteCliente(Long id) {
        Cliente clienteDB = getCliente(id);
        if(clienteDB != null) clienteRepository.deleteById(id);
    }

    @Override
    public Cliente findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoId, String numeroIdentificacion) {
        return clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroIdentificacion);
    }
}
