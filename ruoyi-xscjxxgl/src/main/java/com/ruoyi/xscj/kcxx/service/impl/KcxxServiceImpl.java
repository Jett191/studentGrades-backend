package com.ruoyi.xscj.kcxx.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.xscj.kcxx.domain.Js;
import com.ruoyi.xscj.kcxx.domain.Kcxx;
import com.ruoyi.xscj.kcxx.domain.KcxxFj;
import com.ruoyi.xscj.kcxx.mapper.KcxxMapper;
import com.ruoyi.xscj.kcxx.service.IKcxxService;
import java.util.Comparator;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.common.utils.SecurityUtils.*;


@Slf4j
@Service
public class KcxxServiceImpl implements IKcxxService {
  @Resource
  private KcxxMapper kcxxMapper;

  @Resource
  private SqlSessionFactory sqlSessionFactory;

  /**
   * 查询课程详细信息
   */
  @Override
  public Kcxx selectKcxxByKcxxId(String kcxxId) {
    if (kcxxId == null || kcxxId.trim().isEmpty()) {
      log.error("课程ID为空，查询失败");
      throw new IllegalArgumentException("课程ID不能为空");
    }

    Kcxx kcxx = kcxxMapper.selectKcxxByKcxxId(kcxxId);

    if (kcxx == null) {
      log.warn("未找到对应的课程，ID={}", kcxxId);
      throw new NoSuchElementException("未找到对应的课程信息，ID=" + kcxxId);
    }

    if (kcxx.getDeleted() != null && kcxx.getDeleted() == 1) {
      log.warn("查询到课程但已被删除，ID={}", kcxxId);
      throw new IllegalStateException("该课程已被删除，ID=" + kcxxId);
    }

    log.info("成功查询课程信息，ID={}", kcxxId);
    return kcxx;
  }

  /**
   * 查询课程信息列表
   */
  @Override
  public List<Kcxx> selectKcxxList(Kcxx kcxx) {
    if (kcxx == null) {
      throw new IllegalArgumentException("查询条件不能为空");
    }

    log.info("开始查询课程信息列表，查询条件：{}", kcxx);

    try {
      if (kcxx.getKcName() != null && !kcxx.getKcName().trim().isEmpty()) {
        kcxx.setKcName(kcxx.getKcName().trim());
      }

      kcxx.setDeleted(0);

      List<Kcxx> list = kcxxMapper.selectKcxxList(kcxx);


      list.sort(Comparator.comparing(Kcxx::getXf, Comparator.nullsLast(Comparator.reverseOrder())));


      list.forEach(item -> {
        if (item.getXf() != null && item.getXf() <= 0) {
          item.setRemark("学分异常，请检查");
        }
      });

      log.info("查询完成，共{}条记录", list.size());
      return list;
    } catch (Exception e) {
      log.error("查询课程列表失败", e);
      throw new RuntimeException("查询课程列表失败，请稍后重试");
    }
  }

  /**
   * 新增课程信息
   */
  @Transactional
  @Override
  public int insertKcxx(Kcxx kcxx) {
    if (kcxx == null) {
      throw new IllegalArgumentException("课程信息不能为空");
    }

    if (kcxx.getKcName() == null || kcxx.getKcName().trim().isEmpty()) {
      throw new IllegalArgumentException("课程名称不能为空");
    }

    if (kcxx.getXf() == null || kcxx.getXf() < 0) {
      throw new IllegalArgumentException("课程学分不能为负数");
    }


    Kcxx exist = kcxxMapper.selectKcxxByKcName(kcxx.getKcName().trim());
    if (exist != null) {
      throw new IllegalStateException("课程名称已存在，请勿重复添加");
    }

    try {
      kcxx.setCreateTime(DateUtils.getNowDate());
      kcxx.setUserId(Math.toIntExact(getUserId()));
      kcxx.setDeptId(Math.toIntExact(getDeptId()));
      kcxx.setCreateBy(getUsername());
      kcxx.setKcxxId(IdUtils.fastSimpleUUID());
      kcxx.setDeleted(0);

      log.info("开始新增课程，课程名：{}", kcxx.getKcName());

      int rows = kcxxMapper.insertKcxx(kcxx);

      if (rows > 0) {
        System.out.println("课程新增成功，课程ID：" + kcxx.getKcxxId());
      } else {
        throw new RuntimeException("插入课程信息失败");
      }

      log.info("课程新增成功，课程ID：{}", kcxx.getKcxxId());

      return rows;
    } catch (Exception e) {
      log.error("新增课程信息失败", e);
      throw new RuntimeException("新增课程信息失败，请稍后重试", e);
    }
  }



