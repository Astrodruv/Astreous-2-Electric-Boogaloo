package player;

import objects.entity.unit.Unit;

import java.util.ArrayList;

public class CompositionData
{
    private final ArrayList<TypeData> data;

    public CompositionData()
    {
        data = new ArrayList<>();
    }

    public int getTotalBuilt(Class<? extends Unit> type)
    {
        for(TypeData t : data)
        {
            if(t.type == type)
            {
                return t.made;
            }
        }
        return 0;
    }

    public int getTotalLost(Class<? extends Unit> type)
    {
        for(TypeData t : data)
        {
            if(t.type == type)
            {
                return t.lost;
            }
        }
        return 0;
    }

    public int getTotalLost()
    {
        int total = 0;

        for(TypeData t : data)
        {
            total += t.lost;

        }
        return total;
    }

    public int getTotalMade()
    {
        int total = 0;

        for(TypeData t : data)
        {
            total += t.made;

        }
        return total;
    }

    public void recordMade(Class<? extends Unit> type)
    {
        for (TypeData t : data)
        {
            if (t.type == type) {
                t.made++;
                return;
            }
        }
        data.add(new TypeData(type));
        recordMade(type);       // call it again to record the data
    }

    public void recordLost(Class<? extends Unit> type)
    {
        for (TypeData t : data)
        {
            if (t.type == type) {
                t.lost++;
                return;
            }
        }
        data.add(new TypeData(type));
    }

    public String toString()
    {
        String message = "";

        for(TypeData t : data)
        {
            message  = message + t + "\n";
        }

        return message;
    }

    static class TypeData
    {
        Class<? extends Unit> type;
        int made;
        int lost;

        TypeData(Class<? extends Unit> type) {
            this.type = type;
            made = 0;
            lost = 0;
        }

        public String toString()
        {
            return type.getSimpleName() + ": " + made + " made / " + lost + " lost";
        }
    }


}
