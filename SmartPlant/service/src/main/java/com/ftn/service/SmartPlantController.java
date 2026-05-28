package com.ftn.service;

import com.ftn.model.Biljka;
import com.ftn.model.DijaganostickiUpit;
import com.ftn.model.IstorijaZalivanja;
import com.ftn.model.Notifikacija;
import com.ftn.model.SenzorskoOcitavanje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/smartplant")
@CrossOrigin(origins = "*")
public class SmartPlantController {

    @Autowired
    private DroolsService droolsService;

    private final Map<Long, Biljka> registrovaneBiljke = new ConcurrentHashMap<>();
    private final Map<Long, List<IstorijaZalivanja>> istorijaZalivanjaStore = new ConcurrentHashMap<>();
    private final Map<Long, List<SenzorskoOcitavanje>> istorijaOcitavanjaStore = new ConcurrentHashMap<>();

    @PostMapping("/biljka")
    public ResponseEntity<Biljka> dodajBiljku(@RequestBody DodajBiljkuRequest request) {
        Biljka biljka = new Biljka(request.getBiljkaId(), request.getNaziv(), request.getTipBiljke());
        registrovaneBiljke.put(biljka.getId(), biljka);
        istorijaZalivanjaStore.putIfAbsent(biljka.getId(), new ArrayList<>());
        istorijaOcitavanjaStore.putIfAbsent(biljka.getId(), new ArrayList<>());
        return ResponseEntity.ok(biljka);
    }

    @GetMapping("/biljke")
    public ResponseEntity<List<Biljka>> getBiljke() {
        return ResponseEntity.ok(new ArrayList<>(registrovaneBiljke.values()));
    }

    @PostMapping("/analiziraj")
    public ResponseEntity<RezultatAnalize> analiziraj(@RequestBody AnalizaRequest request) {
        Biljka biljka = registrovaneBiljke.getOrDefault(
                request.getBiljkaId(),
                new Biljka(request.getBiljkaId(), request.getNaziv(), request.getTipBiljke()));

        registrovaneBiljke.putIfAbsent(biljka.getId(), biljka);
        istorijaZalivanjaStore.putIfAbsent(biljka.getId(), new ArrayList<>());
        istorijaOcitavanjaStore.putIfAbsent(biljka.getId(), new ArrayList<>());

        List<IstorijaZalivanja> istorijaZalivanja = istorijaZalivanjaStore.get(biljka.getId());

        long timestampMs = request.getTimestampMs() > 0
                ? request.getTimestampMs()
                : System.currentTimeMillis();

        SenzorskoOcitavanje ocitavanje = new SenzorskoOcitavanje(
                request.getBiljkaId(),
                request.getVlaznostZemljista(),
                request.getVlaznostVazduha(),
                request.getTemperatura(),
                request.getSvetlost(),
                request.getNivoRezervoara(),
                timestampMs);

        List<SenzorskoOcitavanje> istorijskaOcitavanja = istorijaOcitavanjaStore.get(biljka.getId());
        istorijskaOcitavanja.add(ocitavanje);

        RezultatAnalize rezultat = droolsService.analiziraj(
                ocitavanje, biljka, istorijaZalivanja, timestampMs);

        if (rezultat.getNovaZalivanja() != null) {
            istorijaZalivanja.addAll(rezultat.getNovaZalivanja());
        }

        return ResponseEntity.ok(rezultat);
    }

    @PostMapping("/dijagnostika")
    public ResponseEntity<List<Notifikacija>> dijagnostika(@RequestBody DijagnostikaRequest request) {
        Biljka biljka = registrovaneBiljke.get(request.getBiljkaId());
        if (biljka == null) {
            return ResponseEntity.badRequest().build();
        }

        long nowMs = System.currentTimeMillis();
        DijaganostickiUpit upit = new DijaganostickiUpit(
                request.getBiljkaId(),
                request.getSimptom(),
                request.getVizuelniSimptom(),
                nowMs);

        List<IstorijaZalivanja> istorijaZalivanja = istorijaZalivanjaStore.getOrDefault(
                request.getBiljkaId(), new ArrayList<>());
        List<SenzorskoOcitavanje> istorijskaOcitavanja = istorijaOcitavanjaStore.getOrDefault(
                request.getBiljkaId(), new ArrayList<>());

        List<Notifikacija> notifikacije = droolsService.analizirajDijagnozu(
                upit, biljka, istorijaZalivanja, istorijskaOcitavanja);

        return ResponseEntity.ok(notifikacije);
    }
}