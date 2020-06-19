package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.providers.files.yaml.YamlFileDefinitionProvider;
import dev.codesoapbox.dummy4j.dummies.AddressDummy;
import dev.codesoapbox.dummy4j.dummies.Dummies;
import dev.codesoapbox.dummy4j.dummies.NameDummy;
import dev.codesoapbox.dummy4j.dummies.ScifiDummy;

import java.util.List;
import java.util.function.Function;

public class Dummy4j {

    protected final ExpressionResolver expressionResolver;
    protected final RandomService randomService;
    protected final Dummies dummies;

    public Dummy4j() {
        this(null, null);
    }

    public Dummy4j(Long seed, List<String> locales) {
        this.randomService = seed != null ? new RandomService(seed) : new RandomService(null);

        if (locales != null) {
            this.expressionResolver = new ExpressionResolver(locales, this.randomService,
                    YamlFileDefinitionProvider.standard());
        } else {
            this.expressionResolver = new ExpressionResolver(null, this.randomService,
                    YamlFileDefinitionProvider.standard());
        }

        this.dummies = new Dummies(this);
    }

    public Dummy4j(Long seed) {
        this(seed, null);
    }

    public Dummy4j(List<String> locales) {
        this(null, locales);
    }

    public Dummy4j(ExpressionResolver expressionResolver, RandomService randomService,
                   Function<? super Dummy4j, Dummies> dummiesFactory) {
        this.randomService = randomService;
        this.expressionResolver = expressionResolver;
        this.dummies = dummiesFactory.apply(this);
    }

    public ExpressionResolver getExpressionResolver() {
        return expressionResolver;
    }

    public RandomService random() {
        return randomService;
    }

    public NameDummy name() {
        return dummies.name();
    }

    public AddressDummy address() {
        return dummies.address();
    }

    public ScifiDummy scifi() {
        return dummies.scifi();
    }
}
