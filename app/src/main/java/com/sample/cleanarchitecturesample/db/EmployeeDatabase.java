package com.sample.cleanarchitecturesample.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.sample.cleanarchitecturesample.dao.DaoAccess;
import com.sample.cleanarchitecturesample.model.Employee;

@Database(entities = {Employee.class}, version = 1, exportSchema = false)
public abstract class EmployeeDatabase extends RoomDatabase {

    public abstract DaoAccess daoAccess();

}
