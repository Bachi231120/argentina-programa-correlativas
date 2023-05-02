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
public class ConsolaAlumnos {

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
            System.out.println("\nMENU ALUMNOS");
            System.out.println("Seleccione una opcion");
            System.out.println("1 - buscar");
            System.out.println("2 - agregar");
            System.out.println("0 - volver");

            opcion = SCANNER.nextLine();

            switch (opcion) {
                case "1":
                    menuBuscarAlumno();
                    break;
                case "2":
                    menuAgregarAlumno();
                    break;
                case "0": break;
                default: System.out.println("Opcion no valida");

            }
        } while (!"0".equals(opcion));
    }

    private void menuAgregarAlumno() {
        System.out.println("\nAGREGAR ALUMNO");
        System.out.println("Ingrese el legajo");
        String legajoString = SCANNER.nextLine();
        if (legajoString.length() != 5) {
            System.out.println(" *** el legajo debe contener 5 digitos ***");
            return;
        }
        Integer legajo = null;
        try {
            legajo = Integer.parseInt(legajoString);
        } catch (Exception e) {
            System.out.println(" *** legajo puede contener solo numeros ***");
            return;
        }

        Alumno alumno = alumnoService.buscar(legajo);
        if (alumno != null) {
            System.out.println(" *** el legajo " + legajo + " corresponde a " + alumno.getNombre() + "***");
            return;
        }

        System.out.println("Ingrese el nombre");
        String nombreAlumno = SCANNER.nextLine();

        alumno = alumnoService.crear(legajo, nombreAlumno);
        System.out.println(" *** " + nombreAlumno + " se registro correctamente ***");
        menuAlumno(alumno);
    }

    private void menuBuscarAlumno() {
        System.out.println("\nBUSCAR ALUMNO");
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
        menuAlumno(alumno);
    }

    private void menuAlumno(Alumno alumno) {
        String opcion = null;
        do {
            System.out.println("\n *** informacion ***");
            System.out.println("ALUMNO: " + alumno.getNombre());
            System.out.println("Legajo: " + alumno.getLegajo());
            System.out.println("Inscripciones: ");
            alumno.getInscripciones().forEach(inscripcion -> {
                System.out.println(" - " + inscripcion.getMateria().getNombre() + ": " + (inscripcion.getAprobada() ? "aprobada" : "no aprobada"));
            });
            System.out.println("Seleccione una opcion");
            System.out.println("1 - agregar materia aprobada");
            System.out.println("0 - volver");

            opcion = SCANNER.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Ingrese el nombre de la materia aprobada");
                    String nombreAprobada = SCANNER.nextLine();

                    Inscripcion inscripcionAlumno = alumno.getInscripciones().stream()
                            .filter(inscripcion -> nombreAprobada.equals(inscripcion.getMateria().getNombre()))
                            .findFirst()
                            .orElse(null);

                    if (inscripcionAlumno == null) {
                        System.out.println(" *** El alumno no esta inscripto a " + nombreAprobada);
                        break;
                    } else if (inscripcionAlumno.getAprobada()) {
                        System.out.println(" *** El alumno ya aprobo la materia ***");
                        break;
                    }

                    Materia materia = materiaService.buscar(nombreAprobada);
                    if (materia == null) {
                        System.out.println(" *** No existe la materia " + nombreAprobada + " ***");
                        break;
                    }

                    inscripcionAlumno.setAprobada(true);
                    inscripcionService.guardar(inscripcionAlumno);
                    System.out.println(" *** materia aprobada registrada correctamente ***");
                    break;
                case "0": break;
                default: System.out.println("Opcion no valida");
            }
        } while (!"0".equals(opcion));
    }


}
