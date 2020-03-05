package wolox.training.utils;

import com.google.common.base.Preconditions;
import wolox.training.exceptions.ErrorConstats;

public class ValidateString {
    public static String exists(String value, String name) {
        Preconditions.checkArgument(value != null, ErrorConstats.FIELD_CANNOT_BE_EMPTY, name);
        Preconditions.checkArgument(!value.equals(""), ErrorConstats.FIELD_CANNOT_BE_EMPTY, name);
        return value;
    }
}
