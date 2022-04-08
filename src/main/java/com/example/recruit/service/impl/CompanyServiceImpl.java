package com.example.recruit.service.impl;

import com.example.recruit.entity.Company;
import com.example.recruit.mapper.CompanyMapper;
import com.example.recruit.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 公司表，记录网站已注册的公司信息，一个公司注册成功后，系统会返回该公司唯一会员码，该公司名下所有的招聘人员必须通过会员码成为招聘官	 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
