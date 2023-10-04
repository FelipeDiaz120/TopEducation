package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.PagoService;
import edu.mtisw.monolithicwebapp.services.UsuarioService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PagoServiceTest {
    PagoService pago = new PagoService();
    PagoEntity pagoEntity = new PagoEntity();
    UsuarioEntity usuario = new UsuarioEntity();


    @Test
    void descuentoAñosEgreso() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        int x=2000;
        usuario.setFechaEgreso(LocalDate.of(x,9,10));
        double descuento = pago.descuentoAñosEgreso(usuario);
        assertEquals( 0, descuento, 0.0);
    }
    @Test
    void descuentoColegio() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setTipoColegio("Municipal");
        int x=2000;
        usuario.setFechaEgreso(LocalDate.of(x,9,10));
        double descuento = pago.descuentoColegio(usuario);
        assertEquals( 300000, descuento, 0.0);
    }

    @Test
    void mesesDiferencia() {
        pagoEntity.setFechaEmision(LocalDate.of(2023,8,10));
        pagoEntity.setFechaPago(LocalDate.of(2023,9,9));
        int meses = pago.mesesDiferencia(pagoEntity);
        assertEquals( 0, meses, 0.0);
    }
}
