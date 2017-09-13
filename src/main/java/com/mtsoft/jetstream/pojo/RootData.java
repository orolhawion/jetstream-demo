package com.mtsoft.jetstream.pojo;

import java.util.ArrayList;
import java.util.List;

public class RootData {

    private List<Department> departmentList;
    private List<Employee> unemployedList;

    public List<Department> getDepartmentList() {
        if (departmentList == null) {
            departmentList = new ArrayList<>();
        }
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    public List<Employee> getUnemployedList() {
        if (unemployedList == null) {
            unemployedList = new ArrayList<>();
        }
        return unemployedList;
    }

    public void setUnemployedList(List<Employee> unemployedList) {
        this.unemployedList = unemployedList;
    }
}
