package bnext.backend.api.feedback;

import bnext.backend.api.car.Car;
import bnext.backend.api.car.CarRepository;
import bnext.backend.api.user.User;
import bnext.backend.api.user.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    //restituisce tutti i position presenti
    public @NotNull List<Feedback> getAllFeedbacksOfCar(@NotNull UUID carId) {
        List<Feedback> allFeedbacks = new ArrayList<>();
        // variabile che conterrà soli i position appartenenti ad una determinata macchina
        List<Feedback> feedbacksToReturn = new ArrayList<>();
        // recupero tutti i position
        feedbackRepository.findAll().forEach(allFeedbacks::add);

        if (allFeedbacks.isEmpty()) {
            System.out.println("There's no comment to show, the comment's list is empty");
            return allFeedbacks;
        }


        for (Feedback currentFeedback : allFeedbacks) {
            //System.out.println("Prova********" + currentFeedback.getCar());
            if (currentFeedback.getCar().getCarId().equals(carId))
                feedbacksToReturn.add(currentFeedback);
        }
        //Optional<Feedback> prova = feedbackRepository.findById(feedbacksToReturn.get(1).getIdFeedback());
        //System.out.println("ffffffffff\n"+feedbacksToReturn.get(0));
        return feedbacksToReturn;

    }

    public @NotNull String addFeedback(@NotNull Feedback feedback) {

        if (userRepository.findById(feedback.getUser().getUserId()).isEmpty()) {
            //a seconda di quale lettera si mette sbagliata puo dare errore e non stampa quanto sotto
            return "questo id utente non è presente nel database";
        } else {
            User user = userRepository.findById(feedback.getUser().getUserId()).get();
            feedback.nomeUtente = user.getName();
            feedback.cognomeUtente = user.getSurname();
            feedbackRepository.save(feedback);
            return "Feedback of user '" + feedback.getUser().getUserId() + "' registered";
        }
    }

    public @NotNull String updateFeedback(@NotNull Feedback feedback) {
        try {
            Feedback modifiedFeedback = feedbackRepository.findById(feedback.getIdFeedback()).get();
            if (modifiedFeedback != null) {
                if (modifiedFeedback.getComment() != null)
                    modifiedFeedback.setComment(feedback.getComment());

                /*

                // Non ha senso cambiare l'auto a cui p riferita un position, può solo creare problemi, l'auto è messa
                 //automaticamente
                if(modifiedFeedback.getCar() != null)
                    modifiedFeedback.setCar(position.getCar());

                 */

                feedbackRepository.save(modifiedFeedback);
                return "position upadated correctly";
            } else
                return "No position with this id in the database";
        } catch (Exception ex) {
            return "Error was " + ex.getMessage();
        }
        /*if(feedbackRepository.findById(position.getIdFeedback()).isPresent()) {
            Feedback modifiedFeedback = feedbackRepository.findById(position.getIdFeedback()).get();
            modifiedFeedback.setComment(position.getComment());
            feedbackRepository.save(modifiedFeedback);
            return "position updated correctly";
        }
        return "Feedback with given id not found";*/
    }

    //Cancella il position selezionato
    public @NotNull String deleteFeedback(@NotNull UUID feedbackId) {
        // verifico che esista il position con l'id indicato
        if (feedbackRepository.findById(feedbackId).isEmpty()) {
            return "No position with this id in the database";
        } else
            feedbackRepository.deleteById(feedbackId);
        return " FEEDBACK SUCCESSFULLY DELETED";
    }

    public @NotNull String deleteAllFeedbacks() {
        feedbackRepository.deleteAll();
        return "all feedbacks where deleted";
    }

    public List<Feedback> getAllUserFeedbacks(@NotNull UUID userId) {
        List<Feedback> feedbacksToBeFiltered = new ArrayList<>();
        List<Car> carsToBeFiltered = new ArrayList<>();
        carRepository.findAll().forEach(carsToBeFiltered::add);
        feedbackRepository.findAll().forEach(feedbacksToBeFiltered::add);

        //prendo tutte le auto di questo utente
        List<Car> CurrentUserCars = new ArrayList<>();
        for (Car c : carsToBeFiltered) {
            if (c.getUser().getUserId().equals(userId)) {
                CurrentUserCars.add(c);
            }
        }
        //prendo tutti i position delle macchine dell'utente "loggato"
        List<Feedback> currentUserCarsfeedbacks = new ArrayList<>();
        for (Feedback f : feedbacksToBeFiltered) {
            for (Car c : CurrentUserCars) {
                if (f.getCar().getCarId().equals(c.getCarId())) {
                    currentUserCarsfeedbacks.add(f);
                }
            }
        }
        return currentUserCarsfeedbacks;
    }


}


