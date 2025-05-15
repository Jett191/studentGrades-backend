package com.ruoyi.disk.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.disk.domain.DiskFiles;
import com.ruoyi.disk.mapper.DiskFilesMapper;
import com.ruoyi.disk.service.IDiskFilesService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 网盘文件Service业务层处理
 *
 * @author huacai
 * @date 2024-07-09
 */
@Service
public class DiskFilesServiceImpl implements IDiskFilesService
{
    @Resource
    private DiskFilesMapper diskFilesMapper;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 查询网盘文件
     *
     * @param filesId 网盘文件主键
     * @return 网盘文件
     */
    @Override
    public DiskFiles selectDiskFilesByFilesId(Integer filesId)
    {
        return diskFilesMapper.selectDiskFilesByFilesId(filesId);
    }

    /**
     * 查询网盘文件列表
     *
     * @param diskFiles 网盘文件
     * @return 网盘文件
     */
    @Override
    public List<DiskFiles> selectDiskFilesList(DiskFiles diskFiles) {
        return diskFilesMapper.selectDiskFilesList(diskFiles);
    }

    /**
     * 查询网盘文件列表 (图片)
     * @param diskFiles
     * @return
     */
    @Override
    public List<DiskFiles> selectDiskFilesImageList(DiskFiles diskFiles) {
        return diskFilesMapper.selectDiskFilesImageList(diskFiles);
    }

    /**
     * 查询网盘文件列表 (文档)
     * @param diskFiles
     * @return
     */
    @Override
    public List<DiskFiles> selectDiskFilesWdList(DiskFiles diskFiles) {
        return diskFilesMapper.selectDiskFilesWdList(diskFiles);
    }

    /**
     * 查询网盘文件列表 (视频)
     * @param diskFiles
     * @return
     */
    @Override
    public List<DiskFiles> selectDiskFilesMp4List(DiskFiles diskFiles) {
        return diskFilesMapper.selectDiskFilesMp4List(diskFiles);
    }

    /**
     * 查询网盘文件列表 (压缩)
     * @param diskFiles
     * @return
     */
    @Override
    public List<DiskFiles> selectDiskFilesZipList(DiskFiles diskFiles) {
        return diskFilesMapper.selectDiskFilesZipList(diskFiles);
    }

    /**
     * 新增网盘文件
     *
     * @param diskFiles 网盘文件
     * @return 结果
     */
    @Override
    public int insertDiskFiles(DiskFiles diskFiles) {
        return diskFilesMapper.insertDiskFiles(diskFiles);
    }


    /**
     * 批量新增网盘文件
     *
     * @param diskFiless 网盘文件List
     * @return 结果
     */
    @Override
    public int batchInsertDiskFiles(List<DiskFiles> diskFiless)
    {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        int count = 0;
        if (!CollectionUtils.isEmpty(diskFiless)) {
            try {
                for (int i = 0; i < diskFiless.size(); i++) {
                    int row = diskFilesMapper.insertDiskFiles(diskFiless.get(i));
                    // 防止内存溢出，每100次提交一次,并清除缓存
                    boolean bool = (i >0 && i%100 == 0) || i == diskFiless.size() - 1;
                    if (bool){
                        sqlSession.commit();
                        sqlSession.clearCache();
                    }
                    count = i + 1;
                }
            }catch (Exception e){
                e.printStackTrace();
                // 没有提交的数据可以回滚
                sqlSession.rollback();
            }finally {
                sqlSession.close();
                return count;
            }
        }
        return count;
    }

    /**
     * 修改网盘文件
     *
     * @param diskFiles 网盘文件
     * @return 结果
     */
    @Override
    public int updateDiskFiles(DiskFiles diskFiles)
    {
        diskFiles.setUpdateTime(DateUtils.getNowDate());
        return diskFilesMapper.updateDiskFiles(diskFiles);
    }

    /**
     * 批量删除网盘文件
     *
     * @param filesIds 需要删除的网盘文件主键
     * @return 结果
     */
    @Override
    public int deleteDiskFilesByFilesIds(Integer[] filesIds) {
        diskFilesMapper.deleteDiskFilesByfolderIds(filesIds); //删除文件夹下所有的文件夹和文件
        return diskFilesMapper.deleteDiskFilesByFilesIds(filesIds);
    }

    /**
     * 删除网盘文件信息
     *
     * @param filesId 网盘文件主键
     * @return 结果
     */
    @Override
    public int deleteDiskFilesByFilesId(Integer filesId)
    {
        return diskFilesMapper.deleteDiskFilesByFilesId(filesId);
    }
}
