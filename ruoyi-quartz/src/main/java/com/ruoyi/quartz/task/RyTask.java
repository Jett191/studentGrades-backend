// Todo LFN
package com.ruoyi.quartz.task;

import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.Resource;
import java.util.List;

/**
 * 定时任务调度
 *
 * @author MakabakaO
 * @optimization
 */
@Component("ryTask")
public class RyTask
{
//    @Resource
//    private IJdzsService jdzsService;
//
//    public void selectJdzs(String jdzsId) {
//        Jdzs jdzs = jdzsService.selectJdzsByJdzsId(jdzsId);
//        System.out.println(jdzs);
//    }

  public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i)
  {
    System.out.println(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
  }

  public void ryParams(String params)
  {
    System.out.println("执行有参方法：" + params);
  }

  public void ryNoParams()
  {
    System.out.println("执行无参方法");
  }
}
