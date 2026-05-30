package com.ftn.service;

import org.drools.template.ObjectDataCompiler;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TemplateService {

    private static final Object[][] PODACI_BILJAKA = {
            { "KAKTUS", 5.0, 20.0, 15.0, 5000.0 },
            { "ORHIDEJA", 40.0, 70.0, 18.0, 1000.0 },
            { "PAPRAT", 50.0, 80.0, 16.0, 300.0 },
            { "FIKUS", 30.0, 60.0, 16.0, 2000.0 },
            { "RUZA", 35.0, 65.0, 10.0, 4000.0 },
            { "MONSTERA", 40.0, 70.0, 18.0, 1500.0 },
            { "SANSEVERIJA", 10.0, 30.0, 15.0, 800.0 },
            { "ALOE_VERA", 15.0, 35.0, 16.0, 4000.0 },
            { "SPATIFILUM", 50.0, 75.0, 18.0, 1000.0 },
            { "DRACENA", 30.0, 55.0, 16.0, 1200.0 }
    };

    public String kompajlirajTemplate() {
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(
                    "classpath*:**/mikroklimatski-uslovi.drt");
            if (resources.length == 0) {
                throw new RuntimeException("DRT template fajl nije pronadjen ni na jednoj putanji classpath-a");
            }
            try (InputStream templateStream = resources[0].getInputStream()) {
                ObjectDataCompiler compiler = new ObjectDataCompiler();
                return compiler.compile(buildRows(), templateStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("Greska pri ucitavanju DRT template fajla", e);
        }
    }

    private List<Map<String, Object>> buildRows() {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Object[] row : PODACI_BILJAKA) {
            Map<String, Object> map = new HashMap<>();
            map.put("tipBiljke", row[0]);
            map.put("minVlaga", row[1]);
            map.put("maxVlaga", row[2]);
            map.put("minTemp", row[3]);
            map.put("minSvetlost", row[4]);
            rows.add(map);
        }
        return rows;
    }
}