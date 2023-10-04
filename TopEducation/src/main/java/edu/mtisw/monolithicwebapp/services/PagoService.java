package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.UsuarioService;
import edu.mtisw.monolithicwebapp.repositories.PagoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;
    @Autowired
    UsuarioService usuarioService;

    public PagoEntity guardarPago(PagoEntity pago) {
        return pagoRepository.save(pago);
    }

    public ArrayList<PagoEntity> obtenerPagos() {
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    public ArrayList<PagoEntity> obtenerPorRut(String Rut) {
        return pagoRepository.findAllByRutDuenoPago(Rut);
    }

    public boolean eliminarPago(Long id) {
        try {
            pagoRepository.deleteById(id);
            return true;
        } catch (Exception err) {
            return false;
        }
    }
    public PagoEntity obtenerPagoPorId(Long id){
        Optional<PagoEntity> optionalPago = pagoRepository.findById(id);

        // Verifica si el Optional contiene un valor y, si es así, devuélvelo, de lo contrario, devuelve null o realiza alguna otra acción apropiada.
        return optionalPago.orElse(null); // Puedes cambiar "null" por lo que desees en caso de no encontrar el usuario.
    }
    public void registrarPago(Long id){
        PagoEntity pago = obtenerPagoPorId(id);
        pago.setEstado("Pagado");
        pago.setFechaPago(LocalDate.now());
        guardarPago(pago);
    }
    public void generarPagos(Long id, String tipoPago) {
       UsuarioEntity usuario = usuarioService.obtenerEstudiantePorId(id);
       LocalDate fechaEmisionCuotas = LocalDate.of(2023,3,10);
        int cuotas = 0;
        if (tipoPago.equals("Cuotas")) {
            String tipoColegio = usuario.getTipoColegio();
            if (tipoColegio.equals("Municipal")) {
                for (int i = 10; i > 0; i = i - 1) {
                    PagoEntity pago = new PagoEntity();
                    cuotas = 10;
                    pago.setTotalCuotas(cuotas);
                    pago.setRutDuenoPago(usuario.getRut());
                    pago.setTipoPago(tipoPago);
                    pago.setMonto((1500000 - (descuentoAñosEgreso(usuario) + descuentoColegio(usuario))) / cuotas);
                    pago.setFechaPago(null);
                    pago.setFechaEmision(fechaEmisionCuotas);
                    pago.setEstado("Pendiente");
                    guardarPago(pago);
                    fechaEmisionCuotas= fechaEmisionCuotas.plusMonths(1);
                }return ;
            }
            if (tipoColegio.equals("Subvencionado")) {
                for (int i = 7; i > 0; i = i - 1) {
                    PagoEntity pago = new PagoEntity();
                    cuotas = 7;
                    pago.setTotalCuotas(cuotas);
                    pago.setRutDuenoPago(usuario.getRut());
                    pago.setTipoPago(tipoPago);
                    pago.setMonto((1500000 - (descuentoAñosEgreso(usuario) + descuentoColegio(usuario))) / cuotas);
                    pago.setFechaPago(null);
                    pago.setFechaEmision(fechaEmisionCuotas);
                    pago.setEstado("Pendiente");
                    guardarPago(pago);
                    fechaEmisionCuotas= fechaEmisionCuotas.plusMonths(1);

                }return ;
            }
            if (tipoColegio.equals("Privado")) {
                for (int i = 4; i > 0; i = i - 1) {
                    PagoEntity pago = new PagoEntity();
                    cuotas = 4;
                    pago.setTotalCuotas(cuotas);
                    pago.setRutDuenoPago(usuario.getRut());
                    pago.setTipoPago(tipoPago);
                    pago.setMonto((1500000 - (descuentoAñosEgreso(usuario) + descuentoColegio(usuario))) / cuotas);
                    pago.setFechaPago(null);
                    pago.setFechaEmision(fechaEmisionCuotas);
                    pago.setEstado("Pendiente");
                    guardarPago(pago);
                    fechaEmisionCuotas= fechaEmisionCuotas.plusMonths(1);

                }
                return ;
            }
        }
        if (tipoPago.equals("Contado")) {
            cuotas = 0;
            PagoEntity pago = new PagoEntity();
            pago.setTotalCuotas(cuotas);
            pago.setRutDuenoPago(usuario.getRut());
            pago.setTipoPago(tipoPago);
            pago.setMonto((820000));
            pago.setFechaPago(fechaEmisionCuotas);
            pago.setFechaEmision(fechaEmisionCuotas);
            pago.setEstado("Pagado");
            guardarPago(pago);
            return ;
        }
        return ;
    }



    public int descuentoColegio(UsuarioEntity usuario){

        int dcto = 0;
        String tipoColegio = usuario.getTipoColegio();
        if (tipoColegio.equals("Municipal")){
             dcto = (int) (1500000 * 0.20);
            return dcto;
        }
        if (tipoColegio.equals("Subvencionado")){
            dcto = (int) (1500000 * 0.10);
            return dcto;
        }
        if (tipoColegio=="Privado"){
            dcto =  0;
            return dcto;
        }
        return dcto;
    }
    public int calcularAnosEgreso(UsuarioEntity usuario){

        LocalDate ahora = LocalDate.now();
        LocalDate fechaEgreso = usuario.getFechaEgreso();
        Period periodo = Period.between(fechaEgreso, ahora);
        int diferenciaEnAños = periodo.getYears();
        return diferenciaEnAños;

    }

    public int descuentoAñosEgreso(UsuarioEntity usuario){
        int añosDeEgreso= calcularAnosEgreso(usuario);
        int dcto= 0;
        if (añosDeEgreso < 1){
            dcto= (int) (1500000 * 0.15);
            return dcto;
        }
        if (añosDeEgreso == 1  || añosDeEgreso==2 ){
            dcto= (int) (1500000 * 0.08);
            return dcto;
        }
        if (añosDeEgreso == 3  || añosDeEgreso==4 ){
            dcto= (int) (1500000 * 0.04);
            return dcto;
        }
        if (añosDeEgreso >= 5 ){
            dcto= 0;
            return dcto;
        }
        return dcto;
    }
    public int mesesDiferencia(PagoEntity pago){
        LocalDate fechaEmision = pago.getFechaEmision();
        LocalDate fechaPago = pago.getFechaPago();
        int diferenciaEnMeses = (int) ChronoUnit.MONTHS.between(fechaEmision, fechaPago);
        return diferenciaEnMeses;
    }

}

