package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * Provides methods for generating values related to research papers
 *
 * @since 0.2.0
 */
public class ResearchPaperDummy {

    private final Dummy4j dummy4j;

    public ResearchPaperDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    /**
     * Generates a random research paper title.
     * E.g. {@code Scaling Effect on Statistical Behavior of Switching Parameters of Ferroelectric Capacitors}
     */
    public String title() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title}");
    }

    /**
     * Generates a random research paper title in the field of social sciences.
     * E.g. {@code An Analysis of the Relationship Between the Inter-War Period and Labour migration}
     */
    public String titleSocial() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_social}");
    }

    /**
     * Generates a random research paper title in the field of anthropology.
     * E.g. {@code The Role of Gift Giving Customs in Contemporary History}
     */
    public String titleAnthropology() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_anthropology}");
    }

    /**
     * Generates a random research paper title in the field of history.
     * E.g. {@code An Introduction to the History of Bhutan}
     */
    public String titleHistory() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_history}");
    }

    /**
     * Generates a random research paper title in the field of natural sciences.
     * E.g. {@code Mechanical Stress Effect on the Taurus Molecular Cloud}
     */
    public String titleNatural() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_natural}");
    }

    /**
     * Generates a random research paper title in the field of physics.
     * E.g. {@code Limits from the Funk Experiment on the Neutrino-Driven Convection in Core-Collapse Supernovae}
     */
    public String titlePhysics() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_physics}");
    }

    /**
     * Generates a random research paper title in the field of biology.
     * E.g. {@code Effects of Glutamine Deprivation on Oxidative Stress and Cell Survival in Breast Cell Lines}
     */
    public String titleBiology() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_biology}");
    }
}
