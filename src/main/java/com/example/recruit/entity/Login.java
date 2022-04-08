package com.example.recruit.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 登录表，记录所有已注册的用户账号信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Login extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 登录ID
     */
    @TableId(value = "login_id", type = IdType.INPUT)
    private String loginId;

    /**
     * 登录手机号
     */
    private String loginPhone;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 登录角色 / 登录类型：0求职者 / 1企业招聘官 / 2管理员
     */
    private String loginRole;

    /**
     * 用户最近登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginDate;

    /**
     * 账号封禁标志位，默认值为N
     */
    private String banFlag;


}
