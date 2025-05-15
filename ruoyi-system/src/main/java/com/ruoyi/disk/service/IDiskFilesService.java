package com.ruoyi.disk.service;

import com.ruoyi.disk.domain.DiskFiles;

import java.util.List;

/**
 * 网盘文件Service接口
 *
 * @author huacai
 * @date 2024-07-09
 */
public interface IDiskFilesService
{
    /**
     * 查询网盘文件
     *
     * @param filesId 网盘文件主键
     * @return 网盘文件
     */
    public DiskFiles selectDiskFilesByFilesId(Integer filesId);

    /**
     * 查询网盘文件列表
     *
     * @param diskFiles 网盘文件
     * @return 网盘文件集合
     */
    public List<DiskFiles> selectDiskFilesList(DiskFiles diskFiles);


    /**
     * 查询网盘文件列表 (图片)
     * @param diskFiles
     * @return
     */
    List<DiskFiles> selectDiskFilesImageList(DiskFiles diskFiles);

    /**
     * 查询网盘文件列表 (文档)
     * @param diskFiles
     * @return
     */
    List<DiskFiles> selectDiskFilesWdList(DiskFiles diskFiles);


    /**
     * 查询网盘文件列表 (视频)
     * @param diskFiles
     * @return
     */
    List<DiskFiles> selectDiskFilesMp4List(DiskFiles diskFiles);

    /**
     * 查询网盘文件列表 (压缩)
     * @param diskFiles
     * @return
     */
    List<DiskFiles> selectDiskFilesZipList(DiskFiles diskFiles);

    /**
     * 新增网盘文件
     *
     * @param diskFiles 网盘文件
     * @return 结果
     */
    public int insertDiskFiles(DiskFiles diskFiles);

    /**
     * 批量新增网盘文件
     *
     * @param diskFiless 网盘文件List
     * @return 结果
     */
    public int batchInsertDiskFiles(List<DiskFiles> diskFiless);

    /**
     * 修改网盘文件
     *
     * @param diskFiles 网盘文件
     * @return 结果
     */
    public int updateDiskFiles(DiskFiles diskFiles);

    /**
     * 批量删除网盘文件
     *
     * @param filesIds 需要删除的网盘文件主键集合
     * @return 结果
     */
    public int deleteDiskFilesByFilesIds(Integer[] filesIds);

    /**
     * 删除网盘文件信息
     *
     * @param filesId 网盘文件主键
     * @return 结果
     */
    public int deleteDiskFilesByFilesId(Integer filesId);
}
