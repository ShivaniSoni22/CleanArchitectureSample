package com.sample.cleanarchitecturesample.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.sample.cleanarchitecturesample.model.Employee;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    Long insertTask(Employee employee);

    @Query("SELECT * FROM employee")
    LiveData<List<Employee>> getAllEmployees();

    @Query("SELECT * FROM employee WHERE id =:taskId")
    LiveData<Employee> getEmployee(int taskId);

    @Update
    void updateEmployee(Employee employee);

    @Delete
    void deleteEmployee(Employee employee);

    @Delete
    void deleteAllEmployees(List<Employee> employeeList);
}
