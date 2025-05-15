// Todo LFN
package com.ruoyi.quartz.task;

import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * 定时执行数据库备份
 */
@Component("dataBackupTask")
public class DataBackupTask {

  /**
   * 备份方法
   */
  public void run() {
    // 下面这行只是示例，换成你自己环境的 mysqldump 命令
    String command = "mysqldump -uDB_USER -pDB_PASS DB_NAME > /path/to/backup/db_${date:yyyyMMdd}.sql";
    try {
      Runtime.getRuntime().exec(new String[] { "bash", "-c", command });
      System.out.println("数据库备份完成");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
