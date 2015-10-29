package controller;

import java.util.Date;

public class Alarm
{
    private boolean trigger;
    private ErrorTypes error;
    private Date date;    
    
    public void triggerAlarm(ErrorTypes e)
    {
        error = e;
        trigger = true;
        date = new Date();
    }
    
    public boolean isTriggered()
    {
        return trigger;
    }
    
    public void stopAlarm()
    {
        trigger = false;
    }
    
    public void getAlarm()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Alarm triggered at ").append(getDate()).append("\n");
        sb.append("Error:\n");
        sb.append(error.toString());
    }
    
    public Date getDate()
    {
        return date;
    }
}
