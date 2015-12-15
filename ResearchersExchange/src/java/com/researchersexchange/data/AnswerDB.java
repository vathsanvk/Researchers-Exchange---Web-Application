/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.researchersexchange.data;

import com.researchersexchange.business.Answer;
import com.researchersexchange.business.Study;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDB {

    public static int addAnswer(Answer ans) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO answer (email, choice, code, dateSubmitted) "
                + "VALUES (?, ?, ?,?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, ans.getEmailParticipant());
            ps.setInt(2, ans.getChoice());
            ps.setString(3, ans.getCode());
            ps.setTimestamp(4, ans.getDateSubmitted());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Answer> getAnswersForCode(String code) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM answer "
                + "WHERE code = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, code);
            rs = ps.executeQuery();
            Answer answer = null;
            ArrayList<Answer> answers = new ArrayList<Answer>();
            while (rs.next()) {
                answer = new Answer();
                answer.setEmailParticipant(rs.getString("email"));
                answer.setChoice(rs.getInt("choice"));
                answer.setCode(rs.getString("code"));
                answer.setDateSubmitted(rs.getTimestamp("dateSubmitted"));

                answers.add(answer);
            }
            return answers;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static ArrayList<Answer> getAnswersForEmail(String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM answer "
                + "WHERE email = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            Answer answer = null;
            ArrayList<Answer> answers = new ArrayList<Answer>();
            while (rs.next()) {
                answer = new Answer();
                answer.setEmailParticipant(rs.getString("email"));
                answer.setChoice(rs.getInt("choice"));
                answer.setCode(rs.getString("code"));
                answer.setDateSubmitted(rs.getTimestamp("dateSubmitted"));

                answers.add(answer);
            }
            return answers;
        } catch (SQLException e) {
            System.out.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

}
