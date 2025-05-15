package com.ruoyi.disk.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.disk.domain.DiskFiles;
import com.ruoyi.disk.service.IDiskFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 网盘文件Controller
 *
 * @author huacai
 * @date 2024-07-09
 */
@RestController
@RequestMapping("/disk/files")
public class DiskFilesController extends BaseController
{
    @Autowired
    private IDiskFilesService diskFilesService;

    /**
     * 查询网盘文件列表 (全部文件 , 视频, 压缩)
     */
    @PreAuthorize("@ss.hasPermi('disk:files:list')")
    @GetMapping("/list")
    public TableDataInfo list(DiskFiles diskFiles)
    {
        startPage();
        List<DiskFiles> list = diskFilesService.selectDiskFilesList(diskFiles);
        return getDataTable(list);
    }

    /**
     * 查询网盘文件列表 (图片)
     */
    @PreAuthorize("@ss.hasPermi('disk:files:list')")
    @GetMapping("/list/image")
    public TableDataInfo listImage(DiskFiles diskFiles)
    {
        startPage();
        List<DiskFiles> list = diskFilesService.selectDiskFilesImageList(diskFiles);
        return getDataTable(list);
    }

    /**
     * 查询网盘文件列表 (文档)
     */
    @PreAuthorize("@ss.hasPermi('disk:files:list')")
    @GetMapping("/list/wd")
    public TableDataInfo listWd(DiskFiles diskFiles) {
        startPage();
        List<DiskFiles> list = diskFilesService.selectDiskFilesWdList(diskFiles);
        return getDataTable(list);
    }

    /**
     * 查询网盘文件列表 (视频)
     */
    @PreAuthorize("@ss.hasPermi('disk:files:list')")
    @GetMapping("/list/mp4")
    public TableDataInfo listMp4(DiskFiles diskFiles) {
        startPage();
        List<DiskFiles> list = diskFilesService.selectDiskFilesMp4List(diskFiles);
        return getDataTable(list);
    }

    /**
     * 查询网盘文件列表 (压缩)
     */
    @PreAuthorize("@ss.hasPermi('disk:files:list')")
    @GetMapping("/list/zip")
    public TableDataInfo listZip(DiskFiles diskFiles) {
        startPage();
        List<DiskFiles> list = diskFilesService.selectDiskFilesZipList(diskFiles);
        return getDataTable(list);
    }

    /**
     * 导出网盘文件列表
     */
    @PreAuthorize("@ss.hasPermi('disk:files:export')")
    @Log(title = "网盘文件", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DiskFiles diskFiles)
    {
        List<DiskFiles> list = diskFilesService.selectDiskFilesList(diskFiles);
        ExcelUtil<DiskFiles> util = new ExcelUtil<DiskFiles>(DiskFiles.class);
        util.exportExcel(response, list, "网盘文件数据");
    }

    /**
     * 下载模板
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<DiskFiles> util = new ExcelUtil<DiskFiles>(DiskFiles.class);
        util.importTemplateExcel(response, "网盘文件数据");
    }

    /**
     * 导入数据
     */
    @Log(title = "网盘文件", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('disk:files:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        ExcelUtil<DiskFiles> util = new ExcelUtil<DiskFiles>(DiskFiles.class);
        InputStream inputStream = file.getInputStream();
        List<DiskFiles> list = util.importExcel(inputStream );
        inputStream.close();
        int count = diskFilesService.batchInsertDiskFiles(list);
        return AjaxResult.success("导入成功" + count + "条信息！");
    }

    /**
     * 获取网盘文件详细信息
     */
    @PreAuthorize("@ss.hasPermi('disk:files:query')")
    @GetMapping(value = "/{filesId}")
    public AjaxResult getInfo(@PathVariable("filesId") Integer filesId)
    {
        return success(diskFilesService.selectDiskFilesByFilesId(filesId));
    }

    /**
     * 新增网盘文件夹
     */
    @PreAuthorize("@ss.hasPermi('disk:files:add')")
    @Log(title = "网盘文件", businessType = BusinessType.INSERT)
    @PostMapping("/addFolder")
    public AjaxResult addFolder(@RequestBody DiskFiles diskFiles) {
        diskFiles.setCreateTime(DateUtils.getNowDate());
        LoginUser loginUser = SecurityUtils.getLoginUser();// 获取当前的用户
        diskFiles.setCreateBy(loginUser.getUsername()); //插入创建者
        diskFiles.setUserId(Math.toIntExact(loginUser.getUserId()));
        diskFiles.setType("folder"); //插入文件类型(后缀)

        return toAjax(diskFilesService.insertDiskFiles(diskFiles));
    }


    /**
     * 新增网盘文件
     */
    @PreAuthorize("@ss.hasPermi('disk:files:add')")
    @Log(title = "网盘文件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DiskFiles diskFiles) {
        diskFiles.setCreateTime(DateUtils.getNowDate());
        String name = diskFiles.getName(); //文件名称
        LoginUser loginUser = SecurityUtils.getLoginUser();// 获取当前的用户
        diskFiles.setCreateBy(loginUser.getUsername()); //插入创建者
        diskFiles.setUserId(Math.toIntExact(loginUser.getUserId()));
        String extName = getFileExtension(name); //文件后缀
        diskFiles.setType(extName); //插入文件类型(后缀)

        return toAjax(diskFilesService.insertDiskFiles(diskFiles));
    }

    // 获取文件的后缀名
    public String getFileExtension(String fileName) {
        File file = new File(fileName);
        String extension = "";
        String name = file.getName();
        int i = name.lastIndexOf('.');
        if (i > 0 && i < name.length() - 1) {
            extension = name.substring(i + 1).toLowerCase();
        }
        return extension;
    }

    /**
     * 修改网盘文件
     */
    @PreAuthorize("@ss.hasPermi('disk:files:edit')")
    @Log(title = "网盘文件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DiskFiles diskFiles)
    {
        return toAjax(diskFilesService.updateDiskFiles(diskFiles));
    }

    /**
     * 删除网盘文件
     */
    @PreAuthorize("@ss.hasPermi('disk:files:remove')")
    @Log(title = "网盘文件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{filesIds}")
    public AjaxResult remove(@PathVariable Integer[] filesIds) {
        return toAjax(diskFilesService.deleteDiskFilesByFilesIds(filesIds));
    }
}
