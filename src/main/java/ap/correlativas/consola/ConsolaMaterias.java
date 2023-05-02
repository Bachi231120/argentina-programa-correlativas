package ap.correlativas.consola;

import ap.correlativas.model.Materia;
import ap.correlativas.service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class ConsolaMaterias {

    private static final Scanner SCANNER = new Scanner(System.in);

    @Autowired
    private MateriaService materiaService;


    public void menuPrincipal() {
        String opcion = null;
        do {
            System.out.println("\nMENU MATERIAS");
            System.out.println("Seleccione una opcion");
            System.out.println("1 - buscar");
            System.out.println("2 - agregar");
            System.out.println("0 - volver");

            opcion = SCANNER.nextLine();

            switch (opcion) {
                case "1":
                    menuBuscarMateria();
                    break;
                case "2":
                    menuAgregarMateria();
                    break;
                case "0": break;
                default: System.out.println("Opcion no valida");

            }
        } while (!"0".equals(opcion));
    }

    private void menuBuscarMateria() {
        System.out.println("\nBUSCAR MATERIA");
        System.out.println("Ingrese el nombre de la materia");
        String nombreMateria = SCANNER.nextLine();
        Materia materia = materiaService.buscar(nombreMateria);
        if (materia == null) {
            System.out.println(" *** No existe la materia " + nombreMateria + " ***");
            return;
        }
        menuMateria(materia);
    }

    private void menuAgregarMateria() {
        System.out.println("\nAGREGAR MATERIA");
        System.out.println("Ingrese el nombre de la materia");
        String nombreMateria = SCANNER.nextLine();
        Materia materia = materiaService.buscar(nombreMateria);
        if (materia != null) {
            System.out.println(" *** Ya existe la materia " + nombreMateria + " ***");
            return;
        }
        materia = materiaService.crear(nombreMateria);
        System.out.println(" *** la materia " + nombreMateria + " fue agregada ***");
        menuMateria(materia);
    }

    private void menuMateria(Materia materia) {
        String opcion = null;
        do {
            System.out.println("\n *** informacion ***");
            System.out.println("MATERIA: " + materia.getNombre());
            System.out.println("Correlativas: " + materia.getCorrelativas().stream().map(Materia::getNombre).collect(Collectors.joining(", ")));
            System.out.println("Seleccione una opcion");
            System.out.println("1 - agregar correlativa");
            System.out.println("0 - volver");

            opcion = SCANNER.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Ingrese el nombre de la correlativa a agregar");
                    String nombreCorrelativa = SCANNER.nextLine();
                    if (nombreCorrelativa.equals(materia.getNombre())) {
                        System.out.println(" *** No puede ser correlativa de si misma ***");
                        break;
                    }
                    Materia correlativa = materiaService.buscar(nombreCorrelativa);
                    if (correlativa == null) {
                        System.out.println(" *** No existe la materia " + nombreCorrelativa + " ***");
                        break;
                    }
                    materia.agregarCorrelativa(correlativa);
                    materiaService.guardar(materia);
                    System.out.println(" *** Correlativa agregada ***");
                    break;
                case "0": break;
                default: System.out.println("Opcion no valida");
            }
        } while (!"0".equals(opcion));
    }

}
