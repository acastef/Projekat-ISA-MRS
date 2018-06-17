package bioskopi.rs.services;

import bioskopi.rs.domain.DTO.FeedbackDTO;
import bioskopi.rs.domain.Feedback;
import bioskopi.rs.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<FeedbackDTO> findByUserId(Long id) {

        List<Feedback> fList = feedbackRepository.findByUserId(id);
        List<FeedbackDTO> returnList = new ArrayList<FeedbackDTO>();

        for (Feedback f : fList) {
            returnList.add(new FeedbackDTO(f));
        }
        return returnList;
    }
    public void saveFeedBack(Feedback f) {
        feedbackRepository.save(f);
    }


    @Override
    public Double getAvgScoreForProjection(long projId) {
        return feedbackRepository.getAverageScore(projId);
    }
}
