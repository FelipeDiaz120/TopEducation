package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.ExamenEntity;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ExamenRepository extends CrudRepository<ExamenEntity, Long> {
    public ArrayList<ExamenEntity> findAllByRutEstudiante(String rutEstudiante);
}
