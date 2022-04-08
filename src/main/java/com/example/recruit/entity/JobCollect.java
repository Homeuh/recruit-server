package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 职位收藏表，记录求职者收藏的职位信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobCollect extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 职位收藏ID
     */
    @TableId(value = "collect_id", type = IdType.INPUT)
    private String collectId;

    /**
     * 职位ID，外键，关联到job表job_id
     */
    private String jobId;

    /**
     * 求职者ID，外键，关联到interviewee表interviewee_id
     */
    private String applicantId;


}
