import java.util.ArrayList;
import java.lang.Math;

public class MathUtil {
    // dont think i need a constructor

    /*
     * functions to write: - gradient
     */

    public static void main(String[] args) {

        long startTime = System.nanoTime();
        // execute minimizing arclength code ...
        Surface surf = new Surface(200, 200, 0.5, 0.3, 0, -0.1);

        for (int n = 0; n < 1250; n++) {
            ArrayList<double[][]> normal = gradient(surf.s, surf.xmesh, surf.ymesh);
            double[][] nx = normal.get(0);
            double[][] ny = normal.get(1);

            // partials
            ArrayList<double[][]> Htop = gradient(nx, surf.xmesh, surf.ymesh);
            double[][] nxx = Htop.get(0);
            double[][] nxy = Htop.get(1);
            ArrayList<double[][]> Hbottom = gradient(ny, surf.xmesh, surf.ymesh);
            double[][] nyx = Hbottom.get(0);
            double[][] nyy = Hbottom.get(1);

            // update temp surface
            double step = (surf.xmesh + surf.ymesh) / 2;
            // double[][] K = new double[surf.ydim][surf.xdim];
            // double[][] N = new double[surf.ydim][surf.xdim];
            double K;
            double N;

            for (int i = 0; i < surf.ydim; i++) {
                for (int j = 0; j < surf.xdim; j++) {
                    // curvature
                    double num = ((Math.pow(nx[i][j], 2) + step) * nyy[i][j] - 2 * nx[i][j] * ny[i][j] * nxy[i][j]
                            + (Math.pow(ny[i][j], 2) + step) * nxx[i][j]);
                    double denum = (2 * Math.pow(Math.pow(nx[i][j], 2) + Math.pow(ny[i][j], 2) + step, 1.5));
                    K = -num / (denum + 0.000001);
                    // normal
                    N = Math.pow(Math.pow(nx[i][j], 2) + Math.pow(ny[i][j], 2), 0.5);

                    // now update surface
                    surf.s[i][j] = surf.s[i][j] - (K * N * 0.0001);
                    // double temp = surf.s[i][j] - (K * N * 0.0001);
                }
            }
        }

        long endTime = System.nanoTime();

        double duration = (double) (endTime - startTime) / 1000000000;
        System.out.println(duration);

        Surface.np(surf.s);
        System.out.println();
        // Surface.np(ny);

    }

    public static ArrayList<double[][]> gradient(double[][] f, double xstep, double ystep) {
        ArrayList<double[][]> gradf = new ArrayList<double[][]>();
        int xdim = f[0].length;
        int ydim = f.length;

        double[][] gx = new double[ydim][xdim];
        double[][] gy = new double[ydim][xdim];

        // four corners first (saves us from having to write conditionals)
        gx[0][0] = (f[0][1] - f[0][0]) / xstep;
        gx[0][xdim - 1] = (f[0][xdim - 2] - f[0][xdim - 1]) / (-xstep);
        gx[ydim - 1][0] = (f[ydim - 1][1] - f[ydim - 1][0]) / xstep;
        gx[ydim - 1][xdim - 1] = (f[ydim - 1][xdim - 2] - f[ydim - 1][xdim - 1]) / (-xstep);

        gy[0][0] = (f[1][0] - f[0][0]) / ystep;
        gy[ydim - 1][0] = (f[ydim - 2][0] - f[ydim - 1][0]) / (-ystep);
        gy[0][xdim - 1] = (f[1][xdim - 1] - f[0][xdim - 1]) / ystep;
        gy[ydim - 1][xdim - 1] = (f[ydim - 2][xdim - 1] - f[ydim - 1][xdim - 1]) / (-ystep);

        // forwards and backwards differences on the boundaries
        // x-direction
        for (int i = 1; i < ydim - 1; i++) {
            // left
            gx[i][0] = (f[i][1] - f[i][0]) / xstep;
            // right
            gx[i][xdim - 1] = (f[i][xdim - 2] - f[i][xdim - 1]) / (-xstep);

            // simulatenously populate the y values
            gy[i][0] = (f[i + 1][0] - f[i - 1][0]) / (2 * ystep);
            gy[i][xdim - 1] = (f[i + 1][xdim - 1] - f[i - 1][xdim - 1]) / (2 * ystep);
        }

        // y-direction
        for (int i = 1; i < xdim - 1; i++) {
            // top
            gy[0][i] = (f[1][i] - f[0][i]) / ystep;
            // bottom
            gy[ydim - 1][i] = (f[ydim - 2][i] - f[ydim - 1][i]) / (-ystep);

            // simultaneously populate the x values
            gx[0][i] = (f[0][i + 1] - f[0][i - 1]) / (2 * xstep);
            gx[ydim - 1][i] = (f[ydim - 1][i + 1] - f[ydim - 1][i - 1]) / (2 * xstep);
        }

        for (int i = 1; i < ydim - 1; i++) {
            for (int j = 1; j < xdim - 1; j++) {
                // central differences
                gx[i][j] = (f[i][j + 1] - f[i][j - 1]) / (2 * xstep);
                gy[i][j] = (f[i + 1][j] - f[i - 1][j]) / (2 * ystep);
            }
        }
        gradf.add(gx);
        gradf.add(gy);

        return gradf;
    }
}
