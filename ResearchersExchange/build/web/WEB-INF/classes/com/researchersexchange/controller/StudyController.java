/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.researchersexchange.controller;

import com.researchersexchange.business.Answer;
import com.researchersexchange.business.Study;
import com.researchersexchange.business.User;
import com.researchersexchange.data.AnswerDB;
import com.researchersexchange.data.StudyDB;
import static com.researchersexchange.data.StudyDB.allAnswers;
import com.researchersexchange.data.UserDB;
import com.researchersexchange.mail.MailClass;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author Srivathsan
 */
@MultipartConfig
public class StudyController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        String imagesPath = getServletContext().getRealPath("/images");

        String url = "";
        if (action == null) {
            if (session.getAttribute("theUser") != null) {
                url = "/main.jsp";
            } else {
                url = "/home.jsp";

            }
        } else {

            User user = (User) session.getAttribute("theUser");
            switch (action) {

                case "studies":

                    if (session.getAttribute("theUser") != null) {
                        ArrayList<Study> myStudies = StudyDB.getStudiesFor(user.getEmail());

                        request.setAttribute("myStudies", myStudies);
                        url = "/studies.jsp";
                    } else {
                        url = "/home.jsp";
                    }
                    break;

                case "new":

                    if (session.getAttribute("theUser") != null) {
                        url = "/newstudy.jsp";
                    } else {
                        url = "/home.jsp";
                    }
                    break;

                case "recommend":

                    if (session.getAttribute("theUser") != null) {
                        url = "/recommend.jsp";
                    } else {
                        url = "/home.jsp";
                    }
                    break;

                case "contact":

                    if (session.getAttribute("theUser") != null) {
                        url = "/contact.jsp";
                    } else {
                        url = "/home.jsp";
                    }
                    break;

                case "confirmr":

                    if (session.getAttribute("theUser") != null) {

                        String name = request.getParameter("study_name");
                        String fromEmail = request.getParameter("email");
                        String toEmail = request.getParameter("friend_email");
                        String message = request.getParameter("message");
                        MailClass.sendMail(message, fromEmail, name, toEmail);
                        url = "/confirmr.jsp";
                    } else {

                        url = "/home.jsp";
                    }
                    break;

                case "confirmc":

                    if (session.getAttribute("theUser") != null) {
                        String name = request.getParameter("study_name");

                        String toEmail = request.getParameter("email");
                        String message = request.getParameter("message");
                        MailClass.sendMail(message, "", name, toEmail);
                        url = "/confirmc.jsp";
                    } else {
                        url = "/home.jsp";
                    }
                    break;

                case "participate":
                    if (user != null) {
                        String studyCode = request.getParameter("studyCode");
                        if (studyCode != null) {
                            Study study = StudyDB.getStudy(studyCode);
                            request.setAttribute("study", study);
                            url = "/question.jsp";
                        } else {
                            ArrayList<Study> openStudies = StudyDB.getOpenAndNotParticipated(user.getEmail());

                            request.setAttribute("openStudies", openStudies);
                            url = "/participate.jsp";
                        }

                    } else {
                        url = "/login.jsp";
                    }
                    break;

                case "edit":
                    if (user != null) {
                        String studyCode = request.getParameter("studyCode");

                        Study study = StudyDB.getStudy(studyCode);
                        log(study.getDescription());
                        request.setAttribute("study", study);
                        url = "/editstudy.jsp";

                    } else {
                        url = "/login.jsp";
                    }
                    break;

                case "update":

                    if (user != null) {

                        String code = request.getParameter("studyCode");

                        String study_name = request.getParameter("study_name");
                        String question_text = request.getParameter("question_text");
                        int participants = Integer.parseInt(request.getParameter("participants"));
                        String description = request.getParameter("description");
                        Part filePart = request.getPart("image_file");
                        String imagesFolder = "";
                        Study oldStudy = StudyDB.getStudy(code);
                        oldStudy.setName(study_name);
                        oldStudy.setQuestion(question_text);
                        oldStudy.setRequestedParticipants(participants);
                        oldStudy.setDescription(description);

                        if (filePart.getSize() > 0) {

                            String fileName = filePart.getSubmittedFileName();

                            InputStream fileContent = filePart.getInputStream();

                            try {
                                File file = new File(imagesPath, fileName);
                                //Files.delete(file.toPath());
                                Files.copy(fileContent, file.toPath());
                            } catch (FileAlreadyExistsException e) {
                                e.printStackTrace();
                            }
                            imagesFolder = "images" + "/" + fileName;
                            oldStudy.setImageURL(imagesFolder);
                        }

                        StudyDB.updateStudy(code, oldStudy);
                        ArrayList<Study> studies = StudyDB.getStudiesFor(user.getEmail());
                        request.setAttribute("myStudies", studies);
                        url = "/studies.jsp";

                    } else {
                        url = "/login.jsp";
                    }
                    break;
                case "add":
                    if (user != null) {

                        String study_name = request.getParameter("study_name");
                        String question_text = request.getParameter("question_text");
                        int participants = Integer.parseInt(request.getParameter("participants"));
                        String description = request.getParameter("description");
                        Part filePart = request.getPart("image_file");
                        String fileName = filePart.getSubmittedFileName();

                        InputStream fileContent = filePart.getInputStream();

                        try {
                            File file = new File(imagesPath, fileName);

                            Files.copy(fileContent, file.toPath());
                        } catch (FileAlreadyExistsException e) {
                            e.printStackTrace();
                        }

                        String imagesFolder = "images" + "/" + fileName;

                        ArrayList<Answer> answers = new ArrayList<Answer>();
                        Study study = new Study("", study_name, new Timestamp(new Date().getTime()),
                                user.getEmail(), question_text, description, "open", imagesFolder, participants, 0, answers);

                        StudyDB.addStudy(study);
                        ArrayList<Study> studies = StudyDB.getStudiesFor(user.getEmail());
                        request.setAttribute("myStudies", studies);
                        url = "/studies.jsp";

                    } else {
                        url = "/login.jsp";
                    }
                    break;
                case "start":
                    if (user != null) {
                        String studyCode = request.getParameter("studyCode");
                        String status = request.getParameter("status");

                        Study study = StudyDB.getStudy(studyCode);
                        study.setStatus(status);
                        StudyDB.updateStudy(studyCode, study);

                        ArrayList<Study> studies = StudyDB.getStudiesFor(user.getEmail());
                        request.setAttribute("myStudies", studies);
                        url = "/studies.jsp";

                    } else {
                        url = "/login.jsp";
                    }
                    break;
                case "stop":
                    if (user != null) {
                        String studyCode = request.getParameter("studyCode");
                        String status = request.getParameter("status");
                        Study study = StudyDB.getStudy(studyCode);
                        study.setStatus(status);
                        StudyDB.updateStudy(studyCode, study);

                        ArrayList<Study> studies = StudyDB.getStudiesFor(user.getEmail());
                        request.setAttribute("myStudies", studies);
                        url = "/studies.jsp";
                    } else {
                        url = "/login.jsp";
                    }
                    break;

                case "answer":
                    if (user != null) {
                        String studyCode = request.getParameter("studyCode");
                        int choice = Integer.parseInt(request.getParameter("choice"));
                        Answer answer = new Answer(user.getEmail(), choice, studyCode, new Timestamp(new Date().getTime()));
                        AnswerDB.addAnswer(answer);

                        user.setCoins(user.getCoins() + 1);
                        user.setParticipation(user.getParticipation() + 1);

                        User currentUser = UserDB.getUser(user.getEmail());
                        currentUser.setCoins(user.getCoins());
                        currentUser.setParticipation(user.getParticipation());

                        user.setParticipants(currentUser.getParticipants());

                        UserDB.updateUser(currentUser);
                        Study study = StudyDB.getStudy(studyCode);
                        User studyCreator = UserDB.getUser(study.getCreatorEmail());
                        studyCreator.setParticipants(studyCreator.getParticipants() + 1);

                        UserDB.updateUser(studyCreator);
                        study.setNumOfParticipants(study.getNumOfParticipants() + 1);
                        StudyDB.updateStudy(studyCode, study);

                        ArrayList<Study> openStudies = StudyDB.getOpenAndNotParticipated(user.getEmail());

                        request.setAttribute("openStudies", openStudies);
                        url = "/participate.jsp";
                    } else {
                        url = "/login.jsp";
                    }
                    break;

                default:
                    break;

            }

        }

        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

    }

}
