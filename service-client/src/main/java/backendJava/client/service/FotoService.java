package backendJava.client.service;

import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FotoService {
    public List<Foto> listAllFoto();

    public Foto getFoto(String id);
    public Foto createFoto(TipoIdentificacion tipoId, String numeroIdentificacion, MultipartFile file);
    public Foto updateFoto(String id, MultipartFile file);
    public void deleteFoto(String id);
}
