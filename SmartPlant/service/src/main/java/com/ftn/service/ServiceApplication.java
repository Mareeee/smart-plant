package com.ftn.service;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServiceApplication {

    @Autowired
    private TemplateService templateService;

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @Bean
    public KieBase kieBase() {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo0.drl",
                kieServices.getResources().newClassPathResource("com/ftn/sbnz/kjar/rules/nivo0.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo1.drl",
                kieServices.getResources().newClassPathResource("com/ftn/sbnz/kjar/rules/nivo1.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo2.drl",
                kieServices.getResources().newClassPathResource("com/ftn/sbnz/kjar/rules/nivo2.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/nivo3.drl",
                kieServices.getResources().newClassPathResource("com/ftn/sbnz/kjar/rules/nivo3.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/cep-pravila.drl",
                kieServices.getResources().newClassPathResource("com/ftn/sbnz/kjar/rules/cep-pravila.drl"));
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/backward-dijagnostika.drl",
                kieServices.getResources().newClassPathResource("com/ftn/sbnz/kjar/rules/backward-dijagnostika.drl"));

        String generisaniDrl = templateService.kompajlirajTemplate();
        kieFileSystem.write(
                "src/main/resources/com/ftn/sbnz/kjar/rules/mikroklimatski-uslovi-generated.drl",
                kieServices.getResources().newByteArrayResource(generisaniDrl.getBytes()));

        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());

        KieBaseConfiguration kieBaseConfig = kieServices.newKieBaseConfiguration();
        kieBaseConfig.setOption(EventProcessingOption.STREAM);
        return kieContainer.newKieBase(kieBaseConfig);
    }
}