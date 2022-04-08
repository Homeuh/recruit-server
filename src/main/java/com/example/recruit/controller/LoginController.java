package com.example.recruit.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.recruit.common.lang.Result;
import com.example.recruit.entity.Login;
import com.example.recruit.entity.Resume;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>
 * 登录表，记录所有已注册的用户账号信息 前端控制器
 * </p>
 *
 * @author 作者：Home
 * @since 2022-04-02
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    // 登录获取用户信息，需要转发请求（重定向二次请求不好）
//    @GetMapping("/getUser/{login_phone}")
//    public Object getUser(@PathVariable String login_phone){
//        try {
//            Login login = loginService.getOne(new QueryWrapper<Login>().eq("login_phone", login_phone));
//            return "forward:/applicant/getUser/"+login.getLoginId()+"/"+login.getLoginRole();
//        } catch(Exception e){
//            return Result.fail(e.toString());
//        }
//    };

    // 手机验证码登录
    @GetMapping("/getUserByPhone")
    public Object getUserByPhone(String login_phone, String login_role){
        try {
            // 如果是直接登录，无需验证登录身份
            if(login_role.equals("")){
                try{
                    Login login = loginService.getOne(new QueryWrapper<Login>()
                            .eq("login_phone", login_phone));
                    Map<String, Object> map = new HashMap<>();
                    map.put("login_id", login.getLoginId());
                    map.put("login_role", login.getLoginRole());
                    return Result.succ(map);
                } catch(Exception e){
                    return Result.fail("账号不存在，请重新输入！");
                }
            }
            // 注册登录：先查找是否数据库中有该用户，没有则直接注册
            int count = loginService.count(new QueryWrapper<Login>()
                    .eq("login_phone", login_phone)
                    .eq("login_role",login_role));
            if(count == 0) {
                Login login = new Login();
                login.setLoginPhone(login_phone);
                login.setLoginRole(login_role);
                login.setLoginDate(LocalDateTime.now());
                loginService.save(login);
            }
            // 注册登录：返回刚注册后或已注册的用户,并修改登陆时间至数据库
            Login login = loginService.getOne(new QueryWrapper<Login>()
                    .eq("login_phone", login_phone)
                    .eq("login_role",login_role));
            loginService.update(new UpdateWrapper<Login>()
                    .eq("login_id",login.getLoginId())
                    .set("login_date",LocalDateTime.now()));
            Map<String, Object> map = new HashMap<>();
            map.put("login_id", login.getLoginId());
            map.put("login_role", login.getLoginRole());
            return Result.succ(map);
        } catch(Exception e){
            return Result.fail(404,"该手机号已被注册,请重新输入",e.toString());
        }
    };

    // 密码登录
    @GetMapping("/getUserByPass/{login_phone}/{password}")
    public Object getUserByPass(@PathVariable String login_phone, @PathVariable String password){
        try {
            Login login = loginService.getOne(new QueryWrapper<Login>().eq("login_phone", login_phone).eq("password", password));
            Map<String, Object> map = new HashMap<>();
            map.put("login_id", login.getLoginId());
            map.put("login_role", login.getLoginRole());
            return Result.succ(map);
        } catch(Exception e){
            return Result.fail(404,"数据库没有该用户记录",e.toString());
        }
    };
}
