package controller;

public class Meassure
{
    private double value;
    private MeassureTypes type;
    
    public Meassure(double value, MeassureTypes type)
    {
        this.value = value;
        this.type = type;
    }
    
    public double getValue()
    {
        return value;
    }
    
    public MeassureTypes getType()
    {
        return type;
    }
    
    public String getStringType()
    {
        return type.name();
    }
}
