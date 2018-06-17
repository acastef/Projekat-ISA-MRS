package bioskopi.rs.domain.DTO;

import bioskopi.rs.domain.Facility;
import bioskopi.rs.domain.Feedback;
import bioskopi.rs.domain.Projection;
import bioskopi.rs.domain.RegisteredUser;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

public class FeedbackDTO {

    private int score;

    private String description;

    private RegisteredUser registeredUser;

    private long projectionId;


    public FeedbackDTO(int score, String description, RegisteredUser registeredUser, long projection) {
        this.score = score;
        this.description = description;
        this.registeredUser = registeredUser;
        this.projectionId = projection;
    }

    public FeedbackDTO(Feedback f) {
        this.score = f.getScore();
        this.description = f.getDescription();
        this.registeredUser = f.getRegisteredUser();
        if (f.getProjection() != null)
            this.projectionId = f.getProjection().getId();
        else
            this.projectionId = 0;
    }

}
