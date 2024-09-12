package com.Hustbbs.community.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.Hustbbs.community.dto.ResultDTO;
import com.Hustbbs.community.exception.CustomizeErrorCode;
import com.Hustbbs.community.model.User;
import com.Hustbbs.community.service.UserService;
import com.Hustbbs.community.utils.TokenProccessor;

import java.util.Map;
import java.util.Random;


@Controller
public class LogupController {

    @Autowired
    private UserService userService;

    @GetMapping("/logup")
    public String toLogUp(){
        return "/logup.html";
    }

    @ResponseBody
    @PostMapping("/logup")
    public Object logUp(@RequestBody String s, Model model){

        System.out.println(s);
        Map<String, Object> mapdata = JSONObject.parseObject(s);

        User user = new User();
        user.setName((String) mapdata.get("name"));
        user.setPassword((String) mapdata.get("password"));
        user.setStudentId((String) mapdata.get("id"));
        user.setToken(TokenProccessor.getInstance().makeToken());
        String [] userImage = {
                "https://b-ssl.duitang.com/uploads/item/201807/14/20180714190256_ycylw.jpeg",
                "https://b-ssl.duitang.com/uploads/item/201806/12/20180612192153_cjoyb.thumb.700_0.jpg",
                "http://b-ssl.duitang.com/uploads/item/201605/24/20160524182828_VAyhZ.jpeg",
                "http://b-ssl.duitang.com/uploads/item/201605/24/20160524182836_e2R3B.jpeg"
        };
        //随机生成头像
        user.setAvatarUrl(userImage[new Random().nextInt(4)]);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setIsSuper("0");

        if(!userService.findIfStudentIdExits(user.getStudentId())){
//            model.addAttribute("error","用户ID已经存在");
//            System.out.println("出错");
            return ResultDTO.errorOf(CustomizeErrorCode.USER_EXITS);
        }
        else{
            userService.insertNewUser(user);
            return ResultDTO.okOf();
        }

    }
}
