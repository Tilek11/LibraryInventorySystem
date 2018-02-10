package A2;

/**
 * DateFormatException used to throw an exception when the format
 * of the date is incorrect
 * 
 * @author: Aeyon Ahmad
 */
@SuppressWarnings("serial")
public class DateFormatException extends Exception
{
    public DateFormatException() {}

    public DateFormatException(String message)
    {
        super(message);
    }
}
