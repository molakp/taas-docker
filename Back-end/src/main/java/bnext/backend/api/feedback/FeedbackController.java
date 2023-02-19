package bnext.backend.api.feedback;


import bnext.backend.api.car.CarRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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
    @RequestMapping("/feedbacks/{carId}")
    public @NotNull List<Feedback> getAllFeedbacksOfCar(@PathVariable @NotNull UUID carId) {

        return feedbackService.getAllFeedbacksOfCar(carId);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/feedbacks/addFeed")
    public String addFeedback(@RequestBody Feedback feedback) {
        return feedbackService.addFeedback(feedback);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/feedbacks/update")
    public @NotNull String updateFeedback(@RequestBody Feedback feedback) {
        return feedbackService.updateFeedback(feedback);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/feedbacks/{feedbackId}")
    public @NotNull String deleteFeedback(@PathVariable @NotNull UUID feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/feedbacks")
    public @NotNull String deleteAllFeedbacks() {
        return feedbackService.deleteAllFeedbacks();
    }


    @RequestMapping("/feedbacks/userId={userId}")
    public @NotNull List<Feedback> getAllUserFeedbacks(@PathVariable @NotNull UUID userId) {

        return feedbackService.getAllUserFeedbacks(userId);
    }
}
