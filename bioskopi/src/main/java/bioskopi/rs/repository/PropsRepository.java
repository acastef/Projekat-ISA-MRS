package bioskopi.rs.repository;


import bioskopi.rs.domain.Props;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Interface that communicate with database for props data
 */
public interface PropsRepository extends JpaRepository<Props,Long> {

    /**
     * @param description of targeted props
     * @return props with given description value
     */
    Props findByDescription(String description);

    @Query(value = "SELECT p FROM Props p WHERE p.active = true")
    List<Props> getAllActive();

}
