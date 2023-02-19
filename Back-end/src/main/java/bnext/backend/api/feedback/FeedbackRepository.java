package bnext.backend.api.feedback;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, UUID> {

    // @Query("SELECT f FROM Feedback f where f.idFeedback=?1")
    //Feedback findById(Long idFeedback);
}
