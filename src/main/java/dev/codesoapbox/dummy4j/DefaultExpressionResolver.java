package dev.codesoapbox.dummy4j;

import com.google.common.collect.Maps;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.definitions.providers.DefinitionProvider;
import dev.codesoapbox.dummy4j.exceptions.MissingLocaleDefinitionsException;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;

/**
 * A default implementation of the expression resolver
 *
 * @since 0.5.0
 */
public final class DefaultExpressionResolver implements ExpressionResolver {

    private static final String ESCAPE_PREFIX = "\\\\?";
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("#\\{((?:(?!#\\{|}).)*)}");
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
        final String expressionWithResolvedKeys = replaceKeyPlaceholders(expression);

        if (!VARIABLE_PATTERN.matcher(expressionWithResolvedKeys).find()) {
            return replaceDigitPlaceholders(expressionWithResolvedKeys);
        }

        return resolve(expressionWithResolvedKeys);
    }

    private String replaceKeyPlaceholders(String expression) {
        final Matcher expressionMatcher = VARIABLE_PATTERN.matcher(expression);

        return replace(expression, expressionMatcher,
                () -> resolveKey(expressionMatcher.group(1)));
    }

    private String replaceDigitPlaceholders(String expressionWithResolvedKeys) {
        final Matcher digitMatcher = DIGIT_PATTERN.matcher(expressionWithResolvedKeys);

        return replace(expressionWithResolvedKeys, digitMatcher,
                () -> String.valueOf(randomService.nextInt(9)));
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
     * Resolves a single key to a random value.
     * Note that this method does not perform any parsing and thus will not resolve values which themselves are
     * expressions.
     *
     * @param key the key to resolved
     * @return a random value based on the key
     */
    private String resolveKey(String key) {
        for (String locale : locales) {
            List<String> result = localizedDefinitions.get(locale).resolve(key);
            if (result != null && !result.isEmpty()) {
                return getRandom(result);
            }
        }
        return "";
    }

    private String getRandom(List<String> result) {
        int i = randomService.nextInt(result.size() - 1);
        return result.get(i);
    }
}
