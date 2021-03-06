package com.parasoft.parabank.dao.jdbc;

import java.math.*;
import java.sql.*;
import java.util.*;

import org.slf4j.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;

import com.parasoft.parabank.dao.*;
import com.parasoft.parabank.domain.*;

/*
 * JDBC implementation of AccountDao
 */
public class JdbcAccountDao extends NamedParameterJdbcDaoSupport implements AccountDao {
    private static class AccountMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(final ResultSet rs, final int rowNum) throws SQLException {
            final Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setCustomerId(rs.getInt("customer_id"));
            account.setType(rs.getInt("type"));
            final BigDecimal balance = rs.getBigDecimal("balance");
            account.setBalance(balance == null ? null : balance.setScale(2));
            return account;
        }
    }
    // private NamedParameterJdbcTemplate namedJdbcTemplate;
    //
    // /**
    // * <DL><DT>Description:</DT><DD>
    // * Getter for the namedJdbcTemplate property
    // * </DD>
    // * <DT>Date:</DT><DD>Oct 8, 2015</DD>
    // * </DL>
    // * @return the value of namedJdbcTemplate field
    // */
    // public NamedParameterJdbcTemplate getNamedJdbcTemplate() {
    // return namedJdbcTemplate;
    // }
    //
    // /**
    // * <DL><DT>Description:</DT><DD>
    // * Setter for the namedJdbcTemplate property
    // * </DD>
    // * <DT>Date:</DT><DD>Oct 8, 2015</DD>
    // * </DL>
    // * @param aNamedJdbcTemplate new value for the namedJdbcTemplate property
    // */
    // public void setNamedJdbcTemplate(NamedParameterJdbcTemplate
    // aNamedJdbcTemplate) {
    // namedJdbcTemplate = aNamedJdbcTemplate;
    // }

    private static final Logger log = LoggerFactory.getLogger(JdbcAccountDao.class);

    private JdbcSequenceDao sequenceDao;

    // /** {@inheritDoc} */
    // protected void initTemplateConfig() {
    // DataSource ds = getDataSource();
    // if (ds!=null) {
    // NamedJgetNamedJdbcTemplate();
    // if (getNamedJdbcTemplate()!=null) {
    //
    // }
    // }
    // }

    /*
     * (non-Javadoc)
     *
     * @see com.parasoft.parabank.dao.AccountDao#createAccount(com.parasoft.parabank. domain.Account)
     */
    @Override
    public int createAccount(final Account account) {
        final String SQL =
            "INSERT INTO Account (id, customer_id, type, balance) VALUES (:id, :customerId, :intType, :balance)";

        final int id = sequenceDao.getNextId("Account");
        account.setId(id);
        final BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(account);
        getNamedParameterJdbcTemplate().update(SQL, source);
        // getJdbcTemplate().update(SQL, account.getId(),
        // account.getCustomerId(), account.getIntType(),
        // account.getBalance());
        log.info("Created new account with id = " + id);

        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.parasoft.parabank.dao.AccountDao#getAccount(int)
     */
    @Override
    public Account getAccount(final int id) {
        final String SQL = "SELECT id, customer_id, type, balance FROM Account WHERE id = ?";

        log.info("Getting account object for id = " + id);
        final Account account = getJdbcTemplate().queryForObject(SQL, new AccountMapper(), id);

        return account;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.parasoft.parabank.dao.AccountDao#getAccountsForCustomerId(int)
     */
    @Override
    public List<Account> getAccountsForCustomerId(final int customerId) {
        final String SQL = "SELECT id, customer_id, type, balance FROM Account WHERE customer_id = ?";

        final List<Account> accounts = getJdbcTemplate().query(SQL, new AccountMapper(), customerId);
        log.info("Retrieved " + accounts.size() + " accounts for customerId = " + customerId);

        return accounts;
    }

    public void setSequenceDao(final JdbcSequenceDao sequenceDao) {
        this.sequenceDao = sequenceDao;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.parasoft.parabank.dao.AccountDao#updateAccount(com.parasoft.parabank. domain.Account)
     */
    @Override
    public void updateAccount(final Account account) {
        final String SQL =
            "UPDATE Account SET customer_id = :customerId, type = :intType, balance = :balance WHERE id = :id";

        final BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(account);
        getNamedParameterJdbcTemplate().update(SQL, source);
        // getJdbcTemplate().update(SQL, account.getCustomerId(),
        // account.getIntType(), account.getBalance(),
        // account.getId());
        log.info("Updated information for account with id = " + account.getId());
    }

    //New DELETE API using account id
    //Note: after talking with Brian, usage of the whole account object
    /*
     * (non-Javadoc)
     *
     * @see com.parasoft.parabank.dao.AccountDao#deleteAccount(com.parasoft.parabank. domain.Account)
     */
    @Override
    public boolean deleteAccount(final int id) {
        try {
            final String SQL =
                    "DELETE FROM Account WHERE id = :id";

            final BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(getAccount(id));
            getNamedParameterJdbcTemplate().update(SQL, source);
            // getJdbcTemplate().update(SQL, account.getCustomerId(),
            // account.getIntType(), account.getBalance(),
            // account.getId());
            log.info("Deleted information for account with id = " + id);
            return true;
        } catch (DataAccessException e) {
            System.out.println("No Account to Delete");
            return false;
        }
    }

    //DELETE API using customer id, to eliminate all accounts associated with customer id
    /*
     * (non-Javadoc)
     *
     * @see com.parasoft.parabank.dao.AccountDao#deleteAllAccount(com.parasoft.parabank. domain.Account)
     */
    /*@Override
    public void deleteAllAccounts(final int customerId) {
        final String SQL =
                "DELETE FROM Account WHERE customer_id = :id";

        final BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(getAccount(customerId));
        getNamedParameterJdbcTemplate().update(SQL, source);
        // getJdbcTemplate().update(SQL, account.getCustomerId(),
        // account.getIntType(), account.getBalance(),
        // account.getId());
        log.info("Deleted Account information for account with customer id = " + customerId);
    }*/
}
