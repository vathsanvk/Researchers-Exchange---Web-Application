/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.researchersexchange.controller;

import com.researchersexchange.business.User;
import com.researchersexchange.data.ForgotPasswordDB;
import com.researchersexchange.data.PasswordUtil;
import com.researchersexchange.data.TempUserDB;
import com.researchersexchange.data.UserDB;
import com.researchersexchange.mail.MailClass;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Srivathsan
 */
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/home.jsp";
        String message = "";

        String action = request.getParameter("action");

        InetAddress inetAddress = InetAddress.getLocalHost();
        String address = request.getRemoteAddr();
        //String address = request.getServerName();
        int port = request.getServerPort();

        Cookie cookie = new Cookie("hostPort", address + " " + port);
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        response.addCookie(cookie);

        String email;
        String password;
        String getTokenDetails = "";
        boolean validPassword;
        String hashAndSalt = "";
        String salt = "";
        long currentTime = new Date().getTime();
        Timestamp expirationTimestamp;
        long expirationTime = 0;
        HttpSession session = request.getSession();

        User user = null;
        if (session.getAttribute("theUser") != null) {
            user = (User) session.getAttribute("theUser");
        }

        if (action == null) {
            url = "/home.jsp";     //default action
        } else {

            switch (action) {

                case "login_page":
                    url = "/login.jsp";
                    break;

                case "login":
                    email = request.getParameter("email");
                    password = request.getParameter("password");
                    boolean userExists = false;

                    if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {

                        salt = UserDB.getSalt(email);

                        if (salt != null && !salt.isEmpty()) {
                            String pass = hashPassword(password, salt);
                            user = UserDB.validateUser(email, pass);
                        }

                        if (user != null) {
                            //user = UserDB.getUser(email);

                            session.setAttribute("theUser", user);

                            url = "/main.jsp";
                        } else {
                            message = "Invalid Credentials";
                            request.setAttribute("msg", message);
                            url = "/login.jsp";
                        }
                    } else {
                        message = "Please enter valid details";
                        request.setAttribute("msg", message);

                        url = "/login.jsp";
                    }
                    break;

                case "create":
                    String name = request.getParameter("name");
                    email = request.getParameter("email");
                    password = request.getParameter("password");
                    String confirmPassword = request.getParameter("confirm_password");

                    validPassword = false;

                    if (name != null && email != null && password != null && confirmPassword != null) {
                        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()
                                && !confirmPassword.isEmpty()) {

                            if (password.equals(confirmPassword)) {
                                try {
                                    PasswordUtil.checkPasswordStrength(password);
                                    message = "";
                                    validPassword = true;
                                    salt = PasswordUtil.getSalt();
                                    hashAndSalt = hashPassword(password, salt);

                                } catch (Exception e) {
                                    message = e.getMessage();
                                    request.setAttribute("msg", message);
                                    url = "/signup.jsp";
                                }
                                //user = new User(name, email, 0, 0, 0);
                                //int count = UserDB.addUser(user, password);
                                if (validPassword) {
                                    user = UserDB.getUser(email);
                                    if (user == null) {
                                        //session.setAttribute("theUser", user);
                                        //url = "/main.jsp";
                                        UUID uuid = UUID.randomUUID();

                                        TempUserDB.addTempUser(name, email, hashAndSalt, new Timestamp(new Date().getTime()),
                                                uuid.toString(), salt);
                                        String emailMessage = "Please click the below link to activate your account" + "\n"
                                                + "http://localhost:8080/ResearchersExchange/userController?action=activate&token=" + uuid.toString();
                                        MailClass.sendMail(emailMessage, "Researchers Exchange", "Activate Account", email);
                                        message = "Account created. Please activate your account by clicking on your email link";
                                        request.setAttribute("msg", message);
                                        url = "/login.jsp";

                                    } else {
                                        message = "Email already exists";
                                        request.setAttribute("msg", message);
                                        url = "/signup.jsp";
                                    }
                                }
                            } else {
                                message = "Passwords do not match";
                                request.setAttribute("msg", message);
                                url = "/signup.jsp";
                            }

                        } else {
                            message = "Please complete all the details";
                            request.setAttribute("msg", message);
                            url = "/signup.jsp";
                        }
                    }
                    break;

                case "activate":
                    String token = request.getParameter("token");
                    String tempUser = "";
                    if (token != null && !token.isEmpty()) {
                        tempUser = TempUserDB.getUser(token);
                    }

                    if (tempUser != null && !tempUser.isEmpty()) {
                        user = new User(tempUser.split("::")[0], tempUser.split("::")[1], 0, 0, 0);
                        UserDB.addUser(user, tempUser.split("::")[2], tempUser.split("::")[3]);
                        TempUserDB.deleteUser(token);
                        session.setAttribute("theUser", user);
                        url = "/main.jsp";
                    } else {
                        message = "Invalid activation, please sign-up again";
                        request.setAttribute("msg", message);
                        url = "/signup.jsp";
                    }

                    break;

                case "how":

                    if (user != null) {
                        url = "/main.jsp";
                    } else {
                        url = "/how.jsp";
                    }

                    break;

                case "about":

                    if (user != null) {
                        url = "/aboutl.jsp";
                    } else {
                        url = "/about.jsp";
                    }
                    break;

                case "home":

                    if (user != null) {
                        url = "/main.jsp";
                    } else {
                        url = "/home.jsp";
                    }
                    break;

                case "main":

                    if (user != null) {
                        url = "/main.jsp";
                    } else {
                        url = "/login.jsp";
                    }
                    break;

                case "signup":

                    url = "/signup.jsp";

                    break;

                case "forgotp":
                    url = "/forgotpassword.jsp";
                    break;

                case "forgotpassword":

                    email = request.getParameter("email");
                    if (email != null && !email.isEmpty()) {
                        UUID uuid = UUID.randomUUID();
                        ForgotPasswordDB.addTempUser(email, uuid.toString(),
                                new Timestamp(new Date().getTime() + (60000 * 5)));
                        String emailMessage = "Please click the below link to activate your account" + "\n"
                                + "http://localhost:8080/ResearchersExchange/userController?action=changep&token=" + uuid.toString();
                        MailClass.sendMail(emailMessage, "Researchers Exchange", "Change Password", email);
                        message = "Email Sent. Please click your email link to change password";
                        request.setAttribute("msg", message);
                        url = "/login.jsp";
                    } else {
                        message = "Please enter a password";
                        request.setAttribute("msg", message);
                        url = "/forgotpassword.jsp";
                    }

                    break;

                case "changep":
                    token = request.getParameter("token");
                    getTokenDetails = ForgotPasswordDB.getUser(token);

                    if (getTokenDetails != null && !getTokenDetails.isEmpty()) {
                        expirationTimestamp = Timestamp.valueOf(getTokenDetails.split("::")[2]);
                        expirationTime = expirationTimestamp.getTime();
                    }

                    log(currentTime + "");
                    log(expirationTime + "");

                    if (expirationTime > currentTime && getTokenDetails != null && !getTokenDetails.isEmpty()) {

                        request.setAttribute("email", getTokenDetails.split("::")[0]);
                        request.setAttribute("token", getTokenDetails.split("::")[1]);
                        url = "/changepassword.jsp";
                    } else {

                        message = "Token expired";
                        request.setAttribute("msg", message);

                        url = "/login.jsp";
                    }

                    break;

                case "changepassword":
                    validPassword = false;
                    email = request.getParameter("email");
                    token = request.getParameter("token");

                    password = request.getParameter("password");
                    confirmPassword = request.getParameter("confirm_password");
                    getTokenDetails = ForgotPasswordDB.getUser(token);

                    expirationTimestamp = Timestamp.valueOf(getTokenDetails.split("::")[2]);
                    expirationTime = expirationTimestamp.getTime();
                    try {
                        PasswordUtil.checkPasswordStrength(password);
                        message = "";
                        validPassword = true;
                        salt = PasswordUtil.getSalt();
                        hashAndSalt = hashPassword(password, salt);

                    } catch (Exception e) {
                        message = e.getMessage();
                        request.setAttribute("msg", message);
                        request.setAttribute("email", email);
                        request.setAttribute("token", token);
                        url = "/changepassword.jsp";
                    }

                    if (validPassword) {
                        if (email != null && !email.isEmpty() && !password.isEmpty()
                                && !confirmPassword.isEmpty()) {

                            if (password.equals(confirmPassword)) {

                                if (expirationTime > currentTime) {

                                    ForgotPasswordDB.deleteUser(token);
                                    int count = UserDB.updatePassword(email, hashAndSalt, salt);

                                    if (count > 0) {
                                        String emailMessage = "Your password has been changed";

                                        MailClass.sendMail(emailMessage, "Researchers Exchange", "Change Password", email);
                                        message = "Password Changed. Please login now";
                                        request.setAttribute("msg", message);
                                        url = "/login.jsp";
                                    } else {
                                        message = "Password change failed";
                                        request.setAttribute("msg", message);
                                        url = "/login.jsp";
                                    }
                                } else {
                                    message = "Token expired";
                                    request.setAttribute("msg", message);
                                    url = "/login.jsp";
                                }
                            } else {
                                message = "Passwords do not match";
                                request.setAttribute("email", email);
                                request.setAttribute("token", token);
                                request.setAttribute("msg", message);
                                url = "/changepassword.jsp";
                            }

                        } else {
                            message = "Please enter valid details";
                            request.setAttribute("email", email);
                            request.setAttribute("token", token);
                            request.setAttribute("msg", message);
                            url = "/changepassword.jsp";
                        }
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

    String hashPassword(String password, String salt
    ) {

        String saltedAndHashedPassword;

        try {

            saltedAndHashedPassword = PasswordUtil.hashAndSaltPassword(password, salt);

            return saltedAndHashedPassword;

        } catch (NoSuchAlgorithmException ex) {
            log(ex.getMessage());
        }
        return null;
    }

}
