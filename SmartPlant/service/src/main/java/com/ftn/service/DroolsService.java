package com.ftn.service;

import com.ftn.model.Alarm;
import com.ftn.model.Biljka;
import com.ftn.model.Dijagnoza;
import com.ftn.model.DijaganostickiUpit;
import com.ftn.model.IstorijaZalivanja;
import com.ftn.model.NalogZaAkciju;
import com.ftn.model.Notifikacija;
import com.ftn.model.SenzorskoOcitavanje;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.api.time.SessionPseudoClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class DroolsService {

    @Autowired
    private TemplateService templateService;

    public RezultatAnalize analiziraj(SenzorskoOcitavanje ocitavanje, Biljka biljka,
            List<IstorijaZalivanja> istorijaZalivanja, long timestampMs) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo0.drl",
                kieServices.getResources().newClassPathResource(
                        "com/ftn/sbnz/kjar/rules/nivo0.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo1.drl",
                kieServices.getResources().newClassPathResource(
                        "com/ftn/sbnz/kjar/rules/nivo1.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo2.drl",
                kieServices.getResources().newClassPathResource(
                        "com/ftn/sbnz/kjar/rules/nivo2.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo3.drl",
                kieServices.getResources().newClassPathResource(
                        "com/ftn/sbnz/kjar/rules/nivo3.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/cep-pravila.drl",
                kieServices.getResources().newClassPathResource(
                        "com/ftn/sbnz/kjar/rules/cep-pravila.drl"));

        String generisaniDrl = templateService.kompajlirajTemplate();
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/mikroklimatski-uslovi-generated.drl",
                kieServices.getResources().newByteArrayResource(
                        generisaniDrl.getBytes()));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        KieBaseConfiguration kieBaseConfig = kieServices.newKieBaseConfiguration();
        kieBaseConfig.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kieContainer.newKieBase(kieBaseConfig);

        KieSessionConfiguration sessionConfig = kieServices.newKieSessionConfiguration();
        sessionConfig.setOption(ClockTypeOption.get("pseudo"));
        KieSession kieSession = kieBase.newKieSession(sessionConfig, null);

        SessionPseudoClock clock = kieSession.getSessionClock();
        clock.advanceTime(timestampMs, TimeUnit.MILLISECONDS);
        long nowMs = clock.getCurrentTime();

        ocitavanje.setTimestampMs(nowMs);

        try {
            kieSession.insert(biljka);
            kieSession.insert(ocitavanje);
            kieSession.insert(nowMs);

            if (istorijaZalivanja != null) {
                for (IstorijaZalivanja zapis : istorijaZalivanja) {
                    kieSession.insert(zapis);
                }
            }

            Alarm alarm = new Alarm(biljka.getId());
            Dijagnoza dijagnoza = new Dijagnoza(biljka.getId());
            NalogZaAkciju nalog = new NalogZaAkciju(biljka.getId());

            kieSession.insert(alarm);
            kieSession.insert(dijagnoza);
            kieSession.insert(nalog);

            kieSession.fireAllRules();

            List<Notifikacija> notifikacije = new ArrayList<>();
            List<IstorijaZalivanja> novaZalivanja = new ArrayList<>();

            for (Object obj : kieSession.getObjects()) {
                if (obj instanceof Notifikacija) {
                    notifikacije.add((Notifikacija) obj);
                } else if (obj instanceof IstorijaZalivanja) {
                    IstorijaZalivanja iz = (IstorijaZalivanja) obj;
                    if (iz.getTimestampMs() == nowMs) {
                        novaZalivanja.add(iz);
                    }
                }
            }

            return new RezultatAnalize(alarm, dijagnoza, nalog, notifikacije, novaZalivanja);

        } finally {
            kieSession.dispose();
        }
    }

    public List<Notifikacija> analizirajDijagnozu(DijaganostickiUpit upit, Biljka biljka,
            List<IstorijaZalivanja> istorijaZalivanja, List<SenzorskoOcitavanje> istorijskaOcitavanja) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/backward-dijagnostika.drl",
                kieServices.getResources().newClassPathResource(
                        "com/ftn/sbnz/kjar/rules/backward-dijagnostika.drl"));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        KieBaseConfiguration kieBaseConfig = kieServices.newKieBaseConfiguration();
        kieBaseConfig.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kieContainer.newKieBase(kieBaseConfig);

        KieSession kieSession = kieBase.newKieSession();

        try {
            kieSession.insert(biljka);
            kieSession.insert(upit);

            if (istorijaZalivanja != null) {
                for (IstorijaZalivanja zapis : istorijaZalivanja) {
                    kieSession.insert(zapis);
                }
            }

            if (istorijskaOcitavanja != null) {
                for (SenzorskoOcitavanje ocitavanje : istorijskaOcitavanja) {
                    kieSession.insert(ocitavanje);
                }
            }

            kieSession.fireAllRules();

            List<Notifikacija> notifikacije = new ArrayList<>();
            for (Object obj : kieSession.getObjects()) {
                if (obj instanceof Notifikacija) {
                    notifikacije.add((Notifikacija) obj);
                }
            }

            return notifikacije;

        } finally {
            kieSession.dispose();
        }
    }
}