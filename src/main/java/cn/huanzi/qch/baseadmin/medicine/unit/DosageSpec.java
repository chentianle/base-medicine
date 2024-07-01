package cn.huanzi.qch.baseadmin.medicine.unit;

public class DosageSpec {

    private Integer timesPerDay;//每日次
    private double pillsPerTime;//每次量
    private String unit;//单位

    public DosageSpec(Integer timesPerDay, double pillsPerTime ,String unit) {
        this.timesPerDay = timesPerDay;
        this.pillsPerTime = pillsPerTime;
        this.unit = unit;
    }

    public Integer getTimesPerDay() {
        return timesPerDay;
    }

    public double getPillsPerTime() {
        return pillsPerTime;
    }

    public String getUnit() {
        return unit;
    }

}
