package xyz.qzpx.em.dataObject;

public class StatisticsDO {

    private String period;

    private Integer income;

    private Integer discount;

    private Integer refund;

    private String allWeekIndex;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getRefund() {
        return refund;
    }

    public void setRefund(Integer refund) {
        this.refund = refund;
    }

    public String getAllWeekIndex() {
        return allWeekIndex;
    }

    public void setAllWeekIndex(String allWeekIndex) {
        this.allWeekIndex = allWeekIndex;
    }
}
