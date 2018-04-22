package bioskopi.rs.repository;


import bioskopi.rs.domain.Props;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

public interface PropsRepository extends JpaRepository<Props,Long> {

    /*
    * Interface that communicate with database
    */

    //gets props by description value
    Props findByDescription(String description);
}
