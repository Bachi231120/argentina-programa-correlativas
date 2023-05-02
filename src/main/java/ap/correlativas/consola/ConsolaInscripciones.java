package ap.correlativas.consola;

import ap.correlativas.model.Alumno;
import ap.correlativas.model.Inscripcion;
import ap.correlativas.model.Materia;
import ap.correlativas.service.AlumnoService;
import ap.correlativas.service.InscripcionService;
import ap.correlativas.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsolaInscripciones {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private MateriaService materiaService;

    @Autowired
    private InscripcionService inscripcionService;

    public void menuPrincipal() {
        String opcion = null;
        do {
            System.out.println("\nMENU INSCRIPCIONES");
            System.out.println("Seleccione una opcion");
            System.out.println("1 - agregar inscripcion");
            System.out.println("0 - volver");

            opcion = SCANNER.nextLine();

            switch (opcion) {
                case "1":
                    menuAgregarInscripcion();
                    break;
                case "0": break;
                default: System.out.println("Opcion no valida");

            }
        } while (!"0".equals(opcion));

    }

    private void menuAgregarInscripcion() {
        System.out.println("AGREGAR INSCRIPCION");
        System.out.println("Ingrese el legajo");
        Integer legajo = null;
        try {
            legajo = Integer.parseInt(SCANNER.nextLine());
        } catch (Exception e) {
            System.out.println(" *** legajo incorrecto ***");
            return;
        }
        Alumno alumno = alumnoService.buscar(legajo);
        if (alumno == null) {
            System.out.println(" *** el legajo " + legajo + " no corresponde a ningun registro ***");
            return;
        }
        System.out.println("Ingrese el nombre de la materia");
        String nombreMateria = SCANNER.nextLine();
        Materia materia = materiaService.buscar(nombreMateria);
        if (materia == null) {
            System.out.println(" *** No existe la materia " + nombreMateria + " ***");
            return;
        }

        Inscripcion inscripcion = null;
        try {
            inscripcion = inscripcionService.agregarInscripcion(materia, alumno);
            System.out.println(" *** la inscripcion fue generada con exito ***");
        } catch (Exception e) {
            System.out.println(" *** " + e.getMessage() + " ***");
        }

    }
}
