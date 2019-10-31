package com.vehiculerental.controllervehicules.vehiculeDataController;

import com.vehiculerental.controllervehicules.vehicule.Vehicule;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
public class VehiculeDataController {

    private RestTemplate restTemplate = new RestTemplate();
    private String vehiculeListURL = "http://172.22.119.157:9081/vehicules";
    private String oneVehiculeURL = "http://172.22.119.157:9081/vehicule/{registrationNbr}";
    private String createVehiculeURL = "http://172.22.119.157:9081/vehicule/create/";
    private String deleteVehiculeURL = "http://172.22.119.157:9081/vehicule/delete/{registrationNbr}";
    private String immatNotFreeURL = "http://172.22.119.142:9093/reservations/immat/notfree/";
    private String updateVehiculeURL = "http://172.22.119.157:9081/vehicule/update/";

    @ApiOperation(value = "Calls the Reservations controller and returns a list of vehicles which are available")
    @GetMapping(value = "vehicule/listDispo/{demandeDateDebut}/{demandeDateFin}")
    public List<Vehicule> getVehiculeImmatFree(@PathVariable int demandeDateDebut, @PathVariable int demandeDateFin) {
        String[] vehiculeNotBilledList = restTemplate.getForObject(immatNotFreeURL + demandeDateDebut + "/" + demandeDateFin, String[].class);
        Vehicule[] vehiculeList = getVehiculeList();
        List<Vehicule> listVehiculeDispo = new ArrayList<>();

//        for (int i = 0; i < vehiculeList.length; i++) {
//            for (int j = 0; j < vehiculeNotBilledList.length; j++) {
//                if (vehiculeList[i].getRegistrationNbr().equals(vehiculeNotBilledList[j])) {
//                    listVehiculeDispo.add(vehiculeList[i]);
//                }
//            }
//        }

        for(Vehicule vehicule: vehiculeList){
            if(!Arrays.asList(vehiculeNotBilledList).contains(vehicule.getRegistrationNbr())){
                listVehiculeDispo.add(vehicule);
            }
        }

        return listVehiculeDispo;
    }


    //Gets the list of vehicles from the vehicle microservice/////////////
    @ApiOperation(value = "Gets the list of vehicles from the vehicle microservice")
    @GetMapping(value = "vehicules")
    public Vehicule[] getVehiculeList() {
        Vehicule vehiculeList[] = restTemplate.getForObject(vehiculeListURL, Vehicule[].class);
        return vehiculeList;
    }

    //Gets one vehicle from the vehicle microservice
    @ApiOperation(value = "Gets one vehicle from the vehicle microservice")
    @GetMapping(value = "vehicule/{registrationNbr}")
    public Vehicule getVehiculeById(@PathVariable String registrationNbr) {
        Vehicule vehicule = restTemplate.getForObject(oneVehiculeURL, Vehicule.class, registrationNbr);
        return vehicule;
    }

    //Deletes a vehicule by calling the miniservice, passes the registration nbr in the URL
    @ApiOperation(value = "Deletes a vehicule by calling the miniservice, passes the registration nbr in the URL")
    @GetMapping(value = "vehicule/delete/{registrationNbr}")
    public void deleteVehicule(@PathVariable String registrationNbr) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("registrationNbr", registrationNbr);
        restTemplate.delete(deleteVehiculeURL, params);
    }

    @PostMapping(value = "vehicule/update")
    public void updateVehicule(@RequestBody Vehicule vehicule){
        restTemplate.put(updateVehiculeURL, vehicule);
    }

    @PostMapping(value = "/vehicule/create")
    public ResponseEntity<Void> addVehicule(@RequestBody Vehicule vehicule){

        Vehicule vehiculeAdded = restTemplate.postForObject(createVehiculeURL, vehicule, Vehicule.class);

        if(vehiculeAdded == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vehiculeAdded.getRegistrationNbr()) // Possibly change to registration_nbr
                .toUri();

        return ResponseEntity.created(location).build();

    }

}
