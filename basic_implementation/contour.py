import numpy as np
import util as u


class contour():
    def __init__(self, surf0, functional):
        #functional is a callable taking in a surface and image and returning a surface
        self.functional = functional
        
        #take in a nparray for initial surface 
        self.s0 = surf0
        self.s = surf0
        self.dim = surf0.shape
    
    def level_set_iter(self, surface, image, dt=0.0001):
        """
        simple euler method surface update given the functional, one step 
        """
        d = lambda x,y: (x**2 + y**2)**0.5
        sy, sx = np.gradient(surface, np.linspace(-1,1, self.dim[0]), np.linspace(-1,1, self.dim[1]))

        return surface - self.functional(surface, image)*d(sx,sy)*dt
    
    def iterate(self, image, dt=0.0001, numiters=1500):
        """
        function for running level_set_iter until solution convergence
        """
        if image.shape==self.dim:
            for i in range(numiters):
                self.s = self.level_set_iter(self.s, image, dt)
                
        else:
            print('reference image must be same dimension as surface')
        
    def set_surf(self, surface):
        #use this if the iterations get messed up and we need to reset
        if surface.shape == self.dim:
            self.s = surface
        else:
            print('we require surface array to be consistent with initialized contour')

    def positive_indeces(surface, geq=True):
        """
        find all indeces corresponding to positive points in the surface 
        positive points denote the interior of the contour from 2D representation
        """
        if geq:
            return list(zip(*np.where(surface>=0)))
        else:
            return list(zip(*np.where(surface>0)))
        
    def converged2mask(surf):
        """
        take the converged surface and generate a mask whose area matches that enclosed by the contour 
        of the surf
        """
        mask = np.zeros((surf.shape[0], surf.shape[1]))
        positive_indeces = contour.positive_indeces(surf, geq=True)
        for index in positive_indeces:
            mask[index] = 1.0
            
        return mask 

    def intensity(self, image, io=True, steps=4): 
        """
        function for returning interior and exterior image intensities given a surface 
        necessary for the intensity functional component for the dynamics
        """
        #gonna do the outwards pointing normal method?
        m = contour.converged2mask(self.s)
        
        #inwards pointing 
        gy,gx = np.gradient(m)
        
        #normalize and flip outwards
        nx = -gx/(np.sqrt(gx**2+gy**2)+ 0.000001)
        ny = -gy/(np.sqrt(gx**2+gy**2)+ 0.000001)
        n = np.sqrt(nx**2+ny**2)
        
        #now walk along the contour in the outwards direction...
        perimeter = contour.positive_indeces(n, geq=False)
        interior = contour.positive_indeces(self.s, geq=True)
        
        i_out = 0
        #lets walk a few steps in the outwards normal direction
        for index in perimeter:
            for i in range(steps):
                normal = np.array(nx[index], ny[index])
                new_index = index + (i+1)*normal
                new_index = (int(new_index[0]), int(new_index[1]))
                
                #update intensity, Note: may be a mistake in the ordering of coordinates...
                i_out+=image[new_index]/(len(perimeter)*steps)
                
        
        #now do the inside
        i_in = 0
        for index in interior:
            i_in += image[index]/len(interior)
        
        return i_in, i_out
        