package com.infoshare.academy.highfive.web.servlet;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.infoshare.academy.highfive.freemarker.TemplateProvider;
import com.infoshare.academy.highfive.service.EmployeeService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private EmployeeService employeeService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        Template template = templateProvider
                .getTemplate(getServletContext(), "login.ftlh");

        Map<String, Object> model = new HashMap<>();

        String role = (String) req.getAttribute("role");
        String user = (String) req.getAttribute("authorization");

        model.put("role", role);
        model.put("authorization", user);

        try {
            template.process(model, writer);
        } catch (TemplateException e) {
          LOGGER.warn("Issue with processing Freemarker template.{}", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())

                .setAudience(Collections.singletonList("326852231702-jbh7kmdv8q7kd4dj8c3rancldhc1das1.apps.googleusercontent.com"))

                .build();

        String idTokenString = req.getParameter("idToken");


        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(idTokenString);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        if (idToken != null) {

            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            LOGGER.info("Google User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            if(emailVerified){
                employeeService.findByEmail(email);
            }
            String name = (String) payload.get("name");




        } else {
            LOGGER.info("Invalid ID token.");
            String info = "Invalid ID token.";
            resp.sendRedirect("/404.html");
        }

        resp.setContentType("text/html;charset=UTF-8");

        resp.setStatus(200);

    }
}
