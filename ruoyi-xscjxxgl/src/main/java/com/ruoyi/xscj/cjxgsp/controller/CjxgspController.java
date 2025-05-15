// Todo SJB
package com.ruoyi.xscj.cjxgsp.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xscj.cjlr.domain.Cjlr;
import com.ruoyi.xscj.cjlr.service.ICjlrService;
import com.ruoyi.xscj.cjxgsp.domain.Cjxgsp;
import com.ruoyi.xscj.cjxgsp.service.ICjxgspService;
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
 * 成绩修改审批Controller
 * @author sjba1
 * @date 2025/4/25
 */
@Api("成绩修改审批")
@RestController
@RequestMapping("/xscj/cjxgsp")
public class CjxgspController extends BaseController {
    @Autowired
    private ICjxgspService cjxgspService;

    //TODO: 邵靖彬 成绩修改
    @Autowired
    private ICjlrService cjlrService;

    /**
     * 查询成绩修改审批列表
     */
    @ApiOperation("查询成绩修改审批列表")
    @PreAuthorize("@ss.hasPermi('xscj:cjxgsp:list')")
    @GetMapping("/list")
    public TableDataInfo list(Cjxgsp cjxgsp) {
        startPage();
        List<Cjxgsp> list = cjxgspService.selectCjxgspList(cjxgsp);
        return getDataTable(list);
    }

    /**
     * 导出成绩修改审批列表
     */
    @ApiOperation("导出成绩修改审批列表")
    @PreAuthorize("@ss.hasPermi('xscj:cjxgsp:export')")
    @Log(title = "成绩修改审批", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Cjxgsp cjxgsp) {
        List<Cjxgsp> list = cjxgspService.selectCjxgspList(cjxgsp);
        ExcelUtil<Cjxgsp> util = new ExcelUtil<Cjxgsp>(Cjxgsp.class);
        util.exportExcel(response, list, "成绩修改审批数据");
    }

    /**
     * 下载模板
     */
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Cjxgsp> util = new ExcelUtil<Cjxgsp>(Cjxgsp.class);
        util.importTemplateExcel(response, "成绩修改审批数据");
    }

    /**
     * 导入数据
     */
    @ApiOperation("导入数据")
    @Log(title = "成绩修改审批", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('xscj:cjxgsp:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<Cjxgsp> util = new ExcelUtil<Cjxgsp>(Cjxgsp.class);
        InputStream inputStream = file.getInputStream();
        List<Cjxgsp> list = util.importExcel(inputStream);
        inputStream.close();
        int count = cjxgspService.batchInsertCjxgsp(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取成绩修改审批详细信息
     */
    @ApiOperation("获取成绩修改审批详细信息")
    @PreAuthorize("@ss.hasPermi('xscj:cjxgsp:query')")
    @GetMapping(value = "/{cjxgspId}")
    public AjaxResult getInfo(@PathVariable("cjxgspId") Integer cjxgspId) {
        return success(cjxgspService.selectCjxgspByCjxgspId(cjxgspId));
    }

    /**
     * 新增成绩修改审批
     */
    @ApiOperation("新增成绩修改审批")
    @PreAuthorize("@ss.hasPermi('xscj:cjxgsp:add')")
    @Log(title = "成绩修改审批", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Cjxgsp cjxgsp) {
        return toAjax(cjxgspService.insertCjxgsp(cjxgsp));
    }

    /**
     * 修改成绩修改审批
     */
    @ApiOperation("修改成绩修改审批")
    @PreAuthorize("@ss.hasPermi('xscj:cjxgsp:edit')")
    @Log(title = "成绩修改审批", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Cjxgsp cjxgsp) {
        return toAjax(cjxgspService.updateCjxgsp(cjxgsp));
    }

    /**
     * 删除成绩修改审批
     */
    @ApiOperation("删除成绩修改审批")
    @PreAuthorize("@ss.hasPermi('xscj:cjxgsp:remove')")
    @Log(title = "成绩修改审批", businessType = BusinessType.DELETE)
    @DeleteMapping("/{cjxgspIds}")
    public AjaxResult remove(@PathVariable Integer[] cjxgspIds) {
        return toAjax(cjxgspService.deleteCjxgspByCjxgspIds(cjxgspIds));
    }

    /**
     * 审批通过
     */
    @ApiOperation("审批通过")
    @Log(title = "审批通过", businessType = BusinessType.UPDATE)
    @PutMapping("/updateSpTg")
    public AjaxResult updateSpTg(@RequestBody Cjxgsp cjxgsp) {
        cjxgsp.setSpZt("审批通过");
        System.out.println(cjxgsp);
        Cjlr cjlr = new Cjlr();
        cjlr.setCjlrId(cjxgsp.getCjlrId());
        cjlr.setKcCj(cjxgsp.getXghCj());
        System.out.println(cjlr);
        cjlrService.updateCjlr(cjlr);
        return toAjax(cjxgspService.updateCjxgsp(cjxgsp));
    }

}
