package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 企业招聘官用户表，记录招聘官的一些基本信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Recruiter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 招聘官ID
     */
    @TableId(value = "recruiter_id", type = IdType.INPUT)
    private String recruiterId;

    /**
     * 账号ID，外键，关联到login表login_id
     */
    private String loginId;

    /**
     * 招聘官姓名
     */
    private String recruiterName;

    /**
     * 招聘官性别
     */
    private String recruiterSex;

    /**
     * 招聘官职位
     */
    private String recruiterDuty;

    /**
     * 招聘官头像
     */
    private String recruiterAvatar;

    /**
     * 招聘官手机号
     */
    private String recruiterTel;

    /**
     * 招聘官微信号
     */
    private String recruiterWechat;

    /**
     * 所属公司ID，外键，关联到company表company_id
     */
    private String companyId;


}
