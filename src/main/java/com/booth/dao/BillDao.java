package com.booth.dao;

import com.booth.pojo.BillVO;

import java.util.List;

/**
 * @author laijunlin
 * @date 2021-01-31 15:45
 */
public interface BillDao {

    /**
     * 批量插入数据库
     * @param list 账单列表
     * @return 成功条数
     */
    int insertBillList(List<BillVO> list);

    /**
     * 查询用户记账天数
     * @param billVO 用户信息
     * @return 记账天数列表
     */
    List<String> selectTrandDateList(BillVO billVO);

    /**
     * 查询用户
     * @param billVO 用户信息
     * @return 列表
     */
    List<String> selectDateList(BillVO billVO);

    /**
     * 查询总记账数
     * @param billVO
     * @return
     */
    int selectBillCount(BillVO billVO);

    /**
     * 查询收入
     * @param billVO
     * @return
     */
    String selectBalanceBillCount(BillVO billVO);

    /**
     * 查询支出
     * @param billVO
     * @return
     */
    String selectPaymentsBillCount(BillVO billVO);
}
