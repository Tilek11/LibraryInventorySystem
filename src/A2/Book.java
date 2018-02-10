package A2;

/**
 * The Book class represent the books that make up our library.
 *  @author Aeyon Ahmad
 */
class Book
{

    String bookName; // the book name
    int valueTag; // an integer between -100 and 100
    Library library; // the library having this book in its inventory
    RentSettings rs; // rent settings

    public Book(String bookName, int valueTag)
    {
        this.bookName = bookName;
        this.valueTag = valueTag;
        this.library = null;
        rs = null;
    }
    
    public Book(String bookName, int valueTag, Library library)
    {
        this.bookName = bookName;
        this.valueTag = valueTag;
        this.library = library;
        rs = null;
    }

    /**
     * 
     * @param rentDate
     * @param dueDate
     * @param library
     * @return whether the book has been successfully rented or failed to be rented
     * @throws DateFormatException
     * @throws RentPeriodException
     */
    public boolean rentBook(String rentDate, String dueDate, Library library)
            throws DateFormatException, RentPeriodException
    {
        if (rs == null)
        {
            try
            {
                rs = new RentSettings(rentDate, dueDate, library);
                System.out.println(this.toString() +
                        library.toString() + "is now rented.");
                return true;
            }
            catch (RentPeriodException rpe)
            {
                System.out.println(rpe.getMessage());
                return false;
            }
            catch (DateFormatException dfe)
            {
                System.out.println(dfe.getMessage());
                return false;
            }
        }
        else Helper.printUnavailable(this, rentDate);
        
        System.out.println(this.toString() + library.toString() + "is already rented.");
        return false;
    }

    /**
     * 
     * @param library
     */
    public void returnBook(Library library)
    {
        rs = null;
        
        System.out.println(this.toString() + library + "has been returned.");
    }

    /**
     * 
     * @param library
     * @return the date the book will be available
     */
    public String availableDate(Library library)
    {
        if (rs != null)
            return "Book (" + bookName + ", " + valueTag + ") is availble at " +
                rs.dueDate + " from library: " + this.library.libraryName;
        return "";
    }

    /**
     * 
     * @return if a book is overdue or not
     */
    public boolean isBookOverdue()
    {
        try
        {
            if (Helper.timeDifference(rs.dueDate, Helper.getCurrentDate()) >= 0)
                return true;
        }
        catch (DateFormatException dfe)
        {
            System.out.println(dfe.getMessage());
        }

        return false;
    }

    /**
     * 
     * @param library
     * @return whether the book is rented or not
     */
    public boolean isRented(Library l)
    {
        if (l.equals(library))
            if (rs != null) return true;
        return false;
    }

    /**
     * 
     * @returns RentSettings object
     */
    public RentSettings getRs()
    {
        return rs;
    }

    /**
     * 
     * @param RentSettings
     */
    public void setRs(RentSettings rs)
    {
        if (rs != null)
            {
                this.rs.rentDate = rs.rentDate;
                this.rs.dueDate = rs.dueDate;
                this.rs.borrowed = rs.borrowed;
            }
    }
    
    /**
     * 
     * @return string with the details for RentSettings
     */
    public String printRs()
    {
        if (rs != null)
            return "RentSettings (" + rs.rentDate + ", " + rs.dueDate
                + ", " + this.library.libraryName + ", " + rs.borrowed + ")";
        return "";
    }
    
    /**
     * 
     * @return rentDate
     */
    public String getRentDate()
    {
        if (rs != null) return rs.rentDate;
        return "";
    }
    
    /**
     * 
     * @return dueDate
     */
    public String getDueDate()
    {
        if (rs != null) return rs.dueDate;
        return "";
    }

    /**
     * override the hashCode
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookName == null) ? 0 : bookName.hashCode());
        result = prime * result + valueTag;
        return result;
    }

    /**
     * Override the equals
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Book other = (Book) obj;
        if (bookName == null)
        {
            if (other.bookName != null) return false;
        }
        else if (!bookName.equals(other.bookName)) return false;
        if (valueTag != other.valueTag) return false;
        return true;
    }

    /**
     * Override the toString
     */
    @Override
    public String toString()
    {
        return bookName();
    }

    /**
     * 
     * @return book name and tag value
     */
    public String bookName()
    {
        return "(" + bookName + ", " + valueTag;
    }

    /**
     * RentSettings shows whether a book is rented currently or not.
     * Nested class that is used to show whether a book is rented or not
     */
    private class RentSettings
    {
        private String rentDate; // date when the item is requested
        private String dueDate; // date when the item must be returned
        private boolean borrowed = false; // true if the item is rented

        /**
         * Default ctr
         * @throws DateFormatException
         */
        private RentSettings() throws DateFormatException
        {
            rentDate = "";
            dueDate = "";
        }

        /**
         * Private ctr
         * @param rentDate
         * @param dueDate
         * @param library
         * @throws DateFormatException
         * @throws RentPeriodException
         */
        private RentSettings(String rentDate, String dueDate, Library library)
            throws DateFormatException, RentPeriodException
        {
            try
            {
                Helper.checkDate(rentDate);
                Helper.checkDate(dueDate);
            }
            catch (DateFormatException dfe)
            {
                throw new DateFormatException(dfe.getMessage());
            }
            
            this.rentDate = rentDate;
            this.dueDate = dueDate;
            this.borrowed = true;
        }
        
        
        /**
         * Overrides hashcode
         */
        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + (borrowed ? 1231 : 1237);
            result = prime * result + ((dueDate == null) ? 0 : dueDate.hashCode());
            result = prime * result + ((rentDate == null) ? 0 : rentDate.hashCode());
            return result;
        }

        /**
         * overrides equals
         */
        @Override
        public boolean equals(Object obj)
        {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            RentSettings other = (RentSettings) obj;
            if (!getOuterType().equals(other.getOuterType())) return false;
            if (borrowed != other.borrowed) return false;
            if (dueDate == null)
            {
                if (other.dueDate != null) return false;
            }
            else if (!dueDate.equals(other.dueDate)) return false;
            if (rentDate == null)
            {
                if (other.rentDate != null) return false;
            }
            else if (!rentDate.equals(other.rentDate)) return false;
            return true;
        }

        /**
         * overrides toString
         */
        @Override
        public String toString()
        {
            return "RentSettings (" + rentDate + ", " + dueDate + ", " + borrowed + ")";
        }

        /**
         * 
         * @return returns the details for the class this is nested in
         */
        private Book getOuterType()
        {
            return Book.this;
        }
    }
}
