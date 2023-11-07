package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {

    private static enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);

        private final int monthDays;

        private Month(final int monthDays) {
            this.monthDays = monthDays;
        }

        public int getDays() {
            return this.monthDays;
        }

        public static Month fromString(final String name) {
            try {
                return Month.valueOf(Objects.requireNonNull(name));
            } catch (IllegalArgumentException e) {
                Month found = null;
                for (final var month : Month.values()) {
                    if (month.name().toUpperCase().startsWith(name.toUpperCase())) {
                        if (found != null) {
                            throw new IllegalArgumentException("More than one month found, input ambiguous");
                        }
                        found = month;
                    }
                }
                if (found == null) {
                    throw new IllegalArgumentException("Month \"" + name + "\" not found");
                }
                return found;
            }
        }
    }

    private static class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(final String month1, final String month2) {
            return Integer.compare(Month.fromString(month1).ordinal(), Month.fromString(month2).ordinal());
        }

    }

    private static class SortByDays implements Comparator<String> {
        @Override
        public int compare(final String month1, final String month2) {
            return Integer.compare(Month.fromString(month1).getDays(), Month.fromString(month2).getDays());
        }
    }

    @Override
    public Comparator<String> sortByDays() {
        return new SortByDays();
    }

    @Override
    public Comparator<String> sortByOrder() {
        return new SortByMonthOrder();
    }
}
