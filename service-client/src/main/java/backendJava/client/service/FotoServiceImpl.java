package backendJava.client.service;

import backendJava.client.dto.FotoDTO;
import backendJava.client.dto.FotoMapper;
import backendJava.client.entity.Cliente;
import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import backendJava.client.exception.Cliente.ClienteNotFoundException;
import backendJava.client.exception.Foto.FotoNotFoundException;
import backendJava.client.repository.ClienteRepository;
import backendJava.client.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FotoServiceImpl implements FotoService{
    private final FotoRepository fotoRepository;
    private final ClienteRepository clienteRepository;

    @Override
    public List<FotoDTO> listAllFoto() {
        return FotoMapper.INSTANCE.mapEntityListToDtoList(fotoRepository.findAll());
    }

    @Override
    public FotoDTO getFoto(String id) {
        return FotoMapper.INSTANCE.fotoToFotoDto(fotoRepository.findById(id).orElse(null));
    }

    @Override
    public FotoDTO createFoto(TipoIdentificacion tipoId, String numeroId, MultipartFile file) {

        Cliente clienteDB = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroId);
        if(clienteDB == null) throw new ClienteNotFoundException(tipoId, numeroId);

        Foto foto = Foto.builder().file(Foto.convertMultipartToBinary(file)).build();

        if(!clienteDB.getFotoMongoId().isEmpty()) this.deleteFoto(tipoId, numeroId, clienteDB.getFotoMongoId());

        foto = fotoRepository.save(foto);
        clienteDB.setFotoMongoId(foto.getId());
        clienteRepository.save(clienteDB);

        return FotoMapper.INSTANCE.fotoToFotoDto(foto);
    }

    @Override
    public FotoDTO updateFoto(String id, MultipartFile file) {

        Foto fotoDB = FotoMapper.INSTANCE.fotoDtoToFoto(this.getFoto(id));
        if(fotoDB == null) throw new FotoNotFoundException(id);

        fotoDB.setFile(Foto.convertMultipartToBinary(file));
        fotoDB = fotoRepository.save(fotoDB);

        return FotoMapper.INSTANCE.fotoToFotoDto(fotoDB);
    }

    @Override
    public void deleteFoto(TipoIdentificacion tipoId, String numeroId, String id) {
        Cliente clienteDB = clienteRepository.findByTipoIdentificacionAndNumeroIdentificacion(tipoId, numeroId);
        if(clienteDB == null) throw new ClienteNotFoundException(tipoId, numeroId);

        Foto foto = FotoMapper.INSTANCE.fotoDtoToFoto(this.getFoto(id));
        if(foto == null) throw new FotoNotFoundException(id);
        fotoRepository.deleteById(foto.getId());
        clienteDB.setFotoMongoId("");
        clienteRepository.save(clienteDB);
    }
}
