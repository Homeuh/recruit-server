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
 * 职位表，记录各个招聘官名下 发布 / 待发布 的职位信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Job extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 职位ID
     */
    @TableId(value = "job_id", type = IdType.INPUT)
    private String jobId;

    /**
     * 招聘官ID，外键，关联到recruiter表recruiter_id
     */
    private String recruiterId;

    /**
     * 公司ID，外键，关联到company表company_id
     */
    private String companyId;

    /**
     * 职位职责 / 职位名称
     */
    private String jobDuty;

    /**
     * 职位标签，方便求职者定位
     */
    private String jobTag;

    /**
     * 职位描述
     */
    private String jobDescription;

    /**
     * 职位诱惑：职位福利缩减版
     */
    private String benefitTag;

    /**
     * 职位要求
     */
    private String jobRequirement;

    /**
     * 工作年限 / 工作年数
     */
    private String jobYear;

    /**
     * 学历要求
     */
    private String education;

    /**
     * 招聘人数
     */
    private Integer recruitNum;

    /**
     * 职位福利 / 工作待遇
     */
    private String jobBenefit;

    /**
     * 工作薪资：有13薪等说法，所以varchar保存
     */
    private String jobSalary;

    /**
     * 办公所在城市
     */
    private String officeCity;

    /**
     * 办公所在地区
     */
    private String officeDistrict;

    /**
     * 办公详细地址 / 工作地点
     */
    private String officeAddress;

    /**
     * 工作行业
     */
    private String jobIndustry;

    /**
     * 工作类型：1全职 / 2兼职 / 3实习，默认为"1"
     */
    private String jobType;

    /**
     * 附加信息
     */
    private String attachedInfo;

    /**
     * 面試信息
     */
    private String interviewInfo;

    /**
     * 职位状态：0上线 / 1草稿箱 /  2下线 
     */
    private String jobStatus;

    /**
     * 招聘官登录ID，用于查表返回招聘官ID和公司ID
     */
    @TableField(exist = false)
    private String loginId;

}
