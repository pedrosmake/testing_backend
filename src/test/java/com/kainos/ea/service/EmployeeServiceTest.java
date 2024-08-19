package com.kainos.ea.service;

import com.kainos.ea.dao.EmployeeDao;
import com.kainos.ea.exception.BankNumberLengthException;
import com.kainos.ea.exception.DatabaseConnectionException;
import com.kainos.ea.exception.DoesNotExistException;
import com.kainos.ea.exception.NinLengthException;
import com.kainos.ea.exception.SalaryTooLowException;
import com.kainos.ea.model.Employee;
import com.kainos.ea.model.EmployeeRequest;
import com.kainos.ea.util.DatabaseConnector;
import com.kainos.ea.validator.EmployeeValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
    EmployeeValidator employeeValidator = Mockito.mock(EmployeeValidator.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    Employee employee = Mockito.mock(Employee.class);
    List<Employee> employeeList = Arrays.asList(
            Mockito.mock(Employee.class),
            Mockito.mock(Employee.class),
            Mockito.mock(Employee.class)
    );

    EmployeeService employeeService = new EmployeeService(employeeDao, employeeValidator, databaseConnector);

    EmployeeRequest employeeRequest = new EmployeeRequest(
            30000,
            "Tim",
            "Bloggs",
            "tbloggs@email.com",
            "1 Main Street",
            "Main Road",
            "Belfast",
            "Antrim",
            "BT99BT",
            "Northern Ireland",
            "12345678901",
            "12345678",
            "AA1A11AA"
    );

    Connection conn;

    @Test
    void insertEmployee_shouldReturnId_whenDaoReturnsId()
            throws DatabaseConnectionException, SQLException,
            BankNumberLengthException, SalaryTooLowException,
            NinLengthException {
        int expectedResult = 1;
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(employeeDao.insertEmployee(employeeRequest, conn)).thenReturn(expectedResult);

        int result = employeeService.insertEmployee(employeeRequest);

        assertEquals(expectedResult, result);
    }

    @Test
    void insertEmployee_shouldThrowSqlException_whenDaoThrowsSqlException() throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(employeeDao.insertEmployee(employeeRequest, conn)).thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> employeeService.insertEmployee(employeeRequest));
    }

    /*
    Mocking Exercise 1

    Write a unit test for the getEmployee method

    When the dao throws a SQLException

    Expect SQLException to be thrown

    This should pass without code changes
     */
    @Test
    void getEmployee_shouldThrowSqlException_whenDaoThrowsSqlException()
            throws SQLException {
        Mockito.when(employeeDao.getEmployee(1, conn)).thenThrow(SQLException.class);
        assertThrows(SQLException.class, () -> employeeService.getEmployee(1));
    }

    /*
    Mocking Exercise 2

    Write a unit test for the getEmployee method

    When the databaseConnector throws a DatabaseConnectionException

    Expect DatabaseConnectionException to be thrown

    This should pass without code changes
     */
    @Test
    void getEmployee_shouldThrowDatabaseConnectionException_whenDatabaseConnectorThrowsDatabaseConnectionException()
            throws DatabaseConnectionException, SQLException {
        Mockito.when(databaseConnector.getConnection()).thenThrow(DatabaseConnectionException.class);
        assertThrows(DatabaseConnectionException.class, () -> employeeService.getEmployee(1));
    }

    /*
    Mocking Exercise 3

    Write a unit test for the getEmployee method

    When the dao returns an employee

    Expect the employee to be returned

    This should pass without code changes
     */
    @Test
    void getEmployee_shouldReturnEmployee_whenDaoReturnsEmployee()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException {
        Mockito.when(employeeDao.getEmployee(1, conn)).thenReturn(employee);
        assertEquals(employee, employeeService.getEmployee(1));
    }

    /*
    Mocking Exercise 4

    Write a unit test for the getEmployee method

    When the dao returns null

    Expect DoesNotExistException to be thrown

    This should fail, make code changes to make this test pass
     */
    @Test
    void getEmployee_shouldThrowDoesNotExistException_whenDaoReturnsNull()
            throws SQLException {
        Mockito.when(employeeDao.getEmployee(1, conn)).thenReturn(null);
        assertThrows(DoesNotExistException.class, () -> employeeService.getEmployee(1));
    }

    /*
    Mocking Exercise 5

    Write a unit test for the getEmployees method

    When the dao returns a list of employees

    Expect the list of employees to be returned

    This should pass without code changes
     */
    @Test
    void getEmployees_shouldReturnListEmployees_whenDaoReturnsListEmployees()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(employeeDao.getEmployees(conn)).thenReturn(employeeList);
        assertEquals(employeeList, employeeService.getEmployees());
    }

    /*
    Mocking Exercise 6

    Write a unit test for the getEmployees method

    When the dao throws a SQL Exception

    Expect the SQL Exception to be returned

    This should pass without code changes
     */
    @Test
    void getEmployees_shouldThrowSQLException_whenDaoThrowsSQLException()
            throws SQLException {
        Mockito.when(employeeDao.getEmployees(conn)).thenThrow(SQLException.class);
        assertThrows(SQLException.class, () -> employeeService.getEmployees());
    }

    /*
    Mocking Exercise 7

    Write a unit test for the getEmployees method

    When the databaseConnector throws a DatabaseConnectionException

    Expect DatabaseConnectionException to be thrown

    This should pass without code changes
     */
    @Test
    void getEmployees_shouldThrowDatabaseConnectionException_whenDatabaseConnectorThrowsDatabaseConnectionException()
            throws DatabaseConnectionException, SQLException {
        Mockito.when(databaseConnector.getConnection()).thenThrow(DatabaseConnectionException.class);
        assertThrows(DatabaseConnectionException.class, () -> employeeService.getEmployees());
    }

    /*
    Mocking Exercise 8

    Write a unit test for the insertEmployee method

    When the databaseConnector throws a DatabaseConnectionException

    Expect DatabaseConnectionException to be thrown

    This should pass without code changes
     */
    @Test
    void insertEmployee_shouldThrowDatabaseConnectionException_whenDatabaseConnectorThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenThrow(DatabaseConnectionException.class);
        assertThrows(DatabaseConnectionException.class, () -> employeeService.insertEmployee(employeeRequest));
    }

    /*
    Mocking Exercise 9

    Write a unit test for the insertEmployee method

    When the validator throws a SalaryTooLowException

    Expect SalaryTooLowException to be thrown

    This should pass without code changes
     */
    @Test
    void insertEmployee_shouldThrowSalaryTooLowException_whenValidatorThrowsSalaryTooLowException()
            throws BankNumberLengthException,
            SalaryTooLowException, NinLengthException {
        Mockito.when(employeeValidator.isValidEmployee(employeeRequest)).thenThrow(SalaryTooLowException.class);
        assertThrows(SalaryTooLowException.class, () -> employeeService.insertEmployee(employeeRequest));
    }

    /*
    Mocking Exercise 10

    Write a unit test for the insertEmployee method

    When the validator throws a BankNumberLengthException

    Expect BankNumberLengthException to be thrown

    This should pass without code changes
     */
    @Test
    void insertEmployee_shouldThrowBankNumberLengthException_whenValidatorThrowsBankNumberLengthException()
            throws BankNumberLengthException, SalaryTooLowException,
            NinLengthException {
        Mockito.when(employeeValidator.isValidEmployee(employeeRequest)).thenThrow(BankNumberLengthException.class);
        assertThrows(BankNumberLengthException.class, () -> employeeService.insertEmployee(employeeRequest));
    }
}
