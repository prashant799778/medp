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
import ConstantData

from flask import Flask, render_template
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'

@app.route('/SignUp', methods=['POST'])
def SignUp():

    try: 
        startlimit,endlimit="",""   
        inputdata = request.form.get('data')       
        
        keyarr = ['name','mobileNo','email','password','gender',"userTypeId"]
      
        inputdata = json.loads(inputdata)
        commonfile.writeLog("signup",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":  
            DeviceId,DeviceType,Os,OsVersion,Country= "","","","","",""
         
            Name = inputdata["name"]
            MobileNo = inputdata["mobileNo"]
            Email = inputdata["email"] 
            Gender = inputdata["gender"]
            Password = inputdata["password"]

            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and Email = '" + str(Email) + "' or MobileNo = '" + str(MobileNo) + "'"
            count = databasefile.SelectCountQuery("UserMaster",WhereCondition,"")
            
            if int(count) > 0:         
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                
                if 'Country' in inputdata:                    
                    Country = inputdata["Country"]  
                if 'userTypeId' in inputdata:                                    
                    userTypeId = inputdata['userTypeId']
                if 'Gender' in inputdata:                    
                    Gender = inputdata['Gender']                  
                if 'DeviceId' in inputdata:                   
                    DeviceId = inputdata['DeviceId'] 
                if 'DeviceType' in inputdata:                    
                    DeviceType = inputdata['DeviceType']
                    DeviceType = ConstantData.GetDeviceTypeId(DeviceType)
                if 'Os' in inputdata:                    
                    Os = inputdata['Os'] 
                if 'OsVersion' in inputdata:                    
                    OsVersion = inputdata['OsVersion']

                column = "userId,email,userName,mobileNo,gender,userTypeId,DeviceId,DeviceType,Os,OsVersion,"
                column = column + "Password,Country"                
                values = " '" + str(UserId) + "','" + str(Email) + "','" + str(Name) + "','" + str(MobileNo) + "','" + str(Gender) + "','" + str(userTypeId) + "', "            
                values = values + " '" + str(DeviceId) + "','" + str(DeviceType) + "','" + str(Os) + "','" + str(OsVersion) + "','"                 
                values = values + " '" + str(Password) + "','" + str(Country) + "',"                
                values = values + " '3' "

                data = databasefile.InsertQuery("UserMaster",column,values)        
                
                if data != "0":
                    column = 'UserId,UserName,UserType'
                    
                    data = databasefile.SelectQuery("UserMaster",column,WhereCondition,"",startlimit,endlimit)                  
                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 

if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5031,debug=True)
    
