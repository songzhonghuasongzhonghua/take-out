package com.song.dto;


import lombok.Data;

import java.io.Serializable;

//@ApiModel(description = "员工登录时传递的数据模型")
@Data
public class EmployeeLoginDTO implements Serializable {

//    @ApiModelProperty("用户名")
    private String username;

//    @ApiModelProperty("密码")
    private String password;

}
