package dev.codesoapbox.dummy4j;

import com.google.common.collect.Maps;
import dev.codesoapbox.dummy4j.definitions.DefinitionProvider;
import dev.codesoapbox.dummy4j.definitions.LocalizedDummyDefinitions;
import dev.codesoapbox.dummy4j.exceptions.MissingLocaleException;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionResolver {

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("#\\{(.*?)\\}");

    protected final Random random;
    protected final List<String> locales;
    protected final Map<String, LocalizedDummyDefinitions> localizedDefinitions;

    public ExpressionResolver(Random random, List<String> locales, DefinitionProvider definitionProvider) {
        this.random = random;
        this.locales = locales;
        this.localizedDefinitions = Maps.uniqueIndex(definitionProvider.get(), LocalizedDummyDefinitions::getLocale);

        locales.forEach(locale -> {
            if(!localizedDefinitions.containsKey(locale)) {
                throw new MissingLocaleException(locale);
            }
        });
    }

    /**
     * Resolves an expression like:
     * {@code #{name.male_first_name} #{name.last_name} }
     * <p>
     * Placeholders which have no definitions will be removed.
     * The method first looks into the locale which waswere passed as first in the list and then goes to the next
     * ones in order if a key could not be resolved.
     *
     * @param expression the expression to evaluate
     * @return a resolved random expression
     */
    public String resolve(String expression) {
        final String result = resolveAllKeys(expression);

        if (!VARIABLE_PATTERN.matcher(result).find()) {
            return result;
        }
        return resolve(result);
    }

    private String resolveAllKeys(String expression) {
        final Matcher expressionMatcher = VARIABLE_PATTERN.matcher(expression);

        final StringBuffer b = new StringBuffer(expression.length());
        while (expressionMatcher.find()) {
            String replacement = resolveKey(expressionMatcher.group(1));
            expressionMatcher.appendReplacement(b, replacement);
        }
        expressionMatcher.appendTail(b);

        return b.toString();
    }

    public String resolveKey(String key) {
        for (String locale : locales) {
            List<String> result = localizedDefinitions.get(locale).resolve(key);
            if (result != null) {
                return getRandom(result);
            }
        }
        return "";
    }

    private String getRandom(List<String> result) {
        int i = random.nextInt(result.size());
        return result.get(i);
    }
}
