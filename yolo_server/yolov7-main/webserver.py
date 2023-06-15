from flask import Flask, render_template, request, redirect, send_file, url_for, Response
import os
import json
from subprocess import Popen
import base64
from PIL import Image
from io import BytesIO

app = Flask(__name__)

@app.route('/')
def index():
    return 'Web App with Python Flask!'


@app.route("/test", methods=["GET"])
def test_message():
    return "Hello world"

@app.route("/predict", methods=["POST"])
def predict_img():
    # For multipart/form-data
    # if 'file' in request.files:
    #     f = request.files['file']
    #     basepath = os.path.dirname(__file__)
    #     filepath = os.path.join(basepath,'uploads',f.filename)
    #     print("upload folder is ", filepath)
    #     f.save(filepath)
        
    #     predict_img.imgpath = f.filename

    # For application/json
    if 'image' in request.json:
        image_name = "imageToSave.jpg"
        data = request.json
        image_data = base64.b64decode(data.get('image'))

        # Create a BytesIO object to work with the image data
        image_buffer = BytesIO(image_data)

        # Open the image using PIL
        image = Image.open(image_buffer)
        basepath = os.path.dirname(__file__)
        filepath = os.path.join(basepath,'uploads',image_name)
        # Save the image to the specified output path
        print("upload folder is ", filepath)
        rotated_image = image.rotate(-90, expand=True)
        rotated_image.save(filepath)

        process = Popen(["python", "detect.py", '--source', filepath, "--weights", "best_1.pt", "--conf", "0.3", "--device", "cpu", "--img-size", "640", "--no-trace", "--exist-ok"], shell=True)
        process.wait()

            
        folder_path = 'runs/detect'
        subfolders = [f for f in os.listdir(folder_path) if os.path.isdir(os.path.join(folder_path, f))]    
        latest_subfolder = max(subfolders, key=lambda x: os.path.getctime(os.path.join(folder_path, x)))    
        image_path = folder_path+'/'+latest_subfolder+'/'+image_name
        json_path = folder_path+'/'+latest_subfolder+'/'+image_name.split('.')[0] + '.json'
        print(json_path) 
    
    data = ''
    with open(json_path) as f:
        data = json.load(f)

    if data == '':
        return "done"
    return data

@app.route('/json_example', methods=['POST'])
def handle_json():
    if 'image' in request.json:
        data = request.json
        image_data = base64.b64decode(data.get('image'))

        # Create a BytesIO object to work with the image data
        image_buffer = BytesIO(image_data)

        # Open the image using PIL
        image = Image.open(image_buffer)

        # Save the image to the specified output path
        image.save("uploads/imageToSave.jpg")
    return "done"

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)  # debug=True causes Restarting with stat