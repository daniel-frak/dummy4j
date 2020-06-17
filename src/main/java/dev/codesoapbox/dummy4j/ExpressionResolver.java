package dev.codesoapbox.dummy4j;

import com.google.common.collect.Maps;
import dev.codesoapbox.dummy4j.definitions.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.exceptions.MissingLocaleException;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.singletonList;

public class ExpressionResolver {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("#\\{(.*?)\\}");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("#(?!\\{)");

    protected final RandomService randomService;
    protected final List<String> locales;
    protected final Map<String, LocalizedDummyDefinitions> localizedDefinitions;

    public ExpressionResolver(List<String> locales, RandomService randomService,
                              DefinitionProvider definitionProvider) {
        if(locales == null || locales.isEmpty()) {
            locales = singletonList("en");
        }
        this.locales = locales;
        this.randomService = randomService;
        this.localizedDefinitions = Maps.uniqueIndex(definitionProvider.get(), LocalizedDummyDefinitions::getLocale);

        locales.forEach(locale -> {
            if (!localizedDefinitions.containsKey(locale)) {
                throw new MissingLocaleException(locale);
            }
        });
    }

    /**
     * Resolves an expression like:
     * {@code #{name.male_first_name} #{name.last_name} }
     * <p>
     * Definition keys are defined as {@code #{definition.path}}.
     * <p>
     * Digits are defined simply as {@code #}.
     * <p>
     * Placeholders which have no definitions will be removed.
     * The method first looks into the locale which was passed as first in the list and then goes to the next
     * ones in order if a key could not be resolved.
     *
     * @param expression the expression to evaluate
     * @return a resolved random expression
     */
    public String resolve(String expression) {
        final String result = resolveAllKeysAndDigits(expression);

        if (!VARIABLE_PATTERN.matcher(result).find()) {
            return result;
        }
        return resolve(result);
    }

    private String resolveAllKeysAndDigits(String expression) {
        final String expressionWithResolvedKeys = replaceKeyPlaceholders(expression);
        return replaceDigitPlaceholders(expressionWithResolvedKeys);
    }

    private String replaceDigitPlaceholders(String expressionWithResolvedKeys) {
        final Matcher digitMatcher = DIGIT_PATTERN.matcher(expressionWithResolvedKeys);

        return replace(expressionWithResolvedKeys, digitMatcher,
                () -> String.valueOf(randomService.nextInt(10)));
    }

    private String replaceKeyPlaceholders(String expression) {
        final Matcher expressionMatcher = VARIABLE_PATTERN.matcher(expression);

        return replace(expression, expressionMatcher,
                () -> resolveKey(expressionMatcher.group(1)));
    }

    private String replace(String expression, Matcher expressionMatcher, Supplier<String> replacementSupplier) {
        final StringBuffer b = new StringBuffer(expression.length());
        while (expressionMatcher.find()) {
            expressionMatcher.appendReplacement(b, replacementSupplier.get());
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
    public String resolveKey(String key) {
        for (String locale : locales) {
            List<String> result = localizedDefinitions.get(locale).resolve(key);
            if (result != null && !result.isEmpty()) {
                return getRandom(result);
            }
        }
        return "";
    }

    private String getRandom(List<String> result) {
        int i = randomService.nextInt(result.size());
        return result.get(i);
    }
}
