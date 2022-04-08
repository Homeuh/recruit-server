package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 简历表，记录求职者的简历信息，支持导入pdf格式的简历
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Resume extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 简历ID
     */
    @TableId(value = "resume_id", type = IdType.INPUT)
    private String resumeId;

    /**
     * 求职者ID，外键，关联到interviewee表interviewee_id
     */
    private String applicantId;

    /**
     * 自我评价 / 个人优势
     */
    private String selfEvaluation;

    /**
     * 附件简历，仅支持pdf格式
     */
    private String attachedFile;


}
