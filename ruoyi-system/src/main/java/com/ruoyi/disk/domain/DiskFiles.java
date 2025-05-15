package com.ruoyi.disk.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 网盘文件对象 disk_files
 *
 * @author huacai
 * @date 2024-07-09
 */
public class DiskFiles extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer filesId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String name;

    /** 是否文件夹 */
    @Excel(name = "是否文件夹")
    private String folder;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String file;

    /** 文件类型 */
    @Excel(name = "文件类型")
    private String type;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String size;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Integer userId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Integer deptId;

    /** 所属文件夹ID */
    @Excel(name = "所属文件夹ID")
    private Integer folderId;

    /** 最外层文件夹ID */
    @Excel(name = "最外层文件夹ID")
    private Integer rootFolderId;

    /** 是否删除 */
    private Integer deletes;

    /** 用户名 */
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFilesId(Integer filesId)
    {
        this.filesId = filesId;
    }

    public Integer getFilesId()
    {
        return filesId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setFolder(String folder)
    {
        this.folder = folder;
    }

    public String getFolder()
    {
        return folder;
    }
    public void setFile(String file)
    {
        this.file = file;
    }

    public String getFile()
    {
        return file;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setSize(String size)
    {
        this.size = size;
    }

    public String getSize()
    {
        return size;
    }
    public void setUserId(Integer userId)
    {
        this.userId = userId;
    }

    public Integer getUserId()
    {
        return userId;
    }
    public void setDeptId(Integer deptId)
    {
        this.deptId = deptId;
    }

    public Integer getDeptId()
    {
        return deptId;
    }
    public void setFolderId(Integer folderId)
    {
        this.folderId = folderId;
    }

    public Integer getFolderId()
    {
        return folderId;
    }
    public void setRootFolderId(Integer rootFolderId)
    {
        this.rootFolderId = rootFolderId;
    }

    public Integer getRootFolderId()
    {
        return rootFolderId;
    }
    public void setDeletes(Integer delete)
    {
        this.deletes = delete;
    }

    public Integer getDeletes()
    {
        return deletes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("filesId", getFilesId())
            .append("name", getName())
            .append("folder", getFolder())
            .append("file", getFile())
            .append("type", getType())
            .append("size", getSize())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("userId", getUserId())
            .append("deptId", getDeptId())
            .append("folderId", getFolderId())
            .append("rootFolderId", getRootFolderId())
            .append("deletes", getDeletes())
            .toString();
    }
}
