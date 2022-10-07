package uweaccsystem;

/**
 *
 * @author George Kelly
 */
public class Lease {
    private Student student;
    private int leaseNo;
    private int leaseDuration;

    public Lease(Student student, int leaseNo, int leaseDuration) {
        this.student = student;
        this.leaseNo = leaseNo;
        this.leaseDuration = leaseDuration;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getLeaseNo() {
        return leaseNo;
    }

    public void setLeaseNo(int leaseNo) {
        this.leaseNo = leaseNo;
    }

    public int getLeaseDuration() {
        return leaseDuration;
    }

    public void setLeaseDuration(int leaseDuration) {
        this.leaseDuration = leaseDuration;
    }
    
    

}
