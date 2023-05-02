package ap.correlativas.service;

import ap.correlativas.model.Alumno;
import ap.correlativas.repository.AlumnoRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    public AlumnoService(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    @Transactional
    public Alumno buscar(Integer legajo) {
        return alumnoRepository.findByLegajo(legajo);
    }

    @Transactional
    public Alumno crear(Integer legajo, String nombre) {

        if (alumnoRepository.findByLegajo(legajo) != null) {
            return null;
        }

        Alumno alumno = new Alumno(nombre, legajo);
        alumnoRepository.save(alumno);

        return alumno;
    }

    @Transactional
    public Alumno guardar(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

}
