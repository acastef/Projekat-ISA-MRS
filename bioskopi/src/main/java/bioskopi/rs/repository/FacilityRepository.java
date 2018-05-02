package bioskopi.rs.repository;

import bioskopi.rs.domain.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}

