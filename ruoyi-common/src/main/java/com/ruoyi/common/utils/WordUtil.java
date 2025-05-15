package com.ruoyi.common.utils;


import cn.afterturn.easypoi.word.WordExportUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @description Word相关处理
 */
public class WordUtil {

    /**
     * EasyPoi 替换数据 导出 word
     * @param templatePath word模板地址
     * @param tempDir      临时文件存放地址
     * @param filename     文件名称
     * @param data         替换参数
     * @param request
     * @param response
     */
    public static String easyPoiExport(String templatePath, String tempDir, String filename, Map<String, Object> data, HttpServletRequest request, HttpServletResponse response) {
        try {
            // 编码文件名
//            String encodedFilename = URLEncoder.encode(filename, "UTF-8");

            // 设置响应头，确保文件名被正确解析
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFilename + "\"");

            // 其他部分代码不变
            Assert.notNull(templatePath, "模板路径不能为空");
            Assert.notNull(tempDir, "临时文件路径不能为空");
            Assert.notNull(filename, "文件名称不能为空");
            Assert.isTrue(filename.endsWith(".docx"), "文件名称仅支持docx格式");

            if (!tempDir.endsWith("/")) {
                tempDir = tempDir + File.separator;
            }

            File file = new File(tempDir);
            if (!file.exists()) {
                file.mkdirs();
            }

            XWPFDocument document = WordExportUtil.exportWord07(templatePath, data);
            String tempPath = tempDir + filename;
            FileOutputStream out = new FileOutputStream(tempPath);
            document.write(out);

            // 删除临时文件
//            deleteTempFile(tempDir, filename);

            return filename;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteTempFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        File f = new File(filePath);
        file.delete();
        f.delete();
    }
}
