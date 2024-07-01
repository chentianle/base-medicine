package cn.huanzi.qch.baseadmin.medicine.unit;

public class DrugSpec {
    private double quantity;
    private String unit;

    public DrugSpec(double quantity, String unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

}
