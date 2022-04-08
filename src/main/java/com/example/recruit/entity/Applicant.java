package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 求职者用户表，记录求职者的一些基本信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Applicant extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 求职者ID
     */
    @TableId(value = "applicant_id", type = IdType.INPUT)
    private String applicantId;

    /**
     * 账号ID，外键，关联到login表login_id
     */
    private String loginId;

    /**
     * 求职者姓名
     */
    private String applicantName;

    /**
     * 求职者性别
     */
    private String applicantSex;

    /**
     * 求职者年龄
     */
    private Integer applicantAge;

    /**
     * 头像
     */
    private String applicantAvatar;

    /**
     * 身份：学生 / 职场人
     */
    private String applicantIdentity;

    /**
     * 工龄
     */
    private String workingYear;

    /**
     * 求职者手机号
     */
    private String applicantTel;

    /**
     * 求职者微信号
     */
    private String applicantWechat;

    /**
     * 求职者联系邮箱
     */
    private String applicantEmail;

    /**
     * 当前所在城市
     */
    private String applicantCity;

    /**
     * 求职者最高学历
     */
    private String applicantEducation;

}
