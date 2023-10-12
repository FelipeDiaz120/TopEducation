package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.services.ExamenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class ExamenController {
    @Autowired
    ExamenService examenService;
    @GetMapping("/subirExamen")
    public String main() {
        return "subirExamen";
    }
    @PostMapping("/subirExamen")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        examenService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        examenService.leerCsv("Examen.csv");
        return "redirect:/puntaje";
    }
    @RequestMapping(value = "/puntaje", method = {RequestMethod.GET, RequestMethod.POST})
    public String puntaje() {
        examenService.guardarPuntaje();
        return "redirect:/listar";
    }

}
