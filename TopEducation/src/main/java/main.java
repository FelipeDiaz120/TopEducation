

import java.time.LocalDate;
import java.time.Period;


public class main {


    public static void main(String[] args) {
        LocalDate fecha1 = LocalDate.of(2021, 2, 1);
        LocalDate fecha2 = LocalDate.of(2024, 1, 1);
        LocalDate fecha3 = LocalDate.now();
        Period periodo = Period.between(fecha1, fecha3);
        int diferenciaEnAnios = periodo.getYears();


        System.out.println("La diferencia en años es: " + diferenciaEnAnios );
    }
}
