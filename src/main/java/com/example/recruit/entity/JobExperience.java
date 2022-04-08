package com.example.recruit.entity;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 工作经验表，记录与求职者简历相关的工作经验信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobExperience extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 工作经验ID
     */
    @TableId(value = "experience_id", type = IdType.INPUT)
    private String experienceId;

    /**
     * 简历ID，外键，关联到resume表resume_id
     */
    private String resumeId;

    /**
     * 曾就职公司名称
     */
    private String companyName;

    /**
     * 工作所在城市
     */
    private String jobCity;

    /**
     * 工作行业
     */
    private String jobIndustry;

    /**
     * 工作职责
     */
    private String jobDuty;

    /**
     * 工作描述
     */
    private String jobDescription;

    /**
     * 工作类型：1全职 / 2兼职 / 3实习，默认为"1"
     */
    private String jobType;

    /**
     * 工作起始日期
     */
    private LocalDate startDate;

    /**
     * 工作截止日期
     */
    private LocalDate endDate;

    /**
     * 工作部门
     */
    private String jobDepartment;

    /**
     * 就职公司类型
     */
    private String companyType;

    /**
     * 就职公司规模
     */
    private String companySize;

}
