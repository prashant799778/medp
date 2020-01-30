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
        print(inputdata)     
        
        keyarr = ['userName','mobileNo','email','password','gender',"userTypeId"]
        commonfile.writeLog("signup",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":  
            DeviceId,DeviceType,Os,ImeiNo,ipAddress,Country,City,organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","","","","","","","","",""
            address,qualification,batchofQualification,instituteName,universityName,universityAddress="","","","","",""
            
         
            Name = inputdata["userName"]
            MobileNo = inputdata["mobileNo"]
            Email = inputdata["email"] 
            Gender = inputdata["gender"]
            Password = inputdata["password"]
            print(Password)

            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and email = '" + str(Email) + "' and password = '" + str(Password) + "'"
            count = databasefile.SelectCountQuery("userMaster",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                print("qqqqqqqqqqqqqqqqqqqqq")
                
                if 'country' in inputdata:                    
                    Country = inputdata["country"]  
                if 'city' in inputdata:                    
                    City = inputdata["city"]  
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
                if 'password' in inputdata:                    
                   Password = inputdata['password']
                if 'organization' in inputdata:                    
                    organization = inputdata['organization']
                
                if 'aboutProfile' in inputdata:                    
                    aboutProfile = inputdata['aboutProfile']

                if 'designation' in inputdata:                    
                   designation = inputdata['designation'] 

                if 'areaofActivity' in inputdata:                    
                    areaofActivity = inputdata['areaofActivity']

                if 'profileCategoryId' in inputdata:                    
                    profileCategoryId = inputdata['profileCategoryId']

                if 'interestId' in inputdata:                    
                    interestId = inputdata['interestId']   

                if 'address' in inputdata:                    
                    address = inputdata['address']

                if 'qualification' in inputdata:                    
                    qualification = inputdata['qualification']

                if 'batchofQualification' in inputdata:                    
                    batchofQualification = inputdata['batchofQualification']

                if 'institutionName' in inputdata:                    
                    instituteName = inputdata['institutionName']  

                if 'universityName' in inputdata:                    
                    universityName = inputdata['universityName']

                if 'universityAddress' in inputdata:                    
                    universityAddress = inputdata['universityAddress']


                print(Password)



               

                columns = " userId, userName, mobileNo, email, userTypeId, gender, password, deviceType, os, ipAddress, country, city, deviceid, imeiNo "          
                values = " '" + str(UserId) + "','" + str(Name) + "','" + str(MobileNo) + "','" + str(Email) + "','" + str(userTypeId) + "','" + str(Gender) + "', "            
                values = values + " '" + str(Password) + "','" + str(DeviceType) + "','" + str(Os) + "','" + str(ipAddress) + "','"                 
                values = values + str(Country) + "','" + str(City) + "','" + str(DeviceId) + "','" + str(ImeiNo) +"'" 


                data = databasefile.InsertQuery("userMaster",columns,values) 

                if data != "0":
                    column = 'userId,userName,userTypeId'
                    
                    data = databasefile.SelectQuery("userMaster",column,WhereCondition,"",startlimit,endlimit)
                    if data["status"]!="false":
                        y=data["result"][0]
                        if (y["userTypeId"] == 5):
                            columns="userId,aboutProfile,organization,designation"
                            values=" '" + str(y["userId"]) + "','" + str(aboutProfile) + "','" + str(organization) + "','" + str(designation) + "'"
                            data1=databasefile.InsertQuery("policyMakerMaster",columns,values) 



                        if (y["userTypeId"] == 6):
                            columns="userId,areaOfActivity,profileCategoryId,designation,interestId"
                            values=" '" + str(y["userId"]) + "','" + str(areaofActivity) + "','" + str(profileCategoryId) + "','" + str(designation) + "','" + str(interestId) + "'"
                            data2=databasefile.InsertQuery("enterprenuerMaster",columns,values) 


                        if (y["userTypeId"]== 7):
                            columns="userId,address,qualification,batchofQualification,institutionName,universityAddress,universityName,interestId"
                            values=" '" + str(y["userId"]) + "','" + str(address) + "','" + str(qualification) + "','" + str(batchofQualification) + "','" + str(instituteName)+ "','" + str(universityAddress)+ "','" + str(universityName)+ "','" + str(interestId) + "'"
                            data3 = databasefile.InsertQuery("studentMaster",columns,values) 


                    else:
                        return commonfile.Errormessage()

                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 

@app.route('/Login', methods=['GET'])
def login():
    try:
        password = request.args['password']
       
        mobile = request.args['email']
        column=  "us.mobileNo,us.userName,us.email,um.userName as userName,us.userId as userId"
        whereCondition= " and us.email = '" + mobile + "' and us.password = '" + password + "'  and  us.userTypeId=um.id"
        groupby,startlimit,endlimit="","",""
        loginuser=databasefile.SelectQuery("userMaster as us,userTypeMaster as um",column,whereCondition, groupby,startlimit,endlimit)
        
               
      
        if  (loginuser["status"]!="false"):   
            Data = {"result":loginuser["result"],"status":"true"}                  
            return Data
        else:
            data={"status":"False","result":"wrong credentials"}
            return data

    except KeyError as e:
        print("Exception---->" +str(e))        
        output = {"result":"Input Keys are not Found","status":"false"}
        return output 
    
    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"result":"something went wrong","status":"false"}
        return output        

@app.route('/addSubAdmins', methods=['POST'])
def addAdmin():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        startlimit,endlimit="",""
        keyarr = ['adminName','userTypeId','emailId','password']
        commonfile.writeLog("addAdmin",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            Name = inputdata["adminName"]
            userTypeId = inputdata["userTypeId"]
            Email = inputdata["emailId"]
            password = inputdata["password"]

            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and email = '" + str(Email) + "' or password = '" + str(password) + "'"
            count = databasefile.SelectCountQuery("userMaster",WhereCondition,"")
            
            if int(count) > 0:
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



@app.route('/adminPannel', methods=['GET'])
def adminPannel():
    try:
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='2'"
        WhereCondition1=" and usertypeId='3'"
        WhereCondition3=" and usertypeId='4'"

        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        data2 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition1,""," ",startlimit,endlimit)
        enterprenuerMasterCount=data2["result"][0]
        data3 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition3,""," ",startlimit,endlimit)
        studentMasterCount=data3["result"][0]
        
        


        if data:           
            Data = {"policyMakerMasterCount":policyMakerMasterCount,"enterprenuerMasterCount":enterprenuerMasterCount,"studentMasterCount":studentMasterCount,"status":"true"}
            return Data
        else:
            output = {"result":"No Data Found","status":"false"}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output


