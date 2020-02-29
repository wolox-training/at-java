package wolox.training.exceptions;

public class ErrorConstats {
    private ErrorConstats() {}
    public static final String BOOK_ALREADY_IN_COLLECTION_MESSAGE = "The book %s is already in the user's collection.";
    public static final String NOT_FOUND_MESSAGE = "The %s was not found";
    public static final String ID_MISSMATCH_MESSAGE = "The %s id does not match with the id requested";
    public static final String FIELD_CANNOT_BE_EMPTY = "The field %s cannot be empty.";
    public static final String MINIMUM_PAGE_AMOUNT_MESSAGE = "The book must have at least %s pages";
    public static final String INVALID_DATE = "The birthdate should be before than today";
}
