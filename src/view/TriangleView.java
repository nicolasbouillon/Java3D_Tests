package view;

import model.Triangle;

public class TriangleView
{
    public Triangle triangle;
    private boolean selected;

    public TriangleView(Triangle triangleIn)
    {
        this.triangle = triangleIn;
    }

    public void setSelected(boolean selectedIn)
    {
        this.selected = selectedIn;
    }

    public boolean isSelected()
    {
        return this.selected;
    }

    public Triangle getTriangle()
    {
        return this.triangle;
    }
    
  
}
