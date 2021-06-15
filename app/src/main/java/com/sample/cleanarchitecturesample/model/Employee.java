package com.sample.cleanarchitecturesample.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employee")
public class Employee implements Parcelable {

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel in) {
            return new Employee(in);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo(name = "name")
    private String employee_name;
    @ColumnInfo(name = "image")
    private String image_url;
    @ColumnInfo(name = "age")
    private int employee_age;
    @ColumnInfo(name = "gender")
    private String employee_gender;
    @ColumnInfo(name = "role")
    private String job_role;
    @ColumnInfo(name = "experience")
    private int job_experience;
    @ColumnInfo(name = "organisation")
    private String company;
    @ColumnInfo(name = "degree")
    private String qualification;
    @ColumnInfo(name = "institution")
    private String college;

    public Employee(@NonNull int id, String employee_name, String image_url,
                    int employee_age, String employee_gender, String job_role,
                    int job_experience, String company, String qualification, String college) {
        this.id = id;
        this.employee_name = employee_name;
        this.image_url = image_url;
        this.employee_age = employee_age;
        this.employee_gender = employee_gender;
        this.job_role = job_role;
        this.job_experience = job_experience;
        this.company = company;
        this.qualification = qualification;
        this.college = college;
    }

    public Employee() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getEmployee_age() {
        return employee_age;
    }

    public void setEmployee_age(int employee_age) {
        this.employee_age = employee_age;
    }

    public String getEmployee_gender() {
        return employee_gender;
    }

    public void setEmployee_gender(String employee_gender) {
        this.employee_gender = employee_gender;
    }

    public String getJob_role() {
        return job_role;
    }

    public void setJob_role(String job_role) {
        this.job_role = job_role;
    }

    public int getJob_experience() {
        return job_experience;
    }

    public void setJob_experience(int job_experience) {
        this.job_experience = job_experience;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    protected Employee(Parcel in) {
        id = in.readInt();
        employee_name = in.readString();
        image_url = in.readString();
        employee_age = in.readInt();
        employee_gender = in.readString();
        job_role = in.readString();
        job_experience = in.readInt();
        company = in.readString();
        qualification = in.readString();
        college = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(employee_name);
        dest.writeString(image_url);
        dest.writeInt(employee_age);
        dest.writeString(employee_gender);
        dest.writeString(job_role);
        dest.writeInt(job_experience);
        dest.writeString(company);
        dest.writeString(qualification);
        dest.writeString(college);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", employee_name='" + employee_name + '\'' +
                ", image_url='" + image_url + '\'' +
                ", employee_age='" + employee_age + '\'' +
                ", employee_gender='" + employee_gender + '\'' +
                ", job_role='" + job_role + '\'' +
                ", job_experience='" + job_experience + '\'' +
                ", company='" + company + '\'' +
                ", qualification='" + qualification + '\'' +
                ", college='" + college + '\'' +
                '}';
    }
}
