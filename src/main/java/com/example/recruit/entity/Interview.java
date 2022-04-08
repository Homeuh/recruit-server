package com.example.recruit.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 面试表，记录求职者的面试信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Interview extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 面试ID
     */
    @TableId(value = "interview_id", type = IdType.INPUT)
    private String interviewId;

    /**
     * 应聘者ID，外键，关联到applicant表applicant_id，当求职者账号注销后，设置为null，当招聘官查看求职者信息时，提示求职者信息不存在并删除该条记录
     */
    private String applicantId;

    /**
     * 投递ID，外键，关联到apply表apply_id
     */
    private String applyId;

    /**
     * 面试时间
     */
    private LocalDateTime interviewDate;

    /**
     * 面试状态：0已拒绝 / 1待面试 / 2面试已结束
     */
    private String interviewStatus;


}
