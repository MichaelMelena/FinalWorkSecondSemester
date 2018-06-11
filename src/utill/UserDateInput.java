package utill;


public class UserDateInput {
    private Date start;
    private Date end;
    private  double value;

    public  UserDateInput(Date start, Date end, double value) {
        this.start=start;
        this.end=end;
        this.value=value;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public double getValue() {
        return value;
    }
}
