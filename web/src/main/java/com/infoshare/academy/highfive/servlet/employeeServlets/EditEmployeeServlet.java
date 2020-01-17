package com.infoshare.academy.highfive.servlet.employeeServlets;

import com.infoshare.academy.highfive.mapper.request.EmployeeRequestMapper;
import com.infoshare.academy.highfive.request.EmployeeRequest;
import com.infoshare.academy.highfive.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

@WebServlet("/manager/add-employee/")
public class EditEmployeeServlet extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Inject
    private EmployeeRequestMapper requestMapper;

    @EJB
    private EmployeeService employeeService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            EmployeeRequest employeeRequest = requestMapper.mapParamsToRequest(req);
            employeeService.editEmployee(employeeRequest);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
