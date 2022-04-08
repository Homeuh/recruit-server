package com.example.recruit.entity;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 项目经验表，记录与求职者简历相关的项目经验信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProjectExperience extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @TableId(value = "project_id", type = IdType.INPUT)
    private String projectId;

    /**
     * 简历ID，外键，关联到resume表resume_id
     */
    private String resumeId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目描述
     */
    private String projectDescription;

    /**
     * 项目职责（在项目中担任的身份）
     */
    private String projectDuty;

    /**
     * 职责描述
     */
    private String dutyDescription;

    /**
     * 项目起始时间
     */
    private LocalDate startDate;

    /**
     * 项目完成时间
     */
    private LocalDate endDate;

    /**
     * 项目链接
     */
    private String projectUrl;

    /**
     * 前端传递的表单数据包括 login_id，非该表字段
     */
    @TableField(exist = false)
    private String loginId;

}
