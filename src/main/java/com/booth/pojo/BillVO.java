package com.booth.pojo;

/**
 * @author laijunlin
 * @date 2021-01-31 15:19
 */
public class BillVO {
    private String openId;
    private String orderId;
    private String tradingDate;
    private String spendType;
    private String transactionType;
    private String counterparty;
    private String goods;
    private String balanceOfPayments;
    private String amt;
    private String payType;
    private String status;
    private String merchantId;
    private String remark;
    private String selectDate;
    private String startDate;
    private String endDate;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BillVO{");
        sb.append("openId='").append(openId).append('\'');
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append(", tradingDate='").append(tradingDate).append('\'');
        sb.append(", spendType='").append(spendType).append('\'');
        sb.append(", transactionType='").append(transactionType).append('\'');
        sb.append(", counterparty='").append(counterparty).append('\'');
        sb.append(", goods='").append(goods).append('\'');
        sb.append(", balanceOfPayments='").append(balanceOfPayments).append('\'');
        sb.append(", amt='").append(amt).append('\'');
        sb.append(", payType='").append(payType).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", merchantId='").append(merchantId).append('\'');
        sb.append(", remark='").append(remark).append('\'');
        sb.append(", selectDate='").append(selectDate).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getSpendType() {
        return spendType;
    }

    public void setSpendType(String spendType) {
        this.spendType = spendType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(String counterparty) {
        this.counterparty = counterparty;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getBalanceOfPayments() {
        return balanceOfPayments;
    }

    public void setBalanceOfPayments(String balanceOfPayments) {
        this.balanceOfPayments = balanceOfPayments;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSelectDate() {
        return selectDate;
    }

    public void setSelectDate(String selectDate) {
        this.selectDate = selectDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
