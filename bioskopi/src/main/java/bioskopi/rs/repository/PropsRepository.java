package bioskopi.rs.repository;


import bioskopi.rs.domain.Props;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface that communicate with database for props props data
 */
public interface PropsRepository extends JpaRepository<Props,Long> {

    /**
     * @param description of targeted props
     * @return props with given description value
     */
    Props findByDescription(String description);
}
