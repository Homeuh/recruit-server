package com.example.recruit.service.impl;

import com.example.recruit.entity.Login;
import com.example.recruit.mapper.LoginMapper;
import com.example.recruit.service.LoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录表，记录所有已注册的用户账号信息 服务实现类
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {

}
