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

    void newTitle() {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void newAuthor() {
        String AuthorID = input("What is the ID of the new author?");
        // check if ID is used

        String firstName = input("What is the author's first name?");
        String lastName = input("What is the author's last name?");

        String sql = "INSERT INTO AUTHOR(AuthorID,FirstName,LastName) VALUES(?,?,?)";
        try ( PreparedStatement prepared = this.connection.prepareStatement(sql)) {
            prepared.setString(1, AuthorID);
            prepared.setString(2, firstName);
            prepared.setString(3, lastName);
            prepared.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        //To change body of generated methods, choose Tools | Templates.
    }

    String input(String message) {
        return JOptionPane.showInputDialog(message);
    }

    void popup(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

}
