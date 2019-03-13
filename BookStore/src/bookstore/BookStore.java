/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstore;

import java.sql.*;

/**
 *
 * @author ckopp
 */
public class BookStore {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String barrier = "| ";
        try {
//            DefaultTableModel = model;
            String path = "jdbc:sqlite://Books.db";
            Connection c = DriverManager.getConnection(path);
            Statement s = c.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
