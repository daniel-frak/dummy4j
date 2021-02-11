package dev.codesoapbox.dummy4j;

/**
 * @since 0.5.0
 */
public interface ExpressionResolver {

    /**
     * Resolves an expression like:
     * {@code #{name.male_first_name} #{name.last_name} }
     * <p>
     * Placeholders are defined as {@code #{definition.path}}.
     * <p>
     * Digits are defined simply as {@code #}.
     * <p>
     * Placeholders which don't resolve to anything will be removed.
     * <p>
     * The method first looks into the primary locale and then goes to the next ones in order if a key could not be
     * resolved.
     * <p>
     * If a path does not resolve to a value or list of values, returns a random key contained directly within it
     * (if possible).
     * <p>
     * It is possible to resolve nested expressions like "{@code #{key1.#{key2}}}" (since SNAPSHOT).
     * In that case, the placeholder "{@code #{key2}}" will be resolved first and its result will be used to resolve
     * the root placeholder.
     *
     * @param expression the expression to evaluate
     * @return a resolved random expression
     */
    String resolve(String expression);
}
