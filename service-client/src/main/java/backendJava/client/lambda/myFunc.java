package backendJava.client.lambda;

import backendJava.client.entity.Cliente;
import backendJava.client.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.function.Supplier;

public class myFunc {
    @Autowired
    private ClienteService clienteService;

    @Bean
    public Supplier<List<Cliente>> clientes(){
        return () -> clienteService.listAllCliente();
    }
}
