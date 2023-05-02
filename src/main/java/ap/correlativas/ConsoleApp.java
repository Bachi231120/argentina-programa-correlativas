package ap.correlativas;

import ap.correlativas.consola.ConsolaAlumnos;
import ap.correlativas.consola.ConsolaInscripciones;
import ap.correlativas.consola.ConsolaMaterias;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleApp implements CommandLineRunner {

    private static final Scanner SCANNER = new Scanner(System.in);

    private final ConsolaMaterias consolaMaterias;
    private final ConsolaAlumnos consolaAlumnos;
    private final ConsolaInscripciones consolaInscripciones;

    public ConsoleApp(ConsolaMaterias consolaMaterias, ConsolaAlumnos consolaAlumnos, ConsolaInscripciones consolaInscripciones) {
        this.consolaMaterias = consolaMaterias;
        this.consolaAlumnos = consolaAlumnos;
        this.consolaInscripciones = consolaInscripciones;
    }

    @Override
    public void run(String... args) throws Exception {
        String opcion = null;
        do {
            System.out.println("\nMENU CORRELATIVAS");
            System.out.println("Seleccione una opcion");
            System.out.println("1 - materias");
            System.out.println("2 - alumnos");
            System.out.println("3 - inscripciones");
            System.out.println("0 - Salir");
            opcion = SCANNER.nextLine();

            switch (opcion) {
                case "1":
                    consolaMaterias.menuPrincipal();
                    break;
                case "2":
                    consolaAlumnos.menuPrincipal();
                    break;
                case "3":
                    consolaInscripciones.menuPrincipal();
                    break;
                case "0": break;
                default: System.out.println("Opcion no valida");
            }
        } while (!"0".equals(opcion));
    }

}
