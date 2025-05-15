package com.ruoyi.xscj.kcxx.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.xscj.kcxx.domain.Js;
import com.ruoyi.xscj.kcxx.domain.Kcxx;
import com.ruoyi.xscj.kcxx.domain.KcxxFj;
import com.ruoyi.xscj.kcxx.service.IKcxxService;
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
 * 课程信息Controller
 *
 */
@RestController
@RequestMapping("/xscj/kcxx")
@Api("课程信息")
public class KcxxController extends BaseController {


  @Resource
  private IKcxxService kcxxService;

  /**
   * 查询课程信息列表
   */
  @ApiOperation("查询课程信息列表")
  @PreAuthorize("@ss.hasPermi('xscj:kcxx:list')")
  @GetMapping("/list")
  public TableDataInfo list(Kcxx kcxx) {
    startPage();
    List<Kcxx> list = kcxxService.selectKcxxList(kcxx);
    return getDataTable(list);
  }

  /**
   * 获取课程信息详细信息
   */
  @ApiOperation("获取课程信息详细信息")
  @PreAuthorize("@ss.hasPermi('xscj:kcxx:query')")
  @GetMapping(value = "/{kcxxId}")
  public AjaxResult getInfo(@PathVariable("kcxxId") String kcxxId) {
    return success(kcxxService.selectKcxxByKcxxId(kcxxId));
  }

}

