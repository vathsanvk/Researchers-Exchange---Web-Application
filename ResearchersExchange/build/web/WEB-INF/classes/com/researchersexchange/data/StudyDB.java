package com.researchersexchange.data;

import com.researchersexchange.business.Answer;
import com.researchersexchange.business.Study;
import com.researchersexchange.business.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class StudyDB {

    public static ArrayList<Answer> allAnswers = new ArrayList<>();

    public static Study getStudy(String studyCode) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM study "
                + "WHERE code = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, studyCode);
            rs = ps.executeQuery();
            Study study = null;
            if (rs.next()) {
                study = new Study();
                study.setName(rs.getString("name"));
                study.setCode(rs.getString("code"));
                study.setDescription(rs.getString("description"));
                study.setCreatorEmail(rs.getString("creatorEmail"));
                study.setDateCreated(rs.getTimestamp("dateCreated"));
                study.setQuestion(rs.getString("question"));
                study.setRequestedParticipants(rs.getInt("requestedParticipants"));
                study.setNumOfParticipants(rs.getInt("numOfParticipants"));
                study.setStatus(rs.getString("status"));
                study.setImageURL(rs.getString("imageURL"));
            }
            return study;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Study> getStudies(String status) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM study "
                + "WHERE status = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, status);
            rs = ps.executeQuery();
            Study study = null;
            ArrayList<Study> studies = new ArrayList<Study>();
            while (rs.next()) {
                study = new Study();
                study.setName(rs.getString("name"));
                study.setCode(rs.getString("code"));
                study.setDescription(rs.getString("description"));
                study.setCreatorEmail(rs.getString("creatorEmail"));
                study.setDateCreated(rs.getTimestamp("dateCreated"));
                study.setQuestion(rs.getString("question"));
                study.setRequestedParticipants(rs.getInt("requestedParticipants"));
                study.setNumOfParticipants(rs.getInt("numOfParticipants"));
                study.setStatus(rs.getString("status"));
                study.setImageURL(rs.getString("imageURL"));
                studies.add(study);
            }
            return studies;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static ArrayList<Study> getStudiesFor(String emailAddress) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM study "
                + "WHERE creatorEmail = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, emailAddress);
            rs = ps.executeQuery();
            Study study = null;
            ArrayList<Study> studies = new ArrayList<Study>();
            while (rs.next()) {
                study = new Study();
                study.setName(rs.getString("name"));
                study.setCode(rs.getString("code"));
                study.setDescription(rs.getString("description"));
                study.setCreatorEmail(rs.getString("creatorEmail"));
                study.setDateCreated(rs.getTimestamp("dateCreated"));
                study.setQuestion(rs.getString("question"));
                study.setRequestedParticipants(rs.getInt("requestedParticipants"));
                study.setNumOfParticipants(rs.getInt("numOfParticipants"));
                study.setStatus(rs.getString("status"));
                study.setImageURL(rs.getString("imageURL"));
                studies.add(study);
            }
            return studies;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }
    
    public static ArrayList<Study> getOpenAndNotParticipated(String emailAddress) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM study WHERE NOT EXISTS(SELECT * FROM answer WHERE study.code = answer.code AND email = ?)"
                + "AND status = ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, emailAddress);
            ps.setString(2, "open");
            rs = ps.executeQuery();
            Study study = null;
            ArrayList<Study> studies = new ArrayList<Study>();
            while (rs.next()) {
                study = new Study();
                study.setName(rs.getString("name"));
                study.setCode(rs.getString("code"));
                study.setDescription(rs.getString("description"));
                study.setCreatorEmail(rs.getString("creatorEmail"));
                study.setDateCreated(rs.getTimestamp("dateCreated"));
                study.setQuestion(rs.getString("question"));
                study.setRequestedParticipants(rs.getInt("requestedParticipants"));
                study.setNumOfParticipants(rs.getInt("numOfParticipants"));
                study.setStatus(rs.getString("status"));
                study.setImageURL(rs.getString("imageURL"));
                studies.add(study);
            }
            return studies;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    

    public static int addStudy(Study study) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO study (name,description,creatorEmail, dateCreated, "
                + "question, imageURL, requestedParticipants, numOfParticipants, status) "
                + "VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, study.getName());
         
            ps.setString(2, study.getDescription());
            ps.setString(3, study.getCreatorEmail());
            ps.setTimestamp(4, study.getDateCreated());
            ps.setString(5, study.getQuestion());
            ps.setString(6, study.getImageURL());
            ps.setInt(7, study.getRequestedParticipants());
            ps.setInt(8, study.getNumOfParticipants());
            ps.setString(9, study.getStatus());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static int updateStudy(String code, Study study) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query = "UPDATE study SET "
                + "name = ?, description = ?, question = ? , imageURL = ?, requestedParticipants = ?,"
                + "numOfParticipants = ?, status = ?"
                + "WHERE code = ?";

        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, study.getName());
            ps.setString(2, study.getDescription());
            ps.setString(3, study.getQuestion());
            ps.setString(4, study.getImageURL());
            ps.setInt(5, study.getRequestedParticipants());
            ps.setInt(6, study.getNumOfParticipants());
            ps.setString(7, study.getStatus());
            ps.setString(8, code);

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

}
