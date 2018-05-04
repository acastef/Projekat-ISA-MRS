package bioskopi.rs.repository;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

/**
 * Interface that communicate with database for facility data
 */
public interface FacilityRepository extends JpaRepository<Facility,Long> {

//    /**
//     *
//     * @param id of targeted facility
//     * @return facility with given id
//     */
//    //@Query("SELECT f.id FROM Facility f where f.id = :id")
//    Facility findById(int id);

    @Query("SELECT p FROM Projection p WHERE p.facility.id = ?1")
    List<Projection> findRepertoireById(long id);

    @Query(value = "SELECT * FROM Facility f WHERE f.type = :type", nativeQuery = true)
    List<Facility> findFacilityByType(@Param("type") String type);


}

