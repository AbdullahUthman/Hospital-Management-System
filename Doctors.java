public class Doctors {
    private static int nextDoctorID = 1;

    private int doctorID;
    private String doctorName;
    private String specialization;
    private String availableFrom;
    private String availableTo;
    private String contactNumber; 
    private double fees;

    public Doctors(String doctorName, String specialization, String availableFrom, String availableTo, String contactNumber, double fees) {
        this.doctorID = nextDoctorID++;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.contactNumber = contactNumber;
        this.fees = fees;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(String availableTo) {
        this.availableTo = availableTo;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public double getFees() {
        return fees;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public String toString() {
        return "Doctor ID: " + doctorID + ", Name: " + doctorName
             + ", Specialization: " + specialization
             + ", Available From: " + availableFrom + " To: " + availableTo
             + ", Contact: " + contactNumber + ", Fees: " + fees;
    }
}
