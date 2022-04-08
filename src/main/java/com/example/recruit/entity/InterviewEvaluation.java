package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 面试评价表，记录求职者应聘后的反馈信息，对职位描述、面试官印象、公司环境进行打分
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class InterviewEvaluation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 面试评价ID
     */
    @TableId(value = "evaluation_id", type = IdType.INPUT)
    private String evaluationId;

    /**
     * 面试ID，外键，关联到interview表interview_id
     */
    private String interviewId;

    /**
     * 应聘者ID，外键，关联到applicant表applicant_id
     */
    private String applicantId;

    /**
     * 面试官ID，外键，关联到recruiter表recruiter_id，可为空，如果招聘官离职或注销账号后设置为空
     */
    private String recruiter_id;

    /**
     * 公司ID，外键，关联到company表company_id
     */
    private String company_id;

    /**
     * 面试评价标题
     */
    private String evaluationTitle;

    /**
     * 面试评价内容描述
     */
    private String evaluationDescription;

    /**
     * 描述相符打分
     */
    private Float scoreDescription;

    /**
     * 对面试官打分
     */
    private Float scoreRecruiter;

    /**
     * 公司环境打分
     */
    private Float scoreEnvironment;

    /**
     * 是否匿名：N不匿名，Y匿名，默认不匿名
     */
    private String hiddenName;

    /**
     * 点赞次数
     */
    private Integer usefulNum;


}
