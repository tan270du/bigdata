package cn.trasen.chengying.service.impl;

import cn.trasen.chengying.entity.RequestServiceInfoQuery;
import cn.trasen.chengying.entity.singleData.SQLInfo;
import cn.trasen.chengying.entity.singleData.ServiceInfo;
import cn.trasen.chengying.service.ReadDocument;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;



/**
 * @author rq
 */
@Slf4j
@Service
public class ReadDocumentImpl implements ReadDocument {
    @Autowired
    private  MongoTemplate mongoTemp;

    private static final String ARGUMENTS = "Argument";
    private static final String RESULTS = "Result";

    @Override
    public String findByName( RequestServiceInfoQuery request ) {
        log.info("请求参数 request = {} ", request);
        Query query = getQuery(request);
        log.info("查询体返回 query = {} ", query);
        ServiceInfo serviceInfo = mongoTemp.findOne(query, ServiceInfo.class);
        if (serviceInfo == null) {
            log.warn("查询无服务信息记录!");
            return null;
        }
        return getServiceResults(request.getDocumentType(),serviceInfo);
    }

    private Query getQuery(RequestServiceInfoQuery request) {
        Query queryInfo = new Query();
        queryInfo.addCriteria(Criteria.where("Service.id").is(request.getServiceName())
                .and("Interface.id").is(request.getInterfaceName()));
        return queryInfo;
    }

    private String getServiceResults(String documentType, ServiceInfo serviceInfo) {
        if (ARGUMENTS.equals(documentType)) {
            return serviceInfo.getArguments();
        }
        if (RESULTS.equals(documentType)) {
            return serviceInfo.getResults();
        }
        return null;
    }

    private String getSqlResults(String documentType, SQLInfo serviceInfo) {
        if (ARGUMENTS.equals(documentType)) {
            return serviceInfo.getArguments();
        }
        if (RESULTS.equals(documentType)) {
            return serviceInfo.getResults();
        }
        return null;
    }

    @Override
    public String readSql(RequestServiceInfoQuery request ) {
        log.info("请求SQL参数 request = {} ", request);
        Query query = getQuery(request);
        log.info("查询体返回 query = {} ", query);
        SQLInfo sqlInfo = mongoTemp.findOne(query, SQLInfo.class);
        if (sqlInfo == null) {
            log.warn("查询无服务信息记录!");
            return null;
        }
        return getSqlResults(request.getDocumentType(),sqlInfo);
    }
}





