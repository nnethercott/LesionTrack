import java.util.ArrayList;
import java.lang.Math;

public class Surface {
    public int xdim;
    public int ydim;
    public double xmesh;
    public double ymesh;

    public double[][] x;
    public double[][] y;
    public double[][] s;

    public Surface() {
        // set dimensions of grid
        this.xdim = 100;
        this.ydim = 100;

        // set mesh
        this.xmesh = (double) 2 / (this.xdim - 1);
        this.ymesh = (double) 2 / (this.ydim - 1);

        // generate x-y plane
        ArrayList<double[][]> xy = gridmesh(xdim, ydim);
        this.x = xy.get(0);
        this.y = xy.get(1);

        // make parabloid initial surface
        this.s = parabloid(1.0, 1.0, 0.0, 0.0);
    }

    public Surface(int xdim, int ydim) {
        // set dimensions of grid
        this.xdim = xdim;
        this.ydim = ydim;

        // set mesh
        this.xmesh = (double) 2 / (this.xdim - 1);
        this.ymesh = (double) 2 / (this.ydim - 1);

        // generate x-y plane
        ArrayList<double[][]> xy = gridmesh(xdim, ydim);
        this.x = xy.get(0);
        this.y = xy.get(1);

        // make parabloid initial surface
        this.s = parabloid(1.0, 1.0, 0.0, 0.0);
    }

    public Surface(int xdim, int ydim, double xrad, double yrad) {
        // set dimensions of grid
        this.xdim = xdim;
        this.ydim = ydim;

        // set mesh
        this.xmesh = (double) 2 / (this.xdim - 1);
        this.ymesh = (double) 2 / (this.ydim - 1);

        // generate x-y plane
        ArrayList<double[][]> xy = gridmesh(xdim, ydim);
        this.x = xy.get(0);
        this.y = xy.get(1);

        // make parabloid initial surface
        this.s = parabloid(xrad, yrad, 0.0, 0.0);
    }

    public Surface(int xdim, int ydim, double xrad, double yrad, double left, double top) {
        // set dimensions of grid
        this.xdim = xdim;
        this.ydim = ydim;

        // set mesh
        this.xmesh = (double) 2 / (this.xdim - 1);
        this.ymesh = (double) 2 / (this.ydim - 1);

        // generate x-y plane
        ArrayList<double[][]> xy = gridmesh(xdim, ydim);
        this.x = xy.get(0);
        this.y = xy.get(1);

        // make parabloid initial surface
        s = parabloid(xrad, yrad, left, top);
    }

    public static void main(String[] args) {
        // checking speed comparison with python
        Surface surf = new Surface(200, 200, 0.5, 0.5);
        np(surf.s);
    }

    public ArrayList<double[][]> gridmesh(int xdim, int ydim) {
        // linspace from -1 to +1
        // include this for legibility and call in init

        ArrayList<double[][]> xy = new ArrayList<double[][]>();
        double[][] x = new double[ydim][xdim];
        double[][] y = new double[ydim][xdim];

        // populate, convention being first y row is -1
        for (int i = 0; i < ydim; i++) {
            for (int j = 0; j < xdim; j++) {
                x[i][j] = (double) -1 + j * xmesh;
                y[i][j] = (double) -1 + i * ymesh;
            }
        }
        xy.add(x);
        xy.add(y);

        return xy;
    }

    // now make a function to generate parabloid surfaces...
    public double[][] parabloid(double xrad, double yrad, double left, double top) {
        double[][] p = new double[ydim][xdim];
        for (int i = 0; i < ydim; i++) {
            for (int j = 0; j < xdim; j++) {
                p[i][j] = -(Math.pow((x[i][j] - left) / xrad, 2) + Math.pow((y[i][j] - top) / yrad, 2)) + 1.0;
            }
        }
        return p;
    }

    public static void np(double[][] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print("[");

            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + ",");
            }
            System.out.print("],");
        }
        System.out.print("]");
    }

}
