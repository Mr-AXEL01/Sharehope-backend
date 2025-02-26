package net.axel.sharehope.util;

import java.util.function.Consumer;

public class UpdateUtils {

    public static <T> void updateField(T newValue, T currentValue, Consumer<T> setter) {
        if (newValue != null && !newValue.equals(currentValue)) {
            setter.accept(newValue);
        }
    }
}
