package backendJava.client.repository;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public Cliente findByTipoIdentificacionAndNumeroIdentificacion(TipoIdentificacion tipoId, String numeroIdentificacion);

    public List<Cliente> findByEdadGreaterThan(int edad);
}
