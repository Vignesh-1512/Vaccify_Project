package Vaccify_Project.Vaccify_Project.exceptions;

public class PatientNotFoundException extends RuntimeException{
    public PatientNotFoundException (String msg)
    {
        super(msg);
    }
}
