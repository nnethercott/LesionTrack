# LesionTrack - Capstone Project 2020/2021

<p align="center">
    <img src="https://nnethercott.github.io/natenethercott/media/capstone_fullbrain.png" height="350" alt="demo2"/>
    <img src="https://nnethercott.github.io/natenethercott/media/capstone_convergence.png" height="350" alt="demo2"/>
</p>

|**Languages** | **Libraries** |
| -----| ---- |
|![Python](https://img.shields.io/badge/Python-yellow) ![Java](https://img.shields.io/badge/Java-blue)| ![Numpy](https://img.shields.io/badge/Numpy-1.19.5-brightgreen) ![Flask](https://img.shields.io/badge/Pillow-9.1.0-brightgreen) ![Matplotlib](https://img.shields.io/badge/Matplotlib-3.4.1-brightgreen)


<a name="description"/>

## Description
An algorithmic approach for tracking and identifying brain lesions/tumours based on the principles of Calculus of Variations is proposed and implemented as a final year thesis project at Queen's University.  For a detailed summary of the mathematics check out [this paper]( https://github.com/nnethercott/LesionTrack/blob/main/Capstone_Report.pdf)

The main implementation can be found in `implementation.ipynb` 


<a name="motivation"/>

## Motivation
To develop a brain-lesion identification algorithm with the goal of accurately labelling MRI and CT scan input images while simultaneously reducing human time manually accomplishing the task. 

<a name="requirements"/>

## Requirements
The project itself is pretty lightweigth as the provided code and utilities are simply an implementation of the proposed algorithm on a tangible dataset for brain tumours.  To install the required libraries simply pip install from `requirements.txt` :
```
pip install -r requirements.txt
```

Using the provided utilities, loading the [sample data](https://www.dropbox.com/s/frug3coeik782t1/sample_data.p?dl=0) from the .pkl format into usable images and masks can be done quite simply:
```
import MRI_util as mri
path_to_sample = #your path to the dataset

scans, masks = mri.load_data(path_to_sample)
```

<a name="results"/>

## Results
The proposed algorithm accomplishes:
* Automated labelling of brain tumours given an initial labelling of the lesion.
* Represents lesion geometry in the evolution of the algorithm.
* Area of Overlap of 34%-77% between automated and manually generated lesion contours.
* Algorithm runtime of 5-10 seconds on an image of ~100x100px. Java implementation cuts this computation time in half. 

<p align="center">
    <img src="https://nnethercott.github.io/natenethercott/media/capstone_test.png" height="450" alt="demo2"/>
</p>

## Next Steps
The main efforts moving forwards aim to optimize computation and the theoretical modelling. To do so we propose:
* Translate .py code into .cpp and make use of pre-written libraries for calculus and linear algebra
* Create a python binding for the new cpp library for ease of algorithm distribution 
* Model more nuanced features present in the geometry of the patient's specific brain


<!--
<img src="https://nnethercott.github.io/natenethercott/media/capstone_fullbrain.png" height="350" alt="demo2"/>
<img src="https://nnethercott.github.io/natenethercott/media/capstone_convergence.png" height="350" alt="demo2"/>
<img src="https://nnethercott.github.io/natenethercott/media/capstone_test.png" height="450" alt="demo2"/>
-->
