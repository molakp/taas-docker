package bnext.backend.api.feedback;


import bnext.backend.api.car.CarRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("feedbacks")
public class FeedbackController {

    //grazie a Spring creiamo un'istanza dell'oggetto da cui poi richiamiamo i metodi predefiniti
    @Autowired
    private final FeedbackService feedbackService;

    @Autowired
    private CarRepository carRepository;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    //mi restituisce tutti i position dati ad una determinata macchina
    @GetMapping("/{carId}")
    public @NotNull List<Feedback> getAllFeedbacksOfCar(@PathVariable @NotNull UUID carId) {

        return feedbackService.getAllFeedbacksOfCar(carId);
    }

    @PostMapping( "/addFeed")
    public String addFeedback(@RequestBody Feedback feedback) {
        return feedbackService.addFeedback(feedback);
    }

    @PutMapping( "/update")
    public @NotNull String updateFeedback(@RequestBody Feedback feedback) {
        return feedbackService.updateFeedback(feedback);
    }

    @DeleteMapping( "/{feedbackId}")
    public @NotNull String deleteFeedback(@PathVariable @NotNull UUID feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }

    @DeleteMapping( "/deleteAllFeedbacks")
    public @NotNull String deleteAllFeedbacks() {
        return feedbackService.deleteAllFeedbacks();
    }


    @GetMapping("/userId={userId}")
    public @NotNull List<Feedback> getAllUserFeedbacks(@PathVariable @NotNull UUID userId) {

        return feedbackService.getAllUserFeedbacks(userId);
    }
}
