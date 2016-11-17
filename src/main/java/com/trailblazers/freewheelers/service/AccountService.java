package com.trailblazers.freewheelers.service;

import com.trailblazers.freewheelers.exceptions.CannotCreateAccountException;
import com.trailblazers.freewheelers.mappers.AccountMapper;
import com.trailblazers.freewheelers.mappers.AccountRoleMapper;
import com.trailblazers.freewheelers.mappers.MyBatisUtil;
import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.AccountRole;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";

    private final AccountRoleMapper accountRoleMapper;
    private SqlSession sqlSession;
    private AccountMapper accountMapper;

    public AccountService() {
        this(MyBatisUtil.getSqlSessionFactory().openSession());
    }

    public AccountService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
        this.accountMapper = sqlSession.getMapper(AccountMapper.class);
        this.accountRoleMapper = sqlSession.getMapper(AccountRoleMapper.class);
    }

    public List<Account> findAll() {
        return accountMapper.findAll();
    }

    public Account getAccountByName(String userName) {
        return accountMapper.getByName(userName);
    }

    public Account get(Long account_id) {
        return accountMapper.getById(account_id);
    }

    public void delete(Account account) {
        accountMapper.delete(account);
        sqlSession.commit();
    }

    public void createAdmin(Account account) {
        create(account, ADMIN);
    }

    public Account createAccount(Account account) {
        create(account, USER);
        return account;
    }

    private void create(Account account, String role) {
        try {
            accountMapper.insert(account);
            accountRoleMapper.insert(roleFor(account, role));
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            throw new CannotCreateAccountException();
        }
    }

    private AccountRole roleFor(Account account, String role) {
        return new AccountRole()
                .setAccount_name(account.getAccount_name())
                .setRole(role);
    }

    public int getNumberOfExistingAccountsByName(String accountName) {
        return accountMapper.getCountOfAccountsByName(accountName);
    }

    public Account updateAccount(Account account) throws CannotUpdateAccountException {
        try{
            accountMapper.update(account);
            return account;
        } catch (Exception e){
            throw new CannotUpdateAccountException();
        }
    }
}
