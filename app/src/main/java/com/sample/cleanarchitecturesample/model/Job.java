
package com.sample.cleanarchitecturesample.model;

//import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Job {

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("exp")
    @Expose
    private Integer exp;
    @SerializedName("organization")
    @Expose
    private String organization;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

}
