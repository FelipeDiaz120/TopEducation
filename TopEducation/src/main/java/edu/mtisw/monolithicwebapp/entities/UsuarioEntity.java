package edu.mtisw.monolithicwebapp.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.time.*;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String rut;
    private String nombres;
    private String apellidos;
    private String nombreColegio;

    //Municipal-Subvencionado-Privado
    private String tipoColegio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEgreso;

}
