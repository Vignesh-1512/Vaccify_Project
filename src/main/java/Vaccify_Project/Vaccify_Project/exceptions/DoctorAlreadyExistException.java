package Vaccify_Project.Vaccify_Project.exceptions;

public class DoctorAlreadyExistException extends RuntimeException{
    public DoctorAlreadyExistException (String msg)
    {
        super(msg);
    }
}
