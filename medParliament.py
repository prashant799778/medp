from flask import Flask,request,abort
import uuid
import json
import json
import numpy as np
import pymysql
import requests
import json
import pymysql
from flask_cors import CORS
from datetime import datetime
from config import Connection
import databasefile
from config import Connection
import commonfile

from flask import Flask, render_template
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'

@app.route('/addUser', methods=['POST'])
def addUser():
    try:
        data1 = commonfile.DecodeInputdata(request.get_data())
        column = " * "
        whereCondition= "mobileNo='"+str(data1["mobileNo"])+ "'"
        data= databasefile.SelectQuery("userMaster",column,whereCondition)
        UserId=uuid.uuid1()
        UserID=UserId.hex
        return {"userid":str(UserID)}
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output


if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5031,debug=True)
    
