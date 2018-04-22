package bioskopi.rs.services;

import bioskopi.rs.domain.Props;

import java.util.List;

public interface PropsService {

    /*
    * Interface that offers service for props
    **/

    // select props by given description
    Props findByDescription(String name);
    // select all props
    List<Props> findAllProps();
}
