package com.example.recruit.service.impl;

import com.example.recruit.entity.Report;
import com.example.recruit.mapper.ReportMapper;
import com.example.recruit.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户举报表，管理员接收并处理来自网站用户的举报信息，并对信息属实的用户(求职者 / 招聘官)账号封禁，公司封禁。若为公司封禁，则该公司名下招聘人员登录账号时，会提示公司已封禁，请联系管理员申诉解封且登录失败 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

}
