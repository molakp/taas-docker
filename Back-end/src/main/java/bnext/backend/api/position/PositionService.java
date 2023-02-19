package bnext.backend.api.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    /*
    public String getCarPosition(String carId) {
        try {
            for (Position position : positionRepository.findAll()) {

                if (position instanceof  CarPosition) {
                    if( ((position).getCarId().equals(carId)){
                        return position.toString();
                    }

                }
            }
            return "Car position not found for Car " + carId;
        }
        catch(Exception e) {
            return e.toString();
        }
    }

    public String setCarPosition(Car car, double lat, double lon) {
        try {
            for (Position position : positionRepository.findAll()) {

                if (position instanceof  Position) {
                    // Se già ho salvato la posizione della macchina la aggiorno senza cambiare l'UUID
                    if( ((CarPosition) position).getCarId().equals(car.getId())){
                        //sovrascruivo la posizione dcella macchina senza cambaire UUID alla posizione
                        positionRepository.save(new CarPosition(car, lat, lon, position.getId()));
                        return "Position updated for car " + car.getId();
                    }

                }
            }
            // Se invece non trovo la posizione già registrara ne creo una con un UUID generato random
            Position position = new Position(lat,lon);
            positionRepository.save(position);
            return "Positon saved for car " + car.getId();
        }
        catch (Exception e) {
            return "Errors while setting Car position: " + e;
        }

    }



*/


}
