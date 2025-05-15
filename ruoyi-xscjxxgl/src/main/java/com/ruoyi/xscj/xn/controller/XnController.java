// Todo PJX
package com.ruoyi.xscj.xn.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.xscj.xn.domain.Xn;
import com.ruoyi.xscj.xn.service.IXnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 学年Controller
 *
 */
@Api("学年")
@RestController
@RequestMapping("/xscj/xn")
public class XnController extends BaseController {

    @Autowired
    private IXnService xnService;

    /**
     * 查询学年列表
     */
    @ApiOperation("查询学年列表")
    @PreAuthorize("@ss.hasPermi('xscj:xn:list')")
    @GetMapping("/list")
    public TableDataInfo list(Xn xn) {
        startPage();
        List<Xn> list = xnService.selectXnList(xn);
        return getDataTable(list);
    }

    /**
     * 导出学年列表
     */
    @ApiOperation("导出学年列表")
    @PreAuthorize("@ss.hasPermi('xscj:xn:export')")
    @Log(title = "学年", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xn xn) {
        List<Xn> list = xnService.selectXnList(xn);
        ExcelUtil<Xn> util = new ExcelUtil<Xn>(Xn.class);
        util.exportExcel(response, list, "学年数据");
    }

    /**
     * 下载模板
     */
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Xn> util = new ExcelUtil<Xn>(Xn.class);
        util.importTemplateExcel(response, "学年数据");
    }

    /**
     * 导入数据
     */
    @ApiOperation("导入数据")
    @Log(title = "学年", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('xscj:xn:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Xn> util = new ExcelUtil<Xn>(Xn.class);
        InputStream inputStream = file.getInputStream();
        List<Xn> list = util.importExcel(inputStream);
        inputStream.close();
        int count = xnService.batchInsertXn(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取学年详细信息
     */
    @ApiOperation("获取学年详细信息")
    @PreAuthorize("@ss.hasPermi('xscj:xn:query')")
    @GetMapping(value = "/{xnId}")
    public AjaxResult getInfo(@PathVariable("xnId") String xnId) {
        return success(xnService.selectXnByXnId(xnId));
    }

    /**
     * 新增学年
     */
    @ApiOperation("新增学年")
    @PreAuthorize("@ss.hasPermi('xscj:xn:add')")
    @Log(title = "学年", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xn xn) {
        xn.setXnId(IdUtils.fastSimpleUUID());
        return toAjax(xnService.insertXn(xn));
    }

    /**
     * 修改学年
     */
    @ApiOperation("修改学年")
    @PreAuthorize("@ss.hasPermi('xscj:xn:edit')")
    @Log(title = "学年", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xn xn) {
        return toAjax(xnService.updateXn(xn));
    }

    /**
     * 删除学年
     */
    @ApiOperation("删除学年")
    @PreAuthorize("@ss.hasPermi('xscj:xn:remove')")
    @Log(title = "学年", businessType = BusinessType.DELETE)
    @DeleteMapping("/{xnIds}")
    public AjaxResult remove(@PathVariable String[] xnIds) {
        return toAjax(xnService.deleteXnByXnIds(xnIds));
    }
}
