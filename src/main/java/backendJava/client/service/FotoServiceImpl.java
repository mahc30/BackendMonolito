package backendJava.client.service;

import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
import backendJava.client.exception.Foto.FotoDeleteErrorException;
import backendJava.client.exception.Foto.FotoNotFoundException;
import backendJava.client.repository.ClienteRepository;
import backendJava.client.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FotoServiceImpl implements FotoService{
    private final FotoRepository fotoRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public List<Foto> listAllFoto() {
        return fotoRepository.findAll();
    }

    @Override
    public Foto getFoto(String id) {
        Foto found = fotoRepository.findById(id).orElse(null);
        if(found == null) throw new FotoNotFoundException(id);
        return found;
    }

    @Override
    public Foto createFoto(TipoIdentificacion tipoId, String numeroId, MultipartFile file) {

        Cliente clienteDB = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroId);
        if(clienteDB == null) throw new ClienteNotFoundException(tipoId, numeroId);

        Foto foto = Foto.builder().file(Foto.convertMultipartToBinary(file)).build();
        this.deleteFoto(clienteDB.getFotoMongoId());

        foto = fotoRepository.save(foto);
        clienteDB.setFotoMongoId(foto.getId());
        clienteRepository.save(clienteDB);

        return foto;
    }

    @Override
    public Foto updateFoto(String id, MultipartFile file) {

        Foto fotoDB = this.getFoto(id);
        if(fotoDB == null) throw new FotoNotFoundException(id);

        fotoDB.setFile(Foto.convertMultipartToBinary(file));
        fotoDB = fotoRepository.save(fotoDB);

        return fotoRepository.save(fotoDB);
    }

    @Override
    public void deleteFoto(String id) {
        Foto foto = this.getFoto(id);
        fotoRepository.deleteById(foto.getId());
    }
}
