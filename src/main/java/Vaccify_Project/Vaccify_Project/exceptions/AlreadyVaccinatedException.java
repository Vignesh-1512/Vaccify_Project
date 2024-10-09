package Vaccify_Project.Vaccify_Project.exceptions;

public class AlreadyVaccinatedException extends RuntimeException{
    public AlreadyVaccinatedException(String msg)
    {
        super(msg);
    }
}
