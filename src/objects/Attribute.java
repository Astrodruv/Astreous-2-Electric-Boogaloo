package objects;

import org.w3c.dom.Attr;

public class Attribute
{
    private float current;
    private float maximum;
    private float regeneration;

    public Attribute(float value)
    {
        this.current = value;
        this.maximum = value;
    }

    public Attribute()
    {

    }

    /************ ACCESSORS *************/

    public float getValue()
    {
        return getCurrent();
    }

    public float getCurrent()
    {
        return current;
    }

    public float getMaximum()
    {
        return maximum;
    }

    public boolean isMaximum() { return getCurrent() == getMaximum(); }

    public float getRegeneration()
    {
        return regeneration;
    }

    public float getPercent()
    {
        return getCurrent() / getMaximum();
    }

    /************ MUTATORS *************/

    public void setRegeneration(float amount)
    {
        regeneration = amount;
    }

    public void setCurrent(float amount)
    {
        current += amount;
    }

    public void setMaximum(float amount)
    {
        maximum = amount;
    }

    public void set(float amount)
    {
       current = amount;
       maximum = amount;
    }

    public void update()
    {
        increaseCurrent(getRegeneration());
    }

    public float increaseCurrent(float amount)
    {
        float original = current;

        // Overflow
        if(amount + current > maximum)
        {
            current = maximum;
            return amount - (maximum - original);
        }
        // Normal
        else
        {
            current = current + amount;
            return 0;
        }
    }

    public void increaseMaximum(float amount)
    {
        maximum += amount;
    }

    public void increaseRegeneration(float amount)
    {
        regeneration += amount;
    }

    public float decreaseCurrent(float amount)
    {
        float original = current;

        // Amount exceeds current - would go negative
        if(amount > current)
        {
            current = 0;
            return amount - current;
        }
        // Amount is less than current
        else
        {
            current = current - amount;
            return 0;
        }
    }

    public void decreaseMaximum(float amount)
    {
        maximum -= amount;
    }

}
