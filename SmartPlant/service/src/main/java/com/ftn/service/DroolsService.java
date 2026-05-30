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
import org.kie.api.KieServices;
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
    private KieBase kieBase;

    public RezultatAnalize analiziraj(SenzorskoOcitavanje ocitavanje, Biljka biljka,
            List<IstorijaZalivanja> istorijaZalivanja, long timestampMs) {

        KieSessionConfiguration sessionConfig = KieServices.Factory.get().newKieSessionConfiguration();
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
                for (SenzorskoOcitavanje o : istorijskaOcitavanja) {
                    kieSession.insert(o);
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