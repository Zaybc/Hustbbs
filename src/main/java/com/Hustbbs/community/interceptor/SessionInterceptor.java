package com.Hustbbs.community.interceptor;

import com.Hustbbs.community.enums.AdPosEnum;
import com.Hustbbs.community.mapper.UserMapper;
import com.Hustbbs.community.model.User;
import com.Hustbbs.community.model.UserExample;
import com.Hustbbs.community.service.AdService;
import com.Hustbbs.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AdService adService;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("拦截器执行");
        //设置 context 级别的属性
        request.getServletContext().setAttribute("redirectUri", redirectUri);
        // 没有登录的时候也可以查看导航
        for (AdPosEnum adPos : AdPosEnum.values()) {
            request.getServletContext().setAttribute(adPos.name(), adService.list(adPos.name()));
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0)
            for (Cookie cookie : cookies) {
//                System.out.println(cookie);
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    if (users.size() != 0) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", users.get(0));
//                        System.out.println(users.get(0));
//                        System.out.println(users.get(0).getIsSuper());
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        session.setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
