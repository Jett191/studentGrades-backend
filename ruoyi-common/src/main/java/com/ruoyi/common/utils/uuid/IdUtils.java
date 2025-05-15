package com.ruoyi.common.utils.uuid;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ID生成器工具类
 *
 * @author ruoyi
 */
public class IdUtils
{
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String simpleUUID()
    {
        return UUID.randomUUID().toString(true);
    }

    /**
     * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 随机UUID
     */
    public static String fastUUID()
    {
        return UUID.fastUUID().toString();
    }

    /**
     * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
     *
     * @return 简化的UUID，去掉了横线
     */
    public static String fastSimpleUUID() {
        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        // 创建一个DateTimeFormatter实例，并指定输出格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        // 格式化当前日期时间为字符串
        String formattedDateTime = now.format(formatter);

        return UUID.fastUUID().toString(true) + formattedDateTime;
    }
}
