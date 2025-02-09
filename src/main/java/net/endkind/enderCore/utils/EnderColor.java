package net.endkind.enderCore.utils;

import java.util.regex.Pattern;

public class EnderColor {
    private static final Pattern HEX_PATTERN = Pattern.compile("#(?:[0-9a-fA-F]{3}|[0-9a-fA-F]{6})");
    private String hex;

    /**
     * Constructs an EnderColor with the specified color string.
     * The input is validated and normalized (e.g. "#FAB" is converted to "#FFAABB").
     *
     * @param hex the HEX color string (e.g., "#FFAABB" or "#FAB")
     * @throws IllegalArgumentException if the input is null, does not start with '#' or has an invalid length/format
     */
    public EnderColor(String hex) {
        isHex(hex, true);

        this.hex = shortHexToNormalHex(hex);
    }

    /**
     * Constructs an EnderColor from the specified RGB components.
     *
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @throws IllegalArgumentException if any component is out of the range 0-255
     */
    public EnderColor(int r, int g, int b) {
        isRGB(r, g, b, true);

        this.hex = rgbToHex(r, g, b);
    }

    /**
     * Constructs an EnderColor from the specified RGB array.
     *
     * @param rgb an array of exactly 3 integers representing the red, green, and blue components
     * @throws IllegalArgumentException if the array is null, does not have exactly 3 elements, or if any component is out of the range 0-255
     */
    public EnderColor(int[] rgb) {
        isRGB(rgb, true);

        this.hex = rgbToHex(rgb);
    }

    // #region GETTER

    /**
     * Returns the stored color string in normalized (6-digit) format.
     *
     * @return the normalized color string (e.g., "#FFAABB")
     */
    public String getHex() {
        return this.hex;
    }

    /**
     * Returns the RGB components of the stored color.
     * <p>
     * The method converts the internal HEX color to an array of integers representing
     * the red, green, and blue components.
     * </p>
     *
     * @return an array of integers representing the red, green, and blue components
     */
    public int[] getRGB() {
        return hexToRgb(this.hex);
    }
    // #endregion

    // #region SETTER
    /**
     * Sets the color to the specified value.
     * The input is validated and normalized.
     *
     * @param hex the HEX color string (e.g., "#FFAABB" or "#FAB")
     * @throws IllegalArgumentException if the input is null, does not start with '#' or has an invalid length/format
     */
    public void set(String hex) {
        isHex(hex, true);

        this.hex = shortHexToNormalHex(hex);
    }
    // #endregion

    // #region HEX
    /**
     * Checks if the given string is a valid color string in HEX format.
     * <p>
     * Valid formats are 3-digit or 6-digit HEX colors starting with a '#'.
     * </p>
     *
     * @param hex the HEX color string (e.g., "#FFAABB" or "#FAB") to check
     * @return {@code true} if the string is a valid HEX color, {@code false} otherwise
     */
    public static boolean isHex(String hex) {
        return isHex(hex, false);
    }

    /**
     * Checks if the given string is a valid color string in HEX format.
     * If {@code throwException} is {@code true} and the input is invalid, an exception is thrown.
     *
     * @param hex the HEX color string (e.g., "#FFAABB" or "#FAB") to check
     * @param throwException if {@code true}, an exception is thrown on invalid input
     * @return {@code true} if the string is a valid HEX color, {@code false} otherwise
     * @throws IllegalArgumentException if the input is null or invalid and {@code throwException} is {@code true}
     */
    public static boolean isHex(String hex, boolean throwException) {
        if (hex == null) {
            if (throwException) {
                throw new IllegalArgumentException("HEX-Color cannot be null");
            }

            return false;
        }

        hex = hex.trim();

        if (!HEX_PATTERN.matcher(hex).matches()) {
            if (throwException) {
                throw new IllegalArgumentException("Invalid HEX-Color format");
            }

            return false;
        }

        return true;
    }

    /**
     * Converts a short HEX color string (e.g., "#FAB") to its normal 6-digit form (e.g., "#FFAABB").
     * If the input is already in 6-digit format, it is returned unmodified.
     *
     * @param hex the HEX color string (e.g., "#FFAABB" or "#FAB") to normalize
     * @return the normalized color string in 6-digit format
     */
    public static String shortHexToNormalHex(String hex) {
        hex = hex.trim();

        if (hex.length() == 4) {
            StringBuilder sb = new StringBuilder("#");

            String r = hex.substring(1, 2);
            String g = hex.substring(2, 3);
            String b = hex.substring(3, 4);

            sb.append(r).append(r);
            sb.append(g).append(g);
            sb.append(b).append(b);

            hex = sb.toString();
        }

        return hex;
    }
    // #endregion

