package backendJava.client.service;

import backendJava.client.entity.Foto;

import java.util.List;

public interface FotoService {
    public List<Foto> listAllFoto();

    public Foto getFoto(String id);
    public Foto createFoto(Foto foto);
    public Foto updateFoto(Foto foto);
    public void deleteFoto(String id);
}
