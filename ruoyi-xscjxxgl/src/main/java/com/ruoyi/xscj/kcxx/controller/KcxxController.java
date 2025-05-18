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

  /**
   * 新增课程信息
   */
  @ApiOperation("新增课程信息")
  @PreAuthorize("@ss.hasPermi('xscj:kcxx:add')")
  @Log(title = "课程信息", businessType = BusinessType.INSERT)
  @PostMapping
  public AjaxResult add(@RequestBody Kcxx kcxx) {
    return toAjax(kcxxService.insertKcxx(kcxx));
  }

  /**
   * 修改课程信息
   */
  @ApiOperation("修改课程信息")
  @PreAuthorize("@ss.hasPermi('xscj:kcxx:edit')")
  @Log(title = "课程信息", businessType = BusinessType.UPDATE)
  @PutMapping
  public AjaxResult edit(@RequestBody Kcxx kcxx) {
    return toAjax(kcxxService.updateKcxx(kcxx));
  }

  /**
   * 删除课程信息
   */
  @ApiOperation("删除课程信息")
  @PreAuthorize("@ss.hasPermi('xscj:kcxx:remove')")
  @Log(title = "课程信息", businessType = BusinessType.DELETE)
  @DeleteMapping("/{kcxxIds}")
  public AjaxResult remove(@PathVariable String[] kcxxIds) {
    return toAjax(kcxxService.deleteKcxxByKcxxIds(kcxxIds));
  }


  /**
   * 下载模板
   */
  @ApiOperation("下载模板")
  @PostMapping("/importTemplate")
  public void importTemplate(HttpServletResponse response) {
    ExcelUtil<Kcxx> util = new ExcelUtil<Kcxx>(Kcxx.class);
    util.importTemplateExcel(response, "课程附件数据");
  }

  /**
   * 导出课程信息列表
   */
  @ApiOperation("导出课程信息列表")
  @PreAuthorize("@ss.hasPermi('xscj:kcxx:export')")
  @Log(title = "课程信息", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  public void export(HttpServletResponse response, Kcxx kcxx) {
    List<Kcxx> list = kcxxService.selectKcxxList(kcxx);
    ExcelUtil<Kcxx> util = new ExcelUtil<Kcxx>(Kcxx.class);
    util.exportExcel(response, list, "课程信息数据");
  }


  /**
   * 获得课程信息主键ID (UUID)
   */
  @ApiOperation("获得课程信息主键ID (UUID)")
  @GetMapping("/getKcXxId")
  public AjaxResult getKcXxId() {
    String getKcXxId = IdUtils.fastSimpleUUID();
    return success(getKcXxId);
  }

  /**
   * 不分页查询课程列表
   * @return
   */
  @ApiOperation("不分页查询课程列表")
  @RequestMapping(value = "/selectKcList", method = RequestMethod.GET)
  public AjaxResult selectKcList() {
    List<Kcxx> list = kcxxService.selectKcList();
    return AjaxResult.success(list);
  }

  /**
   * 不分页查询教师列表
   * @return
   */
  @ApiOperation("不分页查询教师列表")
  @GetMapping("/selectJsList")
  public AjaxResult selectJsList() {
    List<Js> list = kcxxService.selectJsList();
    return AjaxResult.success(list);
  }

  /**
   * 不分页查询学生列表
   * @return
   */
  @ApiOperation("不分页查询学生列表")
  @GetMapping("/selectXsList")
  public AjaxResult selectXsList() {
    List<Js> list = kcxxService.selectXsList();
    return AjaxResult.success(list);
  }

  /**
   * 导入数据
   */
  @ApiOperation("导入数据")
  @Log(title = "课程信息", businessType = BusinessType.IMPORT)
  @PreAuthorize("@ss.hasPermi('xscj:kcxx:import')")
  @PostMapping("/importData")
  public AjaxResult importData(MultipartFile file) throws Exception {
    ExcelUtil<Kcxx> util = new ExcelUtil<Kcxx>(Kcxx.class);
    InputStream inputStream = file.getInputStream();
    List<Kcxx> list = util.importExcel(inputStream);
    inputStream.close();
    int count = kcxxService.batchInsertKcxx(list);
    return AjaxResult.success("导入成功" + count + "条信息！");
  }

  /**
   * 新增附件
   */
  @ApiOperation("新增附件")
  @PostMapping("/add/File/By/subId")
  public AjaxResult insertKcxxFjByKcxxId(@RequestBody KcxxFj kcxxFj) {
    return toAjax(kcxxService.insertKcxxFjByKcxxId(kcxxFj));
  }

  /**
   * 根据ID查询附件列表
   */
  @ApiOperation("根据ID查询附件列表")
  @GetMapping("/get/FileList/By/subId")
  public AjaxResult selectKcxxFjList(String sId) {
    // 去掉 sId 中的 ?lang=zh_CN 部分
    if (sId != null && sId.contains("?")) {
      sId = sId.split("\\?")[0]; // 只保留 ? 前面的部分
    }
    KcxxFj kcxxFj = new KcxxFj();
    kcxxFj.setKcxxId(sId);
    return AjaxResult.success(kcxxService.selectKcxxFjList(kcxxFj));
  }

  /**
   * 删除课程附件
   */
  @ApiOperation("删除课程附件")
  @Log(title = "课程附件", businessType = BusinessType.DELETE)
  @DeleteMapping("/fj/{fjs}")
  public AjaxResult deleteKcxxFjByFjs(@PathVariable Integer[] fjs) {
    return toAjax(kcxxService.deleteKcxxFjByFjs(fjs));
  }
}

