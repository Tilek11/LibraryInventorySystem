package A2;

import java.util.Vector;

/**
 * Main class that tests the rest of our code
 * @author Aeyon Ahmad
 */

public class Main
{

    public static void main(String[] args)
    {

        /** TASK 1 - build libraries from files - at least two libraries */

        System.out.println("\n\n *" + " TASK 1 " + "*");
        Libraries ls = new Libraries(8);
        
        String n = "CustomLibrary";
        
        ls.addLibrary(ls.buildLibraryFromFile("York", "YorkLibrary.txt"));
        for (int i = 1; i < 7; i++)
            ls.addLibrary(ls.buildLibraryFromFile("Custom Library " + i, n + i + ".txt"));
        
        System.out.println(ls);

        /** TASK 2 - ask for a book that is not in any library inventory */

        System.out.println("\n\n *" + " TASK 2 " + "*");
        Book book = new Book("C++", 20);
        Library library = ls.isThereBookInLibraries(book);
        if (library == null) System.out.println(Helper.printNonexistent(book));

        /**
         * TASK 3 - ask for a book that is in a library inventory issue a rent
         * request and print the bookEssentials of Database Management issue the
         * same rent request and print the book return the book issue the rent
         * request with new dates and print the book
         */
        System.out.println("\n\n *" + " TASK 3 " + "*");
        
        library = ls.libraries[0];
        book = new Book("Database Management Systems", 53, library);
        String reqDate = "04/16/2017", reqDate2 = "05/01/2017";
        String dueDate = "04/30/2017", dueDate2 = "05/14/2017";
        
        try
        {
            book.rentBook(reqDate, dueDate, library);
        }
        catch (DateFormatException e)
        {
            e.getMessage();
        }
        catch (RentPeriodException e)
        {
            e.getMessage();
        }
        
        System.out.println(book.toString() + book.library.toString() + book.printRs() + "\n");
        
        try
        {
            book.rentBook(reqDate, dueDate, library);
        }
        catch (DateFormatException e)
        {
            e.getMessage();
        }
        catch (RentPeriodException e)
        {
            e.getMessage();
        }
        System.out.println(book.toString() + library.toString() + book.printRs() + "\n");
        
        book.returnBook(book.library);
        System.out.println(book.toString() + library.toString() + book.printRs() + "\n");
        
        try
        {
            book.rentBook(reqDate2, dueDate2, library);
        }
        catch (DateFormatException e)
        {
            e.getMessage();
        }
        catch (RentPeriodException e)
        {
            e.getMessage();
        }
        System.out.println(book.toString() + library.toString() + book.printRs());
        
        /**
         * TASK 4 - ask for the same book in all libraries
         * return all libraries that have the book
         * return the first library that has the book available
         * */
        System.out.println("\n\n *" + " TASK 4 " + "*");
        
        book = new Book("Database Management Systems", 53);
        Vector<Library> lib = ls.returnAllLibraries(book);
        
        if (lib == null) System.out.println(Helper.printNonexistent(book));
        else
            for (Library l : lib)
                System.out.println("Book (" + book.bookName + ", " + book.valueTag
                        + ") is available at " + l.libraryName);
        
        library = ls.rentBookAvailable(book, reqDate, dueDate);
        if (library == null) System.out.println(Helper.printNonexistent(book));
        else System.out.println("\nBook " + book + ") is available at " + library.libraryName + " on " + reqDate);

        /** TASK 5 - calculate maximum value tag for each library */
        System.out.println("\n\n *" + " TASK 5 " + "*");
        
        for (Library l : ls.getLibraries()) System.out.println(l.findMaximumValueTag());

        /**
         * TASK 6 - inquire about a book - it is available? when does it become
         * available, etc.
         */
        System.out.println("\n\n *" + " TASK 6 " + "*");
        
        library = ls.libraries[0];
        book = new Book("Database Management Systems", 53, library);
        
        /**
         * this code block shows a book being rented
         * and an attempt to rent the book again without returning
         */
        {
            try
            {
                book.rentBook("03/31/2017", "04/25/2017", library);
            }
            catch (DateFormatException e)
            {
                e.getMessage();
            }
            catch (RentPeriodException e)
            {
                e.getMessage();
            }
            System.out.println(book.toString() + library.toString() + book.printRs());
            System.out.println(book.availableDate(library));
            book.returnBook(library);
        }
        System.out.println();
        
        /**
         * the following code block rents a book
         * and inquires if the book is overdue
         */
        {
            try
            {
                book.rentBook("03/25/2017", "03/30/2017", library);
            }
            catch (DateFormatException e)
            {
                e.getMessage();
            }
            catch (RentPeriodException e)
            {
                e.getMessage();
            }
            System.out.println(book.toString() + library.toString() + book.printRs());
            if (book.isBookOverdue())
                System.out.println("Book " + book + library + "is overdue.");
            book.returnBook(library);
        }
        System.out.println();
        
        /**
         * the following shows all libraries that contain the specified book
         */
        lib = ls.returnAllLibraries(book);
        
        if (lib == null) System.out.println(Helper.printNonexistent(book));
        else
            for (Library l : lib)
                System.out.println("Book (" + book.bookName + ", " +
                        book.valueTag + ") is available at " + l.libraryName);
        
        /**
         * the following code block checks the availability of a book
         */
        System.out.println();
        {
            System.out.println(Helper.printAvailable(book, reqDate, library));
            System.out.println(book.toString() + library.toString() + book.printRs());
        }
        /**
         * TASK 7 - if book is borrowed from all libraries find one that has it
         * closest to the requested date
         */
        System.out.println("\n\n *" + " TASK 7 " + "*");

        book = new Book("Database Management Systems", 53);
        
        /**
         * this finds all libraries that have the specified book and rents
         * the book from all of them to help illustrate the closest availability
         * function of our libraries class
         */
        for (Library l : ls.getLibraries())
        {
            if (l.hasBook(book))
            {
                book = new Book("Database Management Systems", 53, l);
                try
                {
                    book.rentBook(reqDate, dueDate, l);
                    System.out.println(book.toString() + l.toString() + book.printRs() + "\n");
                }
                catch (DateFormatException dfe)
                {
                    System.out.println(dfe.getMessage());
                }
                catch (RentPeriodException rpe)
                {
                    System.out.println(rpe.getMessage());
                }
            }
        }
        
        
        //this will display the library that has the book available at the closest date
        library = ls.closestAvailability(book, reqDate2, dueDate2);
        System.out.println("\nBook" + book + library + "is available on " + book.getDueDate() + ".");
    }
}
