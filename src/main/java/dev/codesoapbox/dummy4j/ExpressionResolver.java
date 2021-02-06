package dev.codesoapbox.dummy4j;

import java.util.Set;

/**
 * @since 0.5.0
 */
public interface ExpressionResolver {

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
    String resolve(String expression);

    /**
     * Returns a list of all keys contained directly within a given path.
     * <p>
     * The keys returned will be a superset of keys in every locale.
     * <p>
     * This method does not support expressions.
     *
     * @param path the path whose sub-keys should be returned
     * @return a list of keys
     * @since SNAPSHOT
     */
    Set<String> getKeysFor(String path);
}
