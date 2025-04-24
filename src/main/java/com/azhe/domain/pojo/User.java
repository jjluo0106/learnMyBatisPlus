package com.azhe.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user") // 表跟sql名稱不一致，需要對應
public class User {

    @TableId(type = IdType.AUTO) // 默認使用雪花算法，所以一定要加這才能自增長
    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private String dept_id;
}
