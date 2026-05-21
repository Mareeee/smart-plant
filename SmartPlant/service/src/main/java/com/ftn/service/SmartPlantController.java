package com.ftn.service;

import com.ftn.model.Biljka;
import com.ftn.model.SenzorskoOcitavanje;
import com.ftn.model.TipBiljke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/smartplant")
@CrossOrigin(origins = "*")
public class SmartPlantController {

    @Autowired
    private DroolsService droolsService;

    @PostMapping("/analiziraj")
    public ResponseEntity<RezultatAnalize> analiziraj(@RequestBody AnalizaRequest request) {
        Biljka biljka = new Biljka(request.getBiljkaId(), request.getNaziv(), request.getTipBiljke());
        SenzorskoOcitavanje ocitavanje = new SenzorskoOcitavanje(
                request.getBiljkaId(),
                request.getVlaznostZemljista(),
                request.getTemperatura(),
                request.getSvetlost(),
                request.getNivoRezervoara());
        RezultatAnalize rezultat = droolsService.analiziraj(ocitavanje, biljka);
        return ResponseEntity.ok(rezultat);
    }
}