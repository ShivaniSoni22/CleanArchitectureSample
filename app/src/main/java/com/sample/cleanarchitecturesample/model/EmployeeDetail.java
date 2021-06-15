
package com.sample.cleanarchitecturesample.model;

import java.util.List;
//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.sample.cleanarchitecturesample.model.Data;

//@Generated("jsonschema2pojo")
public class EmployeeDetail {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
