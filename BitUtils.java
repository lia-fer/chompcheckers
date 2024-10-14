/**
 * A utility class for bitwise operations and binary arithmetic supporting multiple data types
 */
class BitUtils {
    /**
     * Sets a specific bit in the given value
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param value The input value
     * @param position The position to set (0-based, from right)
     * @return The value with the bit set
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T setBit(T value, int position) {
        if (value instanceof Byte) {
            return (T) Byte.valueOf((byte) (value.byteValue() | (1 << position)));
        } else if (value instanceof Short) {
            return (T) Short.valueOf((short) (value.shortValue() | (1 << position)));
        } else if (value instanceof Integer) {
            return (T) Integer.valueOf(value.intValue() | (1 << position));
        } else if (value instanceof Long) {
            return (T) Long.valueOf(value.longValue() | (1L << position));
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Clears a specific bit in the given value
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param value The input value
     * @param position The position to clear (0-based, from right)
     * @return The value with the bit cleared
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T clearBit(T value, int position) {
        if (value instanceof Byte) {
            return (T) Byte.valueOf((byte) (value.byteValue() & ~(1 << position)));
        } else if (value instanceof Short) {
            return (T) Short.valueOf((short) (value.shortValue() & ~(1 << position)));
        } else if (value instanceof Integer) {
            return (T) Integer.valueOf(value.intValue() & ~(1 << position));
        } else if (value instanceof Long) {
            return (T) Long.valueOf(value.longValue() & ~(1L << position));
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Toggles a specific bit in the given value
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param value The input value
     * @param position The position to toggle (0-based, from right)
     * @return The value with the bit toggled
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T toggleBit(T value, int position) {
        if (value instanceof Byte) {
            return (T) Byte.valueOf((byte) (value.byteValue() ^ (1 << position)));
        } else if (value instanceof Short) {
            return (T) Short.valueOf((short) (value.shortValue() ^ (1 << position)));
        } else if (value instanceof Integer) {
            return (T) Integer.valueOf(value.intValue() ^ (1 << position));
        } else if (value instanceof Long) {
            return (T) Long.valueOf(value.longValue() ^ (1L << position));
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Gets the value of a specific bit
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param value The input value
     * @param position The position to get (0-based, from right)
     * @return 1 if the bit is set, 0 otherwise
     */
    public static <T extends Number> int getBit(T value, int position) {
        if (value instanceof Byte) {
            return (value.byteValue() >> position) & 1;
        } else if (value instanceof Short) {
            return (value.shortValue() >> position) & 1;
        } else if (value instanceof Integer) {
            return (value.intValue() >> position) & 1;
        } else if (value instanceof Long) {
            return (int)((value.longValue() >> position) & 1);
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Performs binary addition
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param a First operand
     * @param b Second operand
     * @return Sum of a and b
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T add(T a, T b) {
        if (a instanceof Byte) {
            return (T) Byte.valueOf((byte) (a.byteValue() + b.byteValue()));
        } else if (a instanceof Short) {
            return (T) Short.valueOf((short) (a.shortValue() + b.shortValue()));
        } else if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() + b.intValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() + b.longValue());
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Performs binary subtraction
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param a First operand
     * @param b Second operand
     * @return Difference of a and b
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T subtract(T a, T b) {
        if (a instanceof Byte) {
            return (T) Byte.valueOf((byte) (a.byteValue() - b.byteValue()));
        } else if (a instanceof Short) {
            return (T) Short.valueOf((short) (a.shortValue() - b.shortValue()));
        } else if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() - b.intValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() - b.longValue());
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Performs binary multiplication
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param a First operand
     * @param b Second operand
     * @return Product of a and b
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T multiply(T a, T b) {
        if (a instanceof Byte) {
            return (T) Byte.valueOf((byte) (a.byteValue() * b.byteValue()));
        } else if (a instanceof Short) {
            return (T) Short.valueOf((short) (a.shortValue() * b.shortValue()));
        } else if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() * b.intValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() * b.longValue());
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Performs binary division
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param a First operand
     * @param b Second operand
     * @return Quotient of a divided by b
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T divide(T a, T b) {
        if (b.doubleValue() == 0) {
            throw new ArithmeticException("Division by zero");
        }
        if (a instanceof Byte) {
            return (T) Byte.valueOf((byte) (a.byteValue() / b.byteValue()));
        } else if (a instanceof Short) {
            return (T) Short.valueOf((short) (a.shortValue() / b.shortValue()));
        } else if (a instanceof Integer) {
            return (T) Integer.valueOf(a.intValue() / b.intValue());
        } else if (a instanceof Long) {
            return (T) Long.valueOf(a.longValue() / b.longValue());
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Converts a number to binary string
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param value The value to convert
     * @return Binary string representation
     */
    public static <T extends Number> String toBinaryString(T value) {
    if (value instanceof Byte || value instanceof Short || value instanceof Integer) {
        // For Byte, Short, and Integer, use 32 bits
        return String.format("%32s", Integer.toBinaryString(value.intValue())).replace(' ', '0');
    } else if (value instanceof Long) {
        // For Long, use 64 bits
        return String.format("%64s", Long.toBinaryString(value.longValue())).replace(' ', '0');
    }
    throw new IllegalArgumentException("Unsupported number type: " + value.getClass().getName());
}

    /**
     * Converts a number to hexadecimal string
     * @param <T> The numeric type (Byte, Short, Integer, or Long)
     * @param value The value to convert
     * @return Hexadecimal string representation
     */
    public static <T extends Number> String toHexString(T value) {
        if (value instanceof Byte || value instanceof Short) {
            return Integer.toHexString(value.intValue());
        } else if (value instanceof Integer) {
            return Integer.toHexString(value.intValue());
        } else if (value instanceof Long) {
            return Long.toHexString(value.longValue());
        }
        throw new IllegalArgumentException("Unsupported number type");
    }

    /**
     * Counts the number of set bits in a long value
     * @param value The value to count bits in
     * @return The number of set bits
     */
    public static int countBits(long value) {
        int count = 0;
        while (value != 0) {
            value &= (value - 1);
            count++;
        }
        return count;
    }
}