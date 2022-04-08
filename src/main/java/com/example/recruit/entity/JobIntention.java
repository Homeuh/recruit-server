package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 求职意向表，记录与求职者简历相关的求职意向信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobIntention extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 求职意向ID
     */
    @TableId(value = "intention_id", type = IdType.INPUT)
    private String intentionId;

    /**
     * 简历ID，外键，关联到resume表resume_id
     */
    private String resumeId;

    /**
     * 期望工作行业
     */
    private String intentionIndustry;

    /**
     * 期望工作职责
     */
    private String intentionDuty;

    /**
     * 期望工作城市
     */
    private String intentionCity;

    /**
     * 期望工作类型：1全职 / 2兼职 / 3实习，默认为"1"
     */
    private String intentionType;

    /**
     * 期望最低月薪：num + K
     */
    private String minSalary;

    /**
     * 期望最高月薪：num + K
     */
    private String maxSalary;

    /**
     * 到岗时间
     */
    private String arriveDate;

}
