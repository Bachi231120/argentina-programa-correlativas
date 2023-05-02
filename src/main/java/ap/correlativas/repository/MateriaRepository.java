package ap.correlativas.repository;

import ap.correlativas.model.Materia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MateriaRepository extends JpaRepository<Materia, Long> {

    Materia findByNombre(String nombre);

}
