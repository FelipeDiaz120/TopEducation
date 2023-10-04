package edu.mtisw.monolithicwebapp.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rutDuenoPago;
    //Cuotas o Contado
    private String tipoPago;
    private int monto;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaPago;
    private LocalDate fechaEmision;
    private String estado;
    private int totalCuotas;

}