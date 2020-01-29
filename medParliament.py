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
        UserId=uuid.uuid1()
        UserID=UserId.hex
        column = " * "
        whereCondition= " and mobileNo='"+str(data1["mobileNo"])+ "'"
        data= databasefile.SelectQuery("userMaster",column,whereCondition)
        print(data["message"],'data')
        if data == 'No Data Found':
            print('A')
            column = " userId, userName, mobileNo, email, userTypeId, gender "
            values = "'"+str(userId)+ "','"+str(data1["userName"])+"','"+str(data1["mobileNo"])+"','"+str(data1["email"])+"','"+str(data1["userTypeId"])+"','"+str(data1["gender"])+"'"
            insertdata=databasefile.InsertQuery("userMaster",column,values)
            return {"userid":str(UserID)}
        else:
            print('B')
            return {"Status":"User already existed"}
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output


if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5031,debug=True)
    
