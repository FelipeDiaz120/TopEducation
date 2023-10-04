package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.PagoService;
import edu.mtisw.monolithicwebapp.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
@Controller
@RequestMapping
public class PagoController {

    @Autowired
    PagoService pagoService;



    @GetMapping("/listarPagos")
    public String listar(Model model) {
        ArrayList<PagoEntity> pagos=pagoService.obtenerPagos();
        model.addAttribute("pagos",pagos);
        return "indexPago";
    }
    @GetMapping("/generarPagos/{id}/{tipoPago}")
    public String generar(@PathVariable("id") Long id,@PathVariable("tipoPago") String tipoPago ) {

        pagoService.generarPagos(id, tipoPago);

        return "redirect:/listar";
    }

    @GetMapping("/listarPagosRut/{rut}")
    public String listarPorRut(Model model,@PathVariable("rut") String rut) {
        ArrayList<PagoEntity> pagos= pagoService.obtenerPorRut(rut);
        model.addAttribute("pagos",pagos);
        return "indexPago";
    }

    @GetMapping("/registrarPago/{id}")
    public String registrarPagos(HttpServletRequest request,@PathVariable("id") Long id){
        pagoService.registrarPago(id);
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            // Redireccionar al usuario de vuelta a la página actual
            return "redirect:" + referer;
        }
        return "redirect:/listar";
    }

    @GetMapping("/newPago")
    public String agregar(Model model) {
        model.addAttribute("pago",new PagoEntity());
        return "formPago";
    }

    @PostMapping("/savePago")
    public String save(@Validated PagoEntity p) {

        pagoService.guardarPago(p);

        return "redirect:/listarPagos";
    }
    @GetMapping("/eliminarPago/{id}")
    public String eliminar(HttpServletRequest request, Model model, @PathVariable("id") Long id){
        pagoService.eliminarPago(id);
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            // Redireccionar al usuario de vuelta a la página actual
            return "redirect:" + referer;
        }
        return "redirect:/listarPagos";
    }
}
