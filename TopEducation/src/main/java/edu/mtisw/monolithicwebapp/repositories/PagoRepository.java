package edu.mtisw.monolithicwebapp.repositories;

import edu.mtisw.monolithicwebapp.entities.PagoEntity;
import edu.mtisw.monolithicwebapp.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public interface PagoRepository extends CrudRepository<PagoEntity, Long> {
    public PagoEntity findByTipoPago(String tipoPago);
    public ArrayList<PagoEntity> findAllByRutDuenoPago(String rutDuenoPago);
    @Query(value = "SELECT * FROM pagos WHERE pagos.rutDuenoPago = :rutDuenoPago", nativeQuery = true)
    PagoEntity findByRut(@Param("rutDuenoPago") String rutDuenoPago);


}
