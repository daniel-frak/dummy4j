package dev.codesoapbox.dummy4j;

import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import dev.codesoapbox.dummy4j.exceptions.MissingLocaleDefinitionsException;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
    private static final Logger LOG = Logger.getLogger(DefaultExpressionResolver.class.getName());

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
        this.localizedDefinitions = definitionProvider.get().stream()
                .collect(Collectors.toMap(LocalizedDummyDefinitions::getLocale, d -> d));

        locales.forEach(locale -> {
            if (!localizedDefinitions.containsKey(locale)) {
                throw new MissingLocaleDefinitionsException(locale);
            }
        });
    }

    @Override
    public String resolve(String expression) {
        ResolvedValue resolvedValue = ResolvedValue.of("", expression);
        do {
            resolvedValue = replaceMultiLocalePlaceholders(resolvedValue);
            resolvedValue = replaceSingleLocalePlaceholders(resolvedValue);
        } while (MULTI_LOCALE_VARIABLE_PATTERN.matcher(resolvedValue.getValue()).find()
                || SINGLE_LOCALE_VARIABLE_PATTERN.matcher(resolvedValue.getValue()).find());

        return replaceDigitPlaceholders(resolvedValue).getValue();
    }

    private ResolvedValue replaceMultiLocalePlaceholders(ResolvedValue resolvedValue) {
        String expression = resolvedValue.getValue();
        final Matcher expressionMatcher = MULTI_LOCALE_VARIABLE_PATTERN.matcher(expression);

        return replace(resolvedValue, expressionMatcher,
                locale -> resolvePathWithinAllLocales(expressionMatcher.group(1)));
    }

    private ResolvedValue replace(ResolvedValue resolvedValue, Matcher expressionMatcher,
                                  Function<String, ResolvedValue> replacementSupplier) {
        String expression = resolvedValue.getValue();
        String locale = resolvedValue.getLocale();
        final StringBuffer b = new StringBuffer(expression.length());
        while (expressionMatcher.find()) {
            if (expressionMatcher.group().charAt(0) == '\\') {
                expressionMatcher.appendReplacement(b, expressionMatcher.group().substring(1));
            } else {
                ResolvedValue newValue = replacementSupplier.apply(locale);
                locale = newValue.getLocale();
                expressionMatcher.appendReplacement(b, Matcher.quoteReplacement
                        (newValue.getValue()));
            }
        }
        expressionMatcher.appendTail(b);

        return ResolvedValue.of(locale, b.toString());
    }

    /**
     * Resolves a single path to a random value from a superset of all locale values.
     * Note that this method does not perform any parsing and thus will not resolve values which themselves are
     * expressions.
     *
     * @param path the path to resolved
     * @return a random value based on the path
     */
    private ResolvedValue resolvePathWithinAllLocales(String path) {
        List<ResolvedValue> result = locales.stream()
                .flatMap(locale -> localizedDefinitions.get(locale).resolve(path).stream()
                        .map(v -> ResolvedValue.of(locale, v)))
                .collect(toList());

        if (!result.isEmpty()) {
            return getRandom(result);
        }
        LOG.log(Level.FINE, "Could not resolve path: {0} in any locale", path);

        return ResolvedValue.of("", "");
    }

    private ResolvedValue getRandom(List<ResolvedValue> result) {
        int i = randomService.nextInt(result.size() - 1);
        return result.get(i);
    }

    private ResolvedValue replaceSingleLocalePlaceholders(ResolvedValue resolvedValue) {
        String expression = resolvedValue.getValue();
        final Matcher expressionMatcher = SINGLE_LOCALE_VARIABLE_PATTERN.matcher(expression);

        return replace(resolvedValue, expressionMatcher,
                locale -> resolvePathWithinSingleLocale(expressionMatcher.group(1), locale));
    }

    /**
     * Resolves a single path to a random value from a single locale.
     * If originalLocale is empty, the method first looks into the primary locale and then goes to the next ones,
     * preserving order, if a path could not be resolved.
     * If originalLocale is not empty, the method looks only into that locale.
     * Note that this method does not perform any parsing and thus will not resolve values which themselves are
     * expressions.
     *
     * @param path the path to resolved
     * @return a random value based on the path
     */
    private ResolvedValue resolvePathWithinSingleLocale(String path, String originalLocale) {
        for (String locale : locales) {
            if (!originalLocale.isEmpty() && !originalLocale.equals(locale)) {
                continue;
            }
            List<String> result = localizedDefinitions.get(locale).resolve(path);
            if (result != null && !result.isEmpty()) {
                return ResolvedValue.of(locale, getRandomString(result));
            }
        }
        if (!"".equals(originalLocale)) {
            LOG.log(Level.FINE, "Could not resolve path: {0} for locale: {1}", new Object[]{path, originalLocale});
        } else {
            LOG.log(Level.FINE, "Could not resolve path: {0} in any locale", path);
        }

        return ResolvedValue.of("", "");
    }

    private String getRandomString(List<String> result) {
        int i = randomService.nextInt(result.size() - 1);
        return result.get(i);
    }

    private ResolvedValue replaceDigitPlaceholders(ResolvedValue resolvedValue) {
        String expressionWithResolvedKeys = resolvedValue.getValue();
        final Matcher digitMatcher = DIGIT_PATTERN.matcher(expressionWithResolvedKeys);

        return replace(resolvedValue, digitMatcher,
                locale -> ResolvedValue.of(locale, String.valueOf(randomService.nextInt(9))));
    }
}
