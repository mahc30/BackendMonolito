package backendJava.client.service;

import backendJava.client.dto.FotoDTO;
import backendJava.client.entity.Foto;
import backendJava.client.entity.TipoIdentificacion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FotoService {
    public List<FotoDTO> listAllFoto();

    public FotoDTO getFoto(String id);
    public FotoDTO createFoto(TipoIdentificacion tipoId, String numeroId, MultipartFile file);
    public FotoDTO updateFoto(String id, MultipartFile file);
    public void deleteFoto(TipoIdentificacion tipoId, String numeroId, String id);
}
