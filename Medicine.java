public class Medicine {
    private static int nextMedicineID = 1;
    private String medicineName;
    private String medicineType;
    private double price;
    private int qtyAvailable;
    private String isAvailable;

    public Medicine(String medicineName, String medicineType, double price, int qtyAvailable, String isAvailable) {
        medicineID = nextMedicineID++;
        this.medicineName = medicineName;
        this.medicineType = medicineType;
        this.price = price;
        this.qtyAvailable = qtyAvailable;
        this.isAvailable = isAvailable;
    }

    public int getMedicineID() {
        return medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQtyAvailable() {
        return qtyAvailable;
    }

    public void setQtyAvailable(int qtyAvailable) {
        this.qtyAvailable = qtyAvailable;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String toString() {
        return "Medicine ID: " + medicineID + ", Name: " + medicineName + ", Type: " + medicineType +
                ", Price: " + price + ", Qty: " + qtyAvailable + ", Availability: " + isAvailable;
    }
}
