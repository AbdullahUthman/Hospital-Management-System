
public class Patients {
    private static int nextPatientID = 1;

    private int patientID;
    private String patientName;
    private int age;
    private String sex;
    private String contactNumber;
    private String address;

    public Patients(String patientName, int age, String sex, String contactNumber, String address) {
        this.patientID = nextPatientID++;
        this.patientName = patientName;
        this.age = age;
        this.sex = sex;
        this.contactNumber = contactNumber;
        this.address = address;
    }

    public int getPatientID() {
        return patientID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    public String toString() {
        return "Patient ID: " + patientID + ", Name: " + patientName
             + ", Age: " + age + ", Sex: " + sex
             + ", Contact: " + contactNumber + ", Address: " + address;
    }
}