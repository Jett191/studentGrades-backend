// Todo PJX
// Todo WJS
package com.ruoyi.xscj.jbxx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.xscj.jbxx.domain.Xsjbxx;
import com.ruoyi.xscj.jbxx.service.IXsjbxxService;
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
 * 学生基本信息Controller
 *
 * @date 2025/4/19
 */
@RestController
@RequestMapping("/xscj/xsjbxx")
@Api("学生基本信息")
public class XsjbxxController extends BaseController {
    @Autowired
    private IXsjbxxService xsjbxxService;

    //TODO: 彭靖旭 学生基本信息查询

    /**
     * 查询学生基本信息列表
     */
    @ApiOperation("查询学生基本信息列表")
    @PreAuthorize("@ss.hasPermi('xscj:xsjbxx:list')")
    @GetMapping("/list")
    public TableDataInfo list(Xsjbxx xsjbxx) {
        startPage();
        List<Xsjbxx> list = xsjbxxService.selectXsjbxxList(xsjbxx);
        return getDataTable(list);
    }
//TODO: 王家盛 批量导入/导出
    /**
     * 导出学生基本信息列表
     */
    @ApiOperation("导出学生基本信息列表")
    @PreAuthorize("@ss.hasPermi('xscj:xsjbxx:export')")
    @Log(title = "学生基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Xsjbxx xsjbxx) {
        List<Xsjbxx> list = xsjbxxService.selectXsjbxxList(xsjbxx);
        ExcelUtil<Xsjbxx> util = new ExcelUtil<Xsjbxx>(Xsjbxx.class);
        util.exportExcel(response, list, "学生基本信息数据");
    }

    /**
     * 下载模板
     */
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Xsjbxx> util = new ExcelUtil<Xsjbxx>(Xsjbxx.class);
        util.importTemplateExcel(response, "学生基本信息数据");
    }

    /**
     * 导入数据
     */
    @ApiOperation("导入数据")
    @Log(title = "学生基本信息", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('xscj:xsjbxx:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Xsjbxx> util = new ExcelUtil<Xsjbxx>(Xsjbxx.class);
        InputStream inputStream = file.getInputStream();
        List<Xsjbxx> list = util.importExcel(inputStream);
        // 对导入的数据进行处理
        for (Xsjbxx xsjbxx : list) {
            xsjbxx.setXsjbxxId(IdUtils.fastSimpleUUID());
        }
        inputStream.close();
        int count = xsjbxxService.batchInsertXsjbxx(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    //TODO 王家盛 学籍状态管理
    //TODO: 彭靖旭 学生基本信息更改

    /**
     * 获取学生基本信息详细信息
     */
    @ApiOperation("获取学生基本信息详细信息")
    @PreAuthorize("@ss.hasPermi('xscj:xsjbxx:query')")
    @GetMapping(value = "/{xsjbxxId}")
    public AjaxResult getInfo(@PathVariable("xsjbxxId") String xsjbxxId) {
        return success(xsjbxxService.selectXsjbxxByXsjbxxId(xsjbxxId));
    }
    /**
     * 新增学生基本信息
     */
    @ApiOperation("新增学生基本信息")
    @PreAuthorize("@ss.hasPermi('xscj:xsjbxx:add')")
    @Log(title = "学生基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Xsjbxx xsjbxx) {
        return toAjax(xsjbxxService.insertXsjbxx(xsjbxx));
    }

    /**
     * 修改学生基本信息
     */
    @ApiOperation("修改学生基本信息")
    @PreAuthorize("@ss.hasPermi('xscj:xsjbxx:edit')")
    @Log(title = "学生基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Xsjbxx xsjbxx) {
        return toAjax(xsjbxxService.updateXsjbxx(xsjbxx));
    }

    /**
     * 删除学生基本信息
     */
    @ApiOperation("删除学生基本信息")
    @PreAuthorize("@ss.hasPermi('xscj:xsjbxx:remove')")
    @Log(title = "学生基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{xsjbxxIds}")
    public AjaxResult remove(@PathVariable String[] xsjbxxIds) {
        return toAjax(xsjbxxService.deleteXsjbxxByXsjbxxIds(xsjbxxIds));
    }
}
