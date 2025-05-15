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

}
