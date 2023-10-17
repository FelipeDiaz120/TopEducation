package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.UsuarioService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuarioServiceTest {
    UsuarioService usuarioService = new UsuarioService();
    UsuarioEntity usuario = new UsuarioEntity();
    @Test
    void calcularPromedioPuntajes() {
        ArrayList<Integer> puntajes = new ArrayList<>();
        puntajes.add(800);
        puntajes.add(900);
        puntajes.add(1000);
        usuario.setRut("12.345.678-2");
        usuario.setPuntajes(puntajes);
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setFechaEgreso(LocalDate.of(2000,9,10));
        double descuento = usuarioService.calcularPromedioPuntaje(usuario);
        assertEquals( 900, descuento, 0.0);
    }
}
