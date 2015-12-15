package com.researchersexchange.data;

import com.researchersexchange.business.User;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class UserDB {

    public static User getUser(String email) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user "
                + "WHERE email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setParticipants(rs.getInt("participants"));
                user.setParticipation(rs.getInt("participation"));
                user.setCoins(rs.getInt("coins"));
            }
            return user;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static String getSalt(String email) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user "
                + "WHERE email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            String salt = "";
            if (rs.next()) {

                salt = rs.getString("salt");

            }
            return salt;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static int addUser(User user, String password, String salt) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO user (name, email, password, participants, participation, coins,salt) "
                + "VALUES (?, ?, ?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, password);
            ps.setInt(4, user.getParticipants());
            ps.setInt(5, user.getParticipation());
            ps.setInt(6, user.getCoins());
            ps.setString(7, salt);
            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static User validateUser(String email, String password) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM user "
                + "WHERE email = ?  AND password = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User();
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setParticipants(rs.getInt("participants"));
                user.setParticipation(rs.getInt("participation"));
                user.setCoins(rs.getInt("coins"));
            }
            return user;

        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updateUser(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE user SET "
                + "participants = ?, participation = ?, coins = ?  WHERE email = ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setInt(1, user.getParticipants());
            ps.setInt(2, user.getParticipation());
            ps.setInt(3, user.getCoins());
            ps.setString(4, user.getEmail());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static int updatePassword(String email, String password, String salt) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE user SET "
                + "password = ?,salt=?  WHERE email = ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, password);
            ps.setString(2, salt);
            ps.setString(3, email);

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    /*public static int updateParticipations(User user){
     ConnectionPool pool = ConnectionPool.getInstance();
     Connection connection = pool.getConnection();
     PreparedStatement ps = null;

     String query = "UPDATE user SET "
     + "participations = ? ";
              
     try {
     ps = connection.prepareStatement(query);
     int updateParticipations = user.getParticipation()+ 1;
     ps.setInt(1, updateParticipations);
           
     return ps.executeUpdate();
     } catch (SQLException e) {
     System.out.println(e);
     return 0;
     } finally {
     DBUtil.closePreparedStatement(ps);
     pool.freeConnection(connection);
     }
     }
    
     public static int updateUserStudies(User user){
     ConnectionPool pool = ConnectionPool.getInstance();
     Connection connection = pool.getConnection();
     PreparedStatement ps = null;

     String query = "UPDATE user SET "
     + "participants = ? ";
              
     try {
     ps = connection.prepareStatement(query);
     int updateParticipations = user.getParticipants() + 1;
     ps.setInt(1, updateParticipations);
           
     return ps.executeUpdate();
     } catch (SQLException e) {
     System.out.println(e);
     return 0;
     } finally {
     DBUtil.closePreparedStatement(ps);
     pool.freeConnection(connection);
     }
     }*/
}
