package com.infoshare.academy.highfive;

import com.infoshare.academy.highfive.consolmenu.MainMenu;
import com.infoshare.academy.highfive.employeemanager.EmployeeManagementSingleton;
import com.infoshare.academy.highfive.holiday.HolidaysSingleton;
import com.infoshare.academy.highfive.tool.TerminalCleaner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {
    private static final Logger stdout = LoggerFactory.getLogger("CONSOLE_OUT");
    private static final String FILE_NAME = "Holidays.json";
    public static final String FILE_NAME_FOR_EMPLOYEE = "employee_fdb.json";

    private static void initRepositoryFromFiles() throws IOException {
        HolidaysSingleton.getInstance().initFromFile(FILE_NAME);
        EmployeeManagementSingleton.getInstance().initEmployeesDb(FILE_NAME_FOR_EMPLOYEE);
    }


    public static void main(String[] args) throws Exception {
        stdout.info("HIGH-FIVE TEAM \n");

        initRepositoryFromFiles();
        TerminalCleaner.cleanTerminal();
        MainMenu.runMenu();
    }
}
