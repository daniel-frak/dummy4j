package dev.codesoapbox.dummy4j.definitions.providers.files.yaml;

import dev.codesoapbox.dummy4j.definitions.providers.files.ResourceStreamProvider;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
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

import static java.util.stream.Collectors.toList;

/**
 * Loads .yml files from resources
 */
public class YamlFileLoader {

    private static final Logger LOG = Logger.getLogger(YamlFileLoader.class.getSimpleName());
    private static final Pattern FILE_PATTERN = Pattern.compile(".*\\.yml");

    /**
     * Note that Yaml isn't thread safe!
     */
    private final Yaml yaml;
    private final Reflections reflections;
    private final ResourceStreamProvider resourceStreamProvider;

    public YamlFileLoader(Yaml yaml, Reflections reflections, ResourceStreamProvider resourceStreamProvider) {
        this.yaml = yaml;
        this.reflections = reflections;
        this.resourceStreamProvider = resourceStreamProvider;
    }

    public static YamlFileLoader standard() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new ResourcesScanner())
                .setUrls(ClasspathHelper.forJavaClassPath()));
        return new YamlFileLoader(new Yaml(), reflections, new ResourceStreamProvider());
    }

    /**
     * Loads all .yml files from a list of paths and converts each file to a Map
     *
     * @param paths the paths to load from, relative to the resources folder
     * @return a list of maps, each representing a single .yml file
     */
    public List<Map<String, Object>> loadYamlFiles(List<String> paths) {
        Set<String> resources = reflections.getResources(FILE_PATTERN);

        LOG.log(Level.FINE, "Loading definitions from: {0}", resources);

        return resources.stream()
                .filter(r -> isInAllowedPath(r, paths))
                .map(this::loadResourceAsMap)
                .collect(toList());
    }

    private Map<String, Object> loadResourceAsMap(String r) {
        InputStream inputStream = resourceStreamProvider.get(r);
        return yaml.load(inputStream);
    }

    private boolean isInAllowedPath(String resource, List<String> paths) {
        boolean isInAllowedPath = false;
        for (String path : paths) {
            if (resource.startsWith(path)) {
                isInAllowedPath = true;
                break;
            }
        }

        return isInAllowedPath;
    }
}
