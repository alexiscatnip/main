package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Represents a single TimeSlot in TimeTable Class
 *
 */
public class TimeSlot {
    // TODO: Make constraint message more descriptive
    public static final String MESSAGE_GENERAL_CONSTRAINTS = "Does not fit constraints!";
    public static final String MESSAGE_NOT_ENOUGH_ARGUMENTS = "Accepted argument example: Monday 8-10";
    public static final String MESSAGE_CANNOT_PARSE_DAY = "Accepted day format: MONDAY";
    public static final String MESSAGE_CANNOT_PARSE_TIME = "Accepted time format: 8-10";
    public static final String MESSAGE_INVALID_TIME_SLOT = "Invalid TimeSlot";

    // TODO: Change to accept times with non 0 minutes
    // TODO: Allow whitespaces before and after dash
    public static final String VALIDATION_REGEX =
            "\\w+(\\s*)([0-1][0-9]|[2][0-3])[:][0][0][-]([0-1][0-9]|[2][0-3])[:][0][0]";

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private String label;

    public TimeSlot(DayOfWeek day, LocalTime start, LocalTime end) {
        requireNonNull(day);
        requireNonNull(start);
        requireNonNull(end);

        checkArgument(isValidTimeSlot(start, end), MESSAGE_INVALID_TIME_SLOT);

        dayOfWeek = day;
        startTime = start;
        endTime = end;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public String getLabel() {
        return label;
    }

    public static boolean isValidTimeSlot(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public static boolean isValidTimeSlot(LocalTime start, LocalTime end) {
        return !(start.equals(end) || start.isAfter(end));
    }

    /**
     * Checks whether this TimeSlot overlaps with toCompare
     *
     * @param toCompare TimeSlot to compare against
     * @return Whether this TimeSlot overlaps with toCompare
     */
    public boolean isOverlap(TimeSlot toCompare) {
        boolean isSameDay = this.getDayOfWeek() == toCompare.getDayOfWeek();
        boolean isNotOverlapTime = (this.getEndTime().isBefore(toCompare.getStartTime())
                                    || this.getEndTime().equals(toCompare.getStartTime())
                                    || toCompare.getEndTime().isBefore(this.getStartTime())
                                    || toCompare.getEndTime().equals(this.getStartTime()));

        return isSameDay && !isNotOverlapTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeSlot)) {
            return false;
        }

        TimeSlot otherTimeSlot = (TimeSlot) other;

        return otherTimeSlot.getDayOfWeek().equals(getDayOfWeek())
                && otherTimeSlot.getStartTime().equals(getStartTime())
                && otherTimeSlot.getEndTime().equals(getEndTime());
    }
}

