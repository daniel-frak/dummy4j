package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.FileBasedDefinitionProvider;
import dev.codesoapbox.dummy4j.dummies.Dummies;
import dev.codesoapbox.dummy4j.dummies.NameDummy;
import dev.codesoapbox.dummy4j.dummies.SpaceshipDummy;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.Collections.singletonList;

public class Dummy4j {

    protected final List<String> locales;
    protected final ExpressionResolver expressionResolver;
    protected final Dummies dummies;
    private final long seed;

    public Dummy4j() {
        seed = ThreadLocalRandom.current().nextInt();
        locales = singletonList("en");

        Reflections reflections = new Reflections(new ConfigurationBuilder().setScanners(new ResourcesScanner())
                .setUrls(ClasspathHelper.forJavaClassPath()));
        DefinitionProvider definitionProvider =
                new FileBasedDefinitionProvider(new Yaml(), reflections, singletonList("dummy4j"));

        expressionResolver = new ExpressionResolver(new Random(seed), locales, definitionProvider);

        dummies = new Dummies(this);
    }

    public long getSeed() {
        return seed;
    }

    public List<String> getLocales() {
        return locales;
    }

    public ExpressionResolver getExpressionResolver() {
        return expressionResolver;
    }

    public NameDummy name() {
        return dummies.name();
    }

    public SpaceshipDummy spaceship() {
        return dummies.spaceship();
    }
}
