package backendJava.client.service;

import backendJava.client.entity.Foto;
import backendJava.client.repository.FotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FotoServiceImpl implements FotoService{
    private final FotoRepository fotoRepository;

    @Override
    public List<Foto> listAllFoto() {
        return fotoRepository.findAll();
    }

    @Override
    public Foto getFoto(String id) {
        return fotoRepository.findById(id).orElse(null);
    }

    @Override
    public Foto createFoto(Foto foto) {
        return fotoRepository.save(foto);
    }

    @Override
    public Foto updateFoto(Foto foto) {
        Foto fotoDB = getFoto(foto.getId());
        if(fotoDB == null)return null;

        fotoDB.setFoto(foto.getFoto());
        fotoDB.setTipo_archivo(foto.getTipo_archivo());

        return fotoRepository.save(fotoDB);
    }

    @Override
    public void deleteFoto(String id) {
        Foto foto = getFoto(id);
        if(foto != null) fotoRepository.deleteById(id);
    }
}
