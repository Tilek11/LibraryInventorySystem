package A2;

import java.util.Vector;

/**
 * Library is a collection of all the books
 * @author Aeyon Ahmad
 */
public class Library implements MaxTagValue
{

    String libraryName;
    Vector<Book> books;

    /**
     * default ctr
     * @param libraryName
     */
    public Library(String libraryName)
    {
        this.libraryName = libraryName;
        books = new Vector<Book>();
    }

    /**
     * Find maximum tag value in a library
     */
    public int findMaximumValueTag()
    {
        int maxElement = -100;
        
        for (Book b : books)
            if (b.valueTag > maxElement) maxElement = b.valueTag;

        return maxElement;
    }

    /**
     * finds out if its possible to rent a book for the given period of time
     * @param wanted (the book)
     * @param requestDate
     * @param dueDate
     * @return whether the book can be rented or not
     */
    public boolean rentRequest(Book wanted, String requestDate, String dueDate)
    {
        try
        {
            if (Helper.timeDifference(requestDate, dueDate) <= 0) throw new RentPeriodException();
        }
        catch (DateFormatException dfe)
        {
            System.out.println(dfe.getMessage());
            return false;
        }
        catch(RentPeriodException rpe)
        {
            System.out.println(rpe.getMessage());
            return false;
        }
        
        for (Book b : books)
        {
            try
            {
                if (Helper.timeDifference(b.availableDate(this), requestDate) <= 0) throw new RentPeriodException();
            }
            catch (DateFormatException dfe)
            {
                System.out.println(dfe.getMessage());
                return false;
            }
            catch(RentPeriodException rpe)
            {
                System.out.println(rpe.getMessage());
                return false;
            }
            if (b.equals(wanted) && !b.isRented(this)) return true;
        }
        
        return false;
    }
    
    /**
     * shows if a library has the book without caring for availability for renting
     * @param book
     * @return
     */
    public boolean hasBook(Book book)
    {
        for (Book b : books)
            if (book.equals(b)) return true;
        return false;
    }

    /**
     * override hashcode
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result +
                ((books == null) ? 0 : books.hashCode());
        result = prime * result +
                ((libraryName == null) ? 0 : libraryName.hashCode());
        return result;
    }

    /**
     * override equals
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Library other = (Library) obj;
        if (books == null)
        {
            if (other.books != null) return false;
        }
        else if (!books.equals(other.books)) return false;
        if (libraryName == null)
        {
            if (other.libraryName != null) return false;
        }
        else if (!libraryName.equals(other.libraryName)) return false;
        return true;
    }
    
    /**
     * override tostring
     */
    @Override
    public String toString()
    {
        return " => " + libraryName + ") ";
    }
}
