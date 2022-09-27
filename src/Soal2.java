import java.util.*;
import java.awt.*;
import javax.swing.JFrame;

class Points {

    public Points(double thisX, double thisY){
        this.x = thisX;
        this.y = thisY;
    }

    public double x;
    public double y;
}

//https://stackoverflow.com/questions/15305833/finding-the-circle-that-covers-most-points-in-space
//using the idea from this link, however the limitation is the point will be integer,
//the idea is by checking every point within radius of a point and then
//find the point which has the most point

public class Soal2 extends Canvas{



    private static Points frameSize = new Points(800,800);
    final static int gridSize = 100;

    //so basically our center location will be multiply of this number
    //the bigger the number, it will be less accurate but will be significantly faster due heap size and
    //complexity of n
    final static int accGrid = 25;

    private static int ssize = 70; //number of dots
    private static int rradius = 75; //radius of objects

    //this hold the variable for each points
    private static Points[] arrPoints;
    Points center;

    HashMap<Points, Integer> candidatePoints = new HashMap<>();
    //todo should find a better method to interate if object is in list or not
    private Points getPointFromCandidateorNew(double x, double y){
        for (Map.Entry<Points, Integer> set : candidatePoints.entrySet()) {
            Points thisObj = set.getKey();
            if ((thisObj.x == x) && (thisObj.y == y))
                return thisObj;
        }
        return new Points(x, y);
    }

    private void createPoints(){
        arrPoints = new Points[ssize];
        for (int i = 0; i < ssize; i++)
            arrPoints[i] = new Points(Math.random() * frameSize.x,Math.random() * frameSize.y);
    }



    private ArrayList<Points> findCenterCandidate(Points point, int radius){
        int startX = (int)((point.x-radius) / accGrid);
        int startY = (int)((point.y-radius) / accGrid);
        int iterationX = (int)Math.ceil(((point.x+radius)/ accGrid));
        int iterationY = (int)Math.ceil(((point.y+radius)/ accGrid));

//        hit all
//        int startX = 0;
//        int startY = 0;
//        int iterationX = 800/25;
//        int iterationY = 800/25;

        ArrayList<Points> points = new ArrayList<>();
        for (int i = startX; i<=iterationX; i++){
            for (int j = startY; j<=iterationY; j++){
                int thisX = i * accGrid;
                int thisY = j * accGrid;

//                if (thisX < 0 || thisY < 0 || thisX > frameSize.x || thisY > frameSize.y)
//                    continue;

                Double res = Math.pow(point.x-thisX,2) + Math.pow(point.y-thisY,2);
                if ( res < radius*radius)
                {
                    Points obj = getPointFromCandidateorNew(thisX, thisY);
                    points.add(obj); //new Points(thisX, thisY)
                }

            }
        }

        return points;
    }

    private Points findCenter(){

        for (final Points point : arrPoints) {
            ArrayList<Points> pts = findCenterCandidate(point, rradius);
            for (final Points pt : pts) {
                if (candidatePoints.containsKey(pt)) {
                    candidatePoints.put(pt, candidatePoints.get(pt)+1);
                }
                else {
                    candidatePoints.put(pt, 1);
                }
            }
        }

        return Collections.max(candidatePoints.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    private void drawGrids(Graphics g){
        int nx = (int) (frameSize.x / gridSize);
        int ny = (int) (frameSize.y / gridSize);
        setForeground(Color.gray);
        g.drawString("0", 0,10);
        for (int i = 1; i < nx; i++) {
            int i100 = i*100;
            g.drawLine(i100, 0, i100, (int) frameSize.y);
            g.drawString(Integer.toString(i100), i100,10);
        }
        for (int i = 1; i < ny; i++) {
            int i100 = i*100;
            g.drawLine( 0, i*100,  (int)frameSize.x, i*100);
            g.drawString(Integer.toString(i100), 0, i100+12);
        }

    }

    private void drawCenter(Graphics g) {
        if (center != null)
            g.drawOval((int) center.x-rradius, (int)center.y-rradius, rradius*2, rradius*2);
    }

    private void drawPoints(Graphics g) {
        for (int i = 0; i < ssize; i++) {
            Points tp = arrPoints[i];
            g.drawOval((int) tp.x-5, (int)tp.y-5, 10, 10);
        }
    }

    private void debugCandidate(Graphics g){
        for (Map.Entry<Points, Integer> set : candidatePoints.entrySet()) {
            Points thisObj = set.getKey();
            g.drawOval((int) thisObj.x-2, (int)thisObj.y-2, 4, 4);
        }
    }

    public void paint(Graphics g) {
        setBackground(Color.WHITE);
        g.clearRect(0,0, (int)frameSize.x, (int)frameSize.y);


        drawGrids(g);
        drawPoints(g);
        drawCenter(g);
        debugCandidate(g);
    }

    public static void main(String[] args) {
        Soal2 m=new Soal2();
//        //generate random points
        m.createPoints();

        m.center = m.findCenter();
        System.out.println("center is ("+m.center.x+" , "+m.center.y+")");
        System.out.println("num of circle are : "+m.candidatePoints.get(m.center));
//
        JFrame f=new JFrame();
        f.add(m);
        f.setSize((int) frameSize.x, (int)frameSize.y);
        //f.setLayout(null);
        f.setVisible(true);
//        m.findCenterCandidate(new Points(110,100), 100);
    }

}