    // #region RGB
    /**
     * Checks if the given RGB components are valid.
     *
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @return {@code true} if all components are in the valid range, {@code false} otherwise
     */
    public static boolean isRGB(int r, int g, int b) {
        return isRGB(r, g, b, false);
    }

    /**
     * Checks if the given RGB components are valid.
     *
     * @param rgb an array of integers representing the red, green, and blue components
     * @return {@code true} if the array has exactly 3 elements and they are in the valid range, {@code false} otherwise
     */
    public static boolean isRGB(int[] rgb) {
        return isRGB(rgb, false);
    }

    /**
     * Checks if the given RGB components are valid.
     *
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @param throwException if {@code true}, an exception is thrown on invalid input
     * @return {@code true} if all components are in the valid range, {@code false} otherwise
     * @throws IllegalArgumentException if any component is out of the range 0-255 and {@code throwException} is {@code true}
     */
    public static boolean isRGB(int r, int g, int b, boolean throwException) {
        if (
                r < 0 || r > 255 ||
                g < 0 || g > 255 ||
                b < 0 || b > 255
        ) {
            if (throwException) {
                throw new IllegalArgumentException("Invalid RGB-Color");
            }

            return false;
        }

        return true;
    }

    /**
     * Checks if the given RGB array is valid.
     *
     * @param rgb an array of integers representing the red, green, and blue components
     * @param throwException if {@code true}, an exception is thrown on invalid input
     * @return {@code true} if the array is non-null, has exactly 3 elements, and all elements are in the valid range; {@code false} otherwise
     * @throws IllegalArgumentException if the array is null or does not have exactly 3 elements, or if any component is invalid
     */
    public static boolean isRGB(int[] rgb, boolean throwException) {
        if (rgb == null) {
            if (throwException) {
                throw new IllegalArgumentException("RGB-Color cannot be null");
            }

            return false;
        }

        if (rgb.length != 3) {
            if (throwException) {
                throw new IllegalArgumentException("RGB-Color must have exactly 3 elements");
            }

            return false;
        }

        return isRGB(rgb[0], rgb[1], rgb[2], throwException);
    }
    // #endregion

    /**
     * Converts a HEX color string to an RGB integer array.
     *
     * @param hex the HEX color string (e.g., "#FFAABB" or "#FAB")
     * @return an array of integers representing the red, green, and blue components
     * @throws IllegalArgumentException if the input is null, does not start with '#' or has an invalid length/format
     */
    public static int[] hexToRgb(String hex) {
        isHex(hex, true);

        hex = hex.trim();

        hex = shortHexToNormalHex(hex);

        hex = hex.substring(1);

        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);

        return new int[]{r, g, b};
    }

    /**
     * Converts the specified RGB components to a HEX color string.
     *
     * @param r the red component (0-255)
     * @param g the green component (0-255)
     * @param b the blue component (0-255)
     * @return the HEX color string corresponding to the specified RGB values (e.g., "#FFAABB")
     * @throws IllegalArgumentException if any component is out of the range 0-255
     */
    public static String rgbToHex(int r, int g, int b) {
        isRGB(r, g, b, true);

        return String.format("#%02X%02X%02X", r, g, b);
    }

    /**
     * Converts the specified RGB array to a HEX color string.
     *
     * @param rgb an array of exactly 3 integers representing the red, green, and blue components
     * @return the HEX color string corresponding to the specified RGB values (e.g., "#FFAABB")
     * @throws IllegalArgumentException if the array is null, does not have exactly 3 elements, or if any component is out of the range 0-255
     */
    public static String rgbToHex(int[] rgb) {
        isRGB(rgb, true);

        return rgbToHex(rgb[0], rgb[1], rgb[2]);
    }

    /**
     * Returns a string representation of this EnderColor.
     * <p>
     * The returned string includes both the normalized HEX color and its corresponding
     * RGB components.
     * </p>
     *
     * @return a string representation of this EnderColor
     */
    @Override
    public String toString() {
        int[] rgb = hexToRgb(hex);
        int r = rgb[0];
        int g = rgb[1];
        int b = rgb[2];

        StringBuilder sb = new StringBuilder("EnderColor{");

        sb.append("HEX: ");
        sb.append(hex);

        sb.append(", RGB: {");
        sb.append("r: ");
        sb.append(r);
        sb.append(", g: ");
        sb.append(g);
        sb.append(", b: ");
        sb.append(b);
        sb.append("}");

        sb.append("}");

        return sb.toString();
    }
}
