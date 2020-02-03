from flask import Flask,request,abort
from flask import Flask, send_from_directory, abort
import uuid
import json
import json
import math, random
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
from sendgrid import SendGridAPIClient
from sendgrid.helpers.mail import Mail

from flask import Flask, render_template
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'

@app.route("/postImage/<image_name>")
def postImage(image_name):
    try:
        return send_from_directory('postImage', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)

def profilePic(image_name):
    try:
        return send_from_directory('profilePic', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)
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
            DeviceId,DeviceType,Os,ImeiNo,ipAddress,Country,City,organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","","0","","","","","","",""
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
                if Country == "":
                    Country='0'  
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


                print(interestId)



               

                columns = " userId, userName, mobileNo, email, userTypeId, gender, password, deviceType, os, ipAddress, countryId, city, deviceid, imeiNo "          
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
                            columns="userId,areaOfActivity,profileCategoryId,designation"
                            values=" '" + str(y["userId"]) + "','" + str(areaofActivity) + "','" + str(profileCategoryId) + "','" + str(designation) + "'"
                            data2=databasefile.InsertQuery("enterprenuerMaster",columns,values)
                            for i in interestId:
                                column="userId,userTypeId,interestId"
                                values=" '" + str(y["userId"]) + "','" + str('6') + "','" + str(i) + "'"
                                data4=databasefile.InsertQuery("userInterestMapping ",column,values) 



                        if (y["userTypeId"]== 7):
                            columns="userId,address,qualificationId,batchOfQualification,institutionName,universityAddress,universityId"
                            values=" '" + str(y["userId"]) + "','" + str(address) + "','" + str(qualification) + "','" + str(batchofQualification) + "','" + str(instituteName)+ "','" + str(universityAddress)+ "','" + str(universityName)+ "'"
                            data3 = databasefile.InsertQuery("studentMaster",columns,values) 
                            for i in interestId:
                                column="userId,userTypeId,interestId"
                                values=" '" + str(y["userId"]) + "','" + str('7') + "','" + str(i) + "'"
                                data5=databasefile.InsertQuery("userInterestMapping ",column,values) 

                             


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
        column=  "us.mobileNo,us.userName,us.email,um.id as userTypeId,us.userId as userId"
        whereCondition= " and us.email = '" + mobile + "' and us.password = '" + password + "'  and  us.userTypeId=um.id"
        groupby,startlimit,endlimit="","",""
        loginuser=databasefile.SelectQuery("userMaster as us,userTypeMaster as um",column,whereCondition, groupby,startlimit,endlimit)
        
               
      
        if  (loginuser["status"]!="false"):   
            Data = {"status":"true","message":"","result":loginuser["result"]}                  
            return Data
        else:
            data = {"status":"false","message":"No Data Found","result":""}
            return data

    except KeyError as e:
        print("Exception---->" +str(e))        
        output = {"status":"false","message":"No Data Found","result":""}
        return output 
    
    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
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
            
            WhereCondition = " and email = '" + str(Email) + "' and password = '" + str(password) + "'"
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
                    print(data)
                    Data = {"status":"true","message":"","result":data["result"]}                  
                    return Data
                else:
                    return commonfile.Errormessage()
        else:
            return msg 
    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output



@app.route('/adminPannel', methods=['GET'])
def adminPannel():
    try:
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and userTypeId>'1'  and usertypeId<'5'"
       
        WhereCondition4=" and usertypeId='5'"
        WhereCondition5=" and usertypeId='6'"
        WhereCondition6=" and usertypeId='7'"

        


        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        totalsubAdmins=data["result"][0]
       
        data2 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition4,""," ",startlimit,endlimit)
        subAdmins2=data2["result"][0]
        data3 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition5,""," ",startlimit,endlimit)
        subAdmins3=data3["result"][0]
        data4 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition6,""," ",startlimit,endlimit)
        subAdmins4=data4["result"][0]
        
        


        if data:           
            Data1 = {"totalAdmins":totalsubAdmins,"policyMakerMasterCount":subAdmins2,"enterprenuerMasterCount":subAdmins3,"studentMasterCount":subAdmins4}
            Data = {"status":"true","message":"","result":Data1}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
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
            userId,whereCondition="",""
            userTypeId=inputdata["userTypeId"]
            if 'userId' in inputdata:
                userId=inputdata['userId']
                whereCondition=" and userId='" + str(userId) + "' "
            
            column="*"
            WhereCondition=" and userTypeId='" + str(userTypeId) + "'"+whereCondition
            data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
            print(data)

            if (data["status"]!="false"): 
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output



@app.route('/allSubAdmins1', methods=['POST'])
def allSubAdmins1():
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
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output




