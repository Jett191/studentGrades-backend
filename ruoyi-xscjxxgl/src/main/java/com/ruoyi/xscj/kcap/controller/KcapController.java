// Todo XSJ
package com.ruoyi.xscj.kcap.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xscj.kcap.domain.Kcap;
import com.ruoyi.xscj.kcap.mapper.KcapMapper;
import com.ruoyi.xscj.kcap.service.IKcapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
/**
 * 课程安排Controller
 *
 */
@RestController
@RequestMapping("/xscj/kcap")
@Api("课程安排")
public class KcapController extends BaseController {
    //TODO: 向世杰 课程安排管理
    @Resource
    private IKcapService kcapService;

    @Resource
    private KcapMapper kcapMapper;

    /**
     * 查询课程安排列表
     */
    //TODO: 向世杰 课程安排管理
    @ApiOperation("查询课程安排列表")
    @PreAuthorize("@ss.hasPermi('xscj:kcap:list')")
    @GetMapping("/list")
    public TableDataInfo list(Kcap kcap) {
        startPage();
        List<Kcap> list = kcapService.selectKcapList(kcap);
        return getDataTable(list);
    }

    /**
     * 导出课程安排列表
     */
    //TODO: 向世杰 课程安排管理
    @ApiOperation("导出课程安排列表")
    @PreAuthorize("@ss.hasPermi('xscj:kcap:export')")
    @Log(title = "课程安排", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Kcap kcap) {
        List<Kcap> list = kcapService.selectKcapList(kcap);
        ExcelUtil<Kcap> util = new ExcelUtil<Kcap>(Kcap.class);
        util.exportExcel(response, list, "课程安排数据");
    }

    /**
     * 下载模板
     */
    //TODO: 向世杰 课程安排管理
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Kcap> util = new ExcelUtil<Kcap>(Kcap.class);
        util.importTemplateExcel(response, "课程安排数据");
    }

    /**
     * 导入数据
     */
    //TODO: 向世杰 课程安排管理
    @ApiOperation("导入数据")
    @Log(title = "课程安排", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('xscj:kcap:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Kcap> util = new ExcelUtil<Kcap>(Kcap.class);
        InputStream inputStream = file.getInputStream();
        List<Kcap> list = util.importExcel(inputStream);
        inputStream.close();
        int count = kcapService.batchInsertKcap(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取课程安排详细信息
     */
    //TODO: 向世杰 课程安排管理
    @ApiOperation("获取课程安排详细信息")
    @PreAuthorize("@ss.hasPermi('xscj:kcap:query')")
    @GetMapping(value = "/{kcapId}")
    public AjaxResult getInfo(@PathVariable("kcapId") String kcapId) {
        return success(kcapService.selectKcapByKcapId(kcapId));
    }
}