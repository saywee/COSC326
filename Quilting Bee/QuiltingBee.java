import java.awt.*;
import java.util.*;
import java.util.List;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;

public class QuiltingBee {
    List<Quilt> quilts = new ArrayList<Quilt>();
    List<Drawing> drawQuilts = new ArrayList<Drawing>();
    public static final int IMAGE_HEIGHT = 500;
    public static final int IMAGE_WIDTH = 500;
    public static final Point IMAGE_CENTER = new Point(IMAGE_WIDTH / 2,
        IMAGE_HEIGHT / 2);

    Graphics2D gr;
    public BufferedImage output;

    public static void main(String[] args) {
        QuiltingBee qb = new QuiltingBee();
        qb.getQuilts();
    }

    public QuiltingBee() {
        this.output = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT,
            BufferedImage.TYPE_INT_ARGB);
        this.gr = this.output.createGraphics();
    }

    /*
     * A method that reads each input line and reads each word, then storing
     * them into individual variables.
     * Each variable will then be used to create a new Quilt object, stored in
     * an arraylist, resized to fit
     * output image file size.
     * 
     */
    public void getQuilts() {
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            Scanner scanLine = new Scanner(line);
            while (scanLine.hasNext()) {
                double scale = scanLine.nextDouble();
                int red = scanLine.nextInt();
                int green = scanLine.nextInt();
                int blue = scanLine.nextInt();
                Quilt qs = new Quilt(scale, scale, red, green, blue);
                quilts.add(qs);
            }
        }
        resize1();
        for (int i = 0; i < quilts.size(); i++) {
            System.out.println("quilt at " + i + " width is : "
                + quilts.get(i).wid + " height is : " + quilts.get(i).hei);
        }

        drawAll(0, IMAGE_CENTER);
        //sorting issue, sort according to proper placement.
        Drawing s = drawQuilts.get(0);
        drawQuilts = drawQuilts.subList(1, drawQuilts.size());
        Collections.sort(drawQuilts, new Comparator<Drawing>() {
            public int compare(Drawing d1, Drawing d2) {

                if (d1.quilt.wid > d2.quilt.wid)
                    return -1;
                else if (d1.quilt.wid < d2.quilt.wid)
                    return 1;
                else
                    return 0;
            }
        });
        drawQuilts.add(0, s);
        for (int i = 0; i < drawQuilts.size(); i++) {
            System.out.println("width is : " + drawQuilts.get(i).quilt.wid
                + " height is : " + drawQuilts.get(i).quilt.hei);
            System.out.println(
                "position at : " + drawQuilts.get(i).pos.getLocation());
        }
        execute();
        drawTo("test", "png");
    }

    /*
     * A recursion method that creates a Drawing object which takes a Quilt
     * object
     * to be drawn at the specified position.
     *
     * @param i the index of Quilt to be drawn.
     * 
     * @param position the x, and y position to be drawn at.
     */
    public void drawAll(int i, Point position) {
        if (i < quilts.size()) {
            Drawing drawing = new Drawing(quilts.get(i), position);
            drawQuilts.add(drawing);
            drawAll(i + 1, quilts.get(i).topLeft);
            drawAll(i + 1, quilts.get(i).topRight);
            drawAll(i + 1, quilts.get(i).bottomLeft);
            drawAll(i + 1, quilts.get(i).bottomRight);
        }
    }

    /*
     * A method that resizes all quilts by taking the width and height of the
     * output image divided by the
     * accumulated width, and height of all quilts. This scale is then
     * multiplied to all individual
     * quilts width and height.
     * 
     */
    public void resize1() {
        // get all sizes of squares for the maximum distance.
        double allWidth = 0;
        double allHeight = 0;

        for (int i = 0; i < quilts.size(); i++) {
            allWidth += quilts.get(i).wid;
            allHeight += quilts.get(i).hei;
        }

        for (int i = 0; i < quilts.size(); i++) {
            double scaleWidth = IMAGE_WIDTH / allWidth;
            double scaleHeight = IMAGE_HEIGHT / allHeight;

            quilts.get(i).wid = quilts.get(i).wid * scaleWidth;
            quilts.get(i).hei = quilts.get(i).hei * scaleHeight;
        }
    }

    /*
     * A method that draws all the Drawing object stored in the arraylist.
     * 
     */
    public void execute() {
        for (int i = 0; i < drawQuilts.size(); i++) {
            drawQuilts.get(i).draw(gr);
        }

    }

    /*
     * A method to write the drawn image to an external image file.
     * 
     * @param name the name of the file.
     * 
     * @param extension the extension of the file
     */
    void drawTo(String name, String extension) {
        try {
            String filename = name + "." + extension;
            File outputfile = new File(filename);
            ImageIO.write(output, extension, outputfile);
            System.out.println("File saved to " + name + "." + extension);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public class Drawing {
        Quilt quilt;
        Point pos;

        /*
         * A constructor to create the Drawing object.
         * Subtracts the given position's x and y coordinate of the previous
         * quilt
         * by the current quilt's x and y coordinate divided by 2 or the centre
         * of the output image
         * for the first quilt so that the current quilt's
         * centre will be at the corners of the previous quilt. Then calculates
         * and stores the new
         * corners of the current quilt.
         * 
         * @param q the current quilt.
         * 
         * @param position the x and y coordinate of the centre of out image or
         * corners of previous quilts.
         */
        public Drawing(Quilt q, Point position) {
            position.x -= (int) (q.wid / 2);
            position.y -= (int) (q.hei / 2);

            this.quilt = q;
            this.pos = position;
            this.quilt.topLeft = new Point(position);
            this.quilt.topRight = new Point((int) (position.x + q.wid),
                position.y);
            this.quilt.bottomLeft = new Point(position.x,
                (int) (position.y + q.hei));
            this.quilt.bottomRight = new Point((int) (position.x + q.wid),
                (int) (position.y + q.hei));
        }

        /*
         * A method that sets the color of the rectangle and draws it.
         * 
         * @param g the graphics to be used.
         */
        public void draw(Graphics2D g) {
            g.setColor(quilt.colour);
            g.fillRect(pos.x, pos.y, (int) quilt.wid, (int) quilt.hei);
        }
    }
}