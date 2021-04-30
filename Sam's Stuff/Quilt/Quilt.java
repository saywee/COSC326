import java.awt.*;
import javax.swing.*;
import java.util.*;

@SuppressWarnings("serial")
public class Quilt extends Canvas {

    private static float defSize = 100;
    private static int windowSize;
    private static ArrayList<Float> val = new ArrayList<Float>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        float totalSize = 0;
        int tracker = 0;
        while (sc.hasNext()) {
            try {
                val.add(Float.parseFloat(sc.next()));
                if(val.get(tracker)%4 != 0){
                    if (val.get(tracker) > 255 || val.get(tracker) < 0) {
                        throw new Exception("Value out of range");
                    }
                }
                tracker++;
            } catch (NumberFormatException e) {
                System.out.println(e + "\nOnly float values accepted.");
                sc.close();
                return;
            } catch (Exception e) {
                System.out.println(e);
                sc.close();
                return;
            }
        }
        if (val.isEmpty() || val.size() % 4 != 0) {
            System.out.println("No/Bad values entered.");
            sc.close();
            return;
        }
        float x = val.get(0);
        for (int i = 0; i < val.size(); i += 4) {
            val.set(i, val.get(i) * 1 / x);
            totalSize += val.get(i);
        }
        JFrame f = new JFrame("Quilt");
        Canvas canvas = new Quilt();
        windowSize = 800;// (int) Math.ceil(defSize * totalSize);
        defSize = (windowSize - 5) / totalSize;
        canvas.setSize(windowSize, windowSize);
        f.add(canvas);
        f.pack();
        f.setVisible(true);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        sc.close();

    }

    public void paint(Graphics g) {
        g.setColor(Color.white);// white border
        g.drawRect(0, 0, 799, 799);// outlining the whole thing?
        g.setColor(
            new Color(val.get(1) / 255, val.get(2) / 255, val.get(3) / 255));
        defSize *= val.get(0);
        int x = (int) (windowSize / 2 - defSize / 2) + 1;
        g.fillRect(x, x, (int) defSize, (int) defSize);
        recursiveDraw(g, 4, x, x, (int) defSize);
    }

    public void recursiveDraw(Graphics g, int depth, int x, int y,
        int prevSize) {
        if (depth == val.size())
            return;
        int defSizeX = (int) (defSize * val.get(depth));
        g.setColor(new Color(val.get(depth + 1) / 255, val.get(depth + 2) / 255,
            val.get(depth + 3) / 255));
        x = x - defSizeX / 2;
        y = y - defSizeX / 2;
        g.fillRect(x, y, defSizeX, defSizeX);
        g.fillRect(x + prevSize, y, defSizeX, defSizeX);
        g.fillRect(x + prevSize, y + prevSize, defSizeX, defSizeX);
        g.fillRect(x, y + prevSize, defSizeX, defSizeX);

        recursiveDraw(g, depth + 4, x, y, defSizeX);
        recursiveDraw(g, depth + 4, x, y + prevSize, defSizeX);
        recursiveDraw(g, depth + 4, x + prevSize, y, defSizeX);
        recursiveDraw(g, depth + 4, x + prevSize, y + prevSize, defSizeX);
    }
}
