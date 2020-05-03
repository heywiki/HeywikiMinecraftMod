package org.wikiwizard.papermc;

public class MiscUtil {

    /**
     * Useful to mask password output:
     * 
     * MiscUtil.stars(password.length())
     * 
     * @param chars
     * @param times
     * @return
     */
    public static final String stars(int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            s.append("*");
        }
        return s.toString();
    }
}