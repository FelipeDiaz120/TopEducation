package edu.mtisw.monolithicwebapp;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.PagoService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PagoServiceTest {
    @Autowired
    PagoService pago = new PagoService();
    PagoEntity pagoEntity = new PagoEntity();
    UsuarioEntity usuario = new UsuarioEntity();


    @Test
    void descuentoAñosEgresoMayor5() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setFechaEgreso(LocalDate.of(2000,9,10));
        double descuento = pago.descuentoAñosEgreso(usuario);
        assertEquals( 0, descuento, 0.0);
    }
    @Test
    void descuentoAñosEgreso4() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setFechaEgreso(LocalDate.of(2019,9,10));
        double descuento = pago.descuentoAñosEgreso(usuario);
        assertEquals( 60000, descuento, 0.0);
    }
    @Test
    void descuentoAñosEgreso3() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setFechaEgreso(LocalDate.of(2020,9,10));
        double descuento = pago.descuentoAñosEgreso(usuario);
        assertEquals( 60000, descuento, 0.0);
    }
    @Test
    void descuentoAñosEgreso2() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setFechaEgreso(LocalDate.of(2021,9,10));
        double descuento = pago.descuentoAñosEgreso(usuario);
        assertEquals( 120000, descuento, 0.0);
    }
    @Test
    void descuentoAñosEgreso1() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setFechaEgreso(LocalDate.of(2022,9,10));
        double descuento = pago.descuentoAñosEgreso(usuario);
        assertEquals( 120000, descuento, 0.0);
    }
    @Test
    void descuentoAñosEgresoMenor1() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setFechaEgreso(LocalDate.of(2023,9,10));
        double descuento = pago.descuentoAñosEgreso(usuario);
        assertEquals( 225000, descuento, 0.0);
    }
    @Test
    void descuentoColegioMunicipal() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setTipoColegio("Municipal");
        double descuento = pago.descuentoColegio(usuario);
        assertEquals( 300000, descuento, 0.0);
    }
    @Test
    void descuentoColegioSubvencionado() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setTipoColegio("Subvencionado");
        double descuento = pago.descuentoColegio(usuario);
        assertEquals( 150000, descuento, 0.0);
    }
    @Test
    void descuentoColegioPrivado() {
        usuario.setRut("12.345.678-2");
        usuario.setNombres("Raul Pedro");
        usuario.setApellidos("Diaz Gonzalez");
        usuario.setTipoColegio("Privado");
        double descuento = pago.descuentoColegio(usuario);
        assertEquals( 0, descuento, 0.0);
    }

    @Test
    void mesesDiferencia() {
        pagoEntity.setFechaEmision(LocalDate.of(2023,8,10));
        pagoEntity.setFechaPago(LocalDate.of(2023,9,9));
        int meses = pago.mesesDiferencia(pagoEntity);
        assertEquals( 0, meses, 0.0);
    }

    @Test
    void montoTotal() {
        ArrayList<PagoEntity> pagos = new ArrayList<>();
        String rut = "20333222-1";
        pagoEntity.setMonto(100);
        pagoEntity.setRutDuenoPago(rut);
        pagos.add(pagoEntity);  // Pago 1
        pagoEntity.setMonto(200);
        pagoEntity.setRutDuenoPago(rut);
        pagos.add(pagoEntity);  // Pago 2
        pagoEntity.setMonto(300);
        pagoEntity.setRutDuenoPago(rut);
        pagos.add(pagoEntity);  // Pago 3

        int monto = pago.montoTotal(rut);
        assertEquals( 600, monto, 0.0);
    }





}
