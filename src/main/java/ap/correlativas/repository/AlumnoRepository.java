package ap.correlativas.repository;

import ap.correlativas.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Alumno findByLegajo(Integer legajo);

}
