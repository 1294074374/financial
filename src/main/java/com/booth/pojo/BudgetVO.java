package com.booth.pojo;

/**
 * @author laijunlin
 * @date 2021-02-02 20:18
 */
public class BudgetVO {
    private String openId;
    private String month;
    private String budget;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BudgetVO{");
        sb.append("openId='").append(openId).append('\'');
        sb.append(", month='").append(month).append('\'');
        sb.append(", budget='").append(budget).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
