package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.UsuarioService;
import edu.mtisw.monolithicwebapp.repositories.PagoRepository;

import lombok.Generated;
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
        ArrayList<PagoEntity> pagos = obtenerPorRut(pago.getRutDuenoPago());
        pago.setFechaPago(LocalDate.now());
        int montoConInteres = interesAtraso(pago);
        for (int i = 0; i < pagos.size(); i++) {
            PagoEntity pagoPendiente = pagos.get(i);
            if (pagoPendiente.getEstado().equals("Pendiente")){
                pagoPendiente.setMonto(montoConInteres);
                guardarPago(pagoPendiente);
            }
        }
        pago.setEstado("Pagado");
        guardarPago(pago);
    }
    @Generated
    public int montoTotal(String rut){
        int total = 0;
        ArrayList<PagoEntity> pagos = obtenerPorRut(rut);
        for (int i = 0; i < pagos.size(); i++) {
            total = total + pagos.get(i).getMonto();
        }
        return total;
    }
    public int calcularCuotasPagadas(String rut){
        ArrayList<PagoEntity> pagos = obtenerPorRut(rut);
        int cuotasPagadas=0;
        for (int i = 0; i < pagos.size(); i++) {
           if (pagos.get(i).getEstado().equals("Pagado")){
               cuotasPagadas++;
           }
        }
        return cuotasPagadas;
    }
    public int calcularTotalCuotasPagadas(String rut){
        ArrayList<PagoEntity> pagos = obtenerPorRut(rut);
        int total=0;
        for (int i = 0; i < pagos.size(); i++) {
            if (pagos.get(i).getEstado().equals("Pagado")){
                total= total + pagos.get(i).getMonto();
            }
        }
        return total;
    }
    public LocalDate ultimoPagoFecha(String rut){
        ArrayList<PagoEntity> pagos = obtenerPorRut(rut);
        LocalDate fecha = null;
        int mes = 0;
        for (PagoEntity pago : pagos) {
            if (pago.getEstado().equals("Pagado") && pago.getFechaPago().getMonthValue() > mes) {
                mes = pago.getFechaPago().getMonthValue();
                fecha=pago.getFechaPago();
            }
        }
        return fecha;
    }
    public int atrasos(String rut){
        ArrayList<PagoEntity> pagos = obtenerPorRut(rut);
        LocalDate fechaActual = LocalDate.now();
        int atrasos = 0;
        for (PagoEntity pago : pagos) {
            if (pago.getEstado().equals("Pagado") && pago.getFechaEmision().isBefore(pago.getFechaPago())) {
            atrasos++;
            }
        }
        return atrasos;
    }
    public UsuarioEntity obtenerDuenoPago(String rut){
        return usuarioService.obtenerPorRut(rut);
    }
    @Generated
    public void registrarDescuentoPromedio(String rut){
        ArrayList<PagoEntity> pagos = obtenerPorRut(rut);

        for (int i = 0; i < pagos.size(); i++) {
            int montoConDescuento = descuentoPromedio(pagos.get(i));
            PagoEntity pagoPendiente = pagos.get(i);
            if (pagoPendiente.getEstado().equals("Pendiente")){
                pagoPendiente.setMonto(montoConDescuento);
                guardarPago(pagoPendiente);
            }
        }
    }
    @Generated
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

    public int interesAtraso(PagoEntity pago){
        int mesesAtraso= mesesDiferencia(pago);
        int interes= 0;

        if (mesesAtraso == 0){
            interes= (pago.getMonto());
            return interes;
        }
        if (mesesAtraso == 1){
            interes= (int) (pago.getMonto() + pago.getMonto() * 0.03);
            return interes;
        }
        if (mesesAtraso == 2){
            interes= (int) (pago.getMonto() + pago.getMonto() * 0.06);
            return interes;
        }
        if (mesesAtraso == 3){
            interes= (int) (pago.getMonto() +pago.getMonto() * 0.09);
            return interes;
        }
        if (mesesAtraso > 3){
            interes= (int) (pago.getMonto() +pago.getMonto() * 0.15);
            return interes;
        }
        return interes;
    }
    public int descuentoPromedio(PagoEntity pago) {
        UsuarioEntity usuario = usuarioService.obtenerPorRut(pago.getRutDuenoPago());
        int promedio = usuario.getPromedio();
        int descuento = 0;
        if (950 <= promedio && promedio <= 1000) {
            descuento = (int) (pago.getMonto() - pago.getMonto() * 0.10);
            return descuento;
        }
        if (900 <= promedio && promedio <= 949) {
            descuento = (int) (pago.getMonto() - pago.getMonto() * 0.05);
            return descuento;
        }
        if (850 <= promedio && promedio <= 899) {
            descuento = (int) (pago.getMonto() - pago.getMonto() * 0.02);
            return descuento;
        }
        if (promedio < 850){
            descuento= pago.getMonto();
            return  descuento;
        }
        return descuento;
    }
}




