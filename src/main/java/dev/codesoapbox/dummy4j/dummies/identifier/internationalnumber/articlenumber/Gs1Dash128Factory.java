package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.articlenumber;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.dummies.DateAndTimeDummy;
import dev.codesoapbox.dummy4j.dummies.shared.string.Padding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class serves as a provider of GS1-128 codes for IdentifierDummy
 *
 * @see <a href="https://en.wikipedia.org/wiki/GS1-128">GS1-128</a>
 * @since SNAPSHOT
 */
public class Gs1Dash128Factory {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");
    private static final String FNC_1 = "<FNC1>";

    final List<Supplier<String>> suppliers;

    private final Dummy4j dummy4j;

    public Gs1Dash128Factory(Dummy4j dummy4j, Gtin14Builder gtin14Builder, SsccFactory ssccFactory) {
        this.dummy4j = dummy4j;
        suppliers = getSuppliers(dummy4j, gtin14Builder, ssccFactory);
    }

    private List<Supplier<String>> getSuppliers(Dummy4j dummy4j, Gtin14Builder gtin14Builder, SsccFactory ssccFactory) {
        return Arrays.asList(
                () -> gtin14Builder.withApplicationIdentifier().build(),
                ssccFactory::createCode,
                () -> {
                    String past = formattedDate(d -> d.past(12, ChronoUnit.MONTHS));
                    return "(11)" + past;
                },
                () -> {
                    String past = formattedDate(d -> d.past(10, ChronoUnit.MONTHS));
                    return "(13)" + past;
                },
                () -> {
                    String future = formattedDate(d -> d.future(36, ChronoUnit.MONTHS));
                    return "(12)" + future;
                },
                () -> {
                    String future = formattedDate(d -> d.future(24, ChronoUnit.MONTHS));
                    return "(15)" + future;
                },
                () -> {
                    String future = formattedDate(d -> d.future(12, ChronoUnit.MONTHS));
                    return "(17)" + future;
                },
                () -> {
                    String value = String.valueOf(dummy4j.number().nextInt(1, 99));
                    return "(20)" + Padding.leftPad(value, 2, '0');
                },
                () -> {
                    int value = dummy4j.number().nextInt(1, 99_999_999);
                    return "(30)" + value + FNC_1;
                },
                () -> {
                    int value = dummy4j.number().nextInt(1, 999_999);
                    return "(8005)" + value + FNC_1;
                }
        );
    }

    private String formattedDate(Function<DateAndTimeDummy, LocalDateTime> dateFunc) {
        return formatDate(dateFunc.apply(dummy4j.dateAndTime()).toLocalDate());
    }

    private String formatDate(LocalDate date) {
        return DATE_FORMATTER.format(date);
    }

    /**
     * Generates a random GS1-128 code
     */
    public String createCode() {
        int howManyParts = dummy4j.number().nextInt(2, 3);
        List<Supplier<String>> selected = dummy4j.unique().of(() -> dummy4j.of(suppliers),
                supplier -> dummy4j.listOf(howManyParts, supplier));

        return selected.stream().map(Supplier::get).collect(Collectors.joining());
    }
}
