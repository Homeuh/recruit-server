package com.example.recruit.service.impl;

import com.example.recruit.entity.Appeal;
import com.example.recruit.mapper.AppealMapper;
import com.example.recruit.service.AppealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 申诉表，管理员接收并处理网站封禁的用户账号 / 公司的申诉材料，并对材料属实的封禁账号 / 公司进行解封 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class AppealServiceImpl extends ServiceImpl<AppealMapper, Appeal> implements AppealService {

}
