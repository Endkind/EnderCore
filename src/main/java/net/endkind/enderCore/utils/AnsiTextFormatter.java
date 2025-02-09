package net.endkind.enderCore.utils;

import net.kyori.adventure.text.format.TextColor;

public final class AnsiTextFormatter {
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String DIM = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String BLINK = "\u001B[5m";
    public static final String RAPID_BLINK = "\u001B[6m";
    public static final String REVERSE = "\u001B[7m";
    public static final String HIDDEN = "\u001B[8m";

    private AnsiTextFormatter() {}

    // #region textColor
    public static String textColor(int r, int g, int b) {
        EnderColor.isRGB(r, g, b, true);

        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
    }

    public static String textColor(int[] rgb) {
        EnderColor.isRGB(rgb, true);

        return textColor(rgb[0], rgb[1], rgb[2]);
    }

    public static String textColor(String hex) {
        EnderColor.isHex(hex, true);

        int[] rgb = EnderColor.hexToRgb(hex);

        return textColor(rgb);
    }

    public static String textColor(TextColor color) {
        String hex = color.asHexString();

        return textColor(hex);
    }

    public static String textColor(EnderColor color) {
        String hex = color.getHex();

        return textColor(hex);
    }
    // #endregion

    // #region backgroundColor
    public static String backgroundColor(int r, int g, int b) {
        EnderColor.isRGB(r, g, b, true);

        return String.format("\u001B[48;2;%d;%d;%dm", r, g, b);
    }

    public static String backgroundColor(int[] rgb) {
        EnderColor.isRGB(rgb, true);

        return backgroundColor(rgb[0], rgb[1], rgb[2]);
    }

    public static String backgroundColor(String hex) {
        EnderColor.isHex(hex, true);

        int[] rgb = EnderColor.hexToRgb(hex);

        return backgroundColor(rgb);
    }

    public static String backgroundColor(TextColor color) {
        String hex = color.asHexString();

        return backgroundColor(hex);
    }

    public static String backgroundColor(EnderColor color) {
        String hex = color.getHex();

        return backgroundColor(hex);
    }
    // #endregion
}
