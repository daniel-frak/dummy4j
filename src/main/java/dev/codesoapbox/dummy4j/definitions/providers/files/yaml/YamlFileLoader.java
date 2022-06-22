package dev.codesoapbox.dummy4j.definitions.providers.files.yaml;

import dev.codesoapbox.dummy4j.definitions.providers.files.ResourceStreamProvider;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * Loads .yml files from resources
 */
class YamlFileLoader {

    private static final Logger LOG = Logger.getLogger(YamlFileLoader.class.getName());
    private static final Pattern FILE_PATTERN = Pattern.compile(".*\\.yml");
    private static final List<Pattern> RESOURCES_PREFIX_PATTERNS = singletonList(
            Pattern.compile("^BOOT-INF/classes/")
    );

    /**
     * Note that Yaml isn't thread safe!
     */
    private final Yaml yaml;
    private final Reflections reflections;
    private final ResourceStreamProvider resourceStreamProvider;

    YamlFileLoader(Yaml yaml, Reflections reflections, ResourceStreamProvider resourceStreamProvider) {
        this.yaml = yaml;
        this.reflections = reflections;
        this.resourceStreamProvider = resourceStreamProvider;
    }

    static YamlFileLoader standard() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(Scanners.Resources)
                .setUrls(ClasspathHelper.forJavaClassPath())
                .addUrls(ClasspathHelper.forClassLoader()));
        return new YamlFileLoader(new Yaml(), reflections, new ResourceStreamProvider());
    }

    /**
     * Loads all .yml files from a list of paths and converts each file to a Map
     *
     * @param paths the paths to load from, relative to the resources folder
     * @return a list of maps, each representing a single .yml file
     */
    List<Map<String, Object>> loadYamlFiles(List<String> paths) {
        Set<String> resources = reflections.getResources(FILE_PATTERN);

        LOG.log(Level.FINE, "Loading definitions from files: {}", resources);

        return resources.stream()
                .map(this::removeResourcePrefixes)
                .distinct()
                .filter(r -> isInAllowedPath(r, paths))
                .map(this::loadResourceAsMap)
                .collect(toList());
    }

    private String removeResourcePrefixes(String resourcePath) {
        for (Pattern pattern : RESOURCES_PREFIX_PATTERNS) {
            resourcePath = pattern.matcher(resourcePath).replaceFirst(resourcePath);
        }
        return resourcePath;
    }

    private Map<String, Object> loadResourceAsMap(String r) {
        InputStream inputStream = resourceStreamProvider.get(r);
        return yaml.load(inputStream);
    }

    private boolean isInAllowedPath(String resourcePath, List<String> paths) {
        for (String path : paths) {
            if (resourcePath.startsWith(path)) {
                return true;
            }
        }
        return false;
    }
}
