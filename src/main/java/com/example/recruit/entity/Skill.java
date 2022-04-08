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
 * 专业技能表，记录与求职者简历相关的专业技能信息
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Skill extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 技能ID
     */
    @TableId(value = "skill_id", type = IdType.INPUT)
    private String skillId;

    /**
     * 简历ID，外键，关联到resume表resume_id
     */
    private String resumeId;

    /**
     * 技能名称
     */
    private String skillName;

    /**
     * 技能掌握程度
     */
    private String masteryDegree;

}
