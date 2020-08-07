package dev.codesoapbox.dummy4j.dummies.color;

import dev.codesoapbox.dummy4j.Dummy4j;
import dev.codesoapbox.dummy4j.ExpressionResolver;
import dev.codesoapbox.dummy4j.NumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;

import static dev.codesoapbox.dummy4j.dummies.color.NumberValidator.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColorDummyTest {

    private static final String expected = "expected";

    @Mock
    private Dummy4j dummy4j;

    @Mock
    private ExpressionResolver expressionResolver;

    @Mock
    private NumberService numberService;

    private ColorDummy colorDummy;

    @BeforeEach
    void setUp() {
        colorDummy = new ColorDummy(dummy4j);
    }

    @Test
    void shouldReturnPrimaryName() {
        mockExpressionResolver();
        when(expressionResolver.resolve("#{color.primary_name}"))
                .thenReturn(expected);
        assertEquals(expected, colorDummy.primaryName());
    }

    private void mockExpressionResolver() {
        when(dummy4j.expressionResolver())
                .thenReturn(expressionResolver);
    }

    @Test
    void shouldReturnBasicName() {
        mockExpressionResolver();
        when(expressionResolver.resolve("#{color.basic_name}"))
                .thenReturn(expected);
        assertEquals(expected, colorDummy.basicName());
    }

    @Test
    void shouldReturnAdditionalName() {
        mockExpressionResolver();
        when(expressionResolver.resolve("#{color.additional_name}"))
                .thenReturn(expected);
        assertEquals(expected, colorDummy.additionalName());
    }

    @Test
    void shouldReturnName() {
        mockExpressionResolver();
        when(expressionResolver.resolve("#{color.name}"))
                .thenReturn(expected);
        assertEquals(expected, colorDummy.name());
    }

    @Test
    void shouldReturnHex() {
        mockNumberService();
        when(numberService.nextInt(MAX_RGB))
                .thenReturn(11896066);
        assertEquals("#b58502", colorDummy.hex());
    }

    private void mockNumberService() {
        when(dummy4j.number())
                .thenReturn(numberService);
    }

    @Test
    void shouldReturnHexAlpha() {
        mockNumberService();
        when(numberService.nextLong(MAX_RGBA))
                .thenReturn(3890366421L);
        assertEquals("#e7e247d5", colorDummy.hexAlpha());
    }

    @Test
    void shouldReturnRGB() {
        mockNumberService();
        when(numberService.nextInt(MAX_RGB))
                .thenReturn(11896066);
        Color color = colorDummy.rgb();
        assertAll(
                () -> assertEquals(181, color.getRed()),
                () -> assertEquals(133, color.getGreen()),
                () -> assertEquals(2, color.getBlue()),
                () -> assertEquals(255, color.getAlpha())
        );
    }

    @Test
    void shouldReturnRGBA() {
        mockNumberService();
        when(numberService.nextLong(MAX_RGBA))
                .thenReturn(3890366421L);
        Color color = colorDummy.rgba();
        assertAll(
                () -> assertEquals(226, color.getRed()),
                () -> assertEquals(71, color.getGreen()),
                () -> assertEquals(213, color.getBlue()),
                () -> assertEquals(231, color.getAlpha()),
                () -> assertEquals(3, color.getTransparency())
        );
    }

    @Test
    void shouldReturnHSB() {
        mockNumberService();
        when(numberService.nextFloat(MAX_ANGLE))
                .thenReturn(150.16583F);
        when(numberService.nextFloat())
                .thenReturn(0.3374765F);
        HSB color = colorDummy.hsb();
        assertAll(
                () -> assertEquals(150.17F, color.getHue()),
                () -> assertEquals(0.34F, color.getSaturation()),
                () -> assertEquals(0.34F, color.getBrightness())
        );
    }

    @Test
    void shouldReturnHSBA() {
        mockNumberService();
        when(numberService.nextFloat(MAX_ANGLE))
                .thenReturn(150.16583F);
        when(numberService.nextFloat())
                .thenReturn(0.3374765F);
        HSBA color = colorDummy.hsba();
        assertAll(
                () -> assertEquals(150.17F, color.getHue()),
                () -> assertEquals(0.34F, color.getSaturation()),
                () -> assertEquals(0.34F, color.getBrightness()),
                () -> assertEquals(0.34F, color.getAlpha())
        );
    }

    @Test
    void shouldReturnHSL() {
        mockNumberService();
        when(numberService.nextFloat(MAX_ANGLE))
                .thenReturn(150.16583F);
        when(numberService.nextFloat())
                .thenReturn(0.3374765F);
        HSL color = colorDummy.hsl();
        assertAll(
                () -> assertEquals(150.17F, color.getHue()),
                () -> assertEquals(0.34F, color.getSaturation()),
                () -> assertEquals(0.34F, color.getLightness())
        );
    }

    @Test
    void shouldReturnHSLA() {
        mockNumberService();
        when(numberService.nextFloat(MAX_ANGLE))
                .thenReturn(150.16583F);
        when(numberService.nextFloat())
                .thenReturn(0.3374765F);
        HSLA color = colorDummy.hsla();
        assertAll(
                () -> assertEquals(150.17F, color.getHue()),
                () -> assertEquals(0.34F, color.getSaturation()),
                () -> assertEquals(0.34F, color.getLightness()),
                () -> assertEquals(0.34F, color.getAlpha())
        );
    }

    @Test
    void shouldReturnCMYK() {
        mockNumberService();
        when(numberService.nextFloat())
                .thenReturn(0.3374765F);
        CMYK color = colorDummy.cmyk();
        assertAll(
                () -> assertEquals(0.34F, color.getCyan()),
                () -> assertEquals(0.34F, color.getMagenta()),
                () -> assertEquals(0.34F, color.getYellow()),
                () -> assertEquals(0.34F, color.getBlack())
        );
    }
}
