mport os
import io
import numpy as np
import json
import tensorflow as tf
import flask
import werkzeug

import keras
from keras.preprocessing import image
from keras.preprocessing.image import img_to_array
from keras.applications.xception import (
    Xception, preprocess_input)
from tensorflow.keras.applications.imagenet_utils import decode_predictions
from tensorflow import keras
from keras import backend as K

from flask_ngrok import run_with_ngrok
from flask import Flask, request, redirect, url_for, jsonify, Response

app = Flask(__name__)
# run_with_ngrok(app)
# folder to storage, can be changed into cloud storage?
app.config['UPLOAD_FOLDER'] = 'Upload'

model = None
graph = None


def load_model():
    global model
    global graph
    model = keras.models.load_model('model-livingroom')
    graph = tf.compat.v1.Session().graph


load_model()


def prepare_image(img):
    img = img_to_array(img)
    img = np.expand_dims(img, axis=0)
    img = preprocess_input(img)
    # return the processed image
    return img

names = ["poor","rich"]

#@app.route("/")
#def hello():
#    return "Hello, World!"

@app.route('/', methods=['GET', 'POST'])
def upload_file():
    data = {"success": False}
    if request.method == 'POST':
        if request.files.get('image'):
            file = flask.request.files['image']
        #    filename = file.filename
            filename = werkzeug.utils.secure_filename(file.filename)
            filepath = os.path.join(app.config['UPLOAD_FOLDER'], filename)
            file.save(filepath)
           # Load the saved image using Keras.
            # Resize it to the Xception format of 299x299 pixels.
            image_size = (299, 299)
            im = keras.preprocessing.image.load_img(filepath,
                                                    target_size=image_size,
                                                    grayscale=False)

            # Preprocess the image and prepare it for classification.
            image = prepare_image(im)

            global graph
            with graph.as_default():
                model = keras.models.load_model('model-livingroom')
                graph = tf.compat.v1.Session().graph
                preds = model.predict(image)
                print(preds)
                # results = decode_predictions(preds)
                data["predictions"] = []

                # for (imagenetID, label, prob) in results[0]:
                #    r = {"label": label, "probability": float(prob)}
                #    data["predictions"].append(r)

                prob = preds
                if prob > 0.45:
                    labelName = names[1]
                else : 
                    labelName = names[0]
                # labelName = names[int(label)]
                r = {"labelName": labelName, "prob": float(prob)}
                data["predictions"].append(r)

                # indicate that the request was a success
                data["success"] = True
                return str(r)

        return str(data)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
