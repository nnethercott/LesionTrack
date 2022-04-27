import numpy as np
import pickle 
from PIL import Image, ImageEnhance, ImageOps, ImageFilter

        
def find_layers(mask_data):
    """
    works with provided dataset...
    """
    l = []
    for i in range(mask_data.shape[-1]):
        if np.mean(mask_data[:,:,i])>0.0:
            l.append(i)
    return l   

def load_data(fname, mode='lesions only'):
    """
    assumes data is pickled ://///
    literally only works with our dataset lmao
    """
    data = pickle.load(open(fname, 'rb'))
    
    #now parse into scans and masks 
    scans = data[1]
    masks = data[2]
    
    if mode == 'lesions only':
        indeces = find_layers(masks)
        
        return np.array([scans[:,:,i] for i in indeces]),np.array([masks[:,:,i] for i in indeces])
    
    elif mode == 'all':
        return scans, masks
    
def preprocess(scans, masks, normalize=True, contrast_factor = 8, blur_rad = 0.5):
    _scans = []
    _masks = []

    for s,m in list(zip(scans, masks)):
        #now do the PIL thing 
        pil_im = Image.fromarray(s)
        pil_im = ImageOps.grayscale(pil_im)

        #enhancer 
        enhancer = ImageEnhance.Contrast(pil_im)
        im = enhancer.enhance(contrast_factor)
        
        #blur 
        im = im.filter(ImageFilter.GaussianBlur(radius=blur_rad))
        
        if normalize:
            _scans.append(np.array(im)/255)
            _masks.append(m/255)
            
        else:
            _scans.append(np.array(im))
            _masks.append(m)
            
    return np.array(_scans), np.array(_masks)
                      
