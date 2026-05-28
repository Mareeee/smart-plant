package com.ftn.sbnz.kjar;

import com.ftn.model.Biljka;
import com.ftn.model.NalogZaAkciju;
import com.ftn.model.Notifikacija;
import com.ftn.model.SenzorskoOcitavanje;
import com.ftn.model.TipBiljke;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CepTest {

    private KieSession kieSession;
    private SessionPseudoClock clock;

    @Before
    public void setup() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/cep-pravila.drl",
                kieServices.getResources().newClassPathResource(
                        "com/ftn/sbnz/kjar/rules/cep-pravila.drl"));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        KieBaseConfiguration kieBaseConfig = kieServices.newKieBaseConfiguration();
        kieBaseConfig.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kieContainer.newKieBase(kieBaseConfig);

        KieSessionConfiguration sessionConfig = kieServices.newKieSessionConfiguration();
        sessionConfig.setOption(ClockTypeOption.get("pseudo"));
        kieSession = kieBase.newKieSession(sessionConfig, null);

        clock = kieSession.getSessionClock();
        clock.advanceTime(1000000L, TimeUnit.MILLISECONDS);
    }

    @After
    public void teardown() {
        kieSession.dispose();
    }

    @Test
    public void testNagliPadTemperature_trebaOkidati() {
        Biljka biljka = new Biljka(1L, "Orhideja", TipBiljke.ORHIDEJA);
        kieSession.insert(biljka);

        long t0 = clock.getCurrentTime();
        SenzorskoOcitavanje staro = new SenzorskoOcitavanje(1L, 50, 60, 21.0, 1500, 80, t0);
        kieSession.insert(staro);
        kieSession.fireAllRules();

        clock.advanceTime(3, TimeUnit.MINUTES);
        long t1 = clock.getCurrentTime();
        SenzorskoOcitavanje novo = new SenzorskoOcitavanje(1L, 50, 60, 10.5, 1500, 80, t1);
        kieSession.insert(novo);
        kieSession.fireAllRules();

        List<Notifikacija> notifikacije = kieSession.getObjects().stream()
                .filter(o -> o instanceof Notifikacija)
                .map(o -> (Notifikacija) o)
                .collect(Collectors.toList());

        boolean alarm = notifikacije.stream()
                .anyMatch(n -> n.getPoruka().contains("Nagli pad temperature"));

        assertTrue("CEP Scenario 1 trebalo je da okine alarm za nagli pad temperature", alarm);
    }

    @Test
    public void testNagliPadTemperature_neSmeTrebaOkidati() {
        Biljka biljka = new Biljka(2L, "Fikus", TipBiljke.FIKUS);
        kieSession.insert(biljka);

        long t0 = clock.getCurrentTime();
        SenzorskoOcitavanje staro = new SenzorskoOcitavanje(2L, 40, 55, 21.0, 2500, 70, t0);
        kieSession.insert(staro);
        kieSession.fireAllRules();

        clock.advanceTime(10, TimeUnit.MINUTES);
        long t1 = clock.getCurrentTime();
        SenzorskoOcitavanje novo = new SenzorskoOcitavanje(2L, 40, 55, 10.5, 2500, 70, t1);
        kieSession.insert(novo);
        kieSession.fireAllRules();

        List<Notifikacija> notifikacije = kieSession.getObjects().stream()
                .filter(o -> o instanceof Notifikacija)
                .map(o -> (Notifikacija) o)
                .collect(Collectors.toList());

        boolean alarm = notifikacije.stream()
                .anyMatch(n -> n.getPoruka().contains("Nagli pad temperature"));

        assertFalse("CEP Scenario 1 ne sme okinuti jer je pad bio izvan prozora od 5 minuta", alarm);
    }

    @Test
    public void testKvarSenzora_trebaOkidati() {
        Biljka biljka = new Biljka(3L, "Kaktus", TipBiljke.KAKTUS);
        NalogZaAkciju nalog = new NalogZaAkciju(3L);
        kieSession.insert(biljka);
        kieSession.insert(nalog);

        double[] vlage = { 10.0, 50.0, 10.0, 50.0, 10.0 };
        for (double vlaga : vlage) {
            long t = clock.getCurrentTime();
            SenzorskoOcitavanje o = new SenzorskoOcitavanje(3L, vlaga, 40, 20.0, 6000, 60, t);
            kieSession.insert(o);
            kieSession.fireAllRules();
            clock.advanceTime(20, TimeUnit.SECONDS);
        }

        List<Notifikacija> notifikacije = kieSession.getObjects().stream()
                .filter(o -> o instanceof Notifikacija)
                .map(o -> (Notifikacija) o)
                .collect(Collectors.toList());

        boolean kvar = notifikacije.stream()
                .anyMatch(n -> n.getPoruka().contains("KVAR SENZORA"));

        assertTrue("CEP Scenario 2 trebalo je da detektuje kvar senzora vlage", kvar);
    }

    @Test
    public void testKvarSenzora_neSmeTrebaOkidati() {
        Biljka biljka = new Biljka(4L, "Paprat", TipBiljke.PAPRAT);
        NalogZaAkciju nalog = new NalogZaAkciju(4L);
        kieSession.insert(biljka);
        kieSession.insert(nalog);

        double[] vlage = { 55.0, 60.0, 58.0, 62.0, 57.0 };
        for (double vlaga : vlage) {
            long t = clock.getCurrentTime();
            SenzorskoOcitavanje o = new SenzorskoOcitavanje(4L, vlaga, 60, 18.0, 400, 70, t);
            kieSession.insert(o);
            kieSession.fireAllRules();
            clock.advanceTime(20, TimeUnit.SECONDS);
        }

        List<Notifikacija> notifikacije = kieSession.getObjects().stream()
                .filter(o -> o instanceof Notifikacija)
                .map(o -> (Notifikacija) o)
                .collect(Collectors.toList());

        boolean kvar = notifikacije.stream()
                .anyMatch(n -> n.getPoruka().contains("KVAR SENZORA"));

        assertFalse("CEP Scenario 2 ne sme okinuti jer su oscilacije vlage male", kvar);
    }
}