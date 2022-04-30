# LesionTrack - Capstone Project 2020/2021

<p align="center">
    <img src="https://nnethercott.github.io/natenethercott/media/capstone_fullbrain.png" height="350" alt="demo2"/>
    <img src="https://nnethercott.github.io/natenethercott/media/capstone_convergence.png" height="350" alt="demo2"/>
</p>

|**Languages** | **Libraries** |
| -----| ---- |
|![Python](https://img.shields.io/badge/Python-yellow) ![Java](https://img.shields.io/badge/Java-blue)| ![Numpy](https://img.shields.io/badge/Numpy-1.19.5-brightgreen) ![Flask](https://img.shields.io/badge/Pillow-8.2.0-brightgreen) ![Matplotlib](https://img.shields.io/badge/Matplotlib-3.4.1-brightgreen)


<a name="description"/>

## Description
An algorithmic approach for tracking and identifying brain lesions/tumours based on the principles of Calculus of Variations is proposed and implemented as a final year thesis project at Queen's University.  For a detailed summary of the mathematics check out [this paper]( https://github.com/nnethercott/LesionTrack/blob/main/Capstone_Report.pdf)

Using the provided utilities, loading the data from the .pkl format into usable images and masks can be done quite simply:
```
import MRI_util as mri
path_to_sample = #your path to the dataset

scans, masks = mri.load_data(path_to_sample)
```

<a name="requirements"/>

## Requirements
The project itself is pretty lightweigth as the provided code and utilities are simply an implementation of the proposed algorithm on a tangible dataset for brain tumours.  As such all that's required to run the project is a working version of Python3, the specified libraries, and the sample data (in .pkl form) for the implementation notebook which can be found [here](https://www.dropbox.com/s/frug3coeik782t1/sample_data.p?dl=0). 


<!--
<img src="https://nnethercott.github.io/natenethercott/media/capstone_fullbrain.png" height="350" alt="demo2"/>
<img src="https://nnethercott.github.io/natenethercott/media/capstone_convergence.png" height="350" alt="demo2"/>
<img src="https://nnethercott.github.io/natenethercott/media/capstone_test.png" height="450" alt="demo2"/>
-->
