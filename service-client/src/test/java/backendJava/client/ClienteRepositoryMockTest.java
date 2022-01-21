package backendJava.client;

import backendJava.client.entity.Ciudad;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.repository.ClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ClienteRepositoryMockTest {

    private List<Cliente> clientes;

    @Autowired
    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setup(){
        clientes.add(Cliente.builder()
                .nombres("Juanito")
                .apellidos("Alcachofa")
                .numeroIdentificacion("188941981")
                .fotoMongoId("TODO")
                .ciudad(Ciudad.Cali)
                        .edad(18)
                .tipoIdentificacion(TipoIdentificacion.CC)
                .build());

        clientes.add(Cliente.builder()
                .nombres("Brian")
                .apellidos("AAAA")
                .numeroIdentificacion("5326262")
                .fotoMongoId("TODO")
                .ciudad(Ciudad.Medellin)
                .edad(30)
                .tipoIdentificacion(TipoIdentificacion.CC)
                .build());

        clientes.add(Cliente.builder()
                .nombres("Olaf")
                .apellidos("SE")
                .numeroIdentificacion("3757634")
                .fotoMongoId("TODO")
                .ciudad(Ciudad.Cali)
                .edad(37)
                .tipoIdentificacion(TipoIdentificacion.CC)
                .build());

        clientes.forEach(cliente -> {clienteRepository.save(cliente);});
    }

    @Test
    public void whenFindByTipoIdentificacion_thenReturnListCliente(){

        Cliente founds = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(clientes.get(0).getTipoIdentificacion(), clientes.get(0).getNumeroIdentificacion());

        Assertions.assertThat(founds).isNotNull();
        Assertions.assertThat(founds.getNumeroIdentificacion()).isEqualTo("188941981");
    }

    @Test
    public void whenFilterByEdad_ThenReturnListCliente(){
        List<Cliente> founds = clienteRepository.findByEdadGreaterThan(20L);

        Assertions.assertThat(founds.size()).isEqualTo(2);
        Assertions.assertThat(founds.get(1).getNumeroIdentificacion()).isEqualTo("3757634");
    }

}
