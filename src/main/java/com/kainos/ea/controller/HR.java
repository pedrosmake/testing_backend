package com.kainos.ea.controller;

import com.kainos.ea.exception.BankNumberLengthException;
import com.kainos.ea.exception.DatabaseConnectionException;
import com.kainos.ea.exception.DoesNotExistException;
import com.kainos.ea.exception.NinLengthException;
import com.kainos.ea.exception.SalaryTooLowException;
import com.kainos.ea.model.EmployeeRequest;
import com.kainos.ea.model.SalesEmployee;
import com.kainos.ea.service.EmployeeService;
import com.kainos.ea.service.SalesEmployeeService;
import io.swagger.annotations.Api;
import org.eclipse.jetty.http.HttpStatus;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("API for HR app")
@Path("/hr")
public class HR {
    private final EmployeeService employeeService;
    private final SalesEmployeeService salesEmployeeService;

    public HR(EmployeeService employeeService, SalesEmployeeService salesEmployeeService) {
        this.employeeService = employeeService;
        this.salesEmployeeService = salesEmployeeService;
    }

    @GET
    @Path("/employee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployees() {
        try {
            return Response.ok(employeeService.getEmployees()).build();
        } catch (SQLException | DatabaseConnectionException e) {
            System.out.println(e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @GET
    @Path("/employee/{employeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("employeeId") int employeeId) {
        try {
            return Response.status(HttpStatus.OK_200).entity(employeeService.getEmployee(employeeId)).build();
        } catch (SQLException | DatabaseConnectionException e) {
            System.out.println(e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        } catch (DoesNotExistException e) {
            return  Response.status(HttpStatus.NOT_FOUND_404).build();
        }
    }

    @GET
    @Path("/salesEmployee/{salesEmployeeId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSalesEmployeeById(@PathParam("salesEmployeeId") int salesEmployeeId){
        try {
            return Response.status(HttpStatus.OK_200).entity(salesEmployeeService.getSalesEmployee(salesEmployeeId)).build();
        } catch (SQLException | DatabaseConnectionException e) {
            System.out.println(e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }

    @POST
    @Path("/employee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createEmployee(EmployeeRequest employee) {
        try {
            int id = employeeService.insertEmployee(employee);
            return Response.status(HttpStatus.CREATED_201).entity(id).build();
        } catch (SQLException | DatabaseConnectionException e) {
            System.out.println(e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                    .build();
        } catch (SalaryTooLowException | BankNumberLengthException | NinLengthException e) {
            System.out.println(e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/salesEmployee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSalesEmployee(SalesEmployee salesEmployee) {
        try {
            salesEmployeeService.insertSalesEmployee(salesEmployee);
            return Response.status(HttpStatus.CREATED_201).build();
        } catch (SQLException | DatabaseConnectionException e) {
            System.out.println(e);
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
        }
    }
}
