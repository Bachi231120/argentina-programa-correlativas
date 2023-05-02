package ap.correlativas.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "legajo", nullable = false)
    private Integer legajo;

    @OneToMany(mappedBy = "alumno", fetch = FetchType.EAGER)
    private Set<Inscripcion> inscripciones;

    public Alumno() {
        this.inscripciones = new HashSet<>();
    }

    public Alumno(String nombre, Integer legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
        this.inscripciones = new HashSet<>();
    }

    public void aprobarMateria(Materia materia) {
        for (Inscripcion inscripcion : this.inscripciones) {
            if (materia.equals(inscripcion.getMateria())) {
                inscripcion.setAprobada(true);
                break;
            }
        }
    }

    public boolean puedeCursar(Materia materia) {
        boolean puedeCursar = true;
        Set<Materia> materiasAprobadas = this.inscripciones.stream()
                .filter(Inscripcion::getAprobada)
                .map(Inscripcion::getMateria)
                .collect(Collectors.toSet());

        for (Materia correlativa : materia.getCorrelativas()) {
            if (materiasAprobadas.stream().noneMatch(aprobada -> correlativa.equals(aprobada))) {
                puedeCursar = false;
                break;
            }
        }

        return puedeCursar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getLegajo() {
        return legajo;
    }

    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    public Set<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(Set<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
}
