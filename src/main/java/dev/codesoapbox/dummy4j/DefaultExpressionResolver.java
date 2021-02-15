package dev.codesoapbox.dummy4j;

import com.google.common.collect.Maps;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import dev.codesoapbox.dummy4j.exceptions.MissingLocaleDefinitionsException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * A default implementation of the expression resolver
 *
 * @since 0.5.0
 */
public final class DefaultExpressionResolver implements ExpressionResolver {

    private static final String ESCAPE_PREFIX = "\\\\?";
    private static final Pattern MULTI_LOCALE_VARIABLE_PATTERN = Pattern.compile("#\\{{2}((?:(?!#\\{|}).)*)}{2}");
    private static final Pattern SINGLE_LOCALE_VARIABLE_PATTERN = Pattern.compile("#\\{(?!\\{)((?:(?!#\\{|}).)*)}");
    private static final Pattern DIGIT_PATTERN = Pattern.compile(ESCAPE_PREFIX + "#(?!\\{)");

    final RandomService randomService;
    final List<String> locales;
    final Map<String, LocalizedDummyDefinitions> localizedDefinitions;

    public DefaultExpressionResolver(List<String> locales, RandomService randomService,
                                     DefinitionProvider definitionProvider) {
        if (locales == null || locales.isEmpty()) {
            locales = singletonList("en");
        }
        this.locales = locales;
        this.randomService = randomService;
        this.localizedDefinitions = Maps.uniqueIndex(definitionProvider.get(), LocalizedDummyDefinitions::getLocale);

        locales.forEach(locale -> {
            if (!localizedDefinitions.containsKey(locale)) {
                throw new MissingLocaleDefinitionsException(locale);
            }
        });
    }

    @Override
    public String resolve(String expression) {
        do {
            expression = replaceMultiLocalePlaceholders(expression);
            expression = replaceSingleLocalePlaceholders(expression);
        } while (MULTI_LOCALE_VARIABLE_PATTERN.matcher(expression).find()
                || SINGLE_LOCALE_VARIABLE_PATTERN.matcher(expression).find());

        return replaceDigitPlaceholders(expression);
    }

    private String replaceMultiLocalePlaceholders(String expression) {
        final Matcher expressionMatcher = MULTI_LOCALE_VARIABLE_PATTERN.matcher(expression);

        return replace(expression, expressionMatcher,
                () -> resolvePathWithinAllLocales(expressionMatcher.group(1)));
    }

    private String replace(String expression, Matcher expressionMatcher, Supplier<String> replacementSupplier) {
        final StringBuffer b = new StringBuffer(expression.length());
        while (expressionMatcher.find()) {
            if (expressionMatcher.group().charAt(0) == '\\') {
                expressionMatcher.appendReplacement(b, expressionMatcher.group().substring(1));
            } else {
                expressionMatcher.appendReplacement(b, Matcher.quoteReplacement(replacementSupplier.get()));
            }
        }
        expressionMatcher.appendTail(b);

        return b.toString();
    }

    /**
     * Resolves a single path to a random value from a superset of all locale values.
     * Note that this method does not perform any parsing and thus will not resolve values which themselves are
     * expressions.
     *
     * @param path the path to resolved
     * @return a random value based on the path
     */
    private String resolvePathWithinAllLocales(String path) {
        List<String> result = locales.stream()
                .map(locale -> localizedDefinitions.get(locale).resolve(path))
                .flatMap(Collection::stream)
                .distinct()
                .collect(toList());

        if (!result.isEmpty()) {
            return getRandom(result);
        }
        return "";
    }

    private String getRandom(List<String> result) {
        int i = randomService.nextInt(result.size() - 1);
        return result.get(i);
    }

    private String replaceSingleLocalePlaceholders(String expression) {
        final Matcher expressionMatcher = SINGLE_LOCALE_VARIABLE_PATTERN.matcher(expression);

        return replace(expression, expressionMatcher,
                () -> resolvePathWithinSingleLocale(expressionMatcher.group(1)));
    }

    /**
     * Resolves a single path to a random value from a single locale.
     * The method first looks into the primary locale and then goes to the next ones in order if a path could not be
     * resolved.
     * Note that this method does not perform any parsing and thus will not resolve values which themselves are
     * expressions.
     *
     * @param path the path to resolved
     * @return a random value based on the path
     */
    private String resolvePathWithinSingleLocale(String path) {
        for (String locale : locales) {
            List<String> result = localizedDefinitions.get(locale).resolve(path);
            if (result != null && !result.isEmpty()) {
                return getRandom(result);
            }
        }
        return "";
    }

    private String replaceDigitPlaceholders(String expressionWithResolvedKeys) {
        final Matcher digitMatcher = DIGIT_PATTERN.matcher(expressionWithResolvedKeys);

        return replace(expressionWithResolvedKeys, digitMatcher,
                () -> String.valueOf(randomService.nextInt(9)));
    }
}
