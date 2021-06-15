package com.sample.cleanarchitecturesample.dao;

import com.sample.cleanarchitecturesample.model.EmployeeDetail;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeDetailsService {
    String BASE_URL = "https://bbf2a516-7989-4779-a5bf-ecb2777960c4.mock.pstmn.io/";

    @GET("v1/prod/t1/employee/getAllDetails")
    Call<EmployeeDetail> getDetail();

}
