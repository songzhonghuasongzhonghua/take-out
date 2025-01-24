package com.song.mapper;

import com.github.pagehelper.Page;
import com.song.annotation.AutoFill;
import com.song.dto.EmployeePageQueryDTO;
import com.song.entity.Employee;
import com.song.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee findByUsername(String username);


    @Insert("insert into employee (username,name,password,phone,sex,id_number,status,create_time,update_time,create_user,update_user)" +
            "values " +
            "(#{username},#{name},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void addEmployee(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
