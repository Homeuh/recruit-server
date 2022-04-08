package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 浏览记录表，记录求职者近期浏览过的职位信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class JobHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 浏览记录ID
     */
    @TableId(value = "history_id", type = IdType.INPUT)
    private String historyId;

    /**
     * 职位ID，可为空，当职位下线 / 被删除时，求职者点击后提示职位已下线并删除该条记录
     */
    private String jobId;

    /**
     * 求职者ID
     */
    private String applicantId;


}
