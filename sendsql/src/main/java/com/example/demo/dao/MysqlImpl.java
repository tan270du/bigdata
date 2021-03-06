package com.example.demo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.UUID;
import static com.example.demo.CurrentTime.currentTime;
import static com.example.demo.WriteMysqlController.mobile;


/**
 * @author rq
 */
@Slf4j
@Repository
@Service
public class MysqlImpl implements Mysql{

    private final JdbcTemplate jdbcTemplate;

    MysqlImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String save(String content) {
        String time = currentTime();
        String uuidStr=UUID.randomUUID().toString().replace("-", "");
        log.info("uuidStr: {}", uuidStr);
        String sql = "INSERT INTO `tbl_sms_outbox` " +
                "(`sismsid`,`extcode`, `destaddr`, `messagecontent`,`reqdeliveryreport`, `requesttime`,`mac`,`msgstatus`,`flag`) " +
                "VALUE(?,'NHYYGJ',?,?,1,?,'172.25.101.100',0,0)";
        int ans = jdbcTemplate.update(sql,uuidStr,mobile,content,time);
        log.info("ans: {}", ans);
        return "success";
    }
}
