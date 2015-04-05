package com.manishk.webcrawler.persistence;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by manish on 05/04/15.
 */
@Repository("robotsTxtDao")
public class RobotsTxtDaoJdbc extends JdbcDaoSupport implements RobotsTxtDao {

    private final String TABLE = "ROBOTSTXT_DISALLOWED";

    private final String SQL_INSERT = "INSERT INTO "+TABLE+" VALUES (?,?)";

    private final String SQL_SELECT = "SELECT DOMAIN,URI FROM "+TABLE+" WHERE DOMAIN=?";

    private final String SQL_DELETE = "DELETE FROM "+TABLE+" WHERE DOMAIN=?";

    @Autowired
    public RobotsTxtDaoJdbc(DataSource dataSource){
        setDataSource(dataSource);
    }


    @Override
    public void insert(final String domain, final List<String> uri) {
        Preconditions.checkNotNull(domain);
        Preconditions.checkNotNull(uri);
        BatchPreparedStatementSetter bps = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1,domain);
                preparedStatement.setString(2,uri.get(i));
            }

            @Override
            public int getBatchSize() {
                return uri.size();
            }
        };
        getJdbcTemplate().batchUpdate(SQL_INSERT, bps);
    }

    @Override
    public void delete(final String domain) {

        Preconditions.checkNotNull(domain);
        PreparedStatementSetter bps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,domain);
            }
        };
        getJdbcTemplate().update(SQL_DELETE, bps);

    }

    @Override
    public Map<String, List<String>> select(final String domain) {
        Preconditions.checkNotNull(domain);
        PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
              preparedStatement.setString(1,domain);
            }
        };
        Map<String,List<String>> map = new HashMap<String, List<String>>();
        final List<String> uri = new ArrayList<String>();
        map.put(domain,uri);
        getJdbcTemplate().query(SQL_SELECT,ps, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                uri.add(resultSet.getString("URI"));
            }
        });
        return map;
    }
}