@app.route('/totalSubAdmins', methods=['GET'])
def totalAdmins():
    try:
       
        startlimit,endlimit="0","5"
        msg="1"
       
        if msg =="1":
            orderby="um.id"
           
            column="um.userName as userName,ut.userName as userType,um.userTypeId as userTypeId,um.id,um.email as email"
            WhereCondition=" and um.userTypeId>'1' and um.userTypeId<'5' and um.userTypeId=ut.id "
            data = databasefile.SelectQueryOrderby("userMaster as um,userTypeMaster as ut",column,WhereCondition,"",startlimit,endlimit,orderby)
            count=len(data["result"])
           
            #        
                    #i.update("userTypeId":usertypeId)




            print(count)

            if (data["status"]!="false"): 
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output



                  



@app.route('/policyMakerPannel', methods=['GET'])
def policyMakerPannel():
    try:
        userId=request.args['userId']
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='5'"
        whereCondition=" and usertypeId='2'and status='1' and  approvedUserId='" + str(userId) + "'"
        whereCondition2=" and usertypeId='2'and status='2' and  approvedUserId='" + str(userId) + "'"

        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
        data3 = databasefile.SelectQueryOrderby("approvedBy",column,whereCondition,""," ",startlimit,endlimit)
        data4= databasefile.SelectQueryOrderby("approvedBy",column,whereCondition2,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        postCounts=data2["result"][0]
        approvedPosts=data3["result"][0]
        rejectedPost=data4["result"][0]
        data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedPost":approvedPosts,"rejectedPost":rejectedPost}]
        # data2.append({"totalpostCounts":postCounts})
        # data2.append({"approvedPost":approvedPosts})
        # data2.append({"rejectedPost":rejectedPost})

        

        if data:           
            Data = {"status":"true","message":"","result":data2}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 


@app.route('/allpolicyMakers', methods=['GET'])
def allpolicyMakers():
    try:
        column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.city,um.countryId,um.email,"
        column=column+"pm.aboutProfile,pm.organization,pm.designation,um.status"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='5' and pm.userId=um.userId "

        
        data = databasefile.SelectQueryOrderby("userMaster as um,policyMakerMaster as pm",column,WhereCondition,""," ",startlimit,endlimit)

      
        
        


        if (data!=0):
            for i in data["result"]:
                userId=i["userId"]
                column="count(*) as count"
                whereCondition=" and pm.usertypeId='5' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                print(data1,"")
                count=data1["count"]

                i["noOfPosts"]=count


            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output                 

@app.route('/enterprenuerMasterPannel', methods=['GET'])
def enterprenuerMasterPannel():
    try:
        userId=request.args['userId']
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='6'"
        whereCondition=" and usertypeId='3'and status='1' and  approvedUserId='" + str(userId) + "'"
        whereCondition2=" and usertypeId='3'and status='2' and  approvedUserId='" + str(userId) + "'"

        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
        data3 = databasefile.SelectQueryOrderby("approvedBy",column,whereCondition,""," ",startlimit,endlimit)
        data4= databasefile.SelectQueryOrderby("approvedBy",column,whereCondition2,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        postCounts=data2["result"][0]
        approvedPosts=data3["result"][0]
        rejectedPost=data4["result"][0]
        data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedPost":approvedPosts,"rejectedPost":rejectedPost}]
        # data2.append({"totalpostCounts":postCounts})
        # data2.append({"approvedPost":approvedPosts})
        # data2.append({"rejectedPost":rejectedPost})

        

        if data:           
            Data = {"status":"true","message":"","result":data2}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output  


@app.route('/allenterprenuers', methods=['GET'])
def allenterprenuer():
    try:
        column="um.mobileNo as mobileNo,um.email ,um.userName as userName,um.password as password,um.userId,um.gender,um.countryId,um.city,"
        column=column+"pm.areaOfActivity,pm.profileCategoryId,pm.designation,um.status"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='6' and pm.userId=um.userId "
        
        data = databasefile.SelectQueryOrderby("userMaster as um,enterprenuerMaster as pm",column,WhereCondition,""," ",startlimit,endlimit)

        if (data!=0):
            for i in data["result"]:
                userId=i["userId"]
                column="count(*) as count"
                whereCondition=" and pm.usertypeId='6' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                print(data1,"")
                count=data1["count"]

                i["noOfPosts"]=count
    
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output  


@app.route('/allenterprenuers1', methods=['GET'])
def allenterprenuer1():
    try:
        column="um.mobileNo as mobileNo,um.email ,um.userName as userName,um.password as password,um.userId,um.gender,um.countryId,um.city,"
        column=column+"pm.areaOfActivity,pcm.name as profileCategory,pm.designation,um.status"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='6' and pm.userId=um.userId and pcm.id=pm.profileCategoryId"
        
        data = databasefile.SelectQueryOrderby("userMaster as um,enterprenuerMaster as pm,profileCategoryMaster as pcm",column,WhereCondition,""," ",startlimit,endlimit)

        if (data!=0):
            for i in data["result"]:
                userId=i["userId"]
                column="count(*) as count"
                whereCondition=" and pm.usertypeId='6' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                print(data1,"")
                count=data1["count"]

                i["noOfPosts"]=count
                columns=" im.name " 
                WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",columns,WhereCondition,"","","","")
                
                i["userInterest"]=[]
                for j in data5["result"]:
                    j.append(i["name"])
    
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output         




@app.route('/studentMasterPannel', methods=['GET'])
def studentMasterPannel():
    try:
        userId=request.args['userId']
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='7'"
        whereCondition=" and usertypeId='4'and status='1' and  approvedUserId='" + str(userId) + "'"
        whereCondition2=" and usertypeId='4'and status='2' and  approvedUserId='" + str(userId) + "'"

        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
        data3 = databasefile.SelectQueryOrderby("approvedBy",column,whereCondition,""," ",startlimit,endlimit)
        data4= databasefile.SelectQueryOrderby("approvedBy",column,whereCondition2,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        postCounts=data2["result"][0]
        approvedPosts=data3["result"][0]
        rejectedPost=data4["result"][0]
        data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedPost":approvedPosts,"rejectedPost":rejectedPost}]
        # data2.append({"totalpostCounts":postCounts})
        # data2.append({"approvedPost":approvedPosts})
        # data2.append({"rejectedPost":rejectedPost})

        

        if data:           
            Data = {"status":"true","message":"","result":data2}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output  




@app.route('/allstudents', methods=['GET'])
def allstudents():
    try:
        column="um.mobileNo as mobileNo,um.email,um.userName as userName,um.password as password,um.userId,um.gender,"
        column=column+" pm.address,pm.qualificationId,pm.batchofQualification,pm.institutionName,pm.universityAddress,pm.universityId,um.status "
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='7' and pm.userId=um.userId"
        
        data = databasefile.SelectQueryOrderby("userMaster as um,studentMaster as pm",column,WhereCondition,""," ",startlimit,endlimit)
      
        
        


        if (data!=0):
            for i in data["result"]:
                userId=i["userId"]
                column="count(*) as count"
                whereCondition=" and pm.usertypeId='7' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                print(data1,"")
                count=data1["count"]

                i["noOfPosts"]=count       
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
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


@app.route('/UpdateUser', methods=['POST'])
def UpdateUser():
    try:
        print('1')
        startlimit,endlimit="",""
        if request.data:
            print('2')
            inputdata = commonfile.DecodeInputdata(request.get_data())         
            keyarr = ['userId','userName','mobileNo','email','gender','country','city','userTypeId']
            commonfile.writeLog("UpdateUser",inputdata,0)
            msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
            if msg == "1":
                print('3')
                organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","",""
                address,qualification,batchofQualification,institutionName,universityName,universityAddress="","","","","",""
                UserId = inputdata["userId"]
                UserName = inputdata["userName"]
                MobileNo = inputdata["mobileNo"]
                Email = inputdata["email"] 
                Gender = inputdata["gender"]
                # Password = inputdata["password"]
                Country = inputdata["country"] 
                City = inputdata["city"] 
                UserTypeId= inputdata["userTypeId"] 
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
                    institutionName = inputdata['institutionName']  

                if 'universityName' in inputdata:                    
                    universityName = inputdata['universityName']

                if 'universityAddress' in inputdata:                    
                    universityAddress = inputdata['universityAddress']
                print('A')
                WhereCondition = " and userId = '" + str(UserId) + "' and  userTypeId = '" + str(UserTypeId) + " '"
                column = " email = '" + str(Email) + "',gender = '" + str(Gender) + "',countryId = '" + str(Country) + "', "
                column = column +  " userName = '" + str(UserName) + "',city = '" + str(City) + "',mobileNo = '" + str(MobileNo) + "'"
                data = databasefile.UpdateQuery("userMaster",column,WhereCondition)
                print(data,'B')
                if (UserTypeId == 5):
                    print('c')
                    WhereCondition = " and userId = '" + str(UserId) + "'"
                    column =" organization = '" + str(organization) + "',aboutProfile='" + str(aboutProfile) + "',designation='" + str(designation) + "'"
                    output=databasefile.UpdateQuery("policyMakerMaster",column,WhereCondition)
                if (UserTypeId == 6):
                    WhereCondition = " and userId = '" + str(UserId) + "'"
                    column=" designation='" + str(designation) + "' , areaOfActivity ='" + str(areaofActivity) + "',profileCategoryId='" + str(profileCategoryId) + "'"
                    output=databasefile.UpdateQuery("enterprenuerMaster",column,WhereCondition)
                    for i in interestId:
                        WhereCondition = " and userId = '" + str(UserId) + "' and userTypeId='6'"
                        column="interestId='" + str(i) + "'"
                        output1=databasefile.UpdateQuery("userInterestMapping ",column,WhereCondition)

                if (UserTypeId == 7):
                    WhereCondition = " and userId = '" + str(UserId) + "'"
                    column=" address='" + str(address) + "',qualificationId  = '" + str(qualification) + "', batchOfQualification ='" + str(batchofQualification) + "', institutionName ='" + str(institutionName) + "',universityId='" + str(universityName) + "',universityAddress='" + str(universityAddress) + "',interestId ='" + str(interestId) + "'"  
                    output=databasefile.UpdateQuery("studentMaster",column,WhereCondition)
                    for i in interestId:
                        WhereCondition = " and userId = '" + str(UserId) + "' and userTypeId='7'"
                        column="interestId='" + str(i) + "'"
                        output=databasefile.UpdateQuery("userInterestMapping ",column,WhereCondition)             
                if data != "0":
                    column = 'userId,userName,userTypeId'
                    data = databasefile.SelectQuery("userMaster",column,WhereCondition,"",startlimit,endlimit)                  
                    return data
                else:
                    return commonfile.Errormessage()
            else:
                return msg
        else:
            return commonfile.InputKeyNotFoundMsg()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()

@app.route('/userPost', methods=['POST'])
def userPost():

    try: 
        startlimit,endlimit="",""   
        inputdata = request.form.get('data') 
        print("===========================",inputdata)      
        inputdata = json.loads(inputdata)
        print("111111111111111111111111111",inputdata)   
        
        keyarr = ['userTypeId','userId','postTitle','postDescription','showuserTypeId','flag']
        commonfile.writeLog("userPost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":      
            userTypeId = inputdata["userTypeId"]
            UserId = inputdata["userId"]
            postTitle = inputdata["postTitle"] 
            postDescription = inputdata["postDescription"]
            showuserTypeId = inputdata["showuserTypeId"]
            flag = inputdata["flag"]
            print('====',flag)
            
           

            PostId = commonfile.CreateHashKey(postTitle,postDescription)
            
            WhereCondition = " and postTitle = '" + str(postTitle) + "' and postDescription = '" + str(postDescription) + "'"
            count = databasefile.SelectCountQuery("userPost",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.postTitlepostDescriptionAlreadyExistMsg()
            else:
                print("qqqqqqqqqqqqqqqqqqqqq")
                postImage,postFilePath,PicPath,filename,postId,postId1="","","","","",""
                
                
                if 'userTypeId' in inputdata:                                    
                    userTypeId = inputdata['userTypeId']
                if 'userId' in inputdata:                                    
                    UserId = inputdata['userId']
                if 'postId' in inputdata:
                    postId1 = inputdata["postId"]

               
                if 'postImage' in request.files:
                    print("immmmmmmmmmmmmmmmm")
                    file = request.files.get('postImage')
                        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","")
                    print(filename,filename) 
                    FolderPath = ConstantData.GetPostImagePath(filename)
                    filepath = '/postImage/' + filename 
                    file.save(FolderPath)
                    PicPath = filepath
                    print(PicPath)
                    
               

                if flag == 'n':
                    columns = " userId,userTypeId,postId, postTitle , postDescription,postImage, postImagePath  "          
                    values = " '" + str(UserId) + "','" + str(userTypeId) + "','" + str(PostId) + "','" + str( postTitle) + "','" + str(postDescription) + "','" + str(filename) + "', "            
                    values = values + " '" + str(PicPath) + "'"
                    data = databasefile.InsertQuery("userPost",columns,values)
                    if data != "0":
                        column = '*'
                        WhereCondition = " and postTitle = '" + str(postTitle) + "' and postDescription = '" + str(postDescription) + "'"
                        
                        data11 = databasefile.SelectQuery("userPost",column,WhereCondition,"",startlimit,endlimit)
                        if data11["status"]!="false":
                            y=data11["result"][0]
                            column="userId,showuserTypeId,postId"
                            values= " '" + str(y["userId"]) + "','" + str(showuserTypeId) + "','" + str(y["postId"]) + "'"
                            data2 = databasefile.InsertQuery("postUserTypeMapping",column,values)
                        else:
                            return commonfile.Errormessage()
                        return data11
                if flag == 'u':
                    WhereCondition = " and postId = '" + str(postId1) + "' and  userTypeId = '" + str(userTypeId) + " '"
                    column = " postTitle = '" + str(postTitle) + "',postDescription = '" + str(postDescription) + "',postImage = '" + str(filename) + "', "
                    column = column +  " postImagePath = '" + str(PicPath) + "'"
                    data = databasefile.UpdateQuery("userPost",column,WhereCondition)
                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 

@app.route('/allPosts1', methods=['POST'])
def allPosts():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
       
        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("allPosts",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            print(orderby)
            
            userTypeId=inputdata["userTypeId"]
            column="um.userName,um.email,um.country,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId,date_format(pm.dateCreate,'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userTypeId=pm.userTypeId and  pm.userId=um.userId and  pm.userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            
          

            if (data["status"]!="false"): 
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output   


@app.route('/allPosts', methods=['POST'])
def allPosts1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("allPosts",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            if 'postId' in inputdata:
                postId=inputdata['postId']
                whereCondition=" and pm.postId='" + str(postId) + "' "


            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId,date_format(pm.dateCreate,'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userTypeId=pm.userTypeId and pm.userId=um.userId and pm.userTypeId='" + str(userTypeId) + "'" +whereCondition
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            
            print("11111111111111")
            print("data",data)

          

            if (data!=0):
                for i in data["result"]:
                    if (i["status"] == 1):
                        print(i["postId"])
                        column="um.userName as approvedBy"
                        WhereCondition="and um.userTypeId=ap.userTypeId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        i["approvedBy"]=data1["approvedBy"]
                        print(data1)
                    if (i["status"]==2):
                        column="um.userName as rejectedBy"
                        WhereCondition="and um.userTypeId=ap.userTypeId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        i["rejectedBy"]=data1["rejectedBy"]
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output  


@app.route('/allUsersPost', methods=['POST'])
def allPosts2():
    try:
        #inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="0","8"

        #keyarr = ['']
        #print(inputdata,"B")
        #commonfile.writeLog("allPosts",inputdata,0)
      
        #msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        msg="1"
        if msg =="1":
            orderby="pm.id"
            
            
            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId,date_format(pm.dateCreate,'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userTypeId=pm.userTypeId and pm.userId=um.userId "
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            print("11111111111111")

          

            if (data!=0):
                for i in data["result"]:
                    if (i["status"] == 1):
                        print(i["postId"])
                        column="um.userName as approvedBy"
                        WhereCondition="and um.userTypeId=ap.userTypeId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        i["approvedBy"]=data1["approvedBy"]
                        print(data1)
                    if (i["status"]==2):
                        column="um.userName as rejectedBy"
                        WhereCondition="and um.userTypeId=ap.userTypeId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        i["rejectedBy"]=data1["rejectedBy"]
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output                                       


@app.route('/myPosts1', methods=['POST'])
def myPosts():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userId','userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("myPosts",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]
            column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(pm.dateCreate,'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
          

            if (data["status"]!="false"):
                
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/myPosts', methods=['POST'])
def myPosts1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userId','userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("myPosts",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]
            column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(pm.dateCreate,'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
          

            if (data!=0): 
                for i in data["result"]:
                    if (i["status"] == 1):
                        print(i["postId"])
                        column="um.userName as approvedBy"
                        WhereCondition="and um.userTypeId=ap.userTypeId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        i["approvedBy"]=data1["approvedBy"]
                        print(data1)
                    if (i["status"]==2):
                        column="um.userName as rejectedBy"
                        WhereCondition="and um.userTypeId=ap.userTypeId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        i["rejectedBy"]=data1["rejectedBy"]

                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output                        


@app.route('/DeletePost', methods=['POST'])
def DeletePost():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userTypeId','userId','postId','showUserTypeId']
        print(inputdata,"B")
        commonfile.writeLog("DeletePost",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]
            postId=inputdata["postId"]
            showUserTypeId=inputdata["showUserTypeId"]

            WhereCondition = " and usertypeId='" + str(userTypeId) + "' and  userId = '" + str(userId) + "' and  postId = '" + str(postId) + "'"
            data = databasefile.DeleteQuery("userPost",WhereCondition)

            whereCondition = " and postId='" + str(postId) +  "'and  userId = '" + str(userId) + "' and  showUserTypeId = '" + str(showUserTypeId) + "'"
            data2 = databasefile.DeleteQuery("postUserTypeMapping",whereCondition)


            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()
 




@app.route('/allQualifications', methods=['GET'])
def allQualifications():
    try:
        columns=" id, qualificationName "
        
        data = databasefile.SelectQueryMaxId("qualificationMaster",columns)
       

        if data:           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 

@app.route('/selectUserTypeMaster', methods=['GET'])
def selectUserTypeMaster():
    try:
        columns=" id, userName "
        
        data = databasefile.SelectQueryMaxId("userTypeMaster",columns)
       

        if data:           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 

@app.route('/allinterests', methods=['POST'])
def allinterests():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        keyarr = ['id']
        startlimit,endlimit="",""
        commonfile.writeLog("allinterests",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            id = inputdata["id"]
            columns=" id, name "
            whereCondition="and interestId = '" + str(id) + "'"
            
            data = databasefile.SelectQueryOrderby("interestMaster",columns,whereCondition,""," ",startlimit,endlimit)
            if (data["status"]!="false"):         
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 

@app.route('/allUniversities', methods=['GET'])
def allUniversities():
    try:
        columns=" id,  universityName  "
        
        data = databasefile.SelectQueryMaxId("universityMaster ",columns)
       

        if data:           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output 


@app.route('/adminDropdown', methods=['GET'])
def adminDropdown():
    try:
        columns=" id,userName  "
        startlimit,endlimit="",""
        whereCondition="and id>1 and id<5"
        
        data = databasefile.SelectQuery("userTypeMaster",columns,whereCondition,"",startlimit,endlimit)
       

        if data:           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output         


@app.route('/allCategories', methods=['GET'])
def allCategories():
    try:
        columns=" id, name "
        
        data = databasefile.SelectQueryMaxId(" profileCategoryMaster ",columns)
       

        if data:           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 

@app.route('/allCountries', methods=['GET'])
def allCountries():
    try:
        columns=" id, countryName "
        
        data = databasefile.SelectQueryMaxId("countryMaster",columns)
       

        if data:           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 

@app.route('/allStatus', methods=['GET'])
def allStatus():
    try:
        columns=" id, name "
        
        data = databasefile.SelectQueryMaxId("statusMaster",columns)
       

        if data:           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 



@app.route('/verifyPost', methods=['POST'])
def verifyPost():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        startlimit,endlimit="",""
        keyarr = ['approvedUserId','postId','userTypeId','id']
        commonfile.writeLog("verifyPost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            approvedUserId = inputdata["approvedUserId"]
            postId = inputdata["postId"]
            userTypeId = inputdata["userTypeId"]
            statusid = inputdata["id"]
            print(statusid,'id')
    
            column = "approvedUserId,postId,userTypeId"                
            values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "'"
            data = databasefile.InsertQuery("approvedBy",column,values)
            if statusid == 1:
                print('1')
                WhereCondition = " and postId = '" + str(postId) + "'"
                column = " status = '" + str("1") + "'"
                data = databasefile.UpdateQuery("approvedBy",column,WhereCondition)
                WhereCondition = " and postId = '" + str(postId) + "'"
                column = " status = '" + str("1") + "'"
                data1 = databasefile.UpdateQuery("userPost",column,WhereCondition)
                if data1!="0":
                    return data1
            if statusid == 2:
                WhereCondition = " and postId = '" + str(postId) + "'"
                column = " status = '" + str("2") + "'"
                data = databasefile.UpdateQuery("approvedBy",column,WhereCondition)
                WhereCondition = " and postId = '" + str(postId) + "'"
                column = " status = '" + str("2") + "'"
                data2 = databasefile.UpdateQuery("userPost",column,WhereCondition)            
                if data2!="0":
                    return data2
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output




@app.route('/updatePost', methods=['POST'])
def updatePost():
    try:
        print('1')
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="","" 
        keyarr = ['postId','userTypeId']
        commonfile.writeLog("updatePost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)

        if msg == "1":
            userTypeId = inputdata["userTypeId"]
            postId = inputdata["postId"]
            columns="*"
            whereCondition="and postId = '" + str(postId) + "'and userTypeId = '" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost",columns,whereCondition,""," ",startlimit,endlimit)
            return data          
        else:
            return msg
    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/updateStatus', methods=['POST'])
def updateStatus():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userTypeId','email','userId']
        print(inputdata,"B")
        commonfile.writeLog("updateStatus",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userTypeId = inputdata["userTypeId"]
            userId = inputdata["userId"]
            email = inputdata["email"]
            column="status"
            whereCondition= " and userTypeId='" + str(userTypeId)+ "' and email = '" + str(email)+ "'  and userId = '" + str(userId)+ "' "
            data=databasefile.SelectQuery("userMaster",column,whereCondition,"",startlimit,endlimit)
            print('AAAA')
            print(data['result'][0]['status'],"status")
            if data['result'][0]['status']==0:
                column="status='1'"
                whereCondition= " and userTypeId='" + str(userTypeId)+ "' and email = '" + str(email)+ "' and userId = '" + str(userId)+ "' "
                output1=databasefile.UpdateQuery("userMaster",column,whereCondition)
                output=output1
                if output!='0':
                    Data = {"status":"true","message":"","result":output["result"]}                  
                    return Data
                else:
                    return commonfile.Errormessage() 

            else:
                column="status='0'"
                whereCondition= " and userTypeId='" + str(userTypeId)+ "' and email = '" + str(email)+ "' and userId = '" + str(userId)+ "' "
                output1=databasefile.UpdateQuery("userMaster",column,whereCondition)
                output=output1    
                if output!='0':
                    Data = {"status":"true","message":"","result":output["result"]}                  
                    return Data
                else:
                    return commonfile.Errormessage()
        else:
            return msg         
 
    except KeyError :
        print("Key Exception---->")   
        output = {"result":"key error","status":"false"}
        return output  

    except Exception as e :
        print("Exceptio`121QWAaUJIHUJG n---->" +str(e))    
        output = {"result":"somthing went wrong","status":"false"}
        return output


@app.route('/generateOtp', methods=['POST'])
def generateOtp():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['email']
        print(inputdata,"B")
        commonfile.writeLog("generateOtp",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            email=str(inputdata["email"])

            digits = "0123456789"
            OTP = ""
            for i in range(4):
                OTP += digits[math.floor(random.random() * 10)]
            message = Mail(
                from_email = 'abcd@gmail.com',
                to_emails = str(email),
                subject = "Otp for Reset Password",
                html_content = '<strong> Otp To Reset Your Password is:' + str(OTP) + ' </strong> <br> .<br> Thanks,medParliament Team')
            sg = SendGridAPIClient('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')
            response = sg.send(message)

          
            column="otp='" + str(OTP)+ "'"
            whereCondition= "  and email = '" + str(email)+ "' "
            output=databasefile.UpdateQuery("userMaster",column,whereCondition)
            columns='otp'
            
            data=databasefile.SelectQuery("userMaster",columns,whereCondition,"",startlimit,endlimit)
            if output!='0':
                Data = {"status":"true","message":"","result":data["result"]}                  
                return Data
            else:
                return commonfile.Errormessage()
        else:
            return msg         
 
    except KeyError :
        print("Key Exception---->")   
        output = {"result":"key error","status":"false"}
        return output  

    except Exception as e :
        print("Exceptio`121QWAaUJIHUJG n---->" +str(e))    
        output = {"result":"somthing went wrong","status":"false"}
        return output

@app.route('/updatePassword', methods=['POST'])
def updatePassword():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['email','password']
        print(inputdata,"B")
        commonfile.writeLog("updatePassword",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            email=str(inputdata["email"])
            password=str(inputdata["password"])
         
            column="password='" + password+ "'"
            whereCondition= "  and email = '" + str(email)+ "' "
            output=databasefile.UpdateQuery("userMaster",column,whereCondition)
                       
            if output!='0':
                Data = {"status":"true","message":commonfile.Successmessage('update'),"result":""}                   
                return Data
            else:
                return commonfile.Errormessage()    
        else:
            return msg         
 
    except KeyError :
        print("Key Exception---->")   
        output = {"result":"key error","status":"false"}
        return output  

    except Exception as e :
        print("Exceptio`121QWAaUJIHUJG n---->" +str(e))    
        output = {"result":"somthing went wrong","status":"false"}
        return output



@app.route('/verifyOtp', methods=['POST'])
def verifyOtp():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['otp','email']
        print(inputdata,"B")
        commonfile.writeLog("verifyOtp",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            otp=str(inputdata['otp'])
            email=str(inputdata['email'])

            column="email"
            whereCondition= " and otp='" + otp+ "' and email='" + email+ "'  "
            data1=databasefile.SelectQuery("userMaster",column,whereCondition,"",startlimit,endlimit)
            if  (data1["status"]!="false"):   
                Data = {"status":"true","message":"","result":data1["result"]}                  
                return Data
            else:
                data = {"status":"false","message":"No Data Found","result":""}
                return data      
        else:
            return msg         
 
    except KeyError :
        print("Key Exception---->")   
        output = {"result":"key error","status":"false"}
        return output  

    except Exception as e :
        print("Exceptio`121QWAaUJIHUJG n---->" +str(e))    
        output = {"result":"somthing went wrong","status":"false"}
        return output          

@app.route('/userProfile', methods=['POST'])
def userProfile():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="0","5"
        keyarr = ['userId','userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("userProfile",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            #orderby="pm.id"
            userId=inputdata['userId']
            userTypeId=int(inputdata['userTypeId'])
            print(userTypeId,'--------',type(userTypeId))
            if userTypeId == 5:
                column="um.userName,um.email,cm.countryName,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"ms.organization,"
                column=column+" ms.aboutProfile, ms.designation"
                WhereCondition=" and cm.id=um.countryId and um.userId=ms.userId and um.userId='" + str(userId) + "'"
                data1 = databasefile.SelectQueryOrderby("userMaster um,policyMakerMaster ms,countryMaster cm",column,WhereCondition,"",startlimit,endlimit,"")
                print("===========================",data1)
                
                orderby="ab.id"
                column="*"
                whereCondition="and ab.userId='" + str(userId)+ "'"
                data4 = databasefile.SelectQueryOrderby("userPost as ab",column,whereCondition,"",startlimit,endlimit,orderby)
                print("++++++++++++++++++++++++++++++++",data4)
                y=type(data4)
                print(type(data4))

                if  data4==0:
                    print("qqqqqqqqqqqqqqqqqqqqqqqqq")
                    data4={"result":'No Posts till now'}
                   
                
                data2={"userProfile":data1["result"],"userPost":data4["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()
            if userTypeId == 6:
                print('HELLO')
                column="um.userName,um.email,pcm.name as profileCategory,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"em.designation,"
                column=column+" em.areaOfActivity"
                WhereCondition=" and pcm.id=em.profileCategoryId and um.userId=em.userId and um.userId='" + str(userId) + "'"
                data1 = databasefile.SelectQueryOrderby("userMaster um,enterprenuerMaster em,profileCategoryMaster pcm",column,WhereCondition,"",startlimit,endlimit,"")
                print(data1)
                
                column=" im.name " 
                WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",column,WhereCondition,"","","","")
                
                data1["result"][0]["userInterest"]=[]
                for i in data5["result"]:
                    data1["result"][0]["userInterest"].append(i["name"])
                
                
                orderby="ab.id"
                column="*"
                whereCondition="and ab.userId='" + userId+ "'"
                data4 = databasefile.SelectQueryOrderby("userPost as ab",column,whereCondition,"",startlimit,endlimit,orderby)
                if  data4==0:
                    print("qqqqqqqqqqqqqqqqqqqqqqqqq")
                    data4={"result":'No Posts till now'}
                data2={"userProfile":data1["result"],"userPost":data4["result"],"userInterest":data5["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()
            if userTypeId == 7:
                column="um.userName,um.email,un.universityName,qm.qualificationName,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"sm.address,"
                column=column+" sm.batchOfQualification, sm.institutionName, sm.universityAddress"
                WhereCondition="  and un.id=sm.universityId and qm.id=sm.qualificationId and um.userId=sm.userId and um.userId='" + str(userId) + "'"
                data1 = databasefile.SelectQueryOrderby("userMaster um,studentMaster sm,universityMaster un,qualificationMaster qm",column,WhereCondition,"",startlimit,endlimit,"")
                print(data1)
                
               
                column=" im.name " 
                WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",column,WhereCondition,"","","","")
                data1["result"][0]["userInterest"]=[]
                for i in data5["result"]:
                    data1["result"][0]["userInterest"].append(i["name"])
                
                
                
                
                orderby="ab.id"
                column="*"
                whereCondition="and ab.userId='" + userId+ "'"
                data4 = databasefile.SelectQueryOrderby("userPost as ab",column,whereCondition,"",startlimit,endlimit,orderby)
                if  data4==0:
                    
                    data4={"result":'No Posts till now'}
                if data1["result"][0]["profilePic"]==None:
                    data1["result"][0]["profilePic"]=GetBaseURL+"/profilePic/defaultPic.jpg"
                data2={"userProfile":data1["result"],"userPost":data4["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()
            else:
                data = {"status":"false","message":"userTypeId is not correct.","result":""}
                return data 
        else:
            return msg         
 
    except KeyError :
        print("Key Exception---->")   
        output = {"result":"key error","status":"false"}
        return output  

    except Exception as e :
        print("Exceptio`121QWAaUJIHUJG n---->" +str(e))    
        output = {"result":"somthing went wrong","status":"false"}
        return output 

@app.route('/changeProfilePic', methods=['POST'])
def changeProfilePic():

    try: 
          
        inputdata = request.form.get('data')       
        
        keyarr = ["userId"]
      
        inputdata = json.loads(inputdata)
        commonfile.writeLog("changeProfilePic",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":  
            PicPath = ""
         
            UserId= inputdata["userId"]
            WhereCondition = " and userId = '" + str(UserId)  + "'"
            # count = databasefile.SelectCountQuery("UserMaster",WhereCondition,"")
            
            
            if 'postImage' in request.files:  
                
                file = request.files.get('postImage')        
                filename = file.filename or ''  
                print(filename)               
                #filename= str(UserId)+".png"
                #filename = filename.replace("'","") 

                #folder path to save campaign image
                FolderPath = ConstantData.GetProfilePicPath(filename)  

                filepath = '/profilePic/' + filename    
                print(file,FolderPath,filepath)
                file.save(FolderPath)
                PicPath = filepath

                
                column = "profilePic = '"  + str(PicPath) + "'"              
                data = databasefile.UpdateQuery("userMaster",column,WhereCondition)        
                
                if data != "0":
                    data["result"]= ConstantData.GetBaseURL()+PicPath
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
    
