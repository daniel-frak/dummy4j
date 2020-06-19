package dev.codesoapbox.dummy4j.definitions.providers.files.yaml;

import dev.codesoapbox.dummy4j.definitions.providers.files.ResourceStreamProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reflections.Reflections;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class YamlFileLoaderTest {

    private YamlFileLoader yamlFileLoader;

    @Mock
    private Yaml yaml;

    @Mock
    private Reflections reflections;

    @Mock
    private ResourceStreamProvider resourceStreamProvider;

    @BeforeEach
    void setUp() {
        yamlFileLoader = new YamlFileLoader(yaml, reflections, resourceStreamProvider);
    }

    @Test
    void shouldLoadYamlFilesFromGivenPaths() {
        String resource1 = "dummy4j/resource1.yml";
        String resource2 = "dummy4j/deep/resource2.yml";
        String resource3 = "other/resource3.yml";
        HashSet<String> resources = new HashSet<>();
        resources.add(resource1);
        resources.add(resource2);
        resources.add(resource3);

        when(reflections.getResources((Pattern) any()))
                .thenReturn(resources);
        when(resourceStreamProvider.get(any()))
                .thenAnswer(this::getStringAsInputStream);

        List<String> loadedResources = new ArrayList<>();
        when(yaml.load((InputStream) any()))
                .thenAnswer(inv -> {
                    loadedResources.add(getInputStreamAsString(inv));
                    return new HashMap<>();
                });

        List<Map<String, Object>> result = yamlFileLoader.loadYamlFiles(singletonList("dummy4j"));
        assertEquals(2, result.size());
        assertTrue(loadedResources.contains(resource1));
        assertTrue(loadedResources.contains(resource2));
    }

    private String getInputStreamAsString(InvocationOnMock inv) {
        return new BufferedReader(new InputStreamReader(inv.getArgument(0)))
                .lines().collect(Collectors.joining("\n"));
    }

    private ByteArrayInputStream getStringAsInputStream(InvocationOnMock inv) {
        return new ByteArrayInputStream(((String) inv.getArgument(0)).getBytes(StandardCharsets.UTF_8));
    }
}