package com.mtsoft.jetstream.db;

import com.mtsoft.jetstream.pojo.Department;
import com.mtsoft.jetstream.pojo.Employee;
import com.mtsoft.jetstream.pojo.RootData;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StorageTest {

    @Test
    public void canLoad() {
        storeTestDataAndShutdown();

        Storage db = Storage.getInstance();

        List<Department> departmentList = db.root().getDepartmentList();
        List<Employee> employeeList = db.root().getUnemployedList();

        assertThat(departmentList.size()).isEqualTo(1);
        assertThat(departmentList.get(0).getName()).isEqualTo("myCompany");
        assertThat(departmentList.get(0).getEmployeeList().size()).isEqualTo(3);

        departmentList.get(0).getEmployeeList().forEach(e -> assertThat(e.getSalary()).isEqualTo(0f));

        assertThat(departmentList.get(0).getEmployeeList().stream()
                .filter(e -> "mustermann".equals(e.getName()))
                .findFirst().isPresent()).isTrue();
        assertThat(departmentList.get(0).getEmployeeList().stream()
                .filter(e -> "mastermann".equals(e.getName()))
                .findFirst().isPresent()).isTrue();
        assertThat(departmentList.get(0).getEmployeeList().stream()
                .filter(e -> "mistermann".equals(e.getName()))
                .findFirst().isPresent()).isTrue();

        assertThat(employeeList.size()).isEqualTo(1);
        assertThat(employeeList.get(0).getSalary()).isEqualTo(0f);
        assertThat(employeeList.get(0).getName()).isEqualTo("applicant");
    }

    private void storeTestDataAndShutdown() {
        Department akquinet = new Department();
        akquinet.setName("myCompany");

        Employee mschroeder2 = new Employee();
        mschroeder2.setName("mustermann");
        mschroeder2.setSalary(0f);

        Employee rmagnus = new Employee();
        rmagnus.setName("mastermann");
        rmagnus.setSalary(0f);

        Employee imissler = new Employee();
        imissler.setName("mistermann");
        imissler.setSalary(0f);

        akquinet.getEmployeeList().add(mschroeder2);
        akquinet.getEmployeeList().add(rmagnus);
        akquinet.getEmployeeList().add(imissler);

        Employee applicant = new Employee();
        applicant.setName("applicant");
        applicant.setSalary(0f);

        RootData root = Storage.getInstance().root();

        root.getDepartmentList().add(akquinet);
        root.getUnemployedList().add(applicant);

        Storage.getInstance().storeRequired(root);

        // Shutdown db to make sure that the previously saved data is loaded from disk when the test is running.
        Storage.getInstance().shutdown();
    }

    @AfterClass
    public static void cleanup() throws Exception {
        String homeDir = System.getProperty("user.home");
        FileUtils.deleteDirectory(new File(homeDir + "/" + Storage.DB_PATH));
    }
}
