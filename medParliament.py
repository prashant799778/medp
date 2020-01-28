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
        whereCondition= "mobile='"+str(data1["mobile"])+ "'"
        data= databasefile.SelectQuery("userMaster",column,whereCondition)
        UserId=uuid.uuid1()
        UserID=UserId.hex
        if data==None:
        	
        	column=" name, mobileNo, email, userTypeId "
        	
        	values =  "'" +str(data1["name"])+"','"+str(data1["mobileNo"])+"','"+str(data1["email"])+"','"+str(["userTypeId"])+ "'"
        	
        	insertdata=databasefile.InsertQuery("userMaster",column,values)
        	
        	column = " * "
        	whereCondition= "mobile='"+str(data1["mobile"])+ "'"
        	data8= databasefile.SelectQuery1("userMaster",column,whereCondition)

        	output= {"result":"User Added Successfully","patient Details":data8[-1],"status":"true"}
            cursor.close()
            return output

            
        else:
            output = {"result":"User Already Added Existed ","status":"true","patient Details":data}
            return output 
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output


if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5090,debug=True)
    
