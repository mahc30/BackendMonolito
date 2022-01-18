package backendJava.client;

import backendJava.client.entity.Ciudad;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.repository.ClienteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class ClienteRepositoryMockTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void whenFindByTipoIdentificacion_thenReturnListCliente(){
        Cliente cliente1 = Cliente.builder()
                .nombres("Juanito")
                .apellidos("Alcachofa")
                .numeroIdentificacion("188941981")
                .foto("TODO")
                .ciudad(Ciudad.builder().id(1L).build())
                .tipoIdentificacion(TipoIdentificacion.builder().id(2L).build())
                .build();

        clienteRepository.save(cliente1);


        Cliente founds = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(cliente1.getTipoIdentificacion(), cliente1.getNumeroIdentificacion());

        Assertions.assertThat(founds).isNotNull();
        Assertions.assertThat(founds.getNumeroIdentificacion()).isEqualTo("188941981");
    }


}
