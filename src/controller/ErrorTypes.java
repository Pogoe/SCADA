package controller;

public enum ErrorTypes
{
    ABNORMAL_TEMP(0, "Abnormal Temperature", "Temperature varies from set values!"),
    ABNORMAL_MOISTURE(1, "Abnormal moisture level", "Moisture varies from set value!"),
    WATER_TOO_HIGH(2, "Too much water", "Water level has exceeded its maximum level!"),
    WATERPUMP_FAILURE(3, "Waterpump failed", "The waterpump has stopped working.");
    
    private final String NAME;
    private final int ID;
    private final String DESCRIPTION;
    
    ErrorTypes(int id, String name, String desc)
    {
        this.ID = id;
        this.NAME = name;
        this.DESCRIPTION = desc;
    }
    
    public int getId(){ return ID; }
    public String getName(){ return NAME; }
    public String getDescription(){ return DESCRIPTION; }
    
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("ID:\t\t\t").append(getId()).append("\n");
        sb.append("Error name:\t\t").append(getName()).append("\n");
        sb.append("Description:\t\t").append(getDescription());
        
        return sb.toString();
    }
}
