package cs.umu.se.fikaspelen;

/**
 * A class for generating strings for timers in fika spelen
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class FikaTimerString {

    /**
     * Generates a string that converts time from milliseconds to minutes:seconds ex: 10:00
     * Zeros are padded ontop to get correct representation
     *
     * @param ms
     * @return String
     */
    public static String generate(long ms) {
        long minutes = ms/60000;
        long seconds = ms % 60000 / 1000;

        String s = Long.toString(minutes);
        s += ":";

        if(seconds < 10) {
            s += "0" + Long.toString(seconds);
        } else {
            s += Long.toString(seconds);
        }

        return s;
    }
}
