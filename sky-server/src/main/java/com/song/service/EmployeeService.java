package com.song.service;

import com.song.dto.EmployeeDTO;
import com.song.dto.EmployeeLoginDTO;
import com.song.dto.EmployeePageQueryDTO;
import com.song.entity.Employee;
import com.song.result.PageResult;

public interface EmployeeService {


    Employee login(EmployeeLoginDTO employeeLoginDTO);

    void addEmployee(EmployeeDTO employeeDTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void startOrStop(Integer status, Long id);

    Employee getById(Long id);

    void update(EmployeeDTO employeeDTO);
}
