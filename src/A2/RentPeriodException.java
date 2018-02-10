package A2;

/**
 * Used if the rent period is incorrect
 * @author Aeyon Ahmad
 *
 */
@SuppressWarnings("serial")
public class RentPeriodException extends Exception
{
    public RentPeriodException() {}

    public RentPeriodException(String message)
    {
        super(message);
    }
}
