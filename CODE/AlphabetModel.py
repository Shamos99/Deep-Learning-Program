import tensorflow as tf
import sys
import os
import numpy as np
from LoadData import LoadData

def getsysargs():

    pixels = sys.argv[1]


    pixels = pixels.split(",")
    narray = []

    for number in pixels:
        narray.append(int(number))

    narray = np.array(narray)
    narray = np.reshape(narray,(1,2500))


    return narray


modelname = "alphabetModel"

#print(modelname)

path = os.path.dirname(os.path.abspath(__file__)) + "/" + modelname

if os.path.isfile(os.path.dirname(os.path.abspath(__file__)) + "/" + modelname):
    #print(modelname)
    try:
        model = tf.keras.models.load_model(path, compile=True)
    except Exception as e:
        print(e)


    #print("first")
    (x_fit, y_fit) = LoadData().loadData()
    #print("second")
    x = getsysargs()
    #print("third")
    predict = model.predict(x_fit)
    predict = np.argmax(np.sum(predict,axis=0))
    print(chr(predict+65))

else:
    print("Fuck you")
