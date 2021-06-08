package com.example.chengying.service.dataList;

import com.example.chengying.entity.dataList.InputReadList;
import com.example.chengying.entity.dataList.ReturnReadServiceList;
import com.example.chengying.entity.dataList.ReturnSqlCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author rq
 */
@Slf4j
@Service
public class ReadListImpl implements ReadList {
    private final MongoTemplate mongoTemp;

    private static final String SERVICE = "Service";
    private static final String INTERFACE = "Interface";

    public ReadListImpl(MongoTemplate mongoTemp) {
        this.mongoTemp = mongoTemp;
    }

    @Override
    public String readServiceList( InputReadList request ) {
        log.info("请求参数 request = {} ", request);
        Query query = getQuery(request);
        log.info("查询体返回 query = {} ", query);
        ReturnReadServiceList returnReadServiceList = mongoTemp.findOne(query, ReturnReadServiceList.class);
        if (returnReadServiceList == null) {
            log.warn("查询无服务信息记录!");
            return null;
        }
        return getResults(request.getRepositoryType(),returnReadServiceList);
    }

    private Query getQuery(InputReadList request) {
        Query queryInfo = new Query();
        queryInfo.addCriteria(Criteria.where("Service.id").is(request.getServiceName())
                .and("Interface.id").is(request.getInterfaceName()));
        return queryInfo;
    }

    private String getResults(String documentType, ReturnReadServiceList returnReadServiceList) {
        if (SERVICE.equals(documentType)) {
            return returnReadServiceList.getCommand() + returnReadServiceList.getServiceName();
        }
        if (INTERFACE.equals(documentType)) {
            return returnReadServiceList.getCommand() + returnReadServiceList.getInterfaceName();
        }
        return null;
    }

    @Override
    public ReturnSqlCommand readSqlList(InputReadList request) {
        log.info("请求SQL参数 request = {} ", request);
        ReturnSqlCommand returnSqlCommand = new ReturnSqlCommand();
        Query query = getQuery(request);
        log.info("查询体返回 query = {} ", query);
        ReturnReadServiceList returnReadServiceList = mongoTemp.findOne(query, ReturnReadServiceList.class);
        if (returnReadServiceList == null) {
            log.warn("查询无服务信息记录!");
            return null;
        }
        returnSqlCommand.setCommand(returnReadServiceList.getCommand());
        returnSqlCommand.setService(returnReadServiceList.getServiceName());
        return returnSqlCommand;
    }
}
