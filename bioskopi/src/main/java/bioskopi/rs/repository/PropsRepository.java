package bioskopi.rs.repository;


import bioskopi.rs.domain.Props;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Interface that communicate with database for props data
 */
public interface PropsRepository extends JpaRepository<Props,Long> {

    /**
     * @param description of targeted props
     * @return props with given description value
     */
    Props findByDescription(String description);

    /**
     * @return next Id for props data
     */
    //@Query(value = "SELECT props.nextval FROM dual", nativeQuery = true)
    //Long getNextId();
}
