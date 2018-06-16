package bioskopi.rs.services;

import bioskopi.rs.domain.Feedback;
import bioskopi.rs.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public Feedback save(Feedback f) {
        return feedbackRepository.saveAndFlush(f);
    }

    @Override
    public void saveFeedBack(Feedback f) {
        feedbackRepository.save(f);
    }

    @Override
    public List<Feedback> findByUserId(long id) {
        return feedbackRepository.findByUserId(id);
    }

    @Override
    public Double getAvgScoreForProjection(long projId) {
        return feedbackRepository.getAverageScore(projId);
    }
}
