/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ckopp
 */
public class BookStore {

    private Connection connection;

    public BookStore() {

        String barrier = "| ";
        try {
//            DefaultTableModel = model;
            String path = "jdbc:sqlite:.\\db\\Books.db";
            this.connection = DriverManager.getConnection(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

    }

    int newTitle() {
        String ISBN = input("Please input the ISBN of the new book (9 chars)");

//        ISBN validity check; length
        if (ISBN.length() != 9 || ISBN.equals("")) {
            popup("Invalid ISBN length");
            return 1;
        }

//      ISBN validity check; exists
        String exists = "Select * from Title where ISBN = ?";
        try ( PreparedStatement prepared = prepSQL(exists)) {
            if (prepared != null) {
                prepared.setString(1, ISBN);
                ResultSet result = prepared.executeQuery();
                // if result.next() == false, then the result returned empty
                //  this means a tuple with that value does not exist
                if (result.next() == true) {
                    popup("This ISBN has already been registered");
                    return 1;
                }
            }
        } catch (SQLException ex) {

        }

        String Title = input("Please input the title of the book");
        String EditionNumber = input("Please input the edition number");
        String Copyright = input("Please input the copyright");

        String insertTitle = "INSERT INTO Title(ISBN,Title,EditionNumber,Copyright) VALUES(?,?,?,?)";

        try ( PreparedStatement preparedTitle = prepSQL(insertTitle)) {
            preparedTitle.setString(1, ISBN);
            preparedTitle.setString(2, Title);
            preparedTitle.setString(3, EditionNumber);
            preparedTitle.setString(4, Copyright);

            preparedTitle.execute();

            popup(
                    String.format("Title with ISBN: %s successfully inserted", ISBN)
            );
        } catch (SQLException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.printSelect("Select * from Author");

        String AuthorID = input("Please input the main author's AuthorID (check console)");

        String insertLink = "INSERT INTO AuthorISBN(AuthorID,ISBN) VALUES(?,?)";

        try ( PreparedStatement preparedLink = prepSQL(insertLink)) {
            preparedLink.setString(1, AuthorID);
            preparedLink.setString(2, ISBN);

            preparedLink.execute();

            popup(
                    String.format("Author with ID: %s is now credited for Title with ISBN: %s", AuthorID, ISBN)
            );
        } catch (SQLException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        //To change body of generated methods, choose Tools | Templates.
    }

    void newAuthor() {
        String AuthorID = input("What is the ID of the new author?");
        // check if ID is used

        String firstName = input("What is the author's first name?");
        String lastName = input("What is the author's last name?");

        String insert = "INSERT INTO AUTHOR(AuthorID,FirstName,LastName) VALUES(?,?,?)";
        try ( PreparedStatement prepared = this.prepSQL(insert)) {
            prepared.setString(1, AuthorID);
            prepared.setString(2, firstName);
            prepared.setString(3, lastName);
            prepared.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        popup("Successfully added new Author");
        //To change body of generated methods, choose Tools | Templates.
    }

    String input(String message) {
        return JOptionPane.showInputDialog(message);
    }

    String inputDefault(String message, String initial) {
        return JOptionPane.showInputDialog(message, initial);
    }

    void popup(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    void editAuthor() {
        String id = input("Which Author are you editing? (Please enter AuthorID)");
        // make sure author ID is valid
        // if it is valid, continue with modifications
        String select = "Select * from Author where AuthorID = ?";
        String fname = null;
        String lname = null;

        try ( PreparedStatement prepared = this.prepSQL(select)) {
            prepared.setString(1, id);
            ResultSet result = prepared.executeQuery();
            fname = result.getString(2);
            lname = result.getString(3);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        //To change body of generated methods, choose Tools | Templates.
        if (fname != null) {
            fname = inputDefault("First Name (If no changes, leave as is and click OK)", fname);
//            System.out.println("FirstName: " + fname);
        }

        if (lname != null) {
            lname = inputDefault("Last Name (If no changes, leave as is and click OK)", lname);
        }

        String update = "UPDATE Author SET FirstName = ?, LastName = ? WHERE AuthorID = ?";
        boolean beFalse = true;
        try ( PreparedStatement prepared = this.prepSQL(update)) {
            prepared.setString(1, fname);
            prepared.setString(2, lname);
            prepared.setString(3, id);
            beFalse = prepared.execute();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (beFalse == false) {
            popup("Author has been updated");
        } else {
            // this should never happen
            popup("Error occured");
        }
    }

    PreparedStatement prepSQL(String sql) {
        PreparedStatement prepared = null;
        try {
            prepared = this.connection.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        return prepared;
    }

    void linkBookAuthor() {
        printSelect("Select ISBN, Title from Title");
        String ISBN = input("Which ISBN is being linked? (ISBN list in command line)");

        printSelect("Select AuthorID, LastName, FirstName from Author");
        String AuthorID = input("Which AuthorID is being linked? (AuthorID list in command line)");

        String exists = "Select * from AuthorISBN where ISBN = ? AND authorID = ?";
        try ( PreparedStatement prepared = prepSQL(exists)) {
            if (prepared != null) {
                prepared.setString(1, ISBN);
                prepared.setString(2, AuthorID);
                ResultSet result = prepared.executeQuery();
                // if result.next() == false, then the result returned empty
                if (result.next() == false) {
                    //proceed with the creation of the new tuple
                    String insert = "INSERT INTO AuthorISBN(AuthorID,ISBN) VALUES(?,?)";

                    try ( PreparedStatement prepared2 = prepSQL(insert)) {
                        prepared2.setString(1, AuthorID);
                        prepared2.setString(2, ISBN);

                        prepared2.execute();

                        popup(
                                String.format("Author with ID: %s is now credited for Title with ISBN: %s", AuthorID, ISBN)
                        );
                    } catch (SQLException ex) {
                        Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    popup("This Author is already credited for that Title");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        //To change body of generated methods, choose Tools | Templates.

    }

    private void printSelect(String select) {
        String barrier = " | ";
        try {
            Statement s = this.connection.createStatement();
            ResultSet result = s.executeQuery(select);

            ResultSetMetaData metadata = result.getMetaData();
            int noOfCol = metadata.getColumnCount();

//            String tuple = result.;
            for (int i = 1; i <= noOfCol; i++) {
                if (i > 1) {
                    System.out.print(barrier);
                }
                System.out.printf("\t%s\t", metadata.getColumnName(i));
            }
            System.out.print("\n\n");
            while (result.next()) {
                for (int i = 1; i <= noOfCol; i++) {
                    if (i > 1) {
                        System.out.print(barrier);
                    }
                    String columnValue = result.getString(i);
                    System.out.printf("\t%s\t", columnValue);
                }

                System.out.println("");
            }

            System.out.println("\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //To change body of generated methods, choose Tools | Templates.
    }

}
