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

    /**
     * @param name of user category
     * @return user category that given name
     */
    UserCategory findByName(String name);

    void save();
}
