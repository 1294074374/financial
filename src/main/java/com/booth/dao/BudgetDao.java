package com.booth.dao;

import com.booth.pojo.BillVO;
import com.booth.pojo.BudgetVO;

/**
 * @author laijunlin
 * @date 2021-02-02 20:18
 */
public interface BudgetDao {

    BudgetVO  selectUserMonthBudget(BillVO billVO);
}
