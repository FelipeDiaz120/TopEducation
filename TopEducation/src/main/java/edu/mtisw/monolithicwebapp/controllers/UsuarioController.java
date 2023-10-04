package edu.mtisw.monolithicwebapp.controllers;

import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping
public class UsuarioController {
    @Autowired
	UsuarioService usuarioService;

    @GetMapping("/listar")
	public String listar(Model model) {
    	ArrayList<UsuarioEntity>usuarios=usuarioService.obtenerUsuarios();
    	model.addAttribute("usuarios",usuarios);
		return "index";
	}
	@GetMapping("/new")
	public String agregar(Model model) {
		model.addAttribute("usuario",new UsuarioEntity());
		return "form";
	}
	@PostMapping("/save")
	public String save(@Validated UsuarioEntity u) {
        usuarioService.guardarUsuario(u);
		return "redirect:/listar";
	}

	@GetMapping("/editar/{id}")
	public String editar(@PathVariable long id, Model model) {
		UsuarioEntity usuario=usuarioService.obtenerEstudiantePorId(id);
		model.addAttribute("usuario",usuario);
		return "form";
	}
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model model,@PathVariable("id") Long id){
		 usuarioService.eliminarUsuario(id);
		 return "redirect:/listar";
	 }

}
