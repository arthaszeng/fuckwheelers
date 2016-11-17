package com.trailblazers.freewheelers.service.impl;

import com.trailblazers.freewheelers.mappers.AccountMapper;
import com.trailblazers.freewheelers.mappers.AccountRoleMapper;
import com.trailblazers.freewheelers.model.Account;
import com.trailblazers.freewheelers.model.AccountRole;
import com.trailblazers.freewheelers.service.AccountService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AccountServiceTest {

    AccountService accountService;

    @Mock
    SqlSession sqlSession;
    @Mock
    AccountMapper accountMapper;
    @Mock
    AccountRoleMapper accountRoleMapper;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        when(sqlSession.getMapper(AccountMapper.class)).thenReturn(accountMapper);
        when(sqlSession.getMapper(AccountRoleMapper.class)).thenReturn(accountRoleMapper);

        accountService = new AccountService(sqlSession);
    }

    @Test
    public void shouldCreateAccountWhenThereAreNoValidationErrors(){
        Account account = getAccountWithoutErrors();

        accountService.createAccount(account);

        verify(accountMapper, times(1)).insert(account);
        verify(accountRoleMapper, times(1)).insert(any(AccountRole.class));
        verify(sqlSession, times(1)).commit();
    }

    private Account getAccountWithoutErrors() {
        Account account = new Account();
        account.setEmail_address("example@example.com");
        account.setPassword("example");
        account.setAccount_name("Example Person");
        account.setPhone_number("1234567890");
        account.setCountry_id(1L);
        account.setAddress_id(12345L);
        return account;
    }
}
