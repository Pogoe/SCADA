package exporter;

public interface IMESExporter 
{
    void maxCapacity(int capacity);
    void currentCapacity(int capacity);
    void removedUnits(int units);
    void harvestedUnits(int units);
}
