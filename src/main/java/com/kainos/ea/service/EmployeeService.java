package com.kainos.ea.service;

import com.kainos.ea.dao.EmployeeDao;
import com.kainos.ea.exception.BankNumberLengthException;
import com.kainos.ea.exception.DoesNotExistException;
import com.kainos.ea.exception.NinLengthException;
import com.kainos.ea.exception.SalaryTooLowException;
import com.kainos.ea.model.Employee;
import com.kainos.ea.exception.DatabaseConnectionException;
import com.kainos.ea.model.EmployeeRequest;
import com.kainos.ea.util.DatabaseConnector;
import com.kainos.ea.validator.EmployeeValidator;

import java.sql.SQLException;
import java.util.List;

public class EmployeeService {
    private final EmployeeDao employeeDao;
    private final EmployeeValidator employeeValidator;
    private final DatabaseConnector databaseConnector;

    public EmployeeService(EmployeeDao employeeDao, EmployeeValidator employeeValidator, DatabaseConnector databaseConnector) {
        this.employeeDao = employeeDao;
        this.employeeValidator = employeeValidator;
        this.databaseConnector = databaseConnector;
    }

    public int insertEmployee(EmployeeRequest employee)
            throws DatabaseConnectionException, SQLException,
            BankNumberLengthException, SalaryTooLowException,
            NinLengthException {
        employeeValidator.isValidEmployee(employee);

        return employeeDao.insertEmployee(employee, databaseConnector.getConnection());
    }

    public Employee getEmployee(int employeeId)
            throws DatabaseConnectionException, SQLException,
            DoesNotExistException {
        Employee employee = employeeDao.getEmployee(employeeId, databaseConnector.getConnection());
        if (employee == null) {
            throw new DoesNotExistException();
        }
        return employee;
    }

    public List<Employee> getEmployees() throws DatabaseConnectionException, SQLException {
        return employeeDao.getEmployees(databaseConnector.getConnection());
    }
}
