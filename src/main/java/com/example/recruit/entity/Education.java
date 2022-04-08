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
 * 教育经历表，记录与求职者简历相关的教育经历信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Education extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 教育经历ID
     */
    @TableId(value = "education_id", type = IdType.INPUT)
    private String educationId;

    /**
     * 简历ID，外键，关联到resume表resume_id
     */
    private String resumeId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 学历
     */
    private String education;

    /**
     * 入学时间
     */
    private LocalDate startDate;

    /**
     * 毕业时间
     */
    private LocalDate endDate;

    /**
     * 专业
     */
    private String major;

    /**
     * 是否为统招
     */
    private String examinationFlag;

    /**
     * 是否为全日制
     */
    private String fulltimeFlag;

    /**
     * 荣誉 / 奖项
     */
    private String honor;

    /**
     * 证书
     */
    private String certificate;

    /**
     * 附加信息
     */
    private String attachedInfo;

}
