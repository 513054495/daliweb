package com.code90.daliweb.service;

import com.code90.daliweb.domain.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 提现数据库操作类
 * @author Ray Lin
 * @create 2018-10-28 18:54
 **/
public interface WithdrawService extends JpaRepository<Withdraw,Integer>,JpaSpecificationExecutor<Withdraw> {
    @Query("select w from Withdraw w where w.id=?1")
    Withdraw getObjectById(String id);
    @Query("select w from Withdraw w where w.createBy=?1")
    List<Withdraw> getWithdrawByUserCode(String userCode);
    @Query("select coalesce(sum(w.money),0) from Withdraw w where w.createBy=?1 and w.status=1")
    double getAllMoneyByUserCode(String userCode);
    @Query("select coalesce(count(w),0) from Withdraw w where w.status=?1")
    int getWithdrawByStatus(int i);
    @Query("select coalesce(sum(w.money),0) from Withdraw w where w.status=1")
    double getAllMoney();
    @Query("select substring(w.createTime,1,7),coalesce(sum(w.money),0) from Withdraw w where w.status=?1 group by substring(w.createTime,1,7)")
    List<Object[]> getWithdrawByMonth(int status);
    @Query("select substring(w.createTime,1,7),coalesce(sum(w.money),0) from Withdraw w group by substring(w.createTime,1,7)")
    List<Object[]> getWithdrawByMonth();
}
