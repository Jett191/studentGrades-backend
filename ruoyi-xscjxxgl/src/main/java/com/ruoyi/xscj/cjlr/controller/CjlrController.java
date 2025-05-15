// Todo WJS
// Todo LJY
// Todo SJB

package com.ruoyi.xscj.cjlr.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.xscj.cjlr.domain.BjCj;
import com.ruoyi.xscj.cjlr.domain.CjTj;
import com.ruoyi.xscj.cjlr.domain.Cjlr;
import com.ruoyi.xscj.cjlr.domain.Gpa;
import com.ruoyi.xscj.cjlr.service.ICjlrService;
import com.ruoyi.xscj.cjxgsp.domain.Cjxgsp;
import com.ruoyi.xscj.cjxgsp.service.ICjxgspService;
import com.ruoyi.xscj.kcxx.domain.Js;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * 成绩录入Controller
 *
 */
@Slf4j
@Api("成绩录入")
@RestController
@RequestMapping("/xscj/cjlr")
public class CjlrController extends BaseController {


    @Autowired
    private ICjlrService cjlrService;

    @Autowired
    private ICjxgspService cjxgspService;


    /**
     * 查询成绩录入列表
     */
    @ApiOperation("查询成绩录入列表")
    @PreAuthorize("@ss.hasPermi('xscj:cjlr:list')")
    @GetMapping("/list")
    public TableDataInfo list(Cjlr cjlr) {
        startPage();
        List<Cjlr> list = cjlrService.selectCjlrList(cjlr);
        return getDataTable(list);
    }

    /**
     * 导出成绩录入列表
     */
    @ApiOperation("导出成绩录入列表")
    @PreAuthorize("@ss.hasPermi('xscj:cjlr:export')")
    @Log(title = "成绩录入", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Cjlr cjlr) {
        List<Cjlr> list = cjlrService.selectCjlrList(cjlr);
        ExcelUtil<Cjlr> util = new ExcelUtil<Cjlr>(Cjlr.class);
        util.exportExcel(response, list, "成绩录入数据");
    }

    /**
     * 下载模板
     */
    @ApiOperation("下载模板")
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) {
        ExcelUtil<Cjlr> util = new ExcelUtil<Cjlr>(Cjlr.class);
        util.importTemplateExcel(response, "成绩录入数据");
    }


    /**
     * 获取成绩录入详细信息
     */
    @ApiOperation("获取成绩录入详细信息")
    @PreAuthorize("@ss.hasPermi('xscj:cjlr:query')")
    @GetMapping(value = "/{cjlrId}")
    public AjaxResult getInfo(@PathVariable("cjlrId") String cjlrId) {
        return success(cjlrService.selectCjlrByCjlrId(cjlrId));
    }

    /**
     * 新增成绩录入
     */
    @ApiOperation("新增成绩录入")
    @PreAuthorize("@ss.hasPermi('xscj:cjlr:add')")
    @Log(title = "成绩录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Cjlr cjlr) {
        cjlr.setUserId(Math.toIntExact(getUserId())); //用户ID
        cjlr.setDeptId(Math.toIntExact(getDeptId())); //部门ID
        cjlr.setCreateBy(getUsername()); //创建人
        cjlr.setCjlrId(IdUtils.fastSimpleUUID());
        return toAjax(cjlrService.insertCjlr(cjlr));
    }

    /**
     * 修改成绩录入
     */
    @ApiOperation("修改成绩录入")
    @PreAuthorize("@ss.hasPermi('xscj:cjlr:edit')")
    @Log(title = "成绩录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Cjlr cjlr) {
        return toAjax(cjlrService.updateCjlr(cjlr));
    }

}