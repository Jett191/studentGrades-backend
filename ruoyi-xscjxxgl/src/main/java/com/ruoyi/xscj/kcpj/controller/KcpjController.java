// Todo SJB
package com.ruoyi.xscj.kcpj.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.xscj.kcpj.service.IKcpjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.xscj.kcpj.domain.Kcpj;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 课程评价Controller
 *
 */
@RestController
@RequestMapping("/xscj/kcpj")
@Api("课程评价")
public class KcpjController extends BaseController {
    @Autowired
    private IKcpjService kcpjService;
    //TODO: 邵靖彬 课程评价管理
    /**
     * 查询课程评价列表
     */
    @ApiOperation("查询课程评价列表")
    @PreAuthorize("@ss.hasPermi('xscj:kcpj:list')")
    @GetMapping("/list")
    public TableDataInfo list(Kcpj kcpj) {
        startPage();
        List<Kcpj> list = kcpjService.selectKcpjList(kcpj);
        return getDataTable(list);
    }

    /**
     * 导出课程评价列表
     */
    @ApiOperation("导出课程评价列表")
    @PreAuthorize("@ss.hasPermi('xscj:kcpj:export')")
    @Log(title = "课程评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Kcpj kcpj) {
        List<Kcpj> list = kcpjService.selectKcpjList(kcpj);
        ExcelUtil<Kcpj> util = new ExcelUtil<Kcpj>(Kcpj.class);
        util.exportExcel(response, list, "课程评价数据");
    }

    /**
     * 下载模板
     */
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Kcpj> util = new ExcelUtil<Kcpj>(Kcpj.class);
        util.importTemplateExcel(response, "课程评价数据");
    }

    /**
     * 导入数据
     */
    @ApiOperation("导入数据")
    @Log(title = "课程评价", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('xscj:kcpj:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Kcpj> util = new ExcelUtil<Kcpj>(Kcpj.class);
        InputStream inputStream = file.getInputStream();
        List<Kcpj> list = util.importExcel(inputStream);
        inputStream.close();
        int count = kcpjService.batchInsertKcpj(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取课程评价详细信息
     */
    @ApiOperation("获取课程评价详细信息")
    @PreAuthorize("@ss.hasPermi('xscj:kcpj:query')")
    @GetMapping(value = "/{kcpjId}")
    public AjaxResult getInfo(@PathVariable("kcpjId") String kcpjId) {
        return success(kcpjService.selectKcpjByKcpjId(kcpjId));
    }

    /**
     * 新增课程评价
     */
    @ApiOperation("新增课程评价")
    @PreAuthorize("@ss.hasPermi('xscj:kcpj:add')")
    @Log(title = "课程评价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Kcpj kcpj) {
        return toAjax(kcpjService.insertKcpj(kcpj));
    }

    /**
     * 修改课程评价
     */
    @ApiOperation("修改课程评价")
    @PreAuthorize("@ss.hasPermi('xscj:kcpj:edit')")
    @Log(title = "课程评价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Kcpj kcpj) {
        return toAjax(kcpjService.updateKcpj(kcpj));
    }

    /**
     * 删除课程评价
     */
    @ApiOperation("删除课程评价")
    @PreAuthorize("@ss.hasPermi('xscj:kcpj:remove')")
    @Log(title = "课程评价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{kcpjIds}")
    public AjaxResult remove(@PathVariable String[] kcpjIds) {
        return toAjax(kcpjService.deleteKcpjByKcpjIds(kcpjIds));
    }
}
