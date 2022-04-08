package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户举报表，管理员接收并处理来自网站用户的举报信息，并对信息属实的用户(求职者 / 招聘官)账号封禁，公司封禁。若为公司封禁，则该公司名下招聘人员登录账号时，会提示公司已封禁，请联系管理员申诉解封且登录失败
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Report extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 举报ID
     */
    @TableId(value = "report_id", type = IdType.INPUT)
    private String reportId;

    /**
     * 被举报用户ID / 被举报公司ID
     */
    private String toId;

    /**
     * 举报用户ID
     */
    private String fromId;

    /**
     * 举报内容描述
     */
    private String reportDescription;

    /**
     * 举报附件信息，图片 / 音频
     */
    private String attachedFile;

    /**
     * 举报状态：0待处理 / 1已处理，默认值为'0'
     */
    private String reportStatus;

    /**
     * 举报结果：N举报失败 / Y举报成功
     */
    private String reportResult;


}
