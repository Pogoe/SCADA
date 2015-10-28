package controller;

public enum Error
{
    ABNORMAL_TEMP(1, "Abnormal Temperature", "Temperature varies from set values.");
    
    private final String NAME;
    private final int ID;
    private final String DESCRIPTION;
    
    Error(int id, String name, String desc)
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
        
        sb.append("Error name:\t").append(getName()).append("\n");
        sb.append("ID:\t\t").append(getId()).append("\n");
        sb.append("Description:\t").append(getDescription());
        
        return sb.toString();
    }
}
