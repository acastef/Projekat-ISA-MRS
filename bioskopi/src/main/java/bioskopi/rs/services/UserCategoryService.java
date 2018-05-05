package bioskopi.rs.services;

import bioskopi.rs.domain.UserCategory;

import java.util.List;

/**
 * Interface that provides service for user category
 */
public interface UserCategoryService {

    /**
     * @return all user categories in database
     */
    List<UserCategory> findAll();
}
