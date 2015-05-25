package com.samnoedel.reader.fs;

public class Path {
    private static final char PATH_SEPARATOR = '/';

    /**
     * Never returns trailing slashes. Does not remove redundant path separators.
     * @param parts A set of paths to be joined into a path
     * @return The concatenated path name
     */
    public static String join(String ...parts) {
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part);
            builder.append(PATH_SEPARATOR);
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
