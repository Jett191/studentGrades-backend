//package com.ruoyi.common.word;
//
//import com.ruoyi.common.config.RuoYiConfig;
//import com.ruoyi.common.utils.StringUtils;
//import com.spire.doc.*;
//import com.spire.doc.documents.Paragraph;
//import com.spire.doc.documents.ParagraphStyle;
//import com.spire.doc.fields.DocPicture;
//import com.spire.doc.formatting.ParagraphFormat;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//
//import java.io.*;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class WordOperation {
//
//    /**
//     * 富文本导出为docx文档
//     *
//     * @param htmlContext       富文本内容
//     * @param temporaryFileName 导出的临时文件名称
//     * @param titleName         这一块富文本的标题
//     * @return
//     * @throws IOException
//     */
//    public static String htmlStringToWord(String htmlContext, String temporaryFileName, String titleName) throws IOException {
//        //替换img 标签中  src的代理路径
//        htmlContext = htmlContext.replace("/dev-api/profile", RuoYiConfig.getProfile())
//                .replace("/prod-api/profile", RuoYiConfig.getProfile())
//                .replace("http://localhost", "");
//        //临时保存文件路径
//        String filePath = RuoYiConfig.getProfile() + RuoYiConfig.getTemporaryFilePath() + temporaryFileName + ".docx";
//        //新建Document对象
//        com.spire.doc.Document document = new com.spire.doc.Document();
//        //在此docx文件中 创一个标题样式
//        ParagraphStyle style1 = new ParagraphStyle(document);
//        style1.setName("titleStyle");
//        style1.getCharacterFormat().setBold(true);
//        style1.getCharacterFormat().setFontSize(20f);
//        style1.getCharacterFormat().setFontName("SimHei");
//
//        // 获取段落格式并设置行间距
//        ParagraphFormat paragraphFormat = style1.getParagraphFormat();
//        paragraphFormat.setLineSpacingRule(LineSpacingRule.Exactly);
////        paragraphFormat.setLineSpacing((float) (5 * 20)); // 假设默认字体大小为20，设置为1.5倍行距
//        // 设置上下间距
//        paragraphFormat.setBeforeSpacing(25);  // 减少上方间距至6磅
//        paragraphFormat.setAfterSpacing(50);  // 增加下方间距至12磅
//
//        document.getStyles().add(style1);
//
//        //添加section
//        Section sec = document.addSection();
//
//        //先新增一个标题
//        Paragraph para1 = sec.addParagraph();
//        //titleName 为 方法传入
//        para1.appendText(titleName);
//        para1.applyStyle("titleStyle");
//
//        //img标签的 正则表达式
//        String imgPattern = "<img\\s+[^>]*src=\"([^\"]*)\"[^>]*>";
//        Pattern pattern = Pattern.compile(imgPattern);
//        Matcher matcher = pattern.matcher(htmlContext);
//
//        //创建表格
//        Table table = sec.addTable(true);
//        //只有一个单元格
//        table.resetCells(1, 1);
//
//        // 获取左页边距
//        float leftMargin = sec.getPageSetup().getMargins().getLeft();
//        // 获取右页边距
//        float rightMargin = sec.getPageSetup().getMargins().getRight();
//        double pageWidth = sec.getPageSetup().getPageSize().getWidth() - leftMargin - rightMargin;
//
//        //获取第(1,1)单元格
//        TableCell cell = table.getRows().get(0).getCells().get(0);
//
//        int lastIndex = 0;
//        while (matcher.find()) {
//            // 获取 img 标签之前的 HTML 片段
//            String textBeforeImg = htmlContext.substring(lastIndex, matcher.start());
//
//            // 添加 HTML 片段到段落
//            Paragraph textParagraph = cell.addParagraph();
//            textParagraph.appendHTML(textBeforeImg);
//
//            // 获取 src 属性的值
//            String imgSrc = matcher.group(1);
//
//            // 添加图片到新段落
//            Paragraph imgParagraph = cell.addParagraph();
//            DocPicture picture = new DocPicture(document);
//            picture.loadImage(imgSrc);
//            imgParagraph.getChildObjects().insert(0, picture);
//            // 更新 lastIndex
//            lastIndex = matcher.end();
//        }
//        //新增表格的段落
//        Paragraph remainingTextParagraph = cell.addParagraph();
//        remainingTextParagraph.appendHTML(htmlContext.substring(lastIndex));
//        //表格宽度
//        table.getRows().get(0).getCells().get(0).setCellWidth((float) (pageWidth * 1.3), CellWidthType.Point);
//        //文档另存为docx
//        document.saveToFile(filePath, FileFormat.Docx);
//
//        return filePath;
//    }
//
//
//    /**
//     * 富文本导出为docx文档  不不含表格框选
//     *
//     * @param htmlContext       富文本内容
//     * @param temporaryFileName 导出的临时文件名称
//     * @param titleName         这一块富文本的标题
//     * @return
//     * @throws IOException
//     */
//    public static String htmlStringToWordUnHaveTable(String htmlContext, String temporaryFileName, String titleName) throws IOException {
//        //临时保存文件路径
//        String filePath = RuoYiConfig.getProfile() + RuoYiConfig.getTemporaryFilePath() + temporaryFileName + ".docx";
//        //新建Document对象
//        com.spire.doc.Document document = new com.spire.doc.Document();
//        //在此docx文件中 创一个标题样式
//        ParagraphStyle style1 = new ParagraphStyle(document);
//        style1.setName("titleStyle");
//        style1.getCharacterFormat().setBold(true);
//        style1.getCharacterFormat().setFontSize(20f);
//        style1.getCharacterFormat().setFontName("SimHei");
//
//        // 获取段落格式并设置行间距
//        ParagraphFormat paragraphFormat = style1.getParagraphFormat();
//        paragraphFormat.setLineSpacingRule(LineSpacingRule.Exactly);
////        paragraphFormat.setLineSpacing((float) (5 * 20)); // 假设默认字体大小为20，设置为1.5倍行距
//        // 设置上下间距
//        paragraphFormat.setBeforeSpacing(25);  // 减少上方间距至6磅
//        paragraphFormat.setAfterSpacing(50);  // 增加下方间距至12磅
//
//        document.getStyles().add(style1);
//
//        //添加section
//        Section sec = document.addSection();
//
//        //先新增一个标题
//        Paragraph para1 = sec.addParagraph();
//        //titleName 为 方法传入
//        para1.appendText(titleName);
//        para1.applyStyle("titleStyle");
//
////        html内容
//        Paragraph para2 = sec.addParagraph();
//        para2.appendHTML(htmlContext);
//
//        //文档另存为docx
//        document.saveToFile(filePath, FileFormat.Docx);
//
//        return filePath;
//    }
//
//    /**
//     * 合并文档  文档合并
//     */
//    public static String mergeDocx(String filePath1, String filePath2, String finalFilePath) {
//
//        //创建 Document 类的对象并从磁盘加载 Word 文档
//        com.spire.doc.Document document = new com.spire.doc.Document(filePath1);
//
//        //将另一个文档插入当前文档
//        document.insertTextFromFile(filePath2, FileFormat.Docx);
//
//        //保存结果文档
//        document.saveToFile(finalFilePath, FileFormat.Docx);
//
//        return finalFilePath;
//    }
//
//    /**
//     * 合并文档  内容合并
//     *
//     * @param filePath1 最终留存文件路径
//     * @param filePath2 被合并文档路径
//     * @return
//     */
//    public static String mergeDocxFile(String filePath1, String filePath2, String finalFilePath) {
//
//        com.spire.doc.Document document1 = new com.spire.doc.Document(filePath1);
//        com.spire.doc.Document document2 = new com.spire.doc.Document(filePath2);
//
//        //在第二个文档中循环获取所有节
//        for (Object sectionObj : (Iterable) document2.getSections()) {
//            Section sec = (Section) sectionObj;
//            //在所有节中循环获取所有子对象
//            for (Object docObj : (Iterable) sec.getBody().getChildObjects()) {
//                DocumentObject obj = (DocumentObject) docObj;
//
//                //获取第一个文档的最后一节
//                Section lastSection = document1.getLastSection();
//
//                //将所有子对象添加到第一个文档的最后一节中
//                Body body = lastSection.getBody();
//                body.getChildObjects().add(obj.deepClone());
//            }
//        }
//
//        //保存结果文档
//        document1.saveToFile(finalFilePath, FileFormat.Docx);
//
//        return finalFilePath;
//
//    }
//
//    /**
//     * 去水印
//     *
//     * @param filePath 目标文件路径
//     * @return
//     */
//    public static String removeWaterMark(String filePath, int charCount) throws Exception {
//        FileInputStream inputStream = new FileInputStream(filePath);
//        XWPFDocument hwpfDocument = new XWPFDocument(inputStream);
//        // 操作文字
//        // removeText(hwpfDocument, charCount);
//        // 操作段落
//        removeFirstParagraph(hwpfDocument);
//        OutputStream os = new FileOutputStream(filePath);
//
//        try {
//            hwpfDocument.write(os);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            os.close();
//            inputStream.close();
//        }
//        return filePath;
//    }
//
//    public static void removeText(XWPFDocument document, int charCount) {
//        int removedChars = 0;
//        List<XWPFParagraph> paragraphs = document.getParagraphs();
//
//        for (XWPFParagraph paragraph : paragraphs) {
//            List<XWPFRun> runs = paragraph.getRuns();
//            for (XWPFRun run : runs) {
//                String text = run.getText(0);
//                if (text != null) {
//                    int textLength = text.length();
//                    if (removedChars + textLength > charCount) {
//                        run.setText(text.substring(charCount - removedChars), 0);
//                        return;
//                    } else {
//                        run.setText("", 0);
//                        removedChars += textLength;
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * 溢出第一段落
//     */
//    public static void removeFirstParagraph(XWPFDocument document) {
//        List<XWPFParagraph> paragraphs = document.getParagraphs();
//        if (!paragraphs.isEmpty()) {
//            document.removeBodyElement(document.getPosOfParagraph(paragraphs.get(0)));
//        }
//    }
//
//    /**
//     * 删除临时文件
//     *
//     * @param filePath 目标文件地址
//     */
//    public static void deleteTempFile(String filePath) {
//        File file = new File(filePath);
//        file.delete();
//    }
//
//    /**
//     * 空字符串 就设置为 空格字符
//     */
//    public static Object isNullStr(String str) {
//        if (StringUtils.isEmpty(str)) {
//            str = " ";
//        }
//        return str;
//    }
//
//
//}
