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
        inputdata =  commonfile.DecodeInputdata(request.get_data())       
        
        keyarr = ['userName','mobileNo','email','password','gender',"userTypeId"]
        commonfile.writeLog("signup",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":  
            DeviceId,DeviceType,Os,ImeiNo,ipAddress,Country= "","","","","","",""
         
            Name = inputdata["userName"]
            MobileNo = inputdata["mobileNo"]
            Email = inputdata["email"] 
            Gender = inputdata["gender"]
            Password = inputdata["password"]

            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and email = '" + str(Email) + "' or mobileNo = '" + str(MobileNo) + "'"
            count = databasefile.SelectCountQuery("UserMaster",WhereCondition,"")
            
            if int(count) > 0:         
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                
                if 'country' in inputdata:                    
                    Country = inputdata["country"]  
                if 'userTypeId' in inputdata:                                    
                    userTypeId = inputdata['userTypeId']
                if 'gender' in inputdata:                    
                    Gender = inputdata['gender']                  
                if 'deviceid' in inputdata:                   
                    DeviceId = inputdata['deviceid'] 
                if 'deviceType' in inputdata:                    
                    DeviceType = inputdata['deviceType']
                    DeviceType = ConstantData.GetDeviceTypeId(DeviceType)
                if 'os' in inputdata:                    
                    Os = inputdata['os'] 
                if 'ImeiNo' in inputdata:                    
                   ImeiNo = inputdata['ImeiNo']
                if 'ipAddress' in inputdata:                    
                   ipAddress = inputdata['ipAddress']


                UserId=uuid.uuid1()
                UserID=UserId.hex    

                columns = " userId, userName, mobileNo, email, userTypeId, gender, password, deviceType, os, ipAddress, country, city, deviceid, imeiNo "          
                values = " '" + str(UserId) + "','" + str(Name) + "','" + str(MobileNo) + "','" + str(Email) + "','" + str(userTypeId) + "','" + str(Gender) + "', "            
                values = values + " '" + str(Password) + "','" + str(DeviceType) + "','" + str(Os) + "','" + str(ipAddress) + "','"                 
                values = values + " '" + str(Country) + "','" + str(DeviceId) + "','" + str(ImeiNo) +"'" 


                data = databasefile.InsertQuery("UserMaster",column,values) 

                if data != "0":
                    column = 'userId,userName,userTypeId'
                    
                    data = databasefile.SelectQuery("UserMaster",column,WhereCondition,"",startlimit,endlimit) 
                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 
@app.route('/addUser', methods=['POST'])
def addUser():
    try:
        data1 = commonfile.DecodeInputdata(request.get_data())
        UserId=uuid.uuid1()
        UserID=UserId.hex
        columns = " * "
        whereCondition= " and mobileNo='"+str(data1["mobileNo"])+ "'"
        data= databasefile.SelectQuery("userMaster",columns,whereCondition)
        print(data["message"],'data')
        if data["message"] == 'No Data Found':
            print('A')
            columns = " userId, userName, mobileNo, email, userTypeId, gender, password, deviceType, os, ipAddress, country, city, deviceid, imeiNo "
            values = "'"+str(UserID)+ "','"+str(data1["userName"])+"','"+str(data1["mobileNo"])+"','"+str(data1["email"])+"','"+str(data1["userTypeId"])+"','"+str(data1["gender"])+"','"+str(data1["password"])+"','"+str(data1["deviceType"])+"','"+str(data1["os"])+"','"+str(data1["ipAddress"])+"','"+str(data1["country"])+"','"+str(data1["city"])+"','"+str(data1["deviceid"])+"','"+str(data1["ImeiNo"])+"'"
            insertdata=databasefile.InsertQuery("userMaster",columns,values)
            columns = " * "
            whereCondition= " and mobileNo='"+str(data1["mobileNo"])+ "'"
            user_data= databasefile.SelectQuery("userMaster",columns,whereCondition)
            print('user_data', user_data["result"]["userTypeId"])
            if user_data["result"]["userTypeId"] == 2 or user_data["result"]["userTypeId"] == 5:
                columns = " userId, organization, aboutProfile, designation "
                values = "'"+str(data1["userId"])+ "','"+str(data1["organization"])+"','"+str(data1["aboutProfile"])+"','"+str(data1["designation"])+"'"
                insertdata=databasefile.InsertQuery("policyMaker",columns,values)
                return {"Status":"PolicyMaker added  successfully"}
            elif user_data["result"]["userTypeId"] == 3 or user_data["result"]["userTypeId"] == 6:
                columns = " userId, designation, areaofActivity, profileCategoryId, interestId "
                values = "'"+str(data1["userId"])+ "','"+str(data1["designation"])+"','"+str(data1["areaofActivity"])+"','"+str(data1["profileCategoryId"])+"','"+str(data1["interestId"])+"'"
                insertdata=databasefile.InsertQuery("enterprenuerMaster",columns,values)
                return {"Status":"Enterprenuer added  successfully"}
            elif user_data["result"]["userTypeId"] == 4 or user_data["result"]["userTypeId"] == 7:
                columns = " userId, address, qualification, batchofQualification, instituteName, universityName, universityAddress, interestId "
                values = "'"+str(data1["userId"])+ "','"+str(data1["address"])+"','"+str(data1["qualification"])+"','"+str(data1["batchofQualification"])+"','"+str(data1["instituteName"])+"','"+str(data1["universityName"])+"','"+str(data1["universityAddress"])+"','"+str(data1["interestId"])+"'"
                insertdata=databasefile.InsertQuery("studentMaster",columns,values)
                return {"Status":"Student added  successfully"}
            else:
                return {"Status":"Not Found"}

            return {"userid":str(UserID), "userTypeId":str(user_data["result"]["userTypeId"])}
        else:
            print('B')
            return {"Status":"User already existed"}
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output

@app.route('/Login', methods=['GET'])
def login():
    try:
        password = request.args['password']
       
        mobile = request.args['email']
        column=  "us.mobileNo,us.userName,us.email,um.usertype,us.userId"
        whereCondition= "us.email = '" + mobile + "' and us.password = '" + password + "'  and  us.usertypeId=um.Id"
        groupby,startlimit,endlimit="","",""
        loginuser=databasefile.SelectQuery("userMaster as us,userTypeMaster as um",column,whereCondition, groupby,startlimit,endlimit)
        
               
      
        if (loginuser!=0):   
            Data = {"result":loginuser,"status":"true"}                  
            return Data
        else:
            data={"status":"Failed","result":"Login Failed"}
            return data

    except KeyError as e:
        print("Exception---->" +str(e))        
        output = {"result":"Input Keys are not Found","status":"false"}
        return output 
    
    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"result":"something went wrong","status":"false"}
        return output        

@app.route('/addAdmin', methods=['POST'])
def addAdmin():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print(inputdata,"A")
        startlimit,endlimit="",""
        keyarr = ['adminName','userTypeId','emailId','password']
        print(inputdata,"B")
        commonfile.writeLog("addAdmin",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print('D')
       
        if msg == "1":
            print('E')
            Name = inputdata["adminName"]
            userTypeId = inputdata["userTypeId"]
            Email = inputdata["emailId"]
            password = inputdata["password"]

            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and Email = '" + str(Email) + "' or password = '" + str(password) + "'"
            count = databasefile.SelectCountQuery("userMaster",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                
                column = "userId,email,userName,password,userTypeId"                
                values = " '" + str(UserId) + "','" + str(Email) + "','" + str(Name) + "','" + str(password) + "','" + str(userTypeId) + "'"

                data = databasefile.InsertQuery("userMaster",column,values)        
                
                if data != "0":
                    column = 'userId,userName,userTypeId'
                    
                    data = databasefile.SelectQuery("userMaster",column,WhereCondition,"",startlimit,endlimit)                  
                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg 




    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"result":"something went wrong","status":"false"}
        return output      

    




if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5031,debug=True)
    
