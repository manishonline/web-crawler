package com.manishk.webcrawler.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by manish on 05/04/15.
 */
@Repository("lastVisitedDao")
public class LastVisitedDaoJdbc extends JdbcDaoSupport implements LastVisitedDao {
    private final String TABLE = "DOMAIN_LASTVISITED";

    private final String SQL_INSERT = "INSERT INTO "+TABLE+"(LASTVISITED,DOMAIN) VALUES (?,?)";

    private final String SQL_SELECT = "SELECT LASTVISITED FROM "+TABLE+" WHERE DOMAIN=?";

    private final String SQL_UPDATE = "UPDATE "+TABLE+" SET LASTVISITED=? WHERE DOMAIN=?";

    private final String SQL_SELECT_REVISIT = "SELECT DOMAIN FROM "+TABLE+" WHERE LASTVISITED < ? ";

    @Autowired
    public LastVisitedDaoJdbc(DataSource dataSource){
        setDataSource(dataSource);
    }

    @Override
    public void insertOrUpdate(final String domain, final Date date) {
        PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
                preparedStatement.setString(2,domain);

            }
        };
        Date existing = select(domain);
        String sql;
        if(existing==null)
            sql = SQL_INSERT;
                    else
            sql = SQL_UPDATE;
        getJdbcTemplate().update(sql,ps);
    }

    @Override
    public Date select(final String domain) {
        PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1,domain);
            }
        };
        List<Date> dateList= getJdbcTemplate().query(SQL_SELECT,ps,new RowMapper<Date>() {
            @Override
            public Date mapRow(ResultSet resultSet, int i) throws SQLException {
                Timestamp ts = resultSet.getTimestamp("LASTVISITED");
                if(ts == null)
                    return null;
                return new Date(ts.getTime());
            }
        });
        if (dateList!=null && !dateList.isEmpty())
            return dateList.get(0);
        else
            return null;
    }

    @Override
    public List<String> select(final Date dateAfter) {
        PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setTimestamp(1,new Timestamp(dateAfter.getTime()));
            }
        };
        final List<String> list = new ArrayList<String>();
         getJdbcTemplate().query(SQL_SELECT_REVISIT,ps,new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                list.add(resultSet.getString("DOMAIN"));
            }
        });
        return list;
    }
}
