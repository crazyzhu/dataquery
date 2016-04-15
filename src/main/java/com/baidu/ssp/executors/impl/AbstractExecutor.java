package com.baidu.ssp.executors.impl;

import com.baidu.ssp.Frame;
import com.baidu.ssp.executors.Executor;
import com.baidu.ssp.querys.Query;
import com.baidu.ssp.tables.Table;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by mojie on 2015/2/10.
 */
public abstract class AbstractExecutor<T extends Query> implements Executor<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Executor.class);
    /**
     * 执行查询，获取结果
     *
     * @param query
     */
    @Override
    public Frame execute(T query) {
        Table table = query.getTable();
        String sql = getSql(query);
        LOGGER.info("generated sql:{}", sql);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = table.getDataSource().getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            return new ResultSet2Frame(table).apply(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtils.closeQuietly(connection, statement, resultSet);
        }
    }

    /**
     * 生成sql
     * */
    public abstract String getSql(T query);
}
