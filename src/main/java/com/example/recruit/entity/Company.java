package com.example.recruit.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公司表，记录网站已注册的公司信息，一个公司注册成功后，系统会返回该公司唯一会员码，该公司名下所有的招聘人员必须通过会员码成为招聘官	
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
//数据传输形式转换为下划线
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Company extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    @TableId(value = "company_id", type = IdType.INPUT)
    private String companyId;

    /**
     * 公司全称
     */
    private String companyFullName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司类型
     */
    private String companyType;

    /**
     * 公司标签 / 公司定位 / 公司业务 / 公司行业
     */
    private String companyTag;

    /**
     * 公司简单描述
     */
    private String companyDescription;

    /**
     * 公司所在城市
     */
    private String companyCity;

    /**
     * 公司所在区
     */
    private String companyDistrict;

    /**
     * 公司注册地址 / 公司位置
     */
    private String companyAddress;

    /**
     * 公司介绍
     */
    private String companyIntroduction;

    /**
     * 公司规模
     */
    private String companySize;

    /**
     * 公司网站链接
     */
    private String companyWebsite;

    /**
     * 公司logo商标
     */
    private String companyLogo;

    /**
     * 企业营业执照
     */
    private String businessLicense;

    /**
     * 企业认证标志位，默认值为N
     */
    private String authFlag;

    /**
     * 公司会员码 / 公司注销码，注册成功由系统生成并返回
     */
    private String memberCode;

    /**
     * 修改人id，外键，关联到recruiter表recruiter_id
     */
    private String updateId;

    /**
     * 公司封禁标志位，默认值为N
     */
    private String banFlag;


}
