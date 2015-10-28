package exporter;

public interface IBatchExporter
{
    void setTemperature(int kelvin);
    void setMoisture(int level);
    void setRedLight(int level);
    void setBlueLight(int level);
    void addWater(int sec);
    void resetError(int errorNum);
    void setFanSpeed(int speed);
}
