package backendJava.client.repository;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

@Repository
public interface FotoRepository  extends MongoRepository<Foto, String>{
    @Query("{id: '?0'}")
    Foto findItemById(String id);


    public long count();
}
