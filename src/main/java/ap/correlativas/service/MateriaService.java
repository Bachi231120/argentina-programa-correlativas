package ap.correlativas.service;

import ap.correlativas.model.Materia;
import ap.correlativas.repository.MateriaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class MateriaService {

    private final MateriaRepository repository;

    public MateriaService(MateriaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Materia buscar(String nombre) {
        return repository.findByNombre(nombre);
    }

    @Transactional
    public Materia crear(String nombre) {

        if(repository.findByNombre(nombre) != null) {
            return null;
        }

        Materia materia = new Materia(nombre);

        return repository.save(materia);
    }

    @Transactional
    public void guardar(Materia materia) {
        repository.save(materia);
    }

}