  /**
   * 修改课程信息
   */
  @Transactional
  @Override
  public int updateKcxx(Kcxx kcxx) {
    if (kcxx == null || kcxx.getKcxxId() == null || kcxx.getKcxxId().trim().isEmpty()) {
      throw new IllegalArgumentException("课程信息或课程ID不能为空");
    }

    try {
      log.info("开始更新课程信息，课程ID：{}", kcxx.getKcxxId());

      kcxx.setUpdateTime(DateUtils.getNowDate());


      int kcxxRows = kcxxMapper.updateKcxx(kcxx);
      if (kcxxRows == 0) {
        throw new RuntimeException("课程更新失败，未找到对应课程ID：" + kcxx.getKcxxId());
      }

      log.info("课程基本信息更新成功，课程ID：{}", kcxx.getKcxxId());

      return kcxxRows;
    } catch (Exception e) {
      log.error("更新课程信息及附件失败，课程ID：{}", kcxx.getKcxxId(), e);
      throw new RuntimeException("更新课程信息及附件失败，请稍后重试", e);
    }
  }



  /**
   * 删除课程信息信息
   */
  @Transactional
  @Override
  public int deleteKcxxByKcxxId(String kcxxId) {
    if (kcxxId == null || kcxxId.trim().isEmpty()) {
      throw new IllegalArgumentException("课程ID不能为空");
    }

    try {
      log.info("开始删除课程，课程ID：{}", kcxxId);

      int kcxxRows = kcxxMapper.deleteKcxxByKcxxId(kcxxId);
      if (kcxxRows == 0) {
        throw new RuntimeException("课程不存在或已被删除，删除失败");
      }

      log.info("课程逻辑删除成功，课程ID：{}", kcxxId);

      return kcxxRows;
    } catch (Exception e) {
      log.error("删除课程失败，课程ID：{}", kcxxId, e);
      throw new RuntimeException("删除课程失败", e);
    }
  }


  /**
   * 批量删除课程信息
   */
  @Transactional
  @Override
  public int deleteKcxxByKcxxIds(String[] kcxxIds) {
    if (kcxxIds == null || kcxxIds.length == 0) {
      throw new IllegalArgumentException("课程ID数组不能为空");
    }

    try {
      log.info("开始批量逻辑删除课程，课程ID数量：{}", kcxxIds.length);

      int kcxxDelRows = kcxxMapper.deleteKcxxByKcxxIds(kcxxIds);
      if (kcxxDelRows == 0) {
        throw new RuntimeException("课程批量删除失败，未找到对应的课程记录");
      }

      log.info("课程批量逻辑删除完成，删除数量：{}", kcxxDelRows);

      return kcxxDelRows;
    } catch (Exception e) {
      log.error("批量删除课程失败，课程ID集合：{}", (Object) kcxxIds, e);
      throw new RuntimeException("批量删除课程失败，请稍后重试", e);
    }
  }

  /**
   * 批量新增课程信息
   */
  @Override
  public int batchInsertKcxx(List<Kcxx> kcxxs) {
    SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
    int count = 0;
    if (!CollectionUtils.isEmpty(kcxxs)) {
      try {
        for (int i = 0; i < kcxxs.size(); i++) {
          int row = kcxxMapper.insertKcxx(kcxxs.get(i));
          boolean bool = (i > 0 && i % 100 == 0) || i == kcxxs.size() - 1;
          if (bool) {
            sqlSession.commit();
            sqlSession.clearCache();
          }
          count = i + 1;
        }
      } catch (Exception e) {
        e.printStackTrace();
        sqlSession.rollback();
      } finally {
        sqlSession.close();
        return count;
      }
    }
    return count;
  }



  /**
   * 不分页查询课程列表
   * @return
   */
  @Override
  public List<Kcxx> selectKcList() {
    return kcxxMapper.selectKcList();
  }

  /**
   * 不分页查询教师列表
   * @return
   */
  @Override
  public List<Js> selectJsList() {
    return kcxxMapper.selectJsList();
  }

  /**
   * 不分页查询学生列表
   * @return
   */
  @Override
  public List<Js> selectXsList() {
    return kcxxMapper.selectXsList();
  }

  /**
   * 新增附件
   * @param kcxxFj
   * @return
   */
  @Override
  public int insertKcxxFjByKcxxId(KcxxFj kcxxFj) {
    return kcxxMapper.insertKcxxFjByKcxxId(kcxxFj);
  }

  /**
   * 根据ID查询附件列表
   * @param kcxxFj
   * @return
   */
  @Override
  public List<KcxxFj> selectKcxxFjList(KcxxFj kcxxFj) {
    System.out.println(kcxxFj);
    return kcxxMapper.selectKcxxFjList(kcxxFj);
  }

  /**
   * 删除课程附件
   * @param fjs
   * @return
   */
  @Override
  public int deleteKcxxFjByFjs(Integer[] fjs) {
    return kcxxMapper.deleteKcxxFjByFjs(fjs);
  }


}