@app.route('/allSubAdmins', methods=['POST'])
def allSubAdmins():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("addAdmin",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userTypeId=inputdata["userTypeId"]
            column="*"
            WhereCondition=" and userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
            print(data)

            if (data["status"]!="false"): 
                print("111111111111111")          
                Data = {"result":data["result"],"status":"true"}
                return Data
            else:
                output = {"result":"No Data Found","status":"false"}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output

                  



@app.route('/policyMakerPannel', methods=['GET'])
def policyMakerPannel():
    try:
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='5'"
        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        
        
        


        if data:           
            Data = {"policyMakeruserCount":policyMakerMasterCount,"status":"true"}
            return Data
        else:
            output = {"result":"No Data Found","status":"false"}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output 


@app.route('/allpolicyMakers', methods=['GET'])
def allpolicyMakers():
    try:
        column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.city,um.country,"
        column=column+"pm.aboutProfile,pm.organization,pm.designation"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='5' and pm.userId=um.userId "
        
        data = databasefile.SelectQueryOrderby("userMaster as um,policyMakerMaster as pm",column,WhereCondition,""," ",startlimit,endlimit)
      
        
        


        if (data["status"]!="false"):           
            Data = {"result":data["result"],"status":"true"}
            return Data
        else:
            output = {"result":"No Data Found","status":"false"}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output                 

@app.route('/enterprenuerMasterPannel', methods=['GET'])
def enterprenuerMasterPannel():
    try:
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='6'"
        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        
        
        


        if data:           
            Data = {"enterprenueruserCount":policyMakerMasterCount,"status":"true"}
            return Data 
        else:
            output = {"result":"No Data Found","status":"false"}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output  


@app.route('/allenterprenuers', methods=['GET'])
def allenterprenuer():
    try:
        column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.country,um.city,"
        column=column+"pm.areaOfActivity,pm.profileCategoryId,pm.designation,pm.interestId"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='6' and pm.userId=um.userId "
        
        data = databasefile.SelectQueryOrderby("userMaster as um,enterprenuerMaster as pm",column,WhereCondition,""," ",startlimit,endlimit)
     
        
        
        


        if (data["status"]!="false"):           
            Data = {"result":data["result"],"status":"true"}
            return Data
        else:
            output = {"result":"No Data Found","status":"false"}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output         


@app.route('/studentMasterPannel', methods=['GET'])
def studentMasterPannel():
    try:
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='7'"
        
        data = databasefile.SelectQueryOrderby("userMaster ",column,WhereCondition,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        
        
        


        if data:           
            Data = {"studentuserCount":policyMakerMasterCount,"status":"true"}
            return Data
        else:
            output = {"result":"No Data Found","status":"false"}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output  




@app.route('/allstudents', methods=['GET'])
def allstudents():
    try:
        column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,"
        column=column+" pm.address,pm.qualification,pm.batchofQualification,pm.institutionName,pm.universityAddress,pm.universityName,pm.interestId "
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='7' and pm.userId=um.userId "
        
        data = databasefile.SelectQueryOrderby("userMaster as um,studentMaster as pm",column,WhereCondition,""," ",startlimit,endlimit)
      
        
        


        if (data["status"]!="false"):           
            Data = {"result":data["result"],"status":"true"}
            return Data
        else:
            output = {"result":"No Data Found","status":"false"}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"result":"something went wrong","status":"false"}
        return output                   





@app.route('/DeleteSubAdmin', methods=['POST'])
def DeleteUser():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userTypeId','userId']
        print(inputdata,"B")
        commonfile.writeLog("DeleteSubAdmin",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]

            WhereCondition = " and usertypeId='" + str(userTypeId) + "' and  userId = '" + str(userTypeId) + "' "
            data = databasefile.DeleteQuery("userMaster",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()
 


@app.route('/DeletePolicyMakers', methods=['POST'])
def DeletePolicyMakers():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userTypeId','userId']
        print(inputdata,"B")
        commonfile.writeLog("DeletePolicyMakers",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]
            WhereCondition1=  " and  userId = '" + str(userTypeId) + "' "  
            data2=   databasefile.DeleteQuery("policyMakerMaster",WhereCondition1)                  
            WhereCondition = " and usertypeId='" + str(userTypeId) + "' and  userId = '" + str(userTypeId) + "' "
            data = databasefile.DeleteQuery("userMaster",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()

@app.route('/DeleteEnterpeneurs', methods=['POST'])
def DeleteEnterpeneurs():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userTypeId','userId']
        print(inputdata,"B")
        commonfile.writeLog("DeleteEnterpeneurs",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]
            WhereCondition1=  " and  userId = '" + str(userTypeId) + "' "  
            data2=   databasefile.DeleteQuery("enterprenuerMaster",WhereCondition1)                  
            WhereCondition = " and usertypeId='" + str(userTypeId) + "' and  userId = '" + str(userTypeId) + "' "
            data = databasefile.DeleteQuery("userMaster",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 


@app.route('/DeleteStudents', methods=['POST'])
def DeleteStudents():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userTypeId','userId']
        print(inputdata,"B")
        commonfile.writeLog("DeleteStudents",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]
            WhereCondition1=  " and  userId = '" + str(userTypeId) + "' "  
            data2=   databasefile.DeleteQuery("studentMaster",WhereCondition1)                  
            WhereCondition = " and usertypeId='" + str(userTypeId) + "' and  userId = '" + str(userTypeId) + "' "
            data = databasefile.DeleteQuery("userMaster",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()    


# @app.route('/UpdateUser', methods=['POST'])
# def UpdateUser():
#     try:
#         startlimit,endlimit="",""
#         if request.data:
#             inputdata = commonfile.DecodeInputdata(request.get_data())         
#             keyarr = ['userId','userName','mobileNo','email','gender','country','city','userTypeId']
#             commonfile.writeLog("UpdateUser",inputdata,0)
#             msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)

#             if msg == "1":
                 #organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","","","","","","","","",""
                 #address,qualification,batchofQualification,instituteName,universityName,universityAddress="","","","","",""

#                 
#                 UserId = inputdata["userId"]
#                 UserName = inputdata["userName"]
#                 MobileNo = inputdata["mobileNo"]
#                 Email = inputdata["email"] 
#                 Gender = inputdata["gender"]
#                 # Password = inputdata["password"]
#                 Country = inputdata["country"] 
                   #City = inputdata["city"] 
                   #UserTypeId= inputdata["city"] 
            
#                 WhereCondition = " and userId = '" + str(UserId) + "' and  userrTypeId = '" + str(UserTypeId) + " '"             
#                 column = " email = '" + str(Email) + "',gender = '" + str(Gender) + "',country = '" + str(Country) + "', "               
#                 column = column + " userName = '" + str(UserName) + "',mobileNo = '" + str(MobileNo) + "' "

#                 data = databasefile.UpdateQuery("UserMaster",column,WhereCondition)
                  #if (UserTypeId ==5):
                        #  WhereCondition = " and userId = '" + str(UserId) + "' "

                  #if  (UserTypeId == 6):
                           # WhereCondition = " and userId = '" + str(UserId) + "'

                  #if (UserTypeId ==7):
                           #WhereCondition = " and userId = '" + str(UserId) + "'  
                
#                 if data != "0":
#                     column = 'UserId,UserName,UserType'
#                     data = databasefile.SelectQuery("UserMaster",column,WhereCondition,"",startlimit,endlimit)                  
#                     return data
#                 else:
#                     return commonfile.Errormessage()
#             else:
#                  return msg
#         else:
#             return commonfile.InputKeyNotFoundMsg()

#     except Exception as e :
#         print("Exception--->" + str(e))                                  
#         return commonfile.Errormessage()




       
@app.route('/createPost', methods=['POST'])
def addAdmin():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        startlimit,endlimit="",""
        keyarr = ['adminName','userTypeId','emailId','password']
        commonfile.writeLog("addAdmin",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            Name = inputdata["adminName"]
            userTypeId = inputdata["userTypeId"]
            Email = inputdata["emailId"]
            password = inputdata["password"]

            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and email = '" + str(Email) + "' or password = '" + str(password) + "'"
            count = databasefile.SelectCountQuery("userMaster",WhereCondition,"")
            
            if int(count) > 0:
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





if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5031,debug=True)
    
