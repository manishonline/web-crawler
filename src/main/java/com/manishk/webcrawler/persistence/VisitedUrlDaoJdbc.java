package com.manishk.webcrawler.persistence;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by manish on 05/04/15.
 */

@Repository("visitedURLDao")
public class VisitedUrlDaoJdbc extends JdbcDaoSupport implements VisitedUrlDao{

    private final String TABLE = "VISITEDURL";

    private final String SQL_INSERT = "INSERT INTO "+TABLE+" VALUES (?,?)";

    private final String SQL_SELECT = "SELECT DOMAIN, URI FROM "+TABLE+" WHERE DOMAIN=? AND URI=?";

    private final String SQL_DELETE = "DELETE FROM "+TABLE+" WHERE DOMAIN=?";

    @Autowired
    public VisitedUrlDaoJdbc(DataSource dataSource){
        setDataSource(dataSource);
    }

    @Override
    public void insert(final String domain, final String uri) {
        Preconditions.checkNotNull(domain);
        Preconditions.checkNotNull(uri);
        final PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,domain.hashCode());
                preparedStatement.setInt(2,uri.hashCode());
            }
        };
        getJdbcTemplate().update(SQL_INSERT,ps);
    }

    @Override
    public Integer select(final String domain, final String uri) {
        Preconditions.checkNotNull(domain);
        Preconditions.checkNotNull(uri);
        final PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,domain.hashCode());
                preparedStatement.setInt(2,uri.hashCode());
            }
        };
        List<Integer> list =  getJdbcTemplate().query(SQL_SELECT,ps, new RowMapper<Integer>() {
            Integer hash = null;
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                hash = resultSet.getInt("DOMAIN");
                return hash;
            }
        });
        return !list.isEmpty()?list.get(0):null;
    }

    @Override
    public void delete(final String domain) {
        Preconditions.checkNotNull(domain);
        final PreparedStatementSetter ps = new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setInt(1,domain.hashCode());
            }
        };
        getJdbcTemplate().update(SQL_DELETE,ps);

    }
}
