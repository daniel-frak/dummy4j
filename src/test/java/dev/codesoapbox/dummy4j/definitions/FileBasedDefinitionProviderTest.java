package dev.codesoapbox.dummy4j.definitions;

import dev.codesoapbox.dummy4j.definitions.files.yaml.FileBasedDefinitionProvider;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.yaml.snakeyaml.Yaml;

import java.util.List;

import static java.util.Collections.*;

class FileBasedDefinitionProviderTest {

    @Test
    void shouldGet() {
//        Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new ResourcesScanner())
//                .setUrls(ClasspathHelper.forJavaClassPath()));
//
//        FileBasedDefinitionProvider fileBasedDefinitionProvider =
//                new FileBasedDefinitionProvider(new Yaml(), reflections, singletonList("dummy4j"));
//        List<LocalizedDummyDefinitions> result = fileBasedDefinitionProvider.get();
//        System.out.println(result);
    }
}