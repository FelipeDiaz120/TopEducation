package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface PagoRepository extends CrudRepository<PagoEntity, Long> {
    public PagoEntity findByTipoPago(String tipoPago);
    public ArrayList<PagoEntity> findAllByRutDuenoPago(String rutDuenoPago);



}
