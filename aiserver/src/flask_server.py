from flask import Flask, request, jsonify
import sys
import os
import fitz
import easyocr
import sample
from googletrans import Translator
from build_vocab import Vocabulary

ocrpath = './src/OCR'
abs_path = os.path.abspath(ocrpath)
sys.path.append(abs_path)

app = Flask(__name__)
app.config['JSON_AS_ASCII'] = False ##인코딩


@app.route('/')
def hello_world():
    return 'Hello, Python!'



@app.route('/process_image', methods=['POST'])
def process_image():

    # 입력 받기   
    pdf_file = request.files['pdf']
    pageIndex = int(request.form['pageIndex'])

    # 파일 저장 후 열기
    with open('uploaded_pdf.pdf', 'wb') as file:
        pdf_file.save(file)
    
    pdf = fitz.open('uploaded_pdf.pdf')
    page = pdf.load_page(pageIndex) #pdf 페이지 가져오기
    image_list = page.get_images(full=True) # 페이지 안의 이미지 가져오기

    cap_result = []

    if len(image_list) != 0: # 페이지 안에 이미지가 있으면
    # 이미지캡셔닝 실행
    
        # cnt = 0
        for img_index, img in enumerate(image_list):
            base_image = pdf.extract_image(img[0])
            image_bytes = base_image["image"]
            caption = sample.captioning(image_bytes)

            translator = Translator()
            # print(caption)
            # print(img_index)
            outStr = translator.translate(caption, dest='ko', src='en')
            # print(outStr.text)
            # outStr = [translator.translate(text, dest='en').text for text in texts]
            cap_result.append(outStr.text)
            # cnt +=1

    # OCR 실행
    get_page_image = page.get_pixmap() # 페이지 자체를 이미지로 가져오기
    get_page_image = get_page_image.tobytes()

    reader = easyocr.Reader(['ko','en'], gpu=True) # Korean, English
    ocr_result = reader.readtext(get_page_image, detail=0)

    # 요약, 텍스트, 사진 생성(임시)
    summary = [len(ocr_result), len(cap_result)]
    text = ocr_result
    picture = cap_result

    # 결과 반환
    return jsonify({
        'pageIndex': pageIndex,
        'summary': summary,
        'text': text,
        'picture': picture
    })



if __name__ == "__main__":
    app.run(host='0.0.0.0') #ip로 실행