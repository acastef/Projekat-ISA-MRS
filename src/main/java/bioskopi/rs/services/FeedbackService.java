package bioskopi.rs.services;

import bioskopi.rs.domain.Feedback;

import java.util.List;

public interface FeedbackService {

    /**
     *
     * @param f feedback
     * @return saved facility
     */
    Feedback save (Feedback f);

    /**
     *
     */
    void saveFeedBack(Feedback f);

    /**
     *
     * @param id of registered user
     * @return List of feedbacks from the user with given id
     */
    List<Feedback> findByUserId(long id);

    /**
     *
     * @param projId id of projection
     * @return average score for projection with given id
     */
    Double getAvgScoreForProjection(long projId);

}
