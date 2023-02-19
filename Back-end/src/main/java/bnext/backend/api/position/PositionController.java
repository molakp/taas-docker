package bnext.backend.api.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PositionController {

    @Autowired
    private PositionService positionService;


   /* @RequestMapping("pos/id={carId}")
    public String getCarPosition(@PathVariable String carId) {
        return positionService.getCarPosition(carId);
    }


    @RequestMapping(method = RequestMethod.POST, value ="pos/@{lat},{lon}")
    public String setCarPosition(@RequestBody Car car, @PathVariable double lat, @PathVariable double lon) {
        return positionService.setCarPosition(car, lat, lon);
    }
*/


}
