package com.sample.cleanarchitecturesample.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sample.cleanarchitecturesample.R;
import com.sample.cleanarchitecturesample.dao.EmployeeDetailsService;
import com.sample.cleanarchitecturesample.model.Data;
import com.sample.cleanarchitecturesample.model.Education;
import com.sample.cleanarchitecturesample.model.Employee;
import com.sample.cleanarchitecturesample.model.EmployeeDetail;
import com.sample.cleanarchitecturesample.model.Job;
import com.sample.cleanarchitecturesample.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final List<List<String>> listEmployeeHeader = new ArrayList<>();
    private final HashMap<String, List<Object>> listEmployeeBody = new HashMap<>();
    private String fullName;
    private EmployeeRepository employeeRepository;
    private boolean isConnected = false;
    private EmployeeDetailsService api;
    private ExpandableListView expandableListView;
    private EmployeeAdapter listAdapter;
    private SwipeRefreshLayout pullToRefresh;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActiveNetworkInfo();
        employeeRepository = new EmployeeRepository(getApplicationContext());
        pullToRefresh = findViewById(R.id.pullToRefresh);
        expandableListView = findViewById(R.id.expandableListView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EmployeeDetailsService.BASE_URL) // Specify your api here
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //  Initialize api class
        api = retrofit.create(EmployeeDetailsService.class);
        pullToRefresh.setOnRefreshListener(() -> {
            getActiveNetworkInfo();
            fetchEmployeeData(this);
            pullToRefresh.setRefreshing(false);
        });
        listAdapter = new EmployeeAdapter(MainActivity.this, listEmployeeHeader, listEmployeeBody);
        fetchEmployeeData(this);
    }

    private void fetchEmployeeData(Context context) {
        listEmployeeBody.clear();
        listEmployeeHeader.clear();
        if (isConnected) {
            prefs = context.getSharedPreferences("application", MODE_PRIVATE);

            // Fetching the values into Pojo file
            Call<EmployeeDetail> call = api.getDetail();
            call.enqueue(new Callback<EmployeeDetail>() {
                @Override
                public void onResponse(Call<EmployeeDetail> call, Response<EmployeeDetail> response) {
                    if (response.body() != null) {
                        List<Data> employeeDataList = response.body().getData();
                        for (int i = 0; i < employeeDataList.size(); i++) {

                            Data employeeData = employeeDataList.get(i);
                            String employeeFirstName = employeeData.getFirstname();
                            String employeeLastName = employeeData.getLastname();
                            int employeeAge = employeeData.getAge() != null ? employeeData.getAge() : -1;
                            String employeeGender = employeeData.getGender();
                            String employeePicture = employeeData.getPicture();
                            Job employeeJob = employeeData.getJob();
                            Education employeeEducation = employeeData.getEducation();

                            List<Object> listOtherData = new ArrayList<>();
                            List<String> listProfileHeader = new ArrayList<>();
                            if (employeeAge > 0)
                                listOtherData.add("<b>Age:</b>  " + employeeAge);
                            if (employeeGender != null)
                                listOtherData.add("<b>Gender:</b>  " + employeeGender);
                            if (employeeJob != null) {
                                listOtherData.add("<b>Role:</b>  " + employeeJob.getRole());
                                listOtherData.add("<b>Experience:</b>  " + employeeJob.getExp() + " years");
                                listOtherData.add("<b>Organization:</b>  " + employeeJob.getOrganization());
                            }
                            if (employeeEducation != null) {
                                listOtherData.add("<b>Degree:</b>  " + employeeEducation.getDegree());
                                listOtherData.add("<b>Institution:</b>  " + employeeEducation.getInstitution());
                            }
                            if (employeeLastName != null) {
                                fullName = employeeFirstName + " " + employeeLastName;
                            } else {
                                fullName = employeeFirstName;
                            }

                            listProfileHeader.add(fullName);
                            if (employeePicture != null)
                                listProfileHeader.add(employeePicture);
                            listEmployeeHeader.add(listProfileHeader);
                            listEmployeeBody.put(fullName, listOtherData);

                            //saving data only for one time in Db
                            if (prefs.getBoolean("employeeDataNotSaved", true)) {
                                employeeRepository.insertTask(fullName, employeeAge, employeeGender, employeePicture,
                                        employeeJob.getRole(), employeeJob.getExp(), employeeJob.getOrganization(),
                                        employeeEducation.getDegree(), employeeEducation.getInstitution());
                            }
                        }
                        expandableListView.setAdapter(listAdapter);
                        prefs.edit().putBoolean("employeeDataNotSaved", false).apply();
                    }
                }

                @Override
                public void onFailure(Call<EmployeeDetail> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } else {
            LiveData<List<Employee>> employeeDataList = employeeRepository.getTasks();
            employeeDataList.observe(this, employees -> {
                for (int i = 0; i < employees.size(); i++) {
                    Employee employee = employees.get(i);
                    String employeeName = employee.getEmployee_name();
                    int employeeAge = employee.getEmployee_age();
                    String employeeGender = employee.getEmployee_gender();
                    String employeePicture = employee.getImage_url();
                    String employeeJobRole = employee.getJob_role();
                    int employeeExperience = employee.getJob_experience();
                    String employeeCompany = employee.getCompany();
                    String employeeDegree = employee.getQualification();
                    String employeeCollege = employee.getCollege();

                    List<Object> listOtherData = new ArrayList<>();
                    List<String> listProfileHeader = new ArrayList<>();
                    if (employeeAge > 0)
                        listOtherData.add("<b>Age:</b>  " + employeeAge);
                    if (employeeGender != null)
                        listOtherData.add("<b>Gender:</b>  " + employeeGender);
                    if (employeeJobRole != null)
                        listOtherData.add("<b>Role:</b>  " + employeeJobRole);
                    if (employeeExperience > 0)
                        listOtherData.add("<b>Experience:</b>  " + employeeExperience + " years");
                    if (employeeCompany != null)
                        listOtherData.add("<b>Organization:</b>  " + employeeCompany);
                    if (employeeDegree != null)
                        listOtherData.add("<b>Degree:</b>  " + employeeDegree);
                    if (employeeCollege != null)
                        listOtherData.add("<b>Institution:</b>  " + employeeCollege);

                    fullName = employeeName;
                    listProfileHeader.add(fullName);
                    if (employeePicture != null)
                        listProfileHeader.add(employeePicture);
                    listEmployeeHeader.add(listProfileHeader);
                    listEmployeeBody.put(fullName, listOtherData);
                }
                expandableListView.setAdapter(listAdapter);
            });
        }
    }

    // Network Check
    private void getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_MOBILE:
                    isConnected = activeNetwork.isConnectedOrConnecting();
                    break;
                default:
                    isConnected = false;
                    break;
            }
        } else {
            // not connected to the internet
            isConnected = false;
        }
    }

}