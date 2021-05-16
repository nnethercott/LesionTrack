import numpy as np
import matplotlib.pyplot as plt

def showsurf(surface, figsize = (6,6), ls=False):
    """
    function for plotting our surface in either 3d or 2d level set
    params:
        surface: 2d scalar valued array representing the surface
        ls: "zero Level set". if True then we plot the level set. defaults to false 
    """
    fig = plt.figure(figsize=figsize)

    x = np.linspace(0, surface.shape[1]-1, surface.shape[1])
    y = np.linspace(0, surface.shape[0]-1, surface.shape[0])

    X,Y = np.meshgrid(x,y)
    
    if not ls:
        ax = fig.add_subplot(111, projection='3d')
        ax.plot_surface(X,Y,surface)
    if ls:
        plt.contour(surface,[0], colors='red')
        
        

def organize(c):
    """
    function for organizing unordered points representing contour according to positive ccw orientation
    useful for visualizing contour properties in an parameterized fashion

    params:
        c: 1D array of contour indeces tuples
    """
    c.sort(key = lambda x: x[0])
    ordered = []
    ordered.append(c[0])
    c.remove(ordered[0])
    
    d = lambda x,y: ((x[0]-y[0])**2+(x[1]-y[1])**2)
    
    while(len(c)> 0):
        c.sort(key=lambda x: d(x,ordered[-1]))
        ordered.append(c[0])
        c.remove(ordered[-1])
    
    #check orientation
    if ordered[0][1]<ordered[1][1]:
        ordered.reverse()
    
    return ordered

def k(s):
    '''
    curvature given as divergence of the unit normal.  Consistent with Sethian
    
    params:
        s: 2D scalar-valued array representing surface 
    '''
    sy, sx = np.gradient(s, np.linspace(-1,1, s.shape[0]), np.linspace(-1,1, s.shape[1]))
    norm = (sx**2 + sy**2)**0.5
    nx = sx/(norm+0.00001)
    ny = sy/(norm+0.00001)
    
    nxy, nxx = np.gradient(nx)
    nyy, nyx = np.gradient(ny)
    
    return nxx+nyy

def mean_curvature(Z):
    '''
    curvature function from stack overflow. step denotes spacing on grid vals
    Note: convention different from Sethian, this function returns negative values from k

    Observations: this function performs more robustly than k
    '''
    #get average step size for curvature function 
    xstep = 2/(Z.shape[1])
    ystep = 2/(Z.shape[0])
    step = 0.5*(xstep+ystep)
    
    Zy, Zx  = np.gradient(Z, np.linspace(-1,1, Z.shape[0]), np.linspace(-1,1, Z.shape[1]))
    Zxy, Zxx = np.gradient(Zx, np.linspace(-1,1, Z.shape[0]), np.linspace(-1,1, Z.shape[1]))
    Zyy, _ = np.gradient(Zy, np.linspace(-1,1, Z.shape[0]), np.linspace(-1,1, Z.shape[1]))
    
    H = (Zx**2 + step)*Zyy - 2*Zx*Zy*Zxy + (Zy**2 + step)*Zxx
    H = -H/(2*(Zx**2 + Zy**2 + step)**(1.5))

    return H

def parabloid(shape, xrad=1, yrad=1, left=0, top=0):
    """
    function for generating an initial surface with ellipse as the zero level set
    params:
        shape: shape of image space
        xrad: normalized x-intercept of the parabloid --> 1 corresponds to image border
        yrad: ^^
        left: displaces ellipse center from left to right, consistent with CSS convention 
        top: ^^ but for y direction
    
    """
    ydim = shape[0]
    xdim = shape[1]

    x = np.linspace(-1, 1, xdim)
    y = np.linspace(-1, 1, ydim)
    xx, yy = np.meshgrid(x,y)

    return -(((xx-left)/xrad)**2+((yy-top)/yrad)**2)+1

def surf2simple(surface, threshold = 0.05):
    
    #two step process
    #step 1
    s = -1*np.ones(surface.shape)
    positive_indeces = list(zip(*np.where(surface>0)))
                            
    for index in positive_indeces:
        s[index] = 1
    
    #step 2
    ny, nx = np.gradient(s)
    n = ny**2+nx**2
    zero_indeces = list(zip(*np.where(n>0)))
    
    for index in zero_indeces:
        s[index] = 0
    
    return s 
