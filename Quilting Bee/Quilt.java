import java.awt.*;

public class Quilt{
  public static final double HEIGHT=100.0;
  public static final double WIDTH = HEIGHT;
  public Color colour;
  public Point topLeft;
  public Point topRight;
  public Point bottomLeft;
  public Point bottomRight;
  public double wid;
  public double hei;
  /*
   * A constructor to create the Quilt object with specified parameters.
   * 
   * @param scale the size of the quilt to be scaled by.
   * @param red the colour level of red
   * @param green the colour level of green
   * @param blue the colour level of blue
   */
  public Quilt(double scale, double scaleH, int red, int green, int blue){
    Color col = new Color(red, green,blue);
    this.colour = col;
    this.wid = WIDTH*scale;
    this.hei = HEIGHT*scaleH;
  }
  /*
   * A method that returns the static height.
   * 
   * @return HEIGHT the static height of the quilt.
   */
  public double getHeight(){
    return HEIGHT;
  }
  
  /*
   * A method that returns the static width.
   * 
   * @return WIDTH the static width of the quilt.
   */
  public double getWidth(){
    return WIDTH;
  }
}