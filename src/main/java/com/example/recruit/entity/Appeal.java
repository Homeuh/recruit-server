package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 申诉表，管理员接收并处理网站封禁的用户账号 / 公司的申诉材料，并对材料属实的封禁账号 / 公司进行解封
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Appeal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "appeal_id", type = IdType.INPUT)
    private String appealId;

    /**
     * 封禁用户ID / 封禁公司ID
     */
    private String toId;

    /**
     * 申诉申请人ID，可有可无
     */
    private String fromId;

    /**
     * 申诉内容描述
     */
    private String appealDescription;

    /**
     * 申诉附件信息，图片
     */
    private String attachedFile;

    /**
     * 申诉状态：0待处理 / 1已处理，默认值为'0'
     */
    private String appealStatus;

    /**
     * 申诉结果：N申诉失败 / Y申诉成功
     */
    private String appealResult;


}
