package edu.mtisw.monolithicwebapp.services;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import edu.mtisw.monolithicwebapp.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.ArrayList;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;


    
    public ArrayList<UsuarioEntity> obtenerUsuarios(){
        return (ArrayList<UsuarioEntity>) usuarioRepository.findAll();
    }
    public UsuarioEntity obtenerPorRut(String rut) { return usuarioRepository.findByRut(rut);}

    public UsuarioEntity guardarUsuario(UsuarioEntity usuario){

        return usuarioRepository.save(usuario);
    }

    public UsuarioEntity obtenerEstudiantePorId(Long id){
        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findById(id);

        // Verifica si el Optional contiene un valor y, si es así, devuélvelo, de lo contrario, devuelve null o realiza alguna otra acción apropiada.
        return optionalUsuario.orElse(null); // Puedes cambiar "null" por lo que desees en caso de no encontrar el usuario.
    }
    public boolean eliminarUsuario(Long id) {
        try{
            usuarioRepository.deleteById(id);
            return true;
        }catch(Exception err){
            return false;
        }
    }
    public int calcularPromedioPuntaje(UsuarioEntity usuario){
        ArrayList<Integer> puntajes = usuario.getPuntajes();
        int sumatoria=0;
        for (int i=0; i<puntajes.size();i++){
            sumatoria= sumatoria + puntajes.get(i);
        }
        usuario.setPromedio(sumatoria/puntajes.size());
        //guardarUsuario(usuario);
        return sumatoria/puntajes.size();
    }


    }

  
