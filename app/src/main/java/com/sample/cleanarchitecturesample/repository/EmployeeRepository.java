package com.sample.cleanarchitecturesample.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.sample.cleanarchitecturesample.db.EmployeeDatabase;
import com.sample.cleanarchitecturesample.model.Employee;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployeeRepository {

    private final String DB_NAME = "employee";
    private final EmployeeDatabase employeeDatabase;

    public EmployeeRepository(Context context) {
        employeeDatabase = Room.databaseBuilder(context, EmployeeDatabase.class, DB_NAME).build();
    }

    public void insertTask(String employeeName, int employeeAge, String employeeGender,
                           String employeePicture, String role, Integer exp,
                           String organization, String degree, String institution) {
        Employee employee = new Employee();
        employee.setEmployee_name(employeeName);
        employee.setEmployee_age(employeeAge);
        employee.setEmployee_gender(employeeGender);
        employee.setImage_url(employeePicture);
        employee.setJob_role(role);
        employee.setJob_experience(exp);
        employee.setCompany(organization);
        employee.setQualification(degree);
        employee.setCollege(institution);
        insertTask(employee);
    }

    public void insertTask(final Employee employee) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> employeeDatabase.daoAccess().insertTask(employee));
    }

    public void updateTask(final Employee employee) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> employeeDatabase.daoAccess().updateEmployee(employee));
    }

    public void deleteTask(final int id) {
        final LiveData<Employee> task = getTask(id);
        if (task != null) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> employeeDatabase.daoAccess().deleteEmployee(task.getValue()));
        }
    }

    public void deleteTask(final Employee employee) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> employeeDatabase.daoAccess().deleteEmployee(employee));
    }

    public LiveData<Employee> getTask(int id) {
        return employeeDatabase.daoAccess().getEmployee(id);
    }

    public LiveData<List<Employee>> getTasks() {
        return employeeDatabase.daoAccess().getAllEmployees();
    }
}