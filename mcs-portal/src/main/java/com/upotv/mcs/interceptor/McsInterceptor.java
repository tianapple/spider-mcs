package com.upotv.mcs.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.upotv.mcs.main.entity.Mcs_user;
import com.upotv.mcs.operlog.entity.Log;
import com.upotv.mcs.operlog.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by wow on 2017/6/26.
 */
@Component
public class McsInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(McsInterceptor.class);

    private static final String LOGGER_ENTITY = "logger_entity";

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StopWatch-StartTime");

    @Autowired
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Log logEntity = new Log();

        //请求路径
        String url = request.getRequestURL().toString();
        String uri = request.getRequestURI();
        //请求方法
        String method = request.getMethod();
        //获取请求参数信息
        String paramData = JSON.toJSONString(request.getParameterMap(), SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
        //客户端IP
        logEntity.setIp(request.getRemoteAddr());
        //请求参数内容json字符串
        logEntity.setParam(paramData);

        logEntity.setPath(uri);

        Mcs_user user =  (Mcs_user)request.getSession().getAttribute("user");

        logEntity.setUsername(user.getUserName());

        request.setAttribute(LOGGER_ENTITY,logEntity);

        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);//线程绑定变量（该数据只有当前请求的线程可见）

        LOGGER.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s", url, method, uri, paramData));

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long endTime = System.currentTimeMillis();
        long beginTime = startTimeThreadLocal.get();
        long consumeTime = endTime - beginTime;

        int status = response.getStatus();

        Log logEntity = (Log)request.getAttribute(LOGGER_ENTITY);
        logEntity.setDuration(consumeTime);
        logEntity.setStatus(status);

        Object exception = request.getAttribute("exception");
        if(exception != null){
            logEntity.setRemark(exception.toString());
            logEntity.setStatus(500);
        }
        logService.insert(logEntity);

        super.afterCompletion(request, response, handler, ex);
    }
}
