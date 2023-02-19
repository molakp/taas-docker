package bnext.backend.position;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/pos")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("id={posId}")
    public String getPositionByID(@PathVariable String posId) {
        return positionService.getPositionById(posId);
    }

    @RequestMapping("getAllPositions")
    public String getAllPositions() {
        return positionService.getAllPositions();
    }

        @PostMapping("savePos")
    public String savePosition(@RequestBody @NotNull Position position) {
        return positionService.createPosition(position).toString();
    }


   /* @RequestMapping(method = RequestMethod.POST, value ="pos/@{lat},{lon}")
    public String setCarPosition(@RequestBody Car car, @PathVariable double lat, @PathVariable double lon) {
        return positionService.setCarPosition(car, lat, lon);
    }*/


}
