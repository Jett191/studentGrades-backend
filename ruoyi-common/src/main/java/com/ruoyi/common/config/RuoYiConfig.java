package com.ruoyi.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * 读取项目相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
public class RuoYiConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 上传路径 */
    private static String profile;

    /** 文件临时存储路径 */
    private static String temporaryFilePath;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    /** 验证码类型 */
    private static String captchaType;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getCopyrightYear()
    {
        return copyrightYear;
    }

    public void setCopyrightYear(String copyrightYear)
    {
        this.copyrightYear = copyrightYear;
    }

    public static String getProfile()
    {
        return profile;
    }

    public void setProfile(String profile)
    {
        RuoYiConfig.profile = profile;
    }

    public static String getTemporaryFilePath() {
        return temporaryFilePath;
    }

    public static void setTemporaryFilePath(String temporaryFilePath) {
        RuoYiConfig.temporaryFilePath = temporaryFilePath;
    }

    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }

    public void setAddressEnabled(boolean addressEnabled)
    {
        RuoYiConfig.addressEnabled = addressEnabled;
    }

    public static String getCaptchaType() {
        return captchaType;
    }

    public void setCaptchaType(String captchaType) {
        RuoYiConfig.captchaType = captchaType;
    }

    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        // 获取当前月
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        // 获取当前日
        String day = String.valueOf(calendar.get(Calendar.DATE));

        // 获取当前小时
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        // 获取当前分钟
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));

        //小时-分钟
        String hourMinute = hour + "点" + minute;
        return getProfile() + "/upload/" + year + "/" + month + "/" + day + "/" + hourMinute;
    }
}
