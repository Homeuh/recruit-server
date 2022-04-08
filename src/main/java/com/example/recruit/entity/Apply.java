package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 简历投递表，记录求职者的简历投递信息，简历投递记录不允许被求职者主动删除，因为与interview表关联，只有当求职者 / 招聘官账号注销、或者公司注销时才可被级联删除
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Apply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 投递ID
     */
    @TableId(value = "apply_id", type = IdType.INPUT)
    private String applyId;

    /**
     * 求职者ID
     */
    private String applicantId;

    /**
     * 投递简历ID，可为null，投递时需要传递简历，如果求职者删除简历，设置为空，在招聘官访问时获取不到则提示后删除该条记录
     */
    private String resumeId;

    /**
     * 投递公司名称
     */
    private String companyId;

    /**
     * 投递职位ID，如果职位下线 / 被删除了，设置为空，在投递者查看投递职位时，提示职位已下线并删除该条记录
     */
    private String jobId;

    /**
     * 接收简历招聘官ID
     */
    private String recruiterId;

    /**
     * 投递状态：0不合适 / 1已投递 / 2被查看 / 3感兴趣 / 4收到面试邀请 / 5面试已结束，默认值为'1'已投递
     */
    private String applyStatus;


}
