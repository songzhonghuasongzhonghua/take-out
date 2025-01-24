package com.song.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.song.constant.PasswordConstant;
import com.song.constant.StatusConstant;
import com.song.context.BaseContext;
import com.song.dto.EmployeeDTO;
import com.song.dto.EmployeeLoginDTO;
import com.song.dto.EmployeePageQueryDTO;
import com.song.entity.Employee;
import com.song.exception.AccountNotFoundException;
import com.song.exception.PasswordErrorException;
import com.song.mapper.EmployeeMapper;
import com.song.result.PageResult;
import com.song.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String password = employeeLoginDTO.getPassword();
        String username = employeeLoginDTO.getUsername();

        Employee employee = employeeMapper.findByUsername(username);

        //用户不存在
        if(employee == null){
            throw new AccountNotFoundException();
        }

        if(!password.equals(employee.getPassword())){
            throw new PasswordErrorException();
        }

        //还有一步操作没写，判断账户是否停用
        return employee;
    }

    @Override
    public void addEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //将DTO的数据拷贝一份到employee中
        BeanUtils.copyProperties(employeeDTO, employee);

        employee.setStatus(StatusConstant.ENABLED);
        employee.setPassword(PasswordConstant.DEFAULT_PASSWORD);
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

//        employee.setCreateUser(BaseContext.getThreadLocal());
//        employee.setUpdateUser(BaseContext.getThreadLocal());

        employeeMapper.addEmployee(employee);


    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
//        employeeMapper.pageQuery(employeePageQueryDTO);
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .id(id)
                .status(status)
                .build();
        employeeMapper.update(employee);
    }

    @Override
    public Employee getById(Long id) {
        Employee employee =  employeeMapper.getById(id);
        employee.setPassword("*****");
        return employee;
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        Employee employee  = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employeeMapper.update(employee);
    }


}
