package com.ftn.service;

import com.ftn.model.Alarm;
import com.ftn.model.Biljka;
import com.ftn.model.Dijagnoza;
import com.ftn.model.NalogZaAkciju;
import com.ftn.model.Notifikacija;
import com.ftn.model.ParametriBiljke;
import com.ftn.model.SenzorskoOcitavanje;
import com.ftn.model.TipBiljke;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DroolsService {

    @Autowired
    private KieContainer kieContainer;

    private List<ParametriBiljke> initParametri() {
        List<ParametriBiljke> parametri = new ArrayList<>();
        parametri.add(new ParametriBiljke(TipBiljke.KAKTUS, 5, 20, 15, 5000));
        parametri.add(new ParametriBiljke(TipBiljke.ORHIDEJA, 40, 70, 18, 1000));
        parametri.add(new ParametriBiljke(TipBiljke.PAPRAT, 50, 80, 16, 300));
        parametri.add(new ParametriBiljke(TipBiljke.FIKUS, 30, 60, 16, 2000));
        parametri.add(new ParametriBiljke(TipBiljke.RUZA, 35, 65, 10, 4000));
        return parametri;
    }

    public RezultatAnalize analiziraj(SenzorskoOcitavanje ocitavanje, Biljka biljka) {
        KieSession kieSession = kieContainer.newKieSession();

        try {
            for (ParametriBiljke p : initParametri()) {
                kieSession.insert(p);
            }

            kieSession.insert(biljka);
            kieSession.insert(ocitavanje);

            Alarm alarm = new Alarm(biljka.getId());
            Dijagnoza dijagnoza = new Dijagnoza(biljka.getId());
            NalogZaAkciju nalog = new NalogZaAkciju(biljka.getId());

            kieSession.insert(alarm);
            kieSession.insert(dijagnoza);
            kieSession.insert(nalog);

            kieSession.fireAllRules();

            Collection<Notifikacija> notifikacije = new ArrayList<>();
            for (Object obj : kieSession.getObjects()) {
                if (obj instanceof Notifikacija) {
                    notifikacije.add((Notifikacija) obj);
                }
            }

            return new RezultatAnalize(alarm, dijagnoza, nalog, new ArrayList<>(notifikacije));

        } finally {
            kieSession.dispose();
        }
    }
}