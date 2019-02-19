import tensorflow as tf
import os
from LoadData import LoadData
import numpy as np

modelname = "alphabetModel"

path = os.path.dirname(os.path.abspath(__file__)) + "/" + modelname


# Load this bitch or create it the first time
if os.path.isfile(os.path.dirname(os.path.abspath(__file__)) + "/" + modelname):
    model = tf.keras.models.load_model(path, compile=True)
else:
    model = tf.keras.models.Sequential()

    model.add(tf.keras.layers.Dense(1250, activation=tf.nn.relu,input_shape=(2500,)))
    model.add(tf.keras.layers.Dropout(0.2))
    model.add(tf.keras.layers.Dense(1000, activation=tf.nn.relu))
    model.add(tf.keras.layers.Dropout(0.2))
    model.add(tf.keras.layers.Dense(500, activation=tf.nn.relu))
    model.add(tf.keras.layers.Dropout(0.2))
    model.add(tf.keras.layers.Dense(26, activation=tf.nn.softmax))

    model.compile(optimizer=tf.keras.optimizers.Adam(),
                  loss=tf.keras.losses.sparse_categorical_crossentropy,
                  metrics=['accuracy'])


(x_fit,y_fit) = LoadData().loadData()

model.fit(x_fit,y_fit,epochs=5,verbose=0)
model.save(os.path.dirname(os.path.abspath(__file__))+"/"+modelname,overwrite=True,include_optimizer=True)

print("Done!")
