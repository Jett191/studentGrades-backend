package com.ruoyi.infoQingJia.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.infoRunTaskData.domain.InfoRunTaskData;
import com.ruoyi.infoRunTaskData.service.IInfoRunTaskDataService;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.ruoyi.infoQingJia.domain.InfoQingJia;
import com.ruoyi.infoQingJia.service.IInfoQingJiaService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 请假Controller
 *
 * @author ruoyi
 * @date 2024-02-22
 */
@RestController
@RequestMapping("/infoQingJia/infoQingJia")
public class InfoQingJiaController extends BaseController {
    @Resource
    private IInfoQingJiaService infoQingJiaService;

    @Resource
    private IInfoRunTaskDataService iInfoRunTaskDataService;

    /**
     * 查询请假列表
     */
    @PreAuthorize("@ss.hasPermi('infoQingJia:infoQingJia:list')")
    @GetMapping("/list")
    public TableDataInfo list(InfoQingJia infoQingJia) {
        startPage();
        List<InfoQingJia> list = infoQingJiaService.selectInfoQingJiaList(infoQingJia);
        return getDataTable(list);
    }

    /**
     * 导出请假列表
     */
    @PreAuthorize("@ss.hasPermi('infoQingJia:infoQingJia:export')")
    @Log(title = "请假", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InfoQingJia infoQingJia) {
        List<InfoQingJia> list = infoQingJiaService.selectInfoQingJiaList(infoQingJia);
        ExcelUtil<InfoQingJia> util = new ExcelUtil<InfoQingJia>(InfoQingJia.class);
        util.exportExcel(response, list, "请假数据");
    }

    /**
     * 获取请假详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(infoQingJiaService.selectInfoQingJiaById(id));
    }

    /**
     * 新增请假
     */
    @PreAuthorize("@ss.hasPermi('infoQingJia:infoQingJia:add')")
    @Log(title = "请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InfoQingJia infoQingJia) {
        infoQingJia.setUserId(getUserId());
        int i = infoQingJiaService.insertInfoQingJia(infoQingJia);
        //向流程业务表中加入数据
        InfoRunTaskData infoRunTaskData = new InfoRunTaskData();
        infoRunTaskData.setBusinessKey(String.valueOf(infoQingJia.getId()));
        infoRunTaskData.setBusinessType(1);
        infoRunTaskData.setBusinessTitle(infoQingJia.getQingJiaTitle());
        infoRunTaskData.setApplyUser(SecurityUtils.getLoginUser().getUser().getNickName());//申请人

        iInfoRunTaskDataService.insertInfoRunTaskData(infoRunTaskData);

        return toAjax(i);
    }

    /**
     * 修改请假
     */
    @PreAuthorize("@ss.hasPermi('infoQingJia:infoQingJia:edit')")
    @Log(title = "请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InfoQingJia infoQingJia) {

        //修改业务中间表关系数据
        InfoRunTaskData infoRunTaskData = new InfoRunTaskData();
        infoRunTaskData.setBusinessTitle(infoQingJia.getQingJiaTitle());
        infoRunTaskData.setBusinessKey(String.valueOf(infoQingJia.getId()));
        infoRunTaskData.setBusinessType(1);
        iInfoRunTaskDataService.updateInfoRunTaskData(infoRunTaskData);

        return toAjax(infoQingJiaService.updateInfoQingJia(infoQingJia));
    }

    /**
     * 删除请假
     */
    @PreAuthorize("@ss.hasPermi('infoQingJia:infoQingJia:remove')")
    @Log(title = "请假", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        //同时删除业务关系数据
        for (Long id : ids) {
            InfoQingJia infoQingJia = infoQingJiaService.selectInfoQingJiaById(id);
            iInfoRunTaskDataService.deleteData(String.valueOf(infoQingJia.getId()), 1);
        }
        return toAjax(infoQingJiaService.deleteInfoQingJiaByIds(ids));
    }
}
