package ap.correlativas.repository;

import ap.correlativas.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    Inscripcion findByAlumno_LegajoAndMateria_Nombre(Integer legajo, String nombreMateria);

}
