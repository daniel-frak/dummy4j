package dev.codesoapbox.dummy4j.dummies;

import dev.codesoapbox.dummy4j.Dummy4j;

/**
 * @since 0.2.0
 */
public class ResearchPaperDummy {

    private final Dummy4j dummy4j;

    public ResearchPaperDummy(Dummy4j dummy4j) {
        this.dummy4j = dummy4j;
    }

    public String title() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title}");
    }

    public String titleSocial() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_natural}");
    }

    public String titleAnthropology() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_anthropology}");
    }

    public String titleHistory() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_history}");
    }

    public String titleNatural() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_natural}");
    }

    public String titlePhysics() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_physics}");
    }

    public String titleBiology() {
        return dummy4j.expressionResolver().resolve("#{research_paper.title_biology}");
    }
}
