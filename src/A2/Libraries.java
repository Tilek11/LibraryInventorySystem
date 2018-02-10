package A2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Libraries is a collection of all the libraries
 * @author Aeyon Ahmad
 *
 */

public class Libraries
{

    public Library[] libraries; // a collection of libraries of type array
    public int numberOfLibraries; // number of libraries in collection
    static int newLibrary = 0; /*used to keep track of the number of libraries
                                to making adding a new library easier*/

    /**
     * default ctr
     * @param numberOfLibraries
     */
    public Libraries(int numberOfLibraries)
    {
        this.numberOfLibraries = numberOfLibraries;
        libraries = new Library[numberOfLibraries];
    }
    
    /**
     * adds library to the list
     * @param library
     */
    public void addLibrary(Library library)
    {
        libraries[newLibrary] = library;
        newLibrary++;
    }
    
    /**
     * builds the libraries from files
     * @param libraryName
     * @param fileName
     * @return library created
     */
    public Library buildLibraryFromFile(String libraryName, String fileName)
    {

        Library library = new Library(libraryName);

        String path = System.getProperty("user.dir");
        Book book = null;
        String s;

        try (BufferedReader br = new BufferedReader(new FileReader(path + "\\" + fileName)))
        {
            // if you run locally on your environment use: new FileReader(path +
            // "/src/" + fileName) ./Root/

            while ((s = br.readLine()) != null)
            {
                String[] tok = s.split(",");
                
                book = new Book(tok[0], Integer.parseInt(tok[1]), library);
                library.books.add(book);
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        return library;
    }

    /**
     * finds a book across all libraries while ignoring whether the
     * book is available for rent or not at the time
     * @param book
     * @return library that contained the book
     */
    public Library isThereBookInLibraries(Book book)
    {
        Library lib = null;
        
        for (int i = 0; i < numberOfLibraries; i++)
        {
            if (lib == null)
            {
                for (Book b : libraries[i].books)
                    if (book.equals(b))
                    {
                        lib = b.library;
                        break;
                    }
            }
        }
            
        return lib;
    }

    /**
     * finds out the first requested book that can be rented from any library
     * @param book
     * @param requestDate
     * @param dueDate
     * @return
     */
    public Library rentBookAvailable(Book book, String requestDate, String dueDate)
    {
        Library foundLibrary = null;
        
        for (int i = 0; i < numberOfLibraries; i++)
        {
            for (Book b : libraries[i].books)
                if (b.equals(book) && !b.isRented(libraries[i]))
                {
                    foundLibrary = libraries[i];
                    break;
                }
            if (foundLibrary != null) break;
        }
        
        return foundLibrary;
    }
    
    /**
     * 
     * @return returns all the libraries
     */
    public Vector<Library> getLibraries()
    {
        Vector<Library> lib = new Vector<Library>();
        for (Library l : libraries) lib.add(l);
        return lib;
    }

    /**
     * sets the libraries
     * @param libraries
     */
    public void setLibraries(Library[] libraries)
    {
        this.libraries = libraries;
    }

    /**
     * @param book
     * @param requestDate
     * @param dueDate
     * @return the library that has the closest dueDate
     */
    public Library closestAvailability(Book book, String requestDate, String dueDate)
    {
        Library foundLibrary = rentBookAvailable(book, requestDate, dueDate);
        long closest = 9999;
        
        
        if (foundLibrary == null)
        {
            for (int i = 0; i < numberOfLibraries; i++)
                if (foundLibrary == null)
                    for (Book b : libraries[i].books)
                        if (book.bookName.equals(b.bookName) && b.isRented(b.library))
                        {
                            try
                            {
                                if (Helper.timeDifference(requestDate, b.getDueDate()) < closest)
                                {
                                    closest = Helper.timeDifference(requestDate, b.getDueDate());
                                    foundLibrary = libraries[i];
                                }
                            }
                            catch (DateFormatException dfe)
                            {
                                System.out.println(dfe.getMessage());
                            }
                        }
        }
        
        return foundLibrary;
    }
    
    /**
     * @param book
     * @return all libraries that contain the book requested
     */
    public Vector<Library> returnAllLibraries(Book book)
    {
        Vector<Library> lib = new Vector<Library>();
        
        for (int i = 0; i < numberOfLibraries; i++)
        {
            for (Book b : libraries[i].books)
                if (book.equals(b))
                    lib.add(libraries[i]);
        }
            
        return lib;
    }
    
    /**
     * override hashcode
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(libraries);
        result = prime * result + numberOfLibraries;
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
        Libraries other = (Libraries) obj;
        if (!Arrays.equals(libraries, other.libraries)) return false;
        if (numberOfLibraries != other.numberOfLibraries) return false;
        return true;
    }
    
    /**
     * override the tostring
     */
    @Override
    public String toString()
    {
        String s = "";
        
        for (int i = 0; i < numberOfLibraries; i++)
        {
            s += "\n[Library = " + libraries[i].libraryName + "\n";
            for (Book b : libraries[i].books)
                s += b.toString() + libraries[i].toString() + "\n";
            s += "]";
        }
        
        return s;
    }
}
