package edu.mtisw.monolithicwebapp.services;

import edu.mtisw.monolithicwebapp.entities.ExamenEntity;
import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.repositories.ExamenRepository;
import lombok.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

@Service
public class ExamenService {
    @Autowired
    ExamenRepository examenRepository;
    @Autowired
    UsuarioService usuarioService;
    private final Logger logg = LoggerFactory.getLogger(ExamenService.class);
    public ArrayList<ExamenEntity> obtenerExamenesPorRut(String Rut) {
        return examenRepository.findAllByRutEstudiante(Rut);
    }

    public void guardarExamen(ExamenEntity examen){
        examenRepository.save(examen);
    }
    public void guardarExamenDB(String rut, String fechaExamen,int puntaje){
        ExamenEntity newExamen = new ExamenEntity();
        newExamen.setFechaExamen(fechaExamen);
        newExamen.setPuntaje(puntaje);
        newExamen.setRutEstudiante(rut);
        guardarExamen(newExamen);
    }
    public ArrayList<ExamenEntity> obtenerExamenes() {
        return (ArrayList<ExamenEntity>) examenRepository.findAll();
    }

    public void guardarPuntaje(){
        ArrayList<ExamenEntity> examenesEstudiante = obtenerExamenes();
        for (int i = 0; i < examenesEstudiante.size(); i++) {
            UsuarioEntity usuario = usuarioService.obtenerPorRut(examenesEstudiante.get(i).getRutEstudiante());
            if (usuario.getPuntajes() == null) {
                ArrayList<Integer> listaPuntajes = new ArrayList<>();
                listaPuntajes.add(examenesEstudiante.get(i).getPuntaje());
                usuario.setPuntajes(listaPuntajes);
                usuarioService.guardarUsuario(usuario);
            } else {
                ArrayList<Integer> listaPuntajes = usuario.getPuntajes();
                listaPuntajes.add(examenesEstudiante.get(i).getPuntaje());
                usuario.setPuntajes(listaPuntajes);
                usuarioService.guardarUsuario(usuario);
                for (int j = 0; j < usuario.getPuntajes().size(); j++) {
                    System.out.println(usuario.getPuntajes().get(j));
                }

            }
        }

    }
    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }
    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;

        examenRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));

            String temp = "";
            String bfRead;

            while((bfRead = bf.readLine()) != null){
                guardarExamenDB(bfRead.split(",")[0], bfRead.split(",")[1], Integer.parseInt(bfRead.split(",")[2]));

                temp = temp + "\n" + bfRead;
            }

            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }
}
