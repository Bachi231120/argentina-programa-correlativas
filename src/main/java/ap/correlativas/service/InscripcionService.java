package ap.correlativas.service;

import ap.correlativas.model.Alumno;
import ap.correlativas.model.Inscripcion;
import ap.correlativas.model.Materia;
import ap.correlativas.repository.InscripcionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;

    public InscripcionService(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @Transactional
    public Inscripcion agregarInscripcion(Materia materia, Alumno alumno) {
        if (!alumno.puedeCursar(materia)) {
            throw new RuntimeException("el alumno no puede cursar la materia");
        }

        if (inscripcionRepository.findByAlumno_LegajoAndMateria_Nombre(alumno.getLegajo(), materia.getNombre()) != null) {
            throw new RuntimeException("el alumno ya esta inscripto a la materia");
        }

        Inscripcion inscripcion = new Inscripcion(materia, alumno, LocalDate.now());

        inscripcionRepository.save(inscripcion);

        return inscripcion;
    }

    @Transactional
    public void guardar(Inscripcion inscripcion) {
        inscripcionRepository.save(inscripcion);
    }

}
