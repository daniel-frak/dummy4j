package dev.codesoapbox.dummy4j.dummies.identifier.internationalnumber.booknumber;

public final class IsbnMother {

    private IsbnMother() {
    }

    public static Isbn isbn() {
        return new Isbn(IsbnType.ISBN_13, "978", "7", "57371", "249",
                "-", "3");
    }
}
