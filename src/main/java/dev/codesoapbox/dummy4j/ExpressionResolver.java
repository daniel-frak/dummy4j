package dev.codesoapbox.dummy4j;

/**
 * @since 0.5.0
 */
public interface ExpressionResolver {

    /**
     * Resolves an expression like:
     * {@code #{name.male_first_name} #{name.last_name} }
     * <p>
     * Single-locale placeholders are defined as {@code #{path.to.key}}.
     * They resolve to a random value from a single locale - the first one which contains values for the path, unless
     * the placeholder is part of a nested expression in which case it will always resolve only to its parent
     * expression's locale. The parent expression's locale is the locale of the first placeholder that was resolved
     * within it.
     * <p>
     * Multi-locale placeholders (since SNAPSHOT) are defined as {@code #{{path.to.key}}}.
     * They resolve to a random value from a superset of all locales' values.
     * <p>
     * Digits are defined simply as {@code #}.
     * <p>
     * Placeholders which don't resolve to anything will be removed from the expression.
     * <p>
     * Since SNAPSHOT:
     * <p>
     * If a path exists but does not resolve to a value or list of values, returns a random key contained directly
     * within it.
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
