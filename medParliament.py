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
import re
from emailActivation import getactivationmail,getactivationmailforDoctor,getactivationmailforDecisionMaker ,\
    getactivationmailforStudent
import razorpay

from flask import Flask, render_template
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'
app.config['MAX_CONTENT_LENGTH'] = 16*1024*1024




@app.route("/AccountVerification",methods=['GET'])
def userId():
    try:
        userId=request.args['userId']
        startlimit,endlimit="",""

        
        column='userTypeId,emailVerificationStatus,userName'
        whereCondition=" and userId='"+ str(userId) +"' "
        output=databasefile.SelectQuery('userMaster',column,whereCondition,"",startlimit,endlimit)
        print(output,"11111111111111111111")
        if output['result']!= "":
            
            if output['result'][0]["emailVerificationStatus"]==0:
                y=output['result'][0]
                if (y['userTypeId'] ==7)  or (y['userTypeId'] =='7') :
                    userName=y['userName']
                    usertypeId=y['userTypeId']
                    column=' emailVerificationStatus=1,status=0 '
                    column2='content'
                    whereCondition2=" and  userTypeId='7'"
                    content=databasefile.SelectQuery1('accountVerficationContent',column2,whereCondition2)
                    data=databasefile.UpdateQuery('userMaster',column,whereCondition)
                    if data !="0":
                        return {"status":"true","userName":userName,"content":content['result']['content'],"message":"Congratulations! Your account has been activated successfully","result":""}
                if (y['userTypeId'] ==13) or  (y['userTypeId'] =='13'):
                    column='emailVerificationStatus=1'
                    userName=y['userName']
                    usertypeId=y['userTypeId']
                    column2='content'
                    whereCondition2=" and  userTypeId='13'"
                    content=databasefile.SelectQuery1('accountVerficationContent',column2,whereCondition2)
                    data1=databasefile.UpdateQuery('userMaster',column,whereCondition)
                    if data1 !="0":
                        return {"status":"true","userName":userName,"content":content['result']['content'],"message":"Your email has been verified. Thank you for verifying your email. Your sign Up details have been sent to our admin  for review. Your account must be approved before you can login. when your account is activated you will get a confirmation mail.","result":""}        
                
                else:
                    column='emailVerificationStatus=1'
                    usertypeId=y['userTypeId']
                    userName=y['userName']
                    column2='content'
                    whereCondition2=" and  userTypeId='"+str(usertypeId)+"'"
                    data1=databasefile.UpdateQuery('userMaster',column,whereCondition)
                    content=databasefile.SelectQuery1('accountVerficationContent',column2,whereCondition2)
                    print("trdyfjukhiljogh",content)
                    if data1 !="0":
                        return {"status":"true","userName":userName,"content":content['result']['content'],"message":"Your email has been verified. Thank you for verifying your email. Your sign Up details have been sent to our admin  for review. Your account must be approved before you can login. when your account is activated you will get a confirmation mail.","result":""}
            else:
                userName=output['result'][0]['userName']
                return {"status":"true","message":"Dear user your Email is Already verified","content":"<h1>Email Verified Already!</h1> <p2> Dear "+str(userName)+",your Email has already been verified<p2> <p3>Thanks for signing up with MedParliament! We're excited to have you with us.</p3> <p4> THANKS & REGARDS </p4> <p5>TEAM MEDPARLIAMENT </p5>","result":""}

            
        else:
            return {"status":"false","message":"Not a valid user","result":""}
    except FileNotFoundError:
        abort(404)




@app.route("/marketingInsights/<image_name>")
def marketingInsights(image_name):
    try:
        return send_from_directory('marketingInsights', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)




@app.route("/notificationimages/<image_name>")
def notificationimages(image_name):
    try:
        return send_from_directory('notificationimages', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)


@app.route("/dashboard/<image_name>")
def marketingInsights1(image_name):
    try:
        return send_from_directory('dashboard', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)        




@app.route("/UpSkillsOpportunity/<image_name>")
def UpSkillsOpportunity(image_name):
    try:
        return send_from_directory('UpSkillsOpportunity', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)





@app.route("/announcementsImage/<image_name>")
def announcementsImage(image_name):
    try:
        return send_from_directory('announcementsImage', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)        



@app.route("/promisingEvent/<image_name>")
def promisingEvent1(image_name):
    try:
        return send_from_directory('promisingEvent', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)




@app.route("/signUpImage/<image_name>")
def signUpVideo1(image_name):
    try:
        return send_from_directory('signUpImage', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)


@app.route("/newsimages/<image_name>")
def newsimages(image_name):
    try:
        return send_from_directory('newsimages', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)

@app.route("/eventImages/<image_name>")
def eventImages(image_name):
    try:
        return send_from_directory('eventImages', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)


@app.route("/gallery/<image_name>")
def gallery(image_name):
    try:
        return send_from_directory('gallery', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)



@app.route("/ourPartners/<image_name>")
def ourPartners1(image_name):
    try:
        return send_from_directory('ourPartners', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)


@app.route("/contentimages/<image_name>")
def contentimages1(image_name):
    try:
        return send_from_directory('contentimages', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)                    

@app.route("/postImage/<image_name>")
def postImage(image_name):
    try:
        return send_from_directory('postImage', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)

@app.route("/profilePic/<image_name>")
def profilePic(image_name):
    try:
        return send_from_directory('profilePic', filename=image_name, as_attachment=False)
    except FileNotFoundError:
        abort(404)
# @app.route('/SignUp1', methods=['POST'])
# def SignUp():

#     try: 
#         startlimit,endlimit="",""   
#         inputdata =  commonfile.DecodeInputdata(request.get_data())  
#         print(inputdata)     
        
#         keyarr = ['userName','mobileNo','email','password','gender',"userTypeId"]
#         commonfile.writeLog("SignUp1",inputdata,0)
#         msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
#         if msg == "1":  
#             DeviceId,DeviceType,Os,ImeiNo,ipAddress,Country,City,organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","","0","","","","","","",""
#             address,qualification,batchofQualification,instituteName,universityName,universityAddress="","","","","",""
#             CompanyName=""
#             areaOfExpertise,hospital,hospitalAddress="","",""
#             occupation,companyAddress="",""
            
         
#             Name = inputdata["userName"]
#             MobileNo = inputdata["mobileNo"]
#             Email = inputdata["email"] 
#             Gender = inputdata["gender"]
#             Password = inputdata["password"]
#             print(Password)

#             UserId = commonfile.CreateHashKey(Email,Name)
            
#             WhereCondition = " and email = '" + str(Email) + "'"
#             count = databasefile.SelectCountQuery("userMaster",WhereCondition,"")
            
#             if int(count) > 0:
#                 print('F')         
#                 return commonfile.EmailMobileAlreadyExistMsg()
#             else:
#                 print("qqqqqqqqqqqqqqqqqqqqq")
                
#                 if 'country' in inputdata:                    
#                     Country = inputdata["country"]
#                 if Country == "":
#                     Country='0'  
#                 if 'city' in inputdata:                    
#                     City = inputdata["city"]  
#                 if 'userTypeId' in inputdata:                                    
#                     userTypeId = inputdata['userTypeId']
#                 if 'gender' in inputdata:                    
#                     Gender = inputdata['gender']                  
#                 if 'deviceid' in inputdata:                   
#                     DeviceId = inputdata['deviceid'] 
#                 if 'deviceType' in inputdata:                    
#                     DeviceType = inputdata['deviceType']
#                     DeviceType = ConstantData.GetDeviceTypeId(DeviceType)
#                 if 'os' in inputdata:                    
#                     Os = inputdata['os'] 
#                 if 'ImeiNo' in inputdata:                    
#                    ImeiNo = inputdata['ImeiNo']
#                 if 'ipAddress' in inputdata:                    
#                    ipAddress = inputdata['ipAddress']
#                 if 'password' in inputdata:                    
#                    Password = inputdata['password']
#                 if 'organization' in inputdata:                    
#                     organization = inputdata['organization']
                
#                 if 'aboutProfile' in inputdata:                    
#                     aboutProfile = inputdata['aboutProfile']

#                 if 'designation' in inputdata:                    
#                    designation = inputdata['designation'] 

#                 if 'areaofActivity' in inputdata:                    
#                     areaofActivity = inputdata['areaofActivity']

#                 if 'profileCategoryId' in inputdata:                    
#                     profileCategoryId = inputdata['profileCategoryId']

#                 if 'interestId' in inputdata:                    
#                     interestId = inputdata['interestId']   

#                 if 'address' in inputdata:                    
#                     address = inputdata['address']

#                 if 'qualification' in inputdata:                    
#                     qualification = inputdata['qualification']

#                 if 'batchofQualification' in inputdata:                    
#                     batchofQualification = inputdata['batchofQualification']

#                 if 'institutionName' in inputdata:                    
#                     instituteName = inputdata['institutionName']  

#                 if 'universityName' in inputdata:                    
#                     universityName = inputdata['universityName']

#                 if 'universityAddress' in inputdata:                    
#                     universityAddress = inputdata['universityAddress']

#                 if 'companyName' in inputdata:                    
#                     CompanyName = inputdata['companyName']

#                 if 'areaOfExpertise' in inputdata:                    
#                     areaOfExpertise = inputdata['areaOfExpertise']

#                 if 'hospital' in inputdata:                    
#                     hospital = inputdata['hospital']

#                 if 'hospitalAddress' in inputdata:                    
#                     hospitalAddress = inputdata['hospitalAddress']
#                 if 'occupation' in inputdata:                    
#                     occupation = inputdata['occupation']

#                 if 'companyAddress' in inputdata:                    
#                     companyAddress = inputdata['companyAddress']
                
#                 if 'multiUserTypeId' in inputdata:
#                     multiUserTypeId = inputdata['multiUserTypeId']

#                 print(interestId)

#                 columns = " status,userId, userName, mobileNo, email, userTypeId, gender, password, deviceType, os, ipAddress, countryId, city, deviceid, imeiNo "          
#                 values = " '" + str(1) + "','"+  str(UserId) + "','" + str(Name) + "','" + str(MobileNo) + "','" + str(Email) + "','" + str(userTypeId) + "','" + str(Gender) + "', "            
#                 values = values + " '" + str(Password) + "','" + str(DeviceType) + "','" + str(Os) + "','" + str(ipAddress) + "','"                 
#                 values = values + str(Country) + "','" + str(City) + "','" + str(DeviceId) + "','" + str(ImeiNo) +"'" 


#                 data = databasefile.InsertQuery("userMaster",columns,values) 

#                 if data != "0":
#                     column = 'userId,userName,userTypeId,profilePic,email'
                    
#                     data = databasefile.SelectQuery("userMaster",column,WhereCondition,"",startlimit,endlimit)
#                     if data["status"]!="false":
#                         y=data["result"][0]
#                         if (y["userTypeId"] == 5):
                            
#                             message = Mail(
#                                 from_email = 'medparliament@medachievers.com',
#                                 to_emails = str(y['email']),
#                                 subject = "Welcome to medParliament",
#                                 html_content = '<strong>Congratulations, you have successfully Signed Up as MedParliaments User <br> <br> You will be notified once your account is  verified by ADMIN </strong> <br> <br> Thanks <br> <br> MedParliament Team')
#                             sg = SendGridAPIClient('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')
#                             response = sg.send(message)

#                             columns="userId,aboutProfile,organization,designation"
#                             values=" '" + str(y["userId"]) + "','" + str(aboutProfile) + "','" + str(organization) + "','" + str(designation) + "'"
#                             data1=databasefile.InsertQuery("policyMakerMaster",columns,values)




#                         if (y["userTypeId"] == 6):
                            
#                             message = Mail(
#                                 from_email = 'medparliament@medachievers.com',
#                                 to_emails = str(y['email']),
#                                 subject = "Welcome to medParliament",
#                                 html_content = '<strong>Congratulations, you have successfully Signed Up as MedParliaments User <br> <br> You will be notified once your account is  verified by ADMIN </strong> <br> <br> Thanks <br> <br> MedParliament Team')
#                             sg = SendGridAPIClient('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')
#                             response = sg.send(message)

#                             columns="userId,areaOfActivity,profileCategoryId,designation,companyName"
#                             values=" '" + str(y["userId"]) + "','" + str(areaofActivity) + "','" + str(profileCategoryId) + "','" + str(designation)+ "','" + str(CompanyName) + "'"
#                             data2=databasefile.InsertQuery("enterprenuerMaster",columns,values)
#                             for i in interestId:
#                                 column="userId,userTypeId,interestId"
#                                 values=" '" + str(y["userId"]) + "','" + str('6') + "','" + str(i) + "'"
#                                 data4=databasefile.InsertQuery("userInterestMapping ",column,values) 



#                         if (y["userTypeId"]== 7):

#                             Y=ConstantData.getwebBaseurl()
#                             Y=Y+"/AccountVerification"+"?userId=" + str(y["userId"]) + " "
#                             message = Mail(
#                                             from_email = 'medparliament@medachievers.com',
#                                             to_emails = str(y["email"]),
#                                             subject = "Account Verification",
#                                             html_content = '<strong> Click on Link: <br> <br> ' + str(Y) + ' </strong> <br> <br> Thanks <br> <br> MedParliament Team')
#                             sg = SendGridAPIClient('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')
#                             response = sg.send(message)
#                             print(response.status_code,'------------------',response.body,"============",response.headers)
                            
#                             print(message)

#                             # column="status='0'"
#                             # dat=databasefile.UpdateQuery('userMaster',column,WhereCondition)


#                             columns="userId,address,qualificationId,batchOfQualification,institutionName,universityAddress,universityId"
#                             values=" '" + str(y["userId"]) + "','" + str(address) + "','" + str(qualification) + "','" + str(batchofQualification) + "','" + str(instituteName)+ "','" + str(universityAddress)+ "','" + str(universityName)+ "'"
#                             data3 = databasefile.InsertQuery("studentMaster",columns,values) 
#                             for i in interestId:
#                                 column="userId,userTypeId,interestId"
#                                 values=" '" + str(y["userId"]) + "','" + str('7') + "','" + str(i) + "'"
#                                 data5=databasefile.InsertQuery("userInterestMapping ",column,values)
                        
#                         if (y["userTypeId"]== 8):

#                             message = Mail(
#                                 from_email = 'medparliament@medachievers.com',
#                                 to_emails = str(y['email']),
#                                 subject = "Welcome to medParliament",
#                                 html_content = '<strong>Congratulations, you have successfully Signed Up as MedParliaments User <br> <br> You will be notified once your account is  verified by ADMIN </strong> <br> <br> Thanks <br> <br> MedParliament Team')
#                             sg = SendGridAPIClient('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')
#                             response = sg.send(message)
                            
#                             columns="userId,qualificationId,designation,areaOfExpertise,hospital,hospitalAddress"
#                             values=" '" + str(y["userId"])+ "','" + str(qualification) + "','" + str(designation) + "','" + str(areaOfExpertise) + "','" + str(hospital)+ "','" + str(hospitalAddress) + "'"
#                             data3= databasefile.InsertQuery("doctorMaster",columns,values)
#                             for i in interestId:
#                                 column="userId,userTypeId,interestId"
#                                 values=" '" + str(y["userId"]) + "','" + str('8') + "','" + str(i) + "'"
#                                 data5=databasefile.InsertQuery("userInterestMapping ",column,values)
                        
#                         if (y["userTypeId"]== 9):
#                             message = Mail(
#                                 from_email = 'medparliament@medachievers.com',
#                                 to_emails = str(y['email']),
#                                 subject = "Welcome to medParliament",
#                                 html_content = '<strong>Congratulations, you have successfully Signed Up as MedParliaments User <br> <br> You will be notified once your account is  verified by ADMIN </strong> <br> <br> Thanks <br> <br> MedParliament Team')
#                             sg = SendGridAPIClient('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')
#                             response = sg.send(message)
#                             columns="userId,designation,occupation,companyName,companyAddress,address"
#                             values=" '" + str(y["userId"])+ "','" + str(designation) + "','" + str(occupation) + "','" + str(CompanyName) + "','" + str(companyAddress)+ "','" + str(address) + "'"
#                             data6=databasefile.InsertQuery("professionalMaster",column,values)
#                             for i in interestId:
#                                 column="userId,userTypeId,interestId"
#                                 values=" '" + str(y["userId"]) + "','" + str('9') + "','" + str(i) + "'"
#                                 data5=databasefile.InsertQuery("userInterestMapping ",column,values)

                        
#                         if (y["userTypeId"]== 13):
#                             message = Mail(
#                                 from_email = 'medparliament@medachievers.com',
#                                 to_emails = str(y['email']),
#                                 subject = "Welcome to medParliament",
#                                 html_content = '<strong>Congratulations, you have successfully Signed Up as MedParliaments User <br> <br> You will be notified once your account is  verified by ADMIN </strong> <br> <br> Thanks <br> <br> MedParliament Team')
#                             sg = SendGridAPIClient('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')
#                             response = sg.send(message)
                            
                               

                             


#                     else:
#                         return commonfile.Errormessage()
#                     if data["result"][0]["profilePic"]==None:
#                         data["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"

#                     data['message']='email verification link has been sent successfully on your email'
#                     return data
#                 else:
#                     return commonfile.Errormessage()
#         else:
#             return msg

#     except Exception as e:
#         print("Exception--->" + str(e))                                  
#         return commonfile.Errormessage() 


# For testing        

def getMailBody(userName, link):
    return '<body style="background-color: #f4f4f4; margin: 0 !important; padding:\
     0 !important;height: 100% !important; width: 100% !important;"> <table border="0" \
     cellpadding="0" cellspacing="0" width="100%" style="border-collapse: collapse \
     !important"> <tr> <td bgcolor="#e1a23c" align="center"> <table border="0" \
     cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">\
      <tr> <td align="center" valign="top" style="padding: 40px 10px 40px 10px;">\
       </td></tr></table> </td></tr><tr> <td bgcolor="#e1a23c" align="center" style="padding:\
        0px 10px 0px 10px;"> <table border="0" cellpadding="0" cellspacing="0" width="100%"\
         style="max-width: 600px;"> <tr> <td bgcolor="#ffffff" align="center" valign="top" \
         style="padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; \
         font-family: \'Roboto\', sans-serif; font-size: 48px; font-weight: 400; letter-spacing:\
          4px; line-height: 48px;"> <h4 style="font-size: 48px; font-weight: 400; margin: 2;">Welcome to MedParliament\
                             </h4> </td></tr></table>\
             </td></tr><tr> <td bgcolor="#f4f4f4" align="center" style="padding: 0px 10px 0px 10px;">\
              <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">\
               <div style="min-height: 250px; text-align: center;background: #ffffff;"> \
               <p style="margin: 0">Hello Dear, ' + str(userName) + '<br><br><strong> Welcome to the MEDPARLIAMENT, the Global Parliament \
               of Healthcare Leadership. </strong><br><br>Thank you for joining our community.\
                <br><br>Please validate your email address by clicking on the button below <br>\
                <br><strong> </p><a href="'+str(link)+'" style="padding: 10px 23px; background:\
                 #e1a23c; color: #fff; display: block; width: 210px; margin: 0 auto; border-radius:\
                  5px;"> Click to Verify Email</button> </div></table> </td></tr><tr> \
                  <td bgcolor="#f4f4f4" align="center" style="padding: 0px 10px 0px 10px;">\
                   <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width:\
                    600px;"> <tr> <td bgcolor="#f4f4f4" align="left" style="padding: 0px 30px 30px 30px;\
                     color: #666666; font-family: \'Roboto\', sans-serif; font-size: 14px; font-weight:\
                      400; line-height: 18px;"></td></tr></table> </td></tr></table></body>'


def getMailBody1(userName, link):
    return '<body style="background-color: #f4f4f4; margin: 0 !important; padding:\
     0 !important;height: 100% !important; width: 100% !important;"> <table border="0" \
     cellpadding="0" cellspacing="0" width="100%" style="border-collapse: collapse \
     !important"> <tr> <td bgcolor="#e1a23c" align="center"> <table border="0" \
     cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">\
      <tr> <td align="center" valign="top" style="padding: 40px 10px 40px 10px;">\
       </td></tr></table> </td></tr><tr> <td bgcolor="#e1a23c" align="center" style="padding:\
        0px 10px 0px 10px;"> <table border="0" cellpadding="0" cellspacing="0" width="100%"\
         style="max-width: 600px;"> <tr> <td bgcolor="#ffffff" align="center" valign="top" \
         style="padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; \
         font-family: \'Roboto\', sans-serif; font-size: 48px; font-weight: 400; letter-spacing:\
          4px; line-height: 48px;"> <h4 style="font-size: 48px; font-weight: 400; margin: 2;">Welcome to MedParliament\
                             </h4> </td></tr></table>\
             </td></tr><tr> <td bgcolor="#f4f4f4" align="center" style="padding: 0px 10px 0px 10px;">\
              <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px;">\
               <div style="min-height: 250px; text-align: center;background: #ffffff;"> \
               <p style="margin: 0">Hello Dr. ' + str(userName) + '<br><br><strong> Welcome to the MEDPARLIAMENT, the Global Parliament \
               of Healthcare Leadership. </strong><br><br>Thank you for joining our community.\
                <br><br>Please validate your email address by clicking on the button below <br>\
                <br><strong> </p><a href="'+str(link)+'" style="padding: 10px 23px; background:\
                 #e1a23c; color: #fff; display: block; width: 210px; margin: 0 auto; border-radius:\
                  5px;"> Click to Verify Email</button> </div></table> </td></tr><tr> \
                  <td bgcolor="#f4f4f4" align="center" style="padding: 0px 10px 0px 10px;">\
                   <table border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width:\
                    600px;"> <tr> <td bgcolor="#f4f4f4" align="left" style="padding: 0px 30px 30px 30px;\
                     color: #666666; font-family: \'Roboto\', sans-serif; font-size: 14px; font-weight:\
                      400; line-height: 18px;"></td></tr></table> </td></tr></table></body>'                      

@app.route('/SignUp', methods=['POST'])
def SignUp1():

    try: 
        startlimit,endlimit="",""   
        inputdata =  commonfile.DecodeInputdata(request.get_data())  
        print(inputdata)     
        
        keyarr = ['userName','mobileNo','email','password','gender',"userTypeId"]
        commonfile.writeLog("SignUp",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":  
            DeviceId,DeviceType,Os,ImeiNo,ipAddress,Country,City,organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","","0","","","","","","",""
            address,qualification,batchofQualification,instituteName,universityName,universityAddress="","","","","",""
            CompanyName=""
            areaOfExpertise,hospital,hospitalAddress="","",""
            occupation,companyAddress="",""
            
         
            Name = inputdata["userName"]
            MobileNo = inputdata["mobileNo"]
            Email = inputdata["email"] 
            Gender = inputdata["gender"]
            Password = inputdata["password"]
            print(Password)

            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and email = '" + str(Email) + "'"
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

                if 'companyName' in inputdata:                    
                    CompanyName = inputdata['companyName']

                if 'areaOfExpertise' in inputdata:                    
                    areaOfExpertise = inputdata['areaOfExpertise']

                if 'hospital' in inputdata:                    
                    hospital = inputdata['hospital']

                if 'hospitalAddress' in inputdata:                    
                    hospitalAddress = inputdata['hospitalAddress']
                if 'occupation' in inputdata:                    
                    occupation = inputdata['occupation']

                if 'companyAddress' in inputdata:                    
                    companyAddress = inputdata['companyAddress']










                print(interestId)



               

                columns = " status,userId, userName, mobileNo, email, userTypeId, gender, password, deviceType, os, ipAddress, countryId, city, deviceid, imeiNo "          
                values = " '" + str(1) + "','"+  str(UserId) + "','" + str(Name) + "','" + str(MobileNo) + "','" + str(Email) + "','" + str(userTypeId) + "','" + str(Gender) + "', "            
                values = values + " '" + str(Password) + "','" + str(DeviceType) + "','" + str(Os) + "','" + str(ipAddress) + "','"                 
                values = values + str(Country) + "','" + str(City) + "','" + str(DeviceId) + "','" + str(ImeiNo) +"'" 


                data = databasefile.InsertQuery("userMaster",columns,values) 

                if data != "0":
                    column = 'userId,userName,userTypeId,profilePic,email'
                    
                    data = databasefile.SelectQuery("userMaster",column,WhereCondition,"",startlimit,endlimit)
                    if data["status"]!="false":
                        y=data["result"][0]
                        if (y["userTypeId"] == 5):
                            
                            Y=ConstantData.getwebBaseurl()
                            Y=Y+"/AccountVerification"+"?userId=" + str(y["userId"]) + " "
                            userName=str(y['userName'])
                            message = Mail(
                                            from_email = 'medparliament@medachievers.com',
                                            to_emails = str(y["email"]),
                                            subject = "Account Verification",
                                            html_content = getMailBody(userName, Y))
                            #html_content = ' Hello Mr. ' + str(userName) + '<br> <br> <strong>  Welcome to the MEDPARLIAMENT, the Global Parliament of Healthcare Leadership. </strong><br> <br> Thank you for joining our community. <br> <br>    Please validate your email address by clicking on the button down below  <br> <br> <strong>   Verify  Your Email:  ' + str(Y) + ' </strong> <br> <br> '
                            
                            sg = SendGridAPIClient('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')
                            response = sg.send(message)

                            columns="userId,aboutProfile,organization,designation"
                            values=" '" + str(y["userId"]) + "','" + str(aboutProfile) + "','" + str(organization) + "','" + str(designation) + "'"
                            data1=databasefile.InsertQuery("policyMakerMaster",columns,values)




                        if (y["userTypeId"] == 6):

                            Y=ConstantData.getwebBaseurl()
                            Y=Y+"/AccountVerification"+"?userId=" + str(y["userId"]) + " "
                            userName=str(y['userName'])
                            message = Mail(
                                            from_email = 'medparliament@medachievers.com',
                                            to_emails = str(y["email"]),
                                            subject = "Account Verification",
                                            html_content = getMailBody(userName, Y))
                            sg = SendGridAPIClient('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')
                            response = sg.send(message)

                            columns="userId,areaOfActivity,profileCategoryId,designation,companyName"
                            values=" '" + str(y["userId"]) + "','" + str(areaofActivity) + "','" + str(profileCategoryId) + "','" + str(designation)+ "','" + str(CompanyName) + "'"
                            data2=databasefile.InsertQuery("enterprenuerMaster",columns,values)
                            for i in interestId:
                                column="userId,userTypeId,interestId"
                                values=" '" + str(y["userId"]) + "','" + str('6') + "','" + str(i) + "'"
                                data4=databasefile.InsertQuery("userInterestMapping ",column,values) 



                        if (y["userTypeId"]== 7):

                            Y=ConstantData.getwebBaseurl()
                            Y=Y+"/AccountVerification"+"?userId=" + str(y["userId"]) + " "
                            userName=str(y['userName'])
                            message = Mail(
                                            from_email = 'medparliament@medachievers.com',
                                            to_emails = str(y["email"]),
                                            subject = "Account Verification",
                                            html_content =getMailBody(userName, Y))
                            sg = SendGridAPIClient('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')
                            response = sg.send(message)
                            print(response.status_code,'------------------',response.body,"============",response.headers)
                            
                            print(message)

                            # column="status='0'"
                            # dat=databasefile.UpdateQuery('userMaster',column,WhereCondition)


                            columns="userId,address,qualificationId,batchOfQualification,institutionName,universityAddress,universityId"
                            values=" '" + str(y["userId"]) + "','" + str(address) + "','" + str(qualification) + "','" + str(batchofQualification) + "','" + str(instituteName)+ "','" + str(universityAddress)+ "','" + str(universityName)+ "'"
                            data3 = databasefile.InsertQuery("studentMaster",columns,values) 
                            for i in interestId:
                                column="userId,userTypeId,interestId"
                                values=" '" + str(y["userId"]) + "','" + str('7') + "','" + str(i) + "'"
                                data5=databasefile.InsertQuery("userInterestMapping ",column,values)
                        
                        if (y["userTypeId"]== 8):

                            Y=ConstantData.getwebBaseurl()
                            Y=Y+"/AccountVerification"+"?userId=" + str(y["userId"]) + " "
                            userName=str(y['userName'])
                            message = Mail(
                                            from_email = 'medparliament@medachievers.com',
                                            to_emails = str(y["email"]),
                                            subject = "Account Verification",
                                            html_content =getMailBody1(userName, Y))
                            sg = SendGridAPIClient('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')
                            response = sg.send(message)
                            
                            columns="userId,qualificationId,designation,areaOfExpertise,hospital,hospitalAddress"
                            values=" '" + str(y["userId"])+ "','" + str(qualification) + "','" + str(designation) + "','" + str(areaOfExpertise) + "','" + str(hospital)+ "','" + str(hospitalAddress) + "'"
                            data3= databasefile.InsertQuery("doctorMaster",columns,values)
                            for i in interestId:
                                column="userId,userTypeId,interestId"
                                values=" '" + str(y["userId"]) + "','" + str('8') + "','" + str(i) + "'"
                                data5=databasefile.InsertQuery("userInterestMapping ",column,values)
                        
                        if (y["userTypeId"]== 9):

                            Y=ConstantData.getwebBaseurl()
                            Y=Y+"/AccountVerification"+"?userId=" + str(y["userId"]) + " "
                            userName=str(y['userName'])
                            message = Mail(
                                            from_email = 'medparliament@medachievers.com',
                                            to_emails = str(y["email"]),
                                            subject = "Account Verification",
                                            html_content = getMailBody(userName, Y))
                            sg = SendGridAPIClient('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')
                            response = sg.send(message)
                            columns="userId,designation,occupation,companyName,companyAddress,address"
                            values=" '" + str(y["userId"])+ "','" + str(designation) + "','" + str(occupation) + "','" + str(CompanyName) + "','" + str(companyAddress)+ "','" + str(address) + "'"
                            data6=databasefile.InsertQuery("professionalMaster",columns,values)
                            for i in interestId:
                                column="userId,userTypeId,interestId"
                                values=" '" + str(y["userId"]) + "','" + str('9') + "','" + str(i) + "'"
                                data5=databasefile.InsertQuery("userInterestMapping ",column,values)

                        if (y["userTypeId"]== 13):
                            Y=ConstantData.getwebBaseurl()
                            Y=Y+"/AccountVerification"+"?userId=" + str(y["userId"]) + " "
                            userName=str(y['userName'])
                            message = Mail(
                                            from_email = 'medparliament@medachievers.com',
                                            to_emails = str(y["email"]),
                                            subject = "Account Verification",
                                            html_content = getMailBody(userName, Y))
                            sg = SendGridAPIClient('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')
                            response = sg.send(message)



                             


                    else:
                        return commonfile.Errormessage()
                    if data["result"][0]["profilePic"]==None:
                        data["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"

                    data['message']='Email verification link has been sent successfully on your email'
                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 

# @app.route('/Login1', methods=['GET'])
# def login():
#     try:
#         password = request.args['password']
       
#         mobile = request.args['email']
#         column=  "us.profilePic,us.mobileNo,us.userName,us.email,um.id as userTypeId,us.userId as userId"
#         whereCondition= " and us.email = '" + mobile + "' and us.password = '" + password + "'  and  us.userTypeId=um.id"
#         groupby,startlimit,endlimit="","",""
#         loginuser=databasefile.SelectQuery("userMaster as us,userTypeMaster as um",column,whereCondition, groupby,startlimit,endlimit)
        
               
      
#         if  (loginuser["status"]!="false"): 
#             if loginuser["result"][0]["profilePic"]==None:
#                     loginuser["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/profilePic.jpg"
#             else:
#                 loginuser["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(loginuser["result"][0]["profilePic"])
#             Data = {"status":"true","message":"","result":loginuser["result"]}                  
#             return Data
#         else:
#             data = {"status":"false","message":"Please Enter Your correct Password and email","result":""}
#             return data

#     except KeyError as e:
#         print("Exception---->" +str(e))        
#         output = {"status":"false","message":"No Data Found","result":""}
#         return output 
    
#     except Exception as e :
#         print("Exception---->" +str(e))           
#         output = {"status":"false","message":"something went wrong","result":""}
#         return output  


@app.route('/Login', methods=['GET'])
def login1():
    try:
        password = request.args['password']
       
        mobile = request.args['email']
        column=  "us.profilePic,us.mobileNo,us.userName,us.email,um.id as userTypeId,us.userId as userId,us.status as status"
        whereCondition= " and us.email = '" + mobile + "' and us.password = '" + password + "'  and  us.userTypeId=um.id "
        groupby,startlimit,endlimit="","",""
        loginuser=databasefile.SelectQuery("userMaster as us,userTypeMaster as um",column,whereCondition, groupby,startlimit,endlimit)
        
               
      
        if  (loginuser["status"]!="false"): 
            if loginuser["result"][0]["profilePic"]==None:
                loginuser["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/profilePic.jpg"
            else:
                loginuser["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(loginuser["result"][0]["profilePic"])

            if loginuser["result"][0]["status"]== 0:
                Data = {"status":"true","message":"","result":loginuser["result"]} 
                return Data
            if loginuser["result"][0]["status"]== 2:
                Data = {"status":"false","message":"your account is Deactivated by admin","result":""} 
                return Data
                

            else:
                Data = {"status":"False","message":"Till Now your account is not approved By admin, after admin approval you can access your account","result":""} 
                return Data
        else:
            data = {"status":"false","message":" Your Email or Password is Wrong, Try Again","result":""}
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
        keyarr = ['adminName','userTypeId','emailId','password','flag']
        commonfile.writeLog("addAdmin",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            Name = inputdata["adminName"]
            userTypeId = inputdata["userTypeId"]
            Email = inputdata["emailId"]
            password = inputdata["password"]
            flag=inputdata["flag"]
            
            if 'userId' in inputdata:
                UserId1=inputdata["userId"]

            if flag =="u":
                    print("111111111111111uuuuuuuuuu")
                    columns= "userName='" + str(Name) + "' ,password='" + str(password) + "',email='" + str(Email) + "'"
                    whereCondition= " and userId='" + str(UserId1) + "' and userTypeId='" + str(userTypeId) + "'"
                    data2=databasefile.UpdateQuery("userMaster",columns,whereCondition)
                    if data2 !='0':
                        return data2
                    else:
                        return commonfile.Errormessage()


            UserId = commonfile.CreateHashKey(Email,Name)
            
            WhereCondition = " and email = '" + str(Email) + "' and password = '" + str(password) + "'"
            count = databasefile.SelectCountQuery("userMaster",WhereCondition,"")
            
            if int(count) > 0:
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                if flag =="i":
                    print("11111111111111111111111")
                
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


@app.route('/addCountry', methods=['POST'])
def addCountry():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        startlimit,endlimit="",""
        keyarr = ['countryName','flag']
        commonfile.writeLog("addCountry",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            Name = inputdata["countryName"]
           
            flag=inputdata["flag"]
            
            if 'id' in inputdata:
                Id=inputdata["id"]

            if flag =="u":
                    print("111111111111111uuuuuuuuuu")
                    columns= "countryName='" + str(Name) + "' "
                    whereCondition= " and id='" + str(Id) + "' "
                    data2=databasefile.UpdateQuery("CountryMasterNew",columns,whereCondition)
                    if data2 !='0':
                        return data2
                    else:
                        return commonfile.Errormessage()


           
            
            WhereCondition = " and countryName = '" + str(Name) + "' "
            count = databasefile.SelectCountQuery("CountryMasterNew",WhereCondition,"")
            
            if int(count) > 0:
                return commonfile.CountryAlreadyExistMsg()
            else:
                if flag =="i":
                    print("11111111111111111111111")
                
                    column = "countryName"                
                    values = " '" + str(Name) + "'"

                    data = databasefile.InsertQuery("CountryMasterNew",column,values)        
                    if data != "0":
                        column = 'id,countryName'
                        
                        data = databasefile.SelectQuery("CountryMasterNew",column,WhereCondition,"",startlimit,endlimit)
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


@app.route('/addUniversity', methods=['POST'])
def addUniversity():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        startlimit,endlimit="",""
        keyarr = ['universityName','flag']
        commonfile.writeLog("addCountry",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            Name = inputdata["universityName"]
           
            flag=inputdata["flag"]
            
            if 'id' in inputdata:
                Id=inputdata["id"]

            if flag =="u":
                    print("111111111111111uuuuuuuuuu")
                    columns= "universityName='" + str(Name) + "' "
                    whereCondition= " and id='" + str(Id) + "' "
                    data2=databasefile.UpdateQuery("universityMaster",columns,whereCondition)
                    if data2 !='0':
                        return data2
                    else:
                        return commonfile.Errormessage()


           
            
            WhereCondition = " and universityName = '" + str(Name) + "' "
            count = databasefile.SelectCountQuery("universityMaster",WhereCondition,"")
            
            if int(count) > 0:
                return commonfile.UniversityAlreadyExistMsg()
            else:
                if flag =="i":
                    print("11111111111111111111111")
                
                    column = "universityName"                
                    values = " '" + str(Name) + "'"

                    data = databasefile.InsertQuery("universityMaster",column,values)        
                    if data != "0":
                        column = 'id,universityName'
                        
                        data = databasefile.SelectQuery("universityMaster",column,WhereCondition,"",startlimit,endlimit)
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



@app.route('/addQualification', methods=['POST'])
def addQualification():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        startlimit,endlimit="",""
        keyarr = ['qualificationName','flag']
        commonfile.writeLog("addQualification",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            Name = inputdata["qualificationName"]
           
            flag=inputdata["flag"]
            
            if 'id' in inputdata:
                Id=inputdata["id"]

            if flag =="u":
                    print("111111111111111uuuuuuuuuu")
                    columns= "qualificationName='" + str(Name) + "' "
                    whereCondition= " and id='" + str(Id) + "' "
                    data2=databasefile.UpdateQuery("qualificationMaster",columns,whereCondition)
                    if data2 !='0':
                        return data2
                    else:
                        return commonfile.Errormessage()


           
            
            WhereCondition = " and qualificationName = '" + str(Name) + "' "
            count = databasefile.SelectCountQuery("qualificationMaster",WhereCondition,"")
            
            if int(count) > 0:
                return commonfile.QualificationAlreadyExistMsg()
            else:
                if flag =="i":
                    print("11111111111111111111111")
                
                    column = "qualificationName"                
                    values = " '" + str(Name) + "'"

                    data = databasefile.InsertQuery("qualificationMaster",column,values)        
                    if data != "0":
                        column = 'id,qualificationName'
                        
                        data = databasefile.SelectQuery("qualificationMaster",column,WhereCondition,"",startlimit,endlimit)
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



# @app.route('/adminPannel1', methods=['GET'])
# def adminPannel():
#     try:
#         column="count(*) as count"
#         startlimit,endlimit="",""
#         WhereCondition=" and userTypeId>'1'  and usertypeId<'5' and status<>2"
       
#         WhereCondition4=" and usertypeId='5'"
#         WhereCondition5=" and usertypeId='6'"
#         WhereCondition6=" and usertypeId='7'"

        


#         data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
#         totalsubAdmins=data["result"][0]
       
#         data2 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition4,""," ",startlimit,endlimit)
#         subAdmins2=data2["result"][0]
#         data3 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition5,""," ",startlimit,endlimit)
#         subAdmins3=data3["result"][0]
#         data4 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition6,""," ",startlimit,endlimit)
#         subAdmins4=data4["result"][0]
        
        


#         if data:           
#             Data1 = {"totalAdmins":totalsubAdmins,"policyMakerMasterCount":subAdmins2,"enterprenuerMasterCount":subAdmins3,"studentMasterCount":subAdmins4}
#             Data = {"status":"true","message":"","result":Data1}
#             return Data
#         else:
#             output = {"status":"false","message":"No Data Found","result":""}
#             return output

#     except Exception as e :
#         print("Exception---->" + str(e))    
#         output = {"status":"false","message":"something went wrong","result":""}
#         return output



@app.route('/adminPannel', methods=['GET'])
def adminPannel1():
    try:
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and userTypeId>'1'  and usertypeId<'5' and status<>2"
        WhereCondition7=" and userTypeId>'9'  and status<>2"
       
        WhereCondition4=" and usertypeId='5'"
        WhereCondition5=" and usertypeId='6'"
        WhereCondition6=" and usertypeId='7'"
        WhereCondition8=" and usertypeId='8'"
        WhereCondition9=" and usertypeId='9'"


        


        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        totalsubAdmins=data["result"][0]

        data7 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition7,""," ",startlimit,endlimit)
        print(data7["result"][0]["count"])
        totalsubAdmins["count"]+=data7["result"][0]["count"]
       
        data2 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition4,""," ",startlimit,endlimit)
        subAdmins2=data2["result"][0]
        data3 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition5,""," ",startlimit,endlimit)
        subAdmins3=data3["result"][0]
        data4 = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition6,""," ",startlimit,endlimit)
        subAdmins4=data4["result"][0]
        data5=databasefile.SelectQueryOrderby("userMaster",column,WhereCondition8,""," ",startlimit,endlimit)
        s1=data5["result"][0]
        data6=databasefile.SelectQueryOrderby("userMaster",column,WhereCondition9,""," ",startlimit,endlimit)
        s2=data6["result"][0]
        
        


        if data:           
            Data1 = {"totalAdmins":totalsubAdmins,"policyMakerMasterCount":subAdmins2,"enterprenuerMasterCount":subAdmins3,"studentMasterCount":subAdmins4,"professionalMasterCount":s2,"doctorMasterCount":s1}
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
            WhereCondition=" and status<>'2' and userTypeId='" + str(userTypeId) + "'"+whereCondition
            data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
            print(data)

            if (data["status"]!=0): 
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



# @app.route('/allSubAdmins1', methods=['POST'])
# def allSubAdmins1():
#     try:
#         inputdata =  commonfile.DecodeInputdata(request.get_data())
#         startlimit,endlimit="",""
#         keyarr = ['userTypeId']
#         print(inputdata,"B")
#         commonfile.writeLog("addAdmin",inputdata,0)
#         print('C')
#         msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
#         if msg =="1":
#             userTypeId=inputdata["userTypeId"]
#             column="*"
#             WhereCondition=" and userTypeId='" + str(userTypeId) + "'"
#             data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
#             print(data)

#             if (data["status"]!="false"): 
#                 print("111111111111111")          
#                 Data = {"status":"true","message":"","result":data["result"]}
#                 return Data
#             else:
#                 output = {"status":"false","message":"No Data Found","result":""}
#                 return output
#         else:
#             return msg         

#     except Exception as e :
#         print("Exception---->" + str(e))    
#         output = {"status":"false","message":"something went wrong","result":""}
#         return output




@app.route('/totalSubAdmins', methods=['GET'])
def totalAdmins():
    try:
       
        startlimit,endlimit="0","5"
        msg="1"
       
        if msg =="1":
            orderby=" id "
           
            column="um.userName as userName,ut.userName as userType,um.userTypeId as userTypeId,um.id,um.email as email,um.profilePic"
            WhereCondition=" and um.userTypeId<>'1' and um.userTypeId<>'5' and um.userTypeId<>'6' and um.userTypeId<>'7' and um.userTypeId<>'8' and um.userTypeId<>'9' and um.userTypeId=ut.id  and um.status<>2"
            data = databasefile.SelectQueryOrderby("userMaster as um,userTypeMaster as ut",column,WhereCondition,"",startlimit,endlimit,orderby)
            count=len(data["result"])
           
            #        
                    #i.update("userTypeId":usertypeId)




            print(count)

            if (data["status"]!="false"): 
                print("111111111111111")
                for i in data['result']:
                    if i["profilePic"]==None:
                        i["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/profilePic.jpg"          
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
        whereCondition=" and status=0 and  usertypeId='5'"
        whereCondition2=" and status=1 and  usertypeId='5'"

        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
        data3 = databasefile.SelectQueryOrderby("userMaster",column,whereCondition,""," ",startlimit,endlimit)
        data4= databasefile.SelectQueryOrderby("userMaster",column,whereCondition2,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        postCounts=data2["result"][0]
        approvedPosts=data3["result"][0]
        rejectedPost=data4["result"][0]
        data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedUser":approvedPosts,"rejectedUser":rejectedPost}]
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



@app.route('/doctorMasterPannel', methods=['POST'])
def doctorMasterPannel():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userId']
        print(inputdata,"B")
        commonfile.writeLog("addAdmin",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userId=inputdata['userId']
            column="count(*) as count"
            startlimit,endlimit="",""
            WhereCondition=" and usertypeId='8'"
            whereCondition=" and status=0 and  usertypeId='8'"
            whereCondition2=" and status=1 and usertypeId='8'"

        
            data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
            data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
            data3 = databasefile.SelectQueryOrderby("userMaster",column,whereCondition,""," ",startlimit,endlimit)
            data4= databasefile.SelectQueryOrderby("userMaster",column,whereCondition2,""," ",startlimit,endlimit)
            policyMakerMasterCount=data["result"][0]
            postCounts=data2["result"][0]
            approvedPosts=data3["result"][0]
            rejectedPost=data4["result"][0]
            data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedUser":approvedPosts,"rejectedUser":rejectedPost}]
            # data2.append({"totalpostCounts":postCounts})
            # data2.append({"approvedPost":approvedPosts})
            # data2.append({"rejectedPost":rejectedPost})



            if data:           
                Data = {"status":"true","message":"","result":data2}
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


@app.route('/professionalsMasterPannel', methods=['POST'])
def  professionalsMasterPannel():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userId']
        print(inputdata,"B")
        commonfile.writeLog("addAdmin",inputdata,0)
        print('C')
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userId=inputdata['userId']
            column="count(*) as count"
            startlimit,endlimit="",""
            WhereCondition=" and usertypeId='9'"
            whereCondition=" and status=1 and  usertypeId='9'"
            whereCondition2=" and status=0 and  usertypeId='9'"

            
            data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
            data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
            data3 = databasefile.SelectQueryOrderby("userMaster",column,whereCondition,""," ",startlimit,endlimit)
            data4= databasefile.SelectQueryOrderby("userMaster",column,whereCondition2,""," ",startlimit,endlimit)
            policyMakerMasterCount=data["result"][0]
            postCounts=data2["result"][0]
            approvedPosts=data3["result"][0]
            rejectedPost=data4["result"][0]
            data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedUser":approvedPosts,"rejectedUser":rejectedPost}]
            # data2.append({"totalpostCounts":postCounts})
            # data2.append({"approvedPost":approvedPosts})
            # data2.append({"rejectedPost":rejectedPost})

            

            if data:           
                Data = {"status":"true","message":"","result":data2}
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


       





@app.route('/allpolicyMakers', methods=['GET'])
def allpolicyMakers():
    try:
        orderby=" um.id"
        column=" um.id,um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.city,um.countryId,um.email,"
        column=column+"pm.aboutProfile,pm.organization,pm.designation,um.status,(cm.Name)countryName,um.emailVerificationStatus as emailStatus"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='5' and um.userId=pm.userId  and um.countryId=cm.Id"

        
        data = databasefile.SelectQueryOrderby("userMaster as um,policyMakerMaster as pm,CountryMasterNew as cm",column,WhereCondition,"",startlimit,endlimit,orderby)
        print(data['result'],"++++++++++++")

      
        
        


        if (data!=0):
            for i in data["result"]:
                userId=i["userId"]
                column="count(*) as count"
                whereCondition=" and pm.usertypeId='5' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                if data1['status'] !="False":
                    count=data1["result"]["count"]
                    i["noOfPosts"]=count
                else:
                    i["noOfPosts"]=0

            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output
    

@app.route('/testallpolicyMakers', methods=['GET'])
def testallpolicyMakers():
    try:
        column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.city,um.countryId,um.email,"
        column=column+"pm.aboutProfile,pm.organization,pm.designation,um.status,cm.countryName"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='5' and pm.userId=um.userId  and um.countryId=cm.id"

        
        data = databasefile.SelectQueryOrderby("userMaster as um,policyMakerMaster as pm,CountryMasterNew as cm",column,WhereCondition,""," ",startlimit,endlimit)

        if (data!=0):
            for i in data["result"]:
                print(i,'iiiiiiiiiiiiiiiiii')
                userId=i["userId"]
                column="count(*) as count"
                whereCondition=" and pm.usertypeId='5' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                print(data1,"")
                count=data1["count"]
                print(i["noOfPosts"],'iiii11111111111111111111111')
                i["noOfPosts"]=count
                print( i["noOfPosts"], '====no of posts')


            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output



@app.route('/allDoctorMaster', methods=['POST'])
def allDoctorMaster():
    try:
        msg="1"
       
        if msg =="1":
            orderby=" um.id"
            column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.email,um.status,"
            column=column+" dm.qualificationId as qualificationName,dm.designation,dm.areaOfExpertise,dm.hospital,dm.hospitalAddress,um.emailVerificationStatus as emailStatus"
            startlimit,endlimit="",""
            WhereCondition=" and um.usertypeId='8' and dm.userId=um.userId  "

            
            data = databasefile.SelectQueryOrderby("userMaster as um,doctorMaster as dm",column,WhereCondition,"",startlimit,endlimit,orderby)

          
            
            


            if (data!=0):
                for i in data["result"]:
                    userId=i["userId"]
                    

                    column="count(*) as count"
                    whereCondition=" and pm.usertypeId='8' and pm.userId='" + str(userId) + "' "
                    data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                    column=" im.name " 
                    WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                    data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",column,WhereCondition,"","","","")
                    a=[]
                    for m in data5["result"]:
                        a.append(m["name"]) 
                        i['interest']=a
                    if data1['status'] !="False":
                        count=data1["result"]["count"]
                        i["noOfPosts"]=count
                    else:
                        i["noOfPosts"]=0


                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output 


@app.route('/allProfessionalMaster', methods=['POST'])
def allprofessionalsMaster():
    try:
        msg="1"
        if msg =="1":
            orderby=" um.id"
            column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.email,um.status,"
            column=column+"pm.userId,pm.designation,pm.occupation,pm.companyName,pm.companyAddress,pm.address,um.emailVerificationStatus as emailStatus"
            startlimit,endlimit="",""
            WhereCondition=" and um.userTypeId='9' and pm.userId=um.userId  "

            
            data = databasefile.SelectQueryOrderby("userMaster as um,professionalMaster as pm",column,WhereCondition,"",startlimit,endlimit,orderby)

          
            
            


            if (data!=0):
                for i in data["result"]:
                    userId=i["userId"]
                    column="count(*) as count"
                    whereCondition=" and pm.usertypeId='9' and pm.userId='" + str(userId) + "' "
                    data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                    column=" im.name " 
                    WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                    data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",column,WhereCondition,"","","","")
                    a=[]
                    for m in data5["result"]:
                        a.append(m["name"]) 
                        print(a)
                        i['interest']=a
                    
                    print(data1,"d")
                    if data1['status'] !="False":
                        count=data1["result"]["count"]
                        i["noOfPosts"]=count
                    else:
                        i["noOfPosts"]=0


                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/allDecisionMaker', methods=['POST'])
def allDecisionMaker():
    try:
        msg="1"
        if msg =="1":
            orderby=" um.id"
            column="um.mobileNo as mobileNo, um.userName as userName,um.password as password,um.userId,um.gender,um.email,um.status,"
            column=column+"um.countryId,(cm.Name)countryName,um.emailVerificationStatus as emailStatus"
            startlimit,endlimit="",""
            WhereCondition=" and um.usertypeId='13' and cm.Id=um.countryId  "

            
            data = databasefile.SelectQueryOrderby("userMaster as um,CountryMasterNew as cm",column,WhereCondition,"",startlimit,endlimit,orderby)

          
            
            


            if (data!=0):
                Data = {"status":"true","message":"","result":data["result"]}
                return Data
            else:
                output = {"status":"false","message":"No Data Found","result":""}
                return output
        else:
            return msg

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
        whereCondition="and status='0' and  usertypeId='6'"
        whereCondition2="and status='1' and  usertypeId='6'"

        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
        data3 = databasefile.SelectQueryOrderby("userMaster",column,whereCondition,""," ",startlimit,endlimit)
        data4= databasefile.SelectQueryOrderby("userMaster",column,whereCondition2,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        postCounts=data2["result"][0]
        approvedPosts=data3["result"][0]
        rejectedPost=data4["result"][0]
        data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedUser":approvedPosts,"rejectedUser":rejectedPost}]
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
        orderby=" um.id"
        column="um.mobileNo as mobileNo,um.email ,um.userName as userName,um.password as password,um.userId,um.gender,um.countryId,um.city,"
        column=column+"pm.areaOfActivity,pm.profileCategoryId,pm.designation,um.status,pm.companyName,um.emailVerificationStatus as emailStatus"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='6' and pm.userId=um.userId "
        
        data = databasefile.SelectQueryOrderby("userMaster as um,enterprenuerMaster as pm",column,WhereCondition,"",startlimit,endlimit,orderby)

        if (data!=0):
            for i in data["result"]:
                userId=i["userId"]
                column="count(*) as count"
                whereCondition=" and pm.usertypeId='6' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)

                column=" im.name " 
                WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",column,WhereCondition,"","","","")
                a=[]
                for m in data5["result"]:
                    a.append(m["name"]) 
                    print(a)
                    i['interest']=a
                print(data1,"")
               
                if data1['status'] !="False":
                    count=data1["result"]["count"]
                    i["noOfPosts"]=count
                else:
                    i["noOfPosts"]=0
    
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output  


# @app.route('/allenterprenuers1', methods=['GET'])
# def allenterprenuer1():
#     try:
#         column="um.mobileNo as mobileNo,um.email ,um.userName as userName,um.password as password,um.userId,um.gender,um.countryId,um.city,"
#         column=column+"pm.areaOfActivity,pcm.name as profileCategory,pm.designation,um.status"
#         startlimit,endlimit="",""
#         WhereCondition=" and um.usertypeId='6' and pm.userId=um.userId and pcm.id=pm.profileCategoryId"
        
#         data = databasefile.SelectQueryOrderby("userMaster as um,enterprenuerMaster as pm,profileCategoryMaster as pcm",column,WhereCondition,""," ",startlimit,endlimit)

#         if (data!=0):
#             for i in data["result"]:
#                 userId=i["userId"]
#                 column="count(*) as count"
#                 whereCondition=" and pm.usertypeId='6' and pm.userId='" + str(userId) + "' "
#                 data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
#                 print(data1,"")
#                 count=data1["count"]

#                 i["noOfPosts"]=count
#                 columns=" im.name " 
#                 WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
#                 data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",columns,WhereCondition,"","","","")
                
#                 i["userInterest"]=[]
#                 for j in data5["result"]:
#                     j.append(i["name"])
    
#             Data = {"status":"true","message":"","result":data["result"]}
#             return Data
#         else:
#             output = {"status":"false","message":"No Data Found","result":""}
#             return output

#     except Exception as e :
#         print("Exception---->" + str(e))    
#         output = {"status":"false","message":"something went wrong","result":""}
#         return output         




@app.route('/studentMasterPannel', methods=['GET'])
def studentMasterPannel():
    try:
        userId=request.args['userId']
        column="count(*) as count"
        startlimit,endlimit="",""
        WhereCondition=" and usertypeId='7'"
        whereCondition="and status='0' and   usertypeId='7'"
        whereCondition2="and status='2' and   usertypeId='7' "

        
        data = databasefile.SelectQueryOrderby("userMaster",column,WhereCondition,""," ",startlimit,endlimit)
        data2 = databasefile.SelectQueryOrderby("userPost",column,WhereCondition,""," ",startlimit,endlimit)
        data3 = databasefile.SelectQueryOrderby("userMaster",column,whereCondition,""," ",startlimit,endlimit)
        data4= databasefile.SelectQueryOrderby("userMaster",column,whereCondition2,""," ",startlimit,endlimit)
        policyMakerMasterCount=data["result"][0]
        postCounts=data2["result"][0]
        approvedPosts=data3["result"][0]
        rejectedPost=data4["result"][0]
        data2=[ {"totalUsers":policyMakerMasterCount,"totalpostCounts":postCounts,"approvedUser":approvedPosts,"rejectedUser":rejectedPost}]
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
        orderby=" um.id"
        column="um.mobileNo as mobileNo,um.email,um.userName as userName,um.password as password,um.userId,um.gender,"
        column=column+" pm.address,pm.qualificationId as qualificationName,pm.batchofQualification,pm.institutionName,pm.universityAddress,pm.universityId as universityName,um.status,um.emailVerificationStatus as emailStatus"
        startlimit,endlimit="",""
        # column=column+"um.countryId,(cm.Name)countryName"
        startlimit,endlimit="",""
        WhereCondition=" and um.usertypeId='7'  and pm.userId=um.userId "
        
        data = databasefile.SelectQueryOrderby("userMaster as um,studentMaster as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
      
        
        


        if (data!=0):
            for i in data["result"]:

                userId=i["userId"]

                column=" im.name " 
                WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",column,WhereCondition,"","","","")
                print(data5,"+++++")
                a=[]
                for m in data5["result"]:
                    a.append(m["name"]) 
                    print(a)
                    i['interest']=a


                column="count(*) as count"
                whereCondition=" and pm.usertypeId='7' and pm.userId='" + str(userId) + "' "
                data1=databasefile.SelectQuery1("userPost as pm",column,whereCondition)
                print(data1,"")
                if data1['status'] !="False":
                    count=data1["result"]["count"]
                    i["noOfPosts"]=count
                else:
                    i["noOfPosts"]=0
                print(i,"+++++++++=====")     
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
            column="status=2"

            WhereCondition = " and usertypeId='" + str(userTypeId) + "' and  userId = '" + str(userId) + "' "
            data=databasefile.UpdateQuery("userMaster",column,WhereCondition)
           

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

@app.route('/UpdateUser1', methods=['POST'])
def UpdateUser1():
    try:
        print('1')
        startlimit,endlimit="",""
        print('2')
        inputdata = request.form.get('data') 
        print("===========================",inputdata)      
        inputdata = json.loads(inputdata)        
        keyarr = ['userId','userName','email','userTypeId']
        print(inputdata)
        commonfile.writeLog("UpdateUser",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg == "1":
            print('3')
            column2=""
            column3=""
            organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","",""
            address,qualification,batchofQualification,institutionName,universityName,universityAddress="","","","","",""
            filename,PicPath,Password,Gender,Country,City,MobileNo="","","123","","","",""
            CompanyName=""

            areaOfExpertise,hospital,hospitalAddress="","",""
            occupation,companyAddress="",""
            
            UserId = inputdata["userId"]
            UserName = inputdata["userName"]
           
            Email = inputdata["email"] 
           
            # Password = inputdata["password"]
            
            UserTypeId= int(inputdata["userTypeId"])
            
            if 'password' in inputdata:                    
                Password = inputdata["password"] 

            if 'mobileNo' in inputdata:               

                MobileNo = inputdata["mobileNo"]

            if Country == "":
                Country='101'        

            if 'country' in inputdata:                    
                Country = inputdata["country"]  
            if 'city' in inputdata:                    
                City = inputdata["city"]  
            
            if 'userTypeId' in inputdata:                                    
                userTypeId = int(inputdata['userTypeId'])
            
            if 'gender' in inputdata:                    
                Gender = inputdata['gender'] 
                column3= column3+" ,gender= '" + str(Gender) + "' "                     
            
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

            if 'companyName' in inputdata:                    
                CompanyName = inputdata['companyName']

            if 'areaOfExpertise' in inputdata:                    
                areaOfExpertise = inputdata['areaOfExpertise']

            if 'hospital' in inputdata:                    
                hospital = inputdata['hospital']

            if 'hospitalAddress' in inputdata:                    
                hospitalAddress = inputdata['hospitalAddress']
            if 'occupation' in inputdata:                    
                occupation = inputdata['occupation']

            if 'companyAddress' in inputdata:                    
                companyAddress = inputdata['companyAddress']


                
            if 'postImage' in request.files:  
                print("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr")
                file = request.files.get('postImage')        
                filename = file.filename or ''  
                print(filename)               
                filename= str(UserId)+".png"
                #filename = filename.replace("'","") 

                #folder path to save campaign image
                FolderPath = ConstantData.GetProfilePicPath(filename)  

                filepath = '/profilePic/' + filename    
                print(filepath,"filepath================")
                print(FolderPath,"FolderPathFolderPathFolderPathFolderPath")
                file.save(FolderPath)
                PicPath = filepath 
                column2= column2 +" ,profilePic= '" + str(PicPath) + "' "    
            
            print('A')
            WhereCondition = " and userId = '" + str(UserId) + "' and  userTypeId = '" + str(userTypeId) + "'"
            column = " email = '" + str(Email) + "',countryId = '" + str(Country) + "', "
            column = column +  " userName = '" + str(UserName) + "',city = '" + str(City) + "',mobileNo = '" + str(MobileNo) + "'" +column2+column3
            data = databasefile.UpdateQuery("userMaster",column,WhereCondition)
            print(data,'B')
            if (UserTypeId == 5):
                print('c')
                WhereCondition = " and userId = '" + str(UserId) + "'"
                column =" organization = '" + str(organization) + "',aboutProfile='" + str(aboutProfile) + "',designation='" + str(designation) + "'"
                output=databasefile.UpdateQuery("policyMakerMaster",column,WhereCondition)
            if (UserTypeId == 6):
                WhereCondition = " and userId = '" + str(UserId) + "'"
                column=" designation='" + str(designation) + "' ,companyName ='" + str(CompanyName) + "', areaOfActivity ='" + str(areaofActivity) + "',profileCategoryId='" + str(profileCategoryId) + "'"
                output=databasefile.UpdateQuery("enterprenuerMaster",column,WhereCondition)
                if interestId!="":
                    WhereCondition = " and userId = '" + str(UserId) + "' and userTypeId='6'"
                    output=databasefile.DeleteQuery("userInterestMapping",WhereCondition)
                    
                    for i in interestId:
                        column="userId,interestId,userTypeId"
                        values=" '" + str(UserId) + "','" + str(i) + "','" + str('6') + "'"
                        
                        output9=databasefile.InsertQuery("userInterestMapping",column,values)  

            if (UserTypeId == 7):
                WhereCondition = " and userId = '" + str(UserId) + "'"
                column=" address='" + str(address) + "',qualificationId  = '" + str(qualification) + "', batchOfQualification ='" + str(batchofQualification) + "', institutionName ='" + str(institutionName) + "',universityId='" + str(universityName) + "',universityAddress='" + str(universityAddress) + "'"  
                output=databasefile.UpdateQuery("studentMaster",column,WhereCondition)

                if interestId!="":
                    WhereCondition1 = " and userId = '" + str(UserId) + "' and userTypeId='7'"
                    output8=databasefile.DeleteQuery("userInterestMapping",WhereCondition1)

                    for i in interestId:
                        column="userId,interestId,userTypeId"
                        values=" '" + str(UserId) + "','" + str(i) + "','" + str('7') + "'"
                        
                        output9=databasefile.InsertQuery("userInterestMapping",column,values)
            if (UserTypeId == 8):
                WhereCondition = " and userId = '" + str(UserId) + "'"
                column="qualificationId ='" + str(qualification) + "', designation ='" + str(designation) + "',areaOfExpertise='" + str(areaOfExpertise) + "',hospital ='" + str(hospital) + "',hospitalAddress='" + str(hospitalAddress) + "'"
                output11=databasefile.UpdateQuery("doctorMaster",column,WhereCondition)
                
                if interestId!="":
                    WhereCondition1 = " and userId = '" + str(UserId) + "' and userTypeId='8'"
                    output8=databasefile.DeleteQuery("userInterestMapping",WhereCondition1)

                    for i in interestId:
                        column="userId,interestId,userTypeId"
                        values=" '" + str(UserId) + "','" + str(i) + "','" + str('8') + "'"
                        
                        output9=databasefile.InsertQuery("userInterestMapping",column,values)
            if (UserTypeId == 9):
                WhereCondition = " and userId = '" + str(UserId) + "'"
                column=" designation='" + str(designation) + "',occupation='" + str(occupation) + "',companyName='" + str(CompanyName) + "',companyAddress= '" + str(companyAddress) + "',address='" + str(address) + "'"
                output11=databasefile.UpdateQuery("professionalMaster",column,WhereCondition)
               
                if interestId!="":
                    WhereCondition1 = " and userId = '" + str(UserId) + "' and userTypeId='9'"
                    output8=databasefile.DeleteQuery("userInterestMapping",WhereCondition1)

                    for i in interestId:
                        column="userId,interestId,userTypeId"
                        values=" '" + str(UserId) + "','" + str(i) + "','" + str('9') + "'"
                        
                        output9=databasefile.InsertQuery("userInterestMapping",column,values)

            if data != "0":
                column = 'userId,userName,userTypeId'
                data = databasefile.SelectQuery("userMaster",column,WhereCondition,"",startlimit,endlimit)                  
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
        print('2')
        inputdata = request.form.get('data') 
        print("===========================",inputdata)      
        inputdata = json.loads(inputdata)        
        keyarr = ['userId','userName','email','userTypeId']
        print(inputdata)
        commonfile.writeLog("UpdateUser",inputdata,0)

        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg == "1":
            print('3')
            column2=""
            organization,aboutProfile,designation,areaofActivity,profileCategoryId,interestId= "","","","","",""
            address,qualification,batchofQualification,institutionName,universityName,universityAddress="","","","","",""
            filename,PicPath,Password,Gender,Country,City,MobileNo="","","123","","","",""
            UserId = inputdata["userId"]
            UserName = inputdata["userName"]
           
            Email = inputdata["email"] 
           
            # Password = inputdata["password"]
            
            UserTypeId= int(inputdata["userTypeId"])
            if 'password' in inputdata:                    
                Password = inputdata["password"]   
            if 'mobileNo' in inputdata:               

                MobileNo = inputdata["mobileNo"]

            if Country == "":
                Country='0'        

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
            if 'postImage' in request.files:  
                print("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr")
                file = request.files.get('postImage')        
                filename = file.filename or ''  
                print(filename)               
                filename= str(UserId)+".png"
                #filename = filename.replace("'","") 

                #folder path to save campaign image
                FolderPath = ConstantData.GetProfilePicPath(filename)  

                filepath = '/profilePic/' + filename    
                print(filepath,"filepath================")
                print(FolderPath,"FolderPathFolderPathFolderPathFolderPath")
                file.save(FolderPath)
                PicPath = filepath 
                column2= column2 +" ,profilePic= '" + str(PicPath) + "' "    
            
            print('A')
            WhereCondition = " and userId = '" + str(UserId) + "' and  userTypeId = '" + str(UserTypeId) + " '"
            column = " email = '" + str(Email) + "',countryId = '" + str(Country) + "', "
            column = column +  " userName = '" + str(UserName) + "',city = '" + str(City) + "',mobileNo = '" + str(MobileNo) + "'" +column2
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
                column=" address='" + str(address) + "',qualificationId  = '" + str(qualification) + "', batchOfQualification ='" + str(batchofQualification) + "', institutionName ='" + str(institutionName) + "',universityId='" + str(universityName) + "',universityAddress='" + str(universityAddress) + "'"  
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
        
        keyarr = ['userTypeId','userId','postTitle','postDescription','flag']
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
                        # if data11["status"]!="false":
                        #     y=data11["result"][0]
                        #     column="userId,showuserTypeId,postId"
                        #     values= " '" + str(y["userId"]) + "','" + str(showuserTypeId) + "','" + str(y["postId"]) + "'"
                        #     data2 = databasefile.InsertQuery("postUserTypeMapping",column,values)
                        # else:
                        #     return commonfile.Errormessage()
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



@app.route('/allPosts', methods=['POST'])
def allPosts1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("allPosts",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            
            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userTypeId=pm.userTypeId and pm.userId=um.userId and pm.userTypeId='" + str(userTypeId) + "'" +whereCondition
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            
            print("11111111111111")
            print("data",data)
           

            

            
            if (data['result']!=""):
                for i in data["result"]:
                    Y=i["postId"]
                    y2=i['userId']
                    column="*"
                    whereCondition=" and postId ='" + str(Y) + "'"
                    data2=databasefile.SelectQuery("likeMaster",column,whereCondition,"",startlimit,endlimit)

                    i['like']=len(data2['result'])
                    print(data2,'++++++++++++')
                    columns="status"
                    whereCondition1= " and userId='" + str(y2) + "' and postId= '" + str(Y) + "'"
                    data22= databasefile.SelectQuery("likeMaster",column,whereCondition,"",startlimit,endlimit)
                    if (data22["result"]!=""):
                        y78=data22['result'][0]
                        if y78['status'] == 0:
                            i['likeStatus']=1
                    if i['like'] ==0:
                        i['likeStatus']=0

                    whereCondition=" and  postId= '"+str(postId) +"'"
                    column="status='1'"
                    data1=databasefile.UpdateQuery('userPost',column,whereCondition)


                    # print(data2)
                    # i['like']=data2['like']
            #     for i in data["result"]:
            #         if (i["status"] == 1):
            #             print(i["postId"])
            #             column="um.userName as approvedBy"
            #             WhereCondition=" and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
            #             data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
            #             print(data1)
            #             if "message" in data1:
            #                 pass
            #             else:
            #                 i["approvedBy"]=data1["approvedBy"]
            #             print(data1)
            #         if (i["status"]==2):
            #             column="um.userName as rejectedBy"
            #             WhereCondition=" and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
            #             data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
            #             print(data1)
            #             if "message" in data1:
            #                 pass
            #             else:
            #                 i["rejectedBy"]=data1["rejectedBy"]
                        
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":[data1["result"],data["result"]]}
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


@app.route('/myInbox1', methods=['POST'])
def myInbox1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("myInbox",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            userId=inputdata['userId']
            
            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userId=pm.userId and  pm.userTypeId='" + str(userTypeId) + "' and pm.userId<>'" + str(userId) + "'" +whereCondition
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            
            print("11111111111111")
            print("data",data)
           

            

            
            if (data['result']!=""):
                

                column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                WhereCondition= " and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
                data2 = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
                print(data2,"data2")
                if data2['result'] !="":
                    for m in data2['result']:
                        postId=m['postId']
                        column1="pm.id,um.userName,pm.postId,um.email,pm.approvedUserId as userId,pm.commentDescription as postDescription,(pm.approvedUserId)userId,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                        WhereCondition1="  and pm.approvedUserId=um.userId and pm.postId='" + str(postId) + "'" 
                        orderby=" id "
                        data1 = databasefile.SelectQueryOrderby("approvedBy as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
                        if data1['result']!="":
                            for j in data1['result']:
                                data['result'].append(j)



                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data["result"],"totalcount":len(data['result'])}
                print(Data,"@@@@@@@@@@@@@@@@@@")
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


@app.route('/myInbox', methods=['POST'])
def myInbox():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("myInbox",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="Id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            userId=inputdata['userId']
            
            column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition= " and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
            print(data,"data2")
            data55=[]
           

            

            
            if (data['result']!=""):
                

                
                
                for m in data['result']:
                    postId=m['postId']
                    column1="pm.id,um.userName,pm.postId,um.email,pm.approvedUserId as userId,pm.commentDescription as postDescription,(pm.approvedUserId)userId,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    WhereCondition1="  and pm.approvedUserId=um.userId and pm.postId='" + str(postId) + "'" 
                    orderby=" id "
                    data1 = databasefile.SelectQueryOrderby("approvedBy as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
                    print(data1,"@@!!!")
                    

                    if data1['result']!="":
                        for l in data1['result']:
                            if (l['userId']!=userId):
                                data55.append(l)

                            whereCondition=" and status='0' and postId='" + str(postId) + "' "
                            column="status=1"
                            up=databasefile.UpdateQuery('approvedBy',column,whereCondition)

                data55=sorted(data55, key = lambda i: i['DateCreate'])
                data55=data55[::-1]
                        

                            
                            

                           

                   
                       



                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data55,"totalcount":len(data55)}
                print(Data,"@@@@@@@@@@@@@@@@@@")
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



@app.route('/myNotification', methods=['POST'])
def myNotification():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("myNotification",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="Id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            userId=inputdata['userId']
            
            column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition= " and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
            print(data,"data2")
            data55=[]
           

            

            
            if (data['result']!=""):
                

                
                
                for m in data['result']:
                    postId=m['postId']
                    column1="pm.id,um.userName,pm.postId,um.email,pm.status,pm.approvedUserId as userId,pm.commentDescription as postDescription,(pm.approvedUserId)userId,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    WhereCondition1=" and pm.status='0' and pm.approvedUserId=um.userId and pm.postId='" + str(postId) + "'" 
                    orderby=" id "
                    data1 = databasefile.SelectQueryOrderby("approvedBy as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
                    print(data1,"@@!!!")
                    

                    if data1['result']!="":
                        for l in data1['result']:

                            whereCondition=" and postId='" + str(postId) + "' "
                            column=" status='1' "
                            up=databasefile.UpdateQuery('approvedBy ',column,whereCondition)
                            print(up,"@@@@@@@@@@@@@@@@@@@")

                            data55.append(l)
                data55=sorted(data55, key = lambda i: i['DateCreate'])
                data55=data55[::-1]
                        

                            
                            

                           

                   
                       



                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data55,"totalcount":len(data55)}
                print(Data,"@@@@@@@@@@@@@@@@@@")
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


@app.route('/myNotificationCount', methods=['POST'])
def myNotificationCount():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("myNotificationCount",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="Id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            userId=inputdata['userId']
            
            column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition= " and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
            print(data,"data2")
            data55=[]
           

            

            
            if (data['result']!=""):
                

                
                
                for m in data['result']:
                    postId=m['postId']
                    column1="pm.id,um.userName,pm.postId,um.email,pm.status,pm.approvedUserId as userId,pm.commentDescription as postDescription,(pm.approvedUserId)userId,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    WhereCondition1=" and pm.status='0' and pm.approvedUserId=um.userId and pm.postId='" + str(postId) + "'" 
                    orderby=" id "
                    data1 = databasefile.SelectQueryOrderby("approvedBy as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
                    print(data1,"@@!!!")
                    

                    if data1['result']!="":
                        for l in data1['result']:
                            data55.append(l)
                data55=sorted(data55, key = lambda i: i['DateCreate'])
                data55=data55[::-1]
                        

                            
                            

                           

                   
                       



                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data55,"totalcount":len(data55)}
                print(Data,"@@@@@@@@@@@@@@@@@@")
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




@app.route('/allPostsThread', methods=['POST'])
def allPostsThread():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['postId']
        print(inputdata,"B")
        commonfile.writeLog("allPosts",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,whereCondition="",""

            
            if 'postId' in inputdata:
                postId=inputdata['postId']
                whereCondition=" and pm.postId='" + str(postId) + "' "
                column1="pm.id,um.userName,um.email,pm.commentDescription,(pm.approvedUserId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                WhereCondition1="  and pm.approvedUserId=um.userId and pm.postId='" + str(postId) + "'" 
                orderby=" id "
                data1 = databasefile.SelectQueryOrderbyAsc("approvedBy as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
            
            whereCondition=""
            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userTypeId=pm.userTypeId and pm.userId=um.userId and pm.postId='" + str(postId) + "'" 
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            
            print("11111111111111")
            print("data",data)

            
            
            if (data!=0):
            
                        
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":[data1["result"],data["result"]]}
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

@app.route('/allPostss1', methods=['POST'])
def allPosts11():
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


            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userTypeId=pm.userTypeId and pm.userId=um.userId and pm.userTypeId='" + str(userTypeId) + "'" +whereCondition
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            
            print("11111111111111")
            print("data",data)

          

            if (data!=0):
                for i in data["result"]:
                    if (i["status"] == 0):
                        print(i["postId"])
                        column="um.userName as commentedBy,ap.commentDescription"
                        WhereCondition=" and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        if "message" in data1:
                            pass
                        else:
                            i["commentedBy"]=data1["commentedBy"]
                            i["commentDescription"]=data1["commentDescription"]
                        print(data1)
                   
                        
                        
                
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
            
            
            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and um.userTypeId=pm.userTypeId and pm.userId=um.userId "
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
            print("11111111111111",data)

          

            if (data!=0):
                for i in data["result"]:
                    if (i["status"] == 1):
                        print(i["postId"])
                        column="um.userName as approvedBy"
                        WhereCondition=" and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        if "message" in data1:
                            pass
                        else:
                            i["approvedBy"]=data1["approvedBy"]
                        print(data1)
                    if (i["status"]==2):
                        column="um.userName as rejectedBy"
                        WhereCondition=" and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                        data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                        print(data1)
                        if "message" in data1:
                            pass
                        else:
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




@app.route('/myPosts', methods=['POST'])
def myPosts():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userId','userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("myPosts",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby=" pm.id "
            
            userTypeId=inputdata["userTypeId"]
            userId=inputdata["userId"]
            #status=int(inputdata["status"])
            column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition= " and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
            data = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
            print(data)
          

            if (data['result']!=""):
               

                # for i in data["result"]:
                #     if (i["status"] == 1):
                #         print(i["postId"])
                #         column="um.userName as approvedBy"
                #         WhereCondition=" and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                #         data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                #         print(data1)
                #         if "message" in data1:
                #             pass
                #         else:
                #             i["approvedBy"]=data1["approvedBy"]
                #         print(data1)
                #     if (i["status"]==2):
                #         column="um.userName as rejectedBy"
                #         WhereCondition="and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
                #         data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
                #         print(data1)
                #         if "message" in data1:
                #             pass
                #         else:
                #             i["rejectedBy"]=data1["rejectedBy"]

                
                print("111111111111111")          
               
                return data
            else:
                data["result"]=[]
                data['message']='No Data Found'

                
                return data
        else:
            return msg         

    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 


# @app.route('/myPostsTest', methods=['POST'])
# def myPostsTest():
#     try:
#         inputdata =  commonfile.DecodeInputdata(request.get_data())
#         startlimit,endlimit="",""
#         keyarr = ['userId','userTypeId']
#         print(inputdata,"B")
#         commonfile.writeLog("myPosts",inputdata,0)
      
#         msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
#         if msg =="1":
#             orderby=" pm.id "
            
#             userTypeId=inputdata["userTypeId"]
#             userId=inputdata["userId"]
#             #status=int(inputdata["status"])
#             column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
#             WhereCondition= " and pm.userId='" + str(userId) + "'and pm.userTypeId='" + str(userTypeId) + "'"
#             data = databasefile.SelectQueryOrderby("userPost as pm",column,WhereCondition,"",startlimit,endlimit,orderby)
#             print(data)
          

#             if (data['result']!=""):
               

#                 # for i in data["result"]:
#                 #     if (i["status"] == 1):
#                 #         print(i["postId"])
#                 #         column="um.userName as approvedBy"
#                 #         WhereCondition=" and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
#                 #         data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
#                 #         print(data1)
#                 #         if "message" in data1:
#                 #             pass
#                 #         else:
#                 #             i["approvedBy"]=data1["approvedBy"]
#                 #         print(data1)
#                 #     if (i["status"]==2):
#                 #         column="um.userName as rejectedBy"
#                 #         WhereCondition="and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"' and ap.approvedUserId=um.userId"
#                 #         data1=databasefile.SelectQuery1("userMaster as um,approvedBy as ap,userPost as pm",column,WhereCondition)
#                 #         print(data1)
#                 #         if "message" in data1:
#                 #             pass
#                 #         else:
#                 #             i["rejectedBy"]=data1["rejectedBy"]

                
#                 print("111111111111111")          
               
#                 return data
#             else:
#                 data["result"]=[]
#                 data['message']='No Data Found'

                
#                 return data
#         else:
#             return msg         

#     except Exception as e :
#         print("Exception---->" + str(e))    
#         output = {"status":"false","message":"something went wrong","result":""}
#         return output 





@app.route('/userPostDashboard', methods=['POST'])
def userPostDashboardApproved():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("userPostDashboard",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            if 'postId' in inputdata:
                postId=inputdata['postId']
                whereCondition=" and pm.postId='" + str(postId) + "' "


            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and pm.status=1 and upm.postId=pm.postId and upm.showUserTypeId='"+ str(userTypeId) +"' and um.userTypeId=pm.userTypeId and pm.userId=um.userId and pm.userTypeId='" + str(userTypeId) + "'" +whereCondition
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um,postUserTypeMapping as upm",column,WhereCondition,"",startlimit,endlimit,orderby)
            
            print("11111111111111")
            print("data",data)

          

            if (data!=0):
                for i in data["result"]:
                    if (i["status"] == 1):
                        print(i["postId"])
                        column=" um.userName as approvedBy "
                        WhereCondition=" and um.userId=ap.approvedUserId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"'"
                        data1=databasefile.SelectQuery1("userMaster um,approvedBy ap,userPost  pm",column,WhereCondition)
                        print(data1)
                        if "message" in data1:
                            pass
                        else:
                            i["approvedBy"]=data1["approvedBy"]
                        print(data1)
                    if (i["status"]==2):
                        column=" um.userName as rejectedBy "
                        WhereCondition=" and um.userId=ap.approvedUserId and pm.postId=ap.postId and pm.postId='"+ str(i["postId"])+"'"
                        data1=databasefile.SelectQuery1("userMaster  um,approvedBy ap,userPost pm",column,WhereCondition)
                        print(data1)
                        if "message" in data1:
                            pass
                        else:
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



@app.route('/userPostDashboardRejected', methods=['POST'])
def userPostDashboardRejected():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("userPostDashboard",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            if 'postId' in inputdata:
                postId=inputdata['postId']
                whereCondition=" and pm.postId='" + str(postId) + "' "


            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and pm.status=2 and upm.postId=pm.postId and upm.showUserTypeId='"+ str(userTypeId) +"' and um.userTypeId=pm.userTypeId and pm.userId=um.userId and pm.userTypeId='" + str(userTypeId) + "'" +whereCondition
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um,postUserTypeMapping as upm",column,WhereCondition,"",startlimit,endlimit,orderby)
            
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

@app.route('/userPostDashboardPending', methods=['POST'])
def userPostDashboardPending():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        keyarr = ['userTypeId']
        print(inputdata,"B")
        commonfile.writeLog("userPostDashboard",inputdata,0)
      
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,whereCondition="",""

            
            userTypeId=inputdata["userTypeId"]
            if 'postId' in inputdata:
                postId=inputdata['postId']
                whereCondition=" and pm.postId='" + str(postId) + "' "


            column="um.userName,um.email,um.countryId,um.city,pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,um.userTypeId as userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=" and pm.status=0 and upm.postId=pm.postId and upm.showUserTypeId='"+ str(userTypeId) +"' and um.userTypeId=pm.userTypeId and pm.userId=um.userId and pm.userTypeId='" + str(userTypeId) + "'" +whereCondition
            data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um,postUserTypeMapping as upm",column,WhereCondition,"",startlimit,endlimit,orderby)
            
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
        whereCondition="and id<>1 and id<>5 and id<>6 and id<>7 and id<>8 and id<>9"
        
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
        columns=" Id as id, Name as countryName  "
        
        data = databasefile.SelectQueryMaxId("CountryMasterNew",columns)
       

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




@app.route('/verifyPost1', methods=['POST'])
def verifyPost1():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['approvedUserId','postId','userTypeId','commentDescription']
        commonfile.writeLog("verifyPost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            approvedUserId = inputdata["approvedUserId"]
            postId = inputdata["postId"]
            column=" userId,userTypeId"
            
            wherecondition=" and postId='"+str(postId)+"'"
            da=databasefile.SelectQuery1('userPost',column,wherecondition)
            userId=da['result']['userId']
            UserTypeId=da['result']['userId']
            
            column="MobileToken,userName"
            wherecondition=" and userId='"+str(userId)+"'"
            Mt=databasefile.SelectQuery1('userMaster',column,wherecondition)

            
            MobileToken=Mt['result']['MobileToken']
            userName=Mt['result']['userName']

            column='userName,WebToken'
            wherecondition=" and userId='"+str(approvedUserId)+"'"
            d=databasefile.SelectQuery1('userMaster',column,wherecondition)
            adminName=d['result']['userName']
            WebToken=d['result']['WebToken']


            
            userTypeId = int(inputdata["userTypeId"])
            print("333333333333333333333")
         
            commentDescription=inputdata['commentDescription']
            column = "approvedUserId,postId,userTypeId,commentDescription"                
            values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(commentDescription) + "'"
            data = databasefile.InsertQuery("approvedBy",column,values)
            if MobileToken !=None:
                if userTypeId != UserTypeId:
                    message=ConstantData.newmessage(MobileToken,commentDescription,adminName,userName)
            if WebToken !=None:
                if userTypeId == UserTypeId:
                    message=ConstantData.newmessage1(WebToken,commentDescription,adminName,userName)


            
            if data!="0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output




@app.route('/likePost', methods=['POST'])
def verifyPost12():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','postId','userTypeId']
        commonfile.writeLog("verifyPost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            approvedUserId = inputdata["userId"]
            postId = inputdata["postId"]
            userTypeId = int(inputdata["userTypeId"])

            WhereCondition = " and postId = '" + str(postId) + "' and userId = '" + str(approvedUserId) + "'"
            count = databasefile.SelectCountQuery("likeMaster",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                print("333333333333333333333")
             
               
                column = "userId,postId,userTypeId"                
                values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "'"
                data = databasefile.InsertQuery("likeMaster",column,values)
                if data!="0":
                    column="*"
                    whereCondition=" and postId ='" + str(postId) + "'"
                    data1=databasefile.SelectQuery("likeMaster",column,whereCondition,"",startlimit,endlimit)
                    if (data1["status"]!="false"):
                        y=data1["result"][0]
                        for i in data1['result']:
                            i['likeStatus']=1
                        y2=i['likeStatus']

                      
                       
                        data1={"status":"true","result":y2,"message":""}
                        return data1
                    else:
                        data1={"status":"true","result":"","message":"No Data Found"}
                        return data1

                else:
                    return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/verifyPost', methods=['POST'])
def verifyPost():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        startlimit,endlimit="",""
        keyarr = ['approvedUserId','postId','id']
        commonfile.writeLog("verifyPost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":
            approvedUserId = inputdata["approvedUserId"]
            postId = inputdata["postId"]
           
            statusid = inputdata["id"]
            commentDescription=inputdata['commentDescription']
            print(statusid,'id')
    
            column = "approvedUserId,postId"                
            values = " '" + str(approvedUserId) + "','" + str(postId) + "'"
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


@app.route('/updateStatustest', methods=['POST'])
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



@app.route('/updateStatus', methods=['POST'])
def updateStatus1():
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
            print(userTypeId,type(userTypeId),"============================")
            column="status ,userName"
            whereCondition= " and userTypeId='" + str(userTypeId)+ "' and email = '" + str(email)+ "'  and userId = '" + str(userId)+ "' "
            data=databasefile.SelectQuery("userMaster",column,whereCondition,"",startlimit,endlimit)
            print('AAAA',data)
            print(data['result'][0]['status'],"status")
            userName=data['result'][0]['userName']
            if data['result'][0]['status']==0:
                message = Mail(
                                from_email = 'medparliament@medachievers.com',
                                to_emails = str(email),
                                subject = "Account DeActivated",
                                html_content = '<strong> Your account has been DeActivated </strong> <br> .<br> Thanks,medParliament Team')
                sg = SendGridAPIClient('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')
                response = sg.send(message)
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
                columns='content'
                whereCondition=" and userTypeId='" + str(userTypeId)+ "' "

                accountVerficationContent=databasefile.SelectQuery1('userContent',columns,whereCondition)
                userDoc=accountVerficationContent['result']['content']
                print(userDoc,"+++++++++++++++++++++++++__________")
                if userTypeId==8:
                    message = Mail(
                                from_email = 'medparliament@medachievers.com',
                                to_emails = str(email),
                                subject = "Account Activated",
                                html_content =getactivationmailforDoctor(userName))# '<strong>Congratulations! Your account has been activated successfully </strong> <br> <br> Thanks,medParliament Team')
                elif userTypeId==7:
                    message = Mail(
                                from_email = 'medparliament@medachievers.com',
                                to_emails = str(email),
                                subject = "Account Activated",
                                html_content =getactivationmailforStudent(userName))# '<strong>Congratulations! Your account has been activated successfully </strong> <br> <br> Thanks,medParliament Team')

                elif userTypeId==13:
                    message = Mail(
                                from_email = 'medparliament@medachievers.com',
                                to_emails = str(email),
                                subject = "Account Activated",
                                html_content =getactivationmailforDecisionMaker(userName))# '<strong>Congratulations! Your account has been activated successfully </strong> <br> <br> Thanks,medParliament Team')
               
                else:
                    message = Mail(
                                    from_email = 'medparliament@medachievers.com',
                                    to_emails = str(email),
                                    subject = "Account Activated",
                                    html_content =getactivationmail(userName))# '<strong>Congratulations! Your account has been activated successfully </strong> <br> <br> Thanks,medParliament Team')
                sg = SendGridAPIClient('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')
                response = sg.send(message)
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
                from_email = 'medparliament@medachievers.com',
                to_emails = str(email),
                subject = "Otp for Reset Password",
                html_content = '<strong> Otp To Reset Your Password is:' + str(OTP) + ' </strong> <br> <br> Thanks<br> <br> MedParliament Team')
            sg = SendGridAPIClient('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')
            response = sg.send(message)
           


          
            column="otp='" + str(OTP)+ "'"
            whereCondition= "  and email = '" + str(email)+ "' "
            output=databasefile.UpdateQuery("userMaster",column,whereCondition)
            columns='otp'
            
            data=databasefile.SelectQuery("userMaster",columns,whereCondition,"",startlimit,endlimit)
            if data['result']!="":
                Data = {"status":"true","message":"","result":data["result"]}                  
                return Data
            else:
                return {"status":"false","message":"Invalid Email","result":""}  
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
                data = {"status":"false","message":"Invalid OTP","result":""}
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
                column="um.userName,um.email,(cm.Name)countryName,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"ms.organization,"
                column=column+" ms.aboutProfile, ms.designation"
                WhereCondition=" and cm.id=um.countryId and um.userId=ms.userId and um.userId='" + str(userId) + "'"
                data1 = databasefile.SelectQueryOrderby("userMaster um,policyMakerMaster ms,CountryMasterNew cm",column,WhereCondition,"",startlimit,endlimit,"")
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
                   
                if data1["result"][0]["profilePic"]==None:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"
                else:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(data1["result"][0]["profilePic"])
                data2={"userProfile":data1["result"],"userPost":data4["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()
            if userTypeId == 6:
                print('HELLO')
                column="um.userName,um.email,pcm.name as profileCategory,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"em.designation,em.companyName,"
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
                if data1["result"][0]["profilePic"]==None:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"
                else:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(data1["result"][0]["profilePic"])
                data2={"userProfile":data1["result"],"userPost":data4["result"],"userInterest":data5["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()
            if userTypeId == 7:
                column="um.userName,um.email,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"sm.address,sm.qualificationId as qualificationName,sm.universityId as universityName,"
                column=column+" sm.batchOfQualification, sm.institutionName, sm.universityAddress"
                WhereCondition="   and um.userId=sm.userId and um.userId='" + str(userId) + "'"
                data1 = databasefile.SelectQueryOrderby("userMaster um,studentMaster sm",column,WhereCondition,"",startlimit,endlimit,"")
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
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"
                else:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(data1["result"][0]["profilePic"])
                data2={"userProfile":data1["result"],"userPost":data4["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()

            if userTypeId == 8:
                column="um.userName,um.email,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"dm.userId,dm.qualificationId as qualificationName,dm.designation,dm.areaOfExpertise,dm.hospital,dm.hospitalAddress"
               
                WhereCondition="    and um.userId=dm.userId and um.userId='" + str(userId) + "'"
                data1 = databasefile.SelectQueryOrderby("userMaster um,doctorMaster dm",column,WhereCondition,"",startlimit,endlimit,"")
                print(data1)
                
               
                column=" im.name " 
                WhereCondition=" and im.id=uim.interestId and uim.userId='"+str(userId)+"'"
                data5= databasefile.SelectQueryOrderby("interestMaster im,userInterestMapping uim",column,WhereCondition,"","","","")
                data1["result"][0]["userInterest"]=[]
                for i in data5["result"]:
                    data1["result"][0]["userInterest"].append(i["name"])
                
                
                
                
                orderby="ab.id"
                column="*"
                whereCondition=" and ab.userId='" + userId+ "'"
                data4 = databasefile.SelectQueryOrderby("userPost as ab",column,whereCondition,"",startlimit,endlimit,orderby)
                if  data4==0:
                    
                    data4={"result":'No Posts till now'}
                if data1["result"][0]["profilePic"]==None:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"
                else:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(data1["result"][0]["profilePic"])
                data2={"userProfile":data1["result"],"userPost":data4["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()
            if userTypeId == 9:
                column="um.userName,um.email,um.status,um.userId,um.userTypeId,um.mobileNo,um.profilePic as profilePic,"
                column=column+"pm.userId,pm.designation,pm.occupation,pm.companyName,pm.companyAddress,pm.address"
               
                WhereCondition="   and um.userId=pm.userId and um.userId='" + str(userId) + "'"
                data1 = databasefile.SelectQueryOrderby("userMaster um,professionalMaster pm",column,WhereCondition,"",startlimit,endlimit,"")
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
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"
                else:
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(data1["result"][0]["profilePic"])
                data2={"userProfile":data1["result"],"userPost":data4["result"]}
                data3={"status":"true","message":"","result":data2}
                if  data3:                     
                    return data3
                else:
                    return commonfile.Errormessage()
                    
            if userTypeId==13 or userTypeId=='13':
                print("==========1=========")
                column="um.mobileNo as mobileNo, um.userName as userName, um.userTypeId,um.password as password, um.profilePic as profilePic, um.userId,um.gender,um.email,um.status,"
                column=column+"um.countryId,(cm.Name)countryName"
                print("==========2=========")
                startlimit,endlimit="",""
                WhereCondition=" and um.usertypeId='13' and cm.Id=um.countryId and   um.userId='" + str(userId) + "' "
                print("==========3=========")
                data1 = databasefile.SelectQueryOrderby("userMaster um,CountryMasterNew cm",column,WhereCondition,"",startlimit,endlimit,"")
                print(data1)
                if data1["result"][0]["profilePic"]==None:
                    print("==========4=========")
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/defaultPic.jpg"
                else:
                    print("==========5=========")
                    data1["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(data1["result"][0]["profilePic"])
                data2={"userProfile":data1["result"],"userPost":""}
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
        print(inputdata)
        print(type(inputdata))
        inputdata = json.loads(inputdata)
        commonfile.writeLog("changeProfilePic",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":  
            PicPath = ""
         
            UserId= inputdata["userId"]
            WhereCondition = " and userId = '" + str(UserId)  + "'"
            # count = databasefile.SelectCountQuery("UserMaster",WhereCondition,"")
            
            print("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq")
            if 'postImage' in request.files:  
                print("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr")
                file = request.files.get('postImage')        
                filename = file.filename or ''  
                print(filename)               
                filename= str(UserId)+".png"
                #filename = filename.replace("'","") 

                #folder path to save campaign image
                FolderPath = ConstantData.GetProfilePicPath(filename)  

                filepath = '/profilePic/' + filename    
                print(filepath,"filepath================")
                print(FolderPath,"FolderPathFolderPathFolderPathFolderPath")
                file.save(FolderPath)
                PicPath = filepath

                print("sssssssssssssssssssssssssssssssss")
                column = "profilePic = '"  + str(PicPath) + "'"              
                data = databasefile.UpdateQuery("userMaster",column,WhereCondition)        
                print(data,"111111111111111111111111111111111111111111111111111")
                if data != "0":
                    #data["result"][]= ConstantData.GetBaseURL()+PicPath
                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()                  





# create news by admin

#market Insights (copy of news)
@app.route('/promissingIntiatives', methods=['POST'])
def promissingIntiatives():

    try:
       
        inputdata = request.form.get('news')    
        inputdata = json.loads(inputdata) 
        print("newsdata",inputdata)
        commonfile.writeLog("news",inputdata,0)
        keyarr = ["newsTitle","userTypeId","summary","newsDesc","flag"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            videoLink=""
            flag=inputdata['flag']
            column,values="",""
            if "newsTitle" in inputdata:
                if inputdata['newsTitle'] != "":
                    newsTitle =commonfile.EscapeSpecialChar(inputdata["newsTitle"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =commonfile.EscapeSpecialChar(inputdata["summary"])
            
            if "newsDesc" in inputdata:
                if inputdata['newsDesc'] != "":
                    newsDesc =commonfile.EscapeSpecialChar(inputdata["newsDesc"]) 

             
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"]


            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"          
            
            
            if 'NewsBanner' in request.files:      
                    file = request.files.get('NewsBanner')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getNewsPath(filename)  

                    filepath = '/newsimages/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            if flag =='i':      
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                      
                    column = column+"newsTitle,userTypeId,imagePath,summary,newsDesc,UserCreate"
                    values =values+ " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) + "','" + str(UserId) + "'"
                    data = databasefile.InsertQuery("promissingIntiatives",column,values)        
                else:
                    column = column+"newsTitle,userTypeId,imagePath,summary,newsDesc"
                    values = values+" '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) +  "'"
                    data = databasefile.InsertQuery("promissingIntiatives",column,values)
            if flag =='u':
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                # if "UserId" in inputdata:
                #     if inputdata['UserId'] != "":
                #         UserId =inputdata["UserId"]
                      
                #     whereCondition= " and id= '"+ str(Id) +"' and UserCreate='"+ str(UserId) +"'" 
                #     column="newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                #     data=databasefile.UpdateQuery("news",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('NewsBanner')
                        print("inputdata=================",type(inputdata1))
                        y=type(inputdata1)
                        print(y)
                        y2=len(inputdata1)
                        if  y2>4: 
                            print(inputdata1)
                            index=re.search("/newsimages", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]


                        whereCondition=" and id= '"+ str(Id) +"'"
                        column="videoLink='"+ str(videoLink) +"', newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                        data=databasefile.UpdateQuery("promissingIntiatives",column,whereCondition)


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 



@app.route('/getPromissingIntiatives', methods=['POST'])
def getPromissingIntiatives():

    try:
        print('AA')
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and n.Status<2 "
        if request.get_data():
            print('B')
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+"  and n.userTypeId IN(0,'"+str(userTypeId)+"')"

            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and n.id='"+str(Id)+"'"
        orderby=" n.id "
        WhereCondition=WhereCondition+" and n.UserCreate=um.userId "
        column = " n.id,n.Status,n.newsTitle,n.videoLink,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, n.imagePath ,um.userName "
        data = databasefile.SelectQueryOrderby("promissingIntiatives n,userMaster um",column,WhereCondition,"","0","10",orderby)
        print(data,"-------------------------------------------")
        data2 = databasefile.SelectTotalCountQuery("promissingIntiatives n,userMaster um",WhereCondition,"")
        data["totalCount"]=data2
        if data != "0":
            
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                    print(y,len(y))
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=y[0]
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print('EXC')
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/news', methods=['POST'])
def news():

    try:
       
        inputdata = request.form.get('news')    
        inputdata = json.loads(inputdata) 
        print("newsdata",inputdata)
        commonfile.writeLog("news",inputdata,0)
        keyarr = ["newsTitle","userTypeId","summary","newsDesc","flag"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            videoLink=""
            flag=inputdata['flag']
            if "newsTitle" in inputdata:
                if inputdata['newsTitle'] != "":
                    newsTitle =commonfile.EscapeSpecialChar(inputdata["newsTitle"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =commonfile.EscapeSpecialChar(inputdata["summary"])
            
            if "newsDesc" in inputdata:
                if inputdata['newsDesc'] != "":
                    newsDesc =commonfile.EscapeSpecialChar(inputdata["newsDesc"]) 

             
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"]


            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"          
            
            
            if 'NewsBanner' in request.files:      
                    file = request.files.get('NewsBanner')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getNewsPath(filename)  

                    filepath = '/newsimages/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            if flag =='i':      
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                      
                    column = column+"newsTitle,userTypeId,imagePath,summary,newsDesc,UserCreate"
                    values =values+ " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) + "','" + str(UserId) + "'"
                    data = databasefile.InsertQuery("news",column,values)        
                else:
                    column = "newsTitle,userTypeId,imagePath,summary,newsDesc"
                    values = " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) +  "'"
                    data = databasefile.InsertQuery("news",column,values)
            if flag =='u':
                print("  update news ")
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                # if "UserId" in inputdata:
                #     if inputdata['UserId'] != "":
                #         UserId =inputdata["UserId"]
                      
                #     whereCondition= " and id= '"+ str(Id) +"' and UserCreate='"+ str(UserId) +"'" 
                #     column="newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                #     data=databasefile.UpdateQuery("news",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('NewsBanner')
                        y=type(inputdata1)
                        print(y)
                        print("  update news1 ")
                        y2=len(inputdata1)
                        if  y2>4: 
                            print("  update news ")
                            index=re.search("/newsimages", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]
                            print("  update news 2")
                            print(inputdata1)


                        whereCondition=" and id= '"+ str(Id) +"'"
                        column="videoLink='"+ str(videoLink) +"',newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                        data=databasefile.UpdateQuery("news",column,whereCondition)


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 



@app.route('/getNews', methods=['POST'])
def getNews():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and n.Status<2 "
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+"  and n.userTypeId IN(0,'"+str(userTypeId)+"')"

            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and n.id='"+str(Id)+"'"
        
       
        
        

        orderby=" n.id "
        WhereCondition=WhereCondition+" and n.UserCreate=um.userId "
        column = " n.id,n.Status,n.newsTitle,n.videoLink,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,n.imagePath ,um.userName "
        data = databasefile.SelectQueryOrderby("news n,userMaster um",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectTotalCountQuery("news n,userMaster um ",WhereCondition,"")
        print(data2,"=====================")
        print(data['result'])
        if data != "0":
            data["totalCount"]=data2
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                    print(y,len(y))
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=y[0]
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()




@app.route('/marketingInsights', methods=['POST'])
def MarketingInsights():

    try:
       
        inputdata = request.form.get('news')    
        inputdata = json.loads(inputdata) 
        print("newsdata",inputdata)
        commonfile.writeLog("news",inputdata,0)
        keyarr = ["newsTitle","userTypeId","summary","newsDesc","flag"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            videoLink=""
            flag=inputdata['flag']
            column,values="",""
            if "newsTitle" in inputdata:
                if inputdata['newsTitle'] != "":
                    newsTitle =commonfile.EscapeSpecialChar(inputdata["newsTitle"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =commonfile.EscapeSpecialChar(inputdata["summary"])
            
            if "newsDesc" in inputdata:
                if inputdata['newsDesc'] != "":
                    newsDesc =commonfile.EscapeSpecialChar(inputdata["newsDesc"]) 

             
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 


            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"       
            
            
            if 'NewsBanner' in request.files:      
                    file = request.files.get('NewsBanner')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getMarketingInsightsPath(filename)  

                    filepath = '/marketingInsights/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            if flag =='i':      
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                      
                    column = column+"newsTitle,userTypeId,imagePath,summary,newsDesc,UserCreate"
                    values =values+ " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) + "','" + str(UserId) + "'"
                    data = databasefile.InsertQuery("marketingInsights",column,values)        
                else:
                    column = column+"newsTitle,userTypeId,imagePath,summary,newsDesc"
                    values = values+" '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) +  "'"
                    data = databasefile.InsertQuery("marketingInsights",column,values)
            if flag =='u':
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                # if "UserId" in inputdata:
                #     if inputdata['UserId'] != "":
                #         UserId =inputdata["UserId"]
                      
                #     whereCondition= " and id= '"+ str(Id) +"' and UserCreate='"+ str(UserId) +"'" 
                #     column="newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                #     data=databasefile.UpdateQuery("news",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('NewsBanner')
                        y=type(inputdata1)
                        print(y)
                        y2=len(inputdata1)
                        if  y2>4:
                            index=re.search("/marketingInsights", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]


                        whereCondition=" and id= '"+ str(Id) +"'"
                        column="videoLink='"+ str(videoLink) +"' ,newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                        data=databasefile.UpdateQuery("marketingInsights",column,whereCondition)


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()








@app.route('/getMarketingInsights', methods=['POST'])
def getMarketingInsights():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and n.Status<2 "
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+"  and n.userTypeId IN(0,'"+str(userTypeId)+"')"

            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and n.id='"+str(Id)+"'"
        
       
        
        

        orderby=" n.id "
        WhereCondition=WhereCondition+" and n.UserCreate=um.userId "
        column = " n.id,n.Status,n.newsTitle,n.videoLink,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, n.imagePath ,um.userName "
        data = databasefile.SelectQueryOrderby("marketingInsights n,userMaster um",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectTotalCountQuery("marketingInsights n,userMaster um",WhereCondition,"")

        if data != "0":
            data["totalCount"]=data2
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=y[0]
                else:
                    i['videoId']=""



            return data
            
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/upSkillsOpportunity', methods=['POST'])
def upSkillsOpportunity():

    try:
       
        inputdata = request.form.get('news')    
        inputdata = json.loads(inputdata) 
        print("newsdata",inputdata)
        commonfile.writeLog("news",inputdata,0)
        keyarr = ["newsTitle","userTypeId","summary","newsDesc","flag"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            flag=inputdata['flag']
            column,values="",""
            if "newsTitle" in inputdata:
                if inputdata['newsTitle'] != "":
                    newsTitle =commonfile.EscapeSpecialChar(inputdata["newsTitle"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =commonfile.EscapeSpecialChar(inputdata["summary"])
            
            if "newsDesc" in inputdata:
                if inputdata['newsDesc'] != "":
                    newsDesc =commonfile.EscapeSpecialChar(inputdata["newsDesc"])

            if "length" in inputdata:
                if inputdata['length'] != "":
                    length =commonfile.EscapeSpecialChar(inputdata["length"])

            if "effort" in inputdata:
                if inputdata['effort'] != "":
                    effort =commonfile.EscapeSpecialChar(inputdata["effort"])

            if "price" in inputdata:
                if inputdata['price'] != "":
                    price =commonfile.EscapeSpecialChar(inputdata["price"]) 

            if "institutions" in inputdata:
                if inputdata['institutions'] != "":
                    institutions =commonfile.EscapeSpecialChar(inputdata["institutions"]) 

            if "level" in inputdata:
                if inputdata['level'] != "":
                    level =commonfile.EscapeSpecialChar(inputdata["level"])

            
            if "language" in inputdata:
                if inputdata['language'] != "":
                   language=commonfile.EscapeSpecialChar(inputdata["language"])
            if "isFeatured" in inputdata:
                isFeatured=commonfile.EscapeSpecialChar(inputdata["isFeatured"])

            if "videoTranscript" in inputdata:
                if inputdata['videoTranscript'] != "":
                    videoTranscript =commonfile.EscapeSpecialChar(inputdata["videoTranscript"]) 


            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"         
                


                 

             
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"]        
            
            
            if 'NewsBanner' in request.files:      
                    file = request.files.get('NewsBanner')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getUpSkillsOpportunity(filename)  

                    filepath = '/UpSkillsOpportunity/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            if flag =='i':      
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                      
                    column =column+ "newsTitle,userTypeId,imagePath,summary,newsDesc,UserCreate,length,effort,price,institutions,level,language,videoTranscript,isFeatured"
                    values =values+ " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) + "','" + str(UserId) + "'"
                    values= values+ " ,'"+ str(length) +"','" + str(effort)+"','" + str(price)+"','" + str(institutions) +"','" + str(level)  +"','" + str(language) +"','" + str(videoTranscript)+"','" + str(isFeatured)+  "'"
                    data = databasefile.InsertQuery("upSkillsOpportunity",column,values)        
                else:
                    column =column+ "newsTitle,userTypeId,imagePath,summary,newsDesc,length,effort,price,institutions,level,language,videoTranscript"
                    values =values+ " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) +  "'"
                    values= values+ ", '"+ str(length) +"','" + str(effort)+"','" + str(price)+"','" + str(institutions) +"','" + str(level)  +"','" + str(language) +"','" + str(videoTranscript)+  "'"
                    data = databasefile.InsertQuery("upSkillsOpportunity",column,values)
            if flag =='u':
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                # if "UserId" in inputdata:
                #     if inputdata['UserId'] != "":
                #         UserId =inputdata["UserId"]
                      
                #     whereCondition= " and id= '"+ str(Id) +"' and UserCreate='"+ str(UserId) +"'" 
                #     column="newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                #     data=databasefile.UpdateQuery("news",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('NewsBanner')
                        y=type(inputdata1)
                        print(y)
                        y2=len(inputdata1)
                        if  y2>4:
                            index=re.search("/UpSkillsOpportunity", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]


                        whereCondition=" and id= '"+ str(Id) +"'"
                        column="videoLink='"+ str(videoLink) +"' ,isFeatured='"+ str(isFeatured) +"',newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"',length='"+ str(length) +"',effort='"+ str(effort) +"',price='"+ str(price) +"',institutions='"+ str(institutions) +"',level='"+ str(level) +"',language='"+ str(language) +"',videoTranscript='"+ str(videoTranscript) +"'"
                        data=databasefile.UpdateQuery("upSkillsOpportunity",column,whereCondition)


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/getUpSkillsOpportunity', methods=['POST'])
def getupSkillsOpportunity():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and n.Status<2 "
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])

            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+"  and n.userTypeId IN(0,'"+str(userTypeId)+"')"

            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and n.id='"+str(Id)+"'"
        
       
        
        

        orderby=" n.id "
        WhereCondition=WhereCondition+" and n.UserCreate=um.userId "
        column = " n.id,n.Status,n.newsTitle,n.userTypeId,n.summary,n.isFeatured,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, n.imagePath,n.videoLink ,um.userName,n.length,n.effort,n.price,n.institutions,n.level,n.language,n.videoTranscript"
        data = databasefile.SelectQueryOrderby("upSkillsOpportunity n,userMaster um",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectTotalCountQuery("upSkillsOpportunity n,userMaster um",WhereCondition,"")

        if data != "0":
            data["totalCount"]=data2
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=y[0]
                else:
                    i['videoId']=""
                y2=i['id']
                columns="count(*) as count"
                whereCondition=" and upSkillsId='"+str(y2)+"'"
                dat=databasefile.SelectQuery('enrollUpskills',columns,whereCondition,"",startlimit,endlimit)
                print(dat,'++++++++++++++++++++++++++++')
                if dat['status']!='false':
                    lki=dat['result'][0]['count']
                    i['enrollCount']=lki
                else:
                    i['enrollCount']=0

            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/getUpSkillsOpportunityEnrolled', methods=['POST'])
def getParliamentEvent111():

    try:        
       startlimit,endlimit="",""
       WhereCondition=""
       if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "Id" in inputdata:
                if inputdata['Id'] != "":
                    Id =inputdata["Id"] 
                    WhereCondition=WhereCondition+" and pm.id='"+str(Id)+"'"

            orderby="ei.id"
            
            column = " us.userName,us.email,us.mobileNo, date_format(CONVERT_TZ(ei.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=WhereCondition+" and pm.id=ei.upSkillsId and us.userId=ei.userId"
            data2=databasefile.SelectQueryOrderby("upSkillsOpportunity as pm,enrollUpskills as ei,userMaster as us",column,WhereCondition,"",startlimit,endlimit,orderby)

            data1 = databasefile.SelectQuery4("upSkillsOpportunity as pm,enrollUpskills as ei,userMaster as us",column,WhereCondition)
            
            if data1['status'] != "false":
                data={"status":"true","result":data2['result'],"totalCount":len(data1['result'])}
                return data
            else:
                return data

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()                           


@app.route('/allMarketingInsightThread', methods=['POST'])
def allMarketingInsightThread():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        WhereCondition1=""

        keyarr = ['Id']
        print(inputdata,"B")
        commonfile.writeLog("allMarketingInsightThread",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,WhereCondition1="",""

            if "userId" in inputdata and 'Id' in inputdata:
                marketingInsightId=inputdata['Id']
                if inputdata['userId'] != "":
                    userId =inputdata["userId"]
                    WhereCondition="  and pm.userId ='"+str(userId)+"' and pm.userId=um.userId and  pm.marketingInsightId='" + str(marketingInsightId) + "' "
                    column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    orderby=" pm.id "
                    data1 = databasefile.SelectQueryOrderbyAsc("marketingInsightComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)
                    WhereCondition1="  and  pm.userId =um.userId and pm.status='1' and pm.marketingInsightId='" + str(marketingInsightId) + "' "
                    data2=databasefile.SelectQueryOrderbyAsc("marketingInsightComment as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
                    print(data2['result'],'@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@')
                    print(data1['result'],"!!!332")
                    if data1['result'] == "":
                        data1['result']=[]
                    for i in data2['result']:
                        if i not in data1['result']:
                            data1['result'].append(i)

                   


                    
                    WhereCondition=" and n.id='"+str(marketingInsightId)+"'"
                    column = " n.id,n.Status,n.newsTitle,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',n.imagePath)imagePath ,um.userName "
                    data = databasefile.SelectQuery1("marketingInsights n,userMaster um",column,WhereCondition)
                    print(data,"@@@@@@@@@@@")

                  
            
            # if 'Id' in inputdata:
            #     marketingInsightId=inputdata['Id']
              
            #     column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            #     WhereCondition="  and pm.userId=um.userId and pm.marketingInsightId='" + str(marketingInsightId) + "'" 
            #     orderby=" id "

            #     data1 = databasefile.SelectQueryOrderbyAsc("marketingInsightComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)
            #     WhereCondition=" and n.id='"+str(marketingInsightId)+"'"
            #     column = " n.id,n.Status,n.newsTitle,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',n.imagePath)imagePath ,um.userName "
            #     data = databasefile.SelectQuery1("marketingInsights n,userMaster um",column,WhereCondition)
            #     print(data,"111111111111111111")
            # print(data,"22222222222222")

            data1['result']=sorted(data1['result'], key = lambda i: i['DateCreate'])
            # data1['result']=data1['result'][::-1]



            
            
            if (data['status']!='False'):
            
                        
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data1['result']}
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


@app.route('/allMarketingInsightThread1', methods=['POST'])
def allMarketingInsightThread1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        WhereCondition1=""

        keyarr = ['Id']
        print(inputdata,"B")
        commonfile.writeLog("allMarketingInsightThread1",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,WhereCondition1="",""

           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])

            if "userId" in inputdata and 'Id' in inputdata:
                marketingInsightId=inputdata['Id']
                if inputdata['userId'] != "":
                    userId =inputdata["userId"]
                    WhereCondition="  and pm.userId ='"+str(userId)+"'  and pm.marketingInsightId='" + str(marketingInsightId) + "' or pm.userId=um.userId  and pm.status='1'"
                    column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    orderby=" id "
                    data1 = databasefile.SelectQueryOrderbyAsc("marketingInsightComment as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
                    WhereCondition=" and n.id='"+str(marketingInsightId)+"'"
                    column = " n.id,n.Status,n.newsTitle,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',n.imagePath)imagePath ,um.userName "
                    data = databasefile.SelectQuery1("marketingInsights n,userMaster um",column,WhereCondition)

                  
            
            if 'Id' in inputdata:
                marketingInsightId=inputdata['Id']
              
                column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                WhereCondition="  and pm.userId=um.userId and pm.marketingInsightId='" + str(marketingInsightId) + "'" 
                orderby=" pm.id"

                data1 = databasefile.SelectQueryOrderbyAsc("marketingInsightComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)
                print(data1,"++++++++++++++++++=")
                

                data2=databasefile.SelectQuery4("marketingInsightComment as pm,userMaster as um",column1,WhereCondition)
                WhereCondition=" and n.id='"+str(marketingInsightId)+"'"
                column = " n.id,n.Status,n.newsTitle,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',n.imagePath)imagePath ,um.userName "
                data = databasefile.SelectQuery1("marketingInsights n,userMaster um",column,WhereCondition)
                print(data,"111111111111111111")
            



            
            
            if (data['status']!='False'):
            
                        
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":[data1['result'],[data['result']]],"totalcount":len(data2['result'])}
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



@app.route('/likeMarketingInsight', methods=['POST'])
def likeMarketingInsight():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','marketingInsightId','userTypeId']
        commonfile.writeLog("likeMarketingInsight",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            approvedUserId = inputdata["userId"]
            postId = inputdata["marketingInsightId"]
            userTypeId = int(inputdata["userTypeId"])

            WhereCondition = " and marketingInsightId = '" + str(postId) + "' and userId = '" + str(approvedUserId) + "'"
            count = databasefile.SelectCountQuery("likeMarketingInsight",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                print("333333333333333333333")
             
               
                column = "userId,marketingInsightId,userTypeId"                
                values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "'"
                data = databasefile.InsertQuery("likeMarketingInsight",column,values)
                if data!="0":
                    column="count(*) as count"
                    whereCondition=" and marketingInsightId ='" + str(postId) + "' "
                    data1=databasefile.SelectQuery("likeMarketingInsight",column,whereCondition,"",startlimit,endlimit)
                    if (data1["status"]!="false"):
                        o=[]
                        y=data1["result"][0]
                        print(y,"+++++++++++++++++=")
                        whereCondition99= " and marketingInsightId ='" + str(postId) + "' and userId='" + str(approvedUserId) + "'"
                        column88="status"
                        da1=databasefile.SelectQuery("likeMarketingInsight",column88,whereCondition99,"",startlimit,endlimit)
                        if da1['status'] != 'false':
                            for i in da1['result']:
                                i['makedone']=1
                                y2={"makedone":1}
                                y.update(y2)
                                o.append(y)        
                       

                      
                       
                        data1={"status":"true","result":o,"message":""}
                        return data1
                    else:
                        data1={"status":"true","result":"","message":"No Data Found"}
                        return data1

                else:
                    return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output  




@app.route('/eventInterest', methods=['POST'])
def verifyPost123():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','eventId','userTypeId']
        commonfile.writeLog("eventInterest",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            approvedUserId = inputdata["userId"]
            postId = inputdata["eventId"]
            userTypeId = int(inputdata["userTypeId"])

            WhereCondition = " and eventId = '" + str(postId) + "' and userId = '" + str(approvedUserId) + "'"
            count = databasefile.SelectCountQuery("eventInterest",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EventInterstAlreadyExistMsg()
            else:
                print("333333333333333333333")
             
               
                column = "userId,eventId,userTypeId,UserCreate"                
                values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(approvedUserId) + "'"
                data = databasefile.InsertQuery("eventInterest",column,values)
                if data!="0":
                    column="*"
                    whereCondition=" and eventId ='" + str(postId) + "'"
                    data1=databasefile.SelectQuery("eventInterest",column,whereCondition,"",startlimit,endlimit)
                    if (data1["status"]!="false"):
                        y=data1["result"][0]
                        for i in data1['result']:
                            i['likeStatus']=1
                        y2=i['likeStatus']

                      
                       
                        data1={"status":"true","result":y2,"message":""}
                        return data1
                    else:
                        data1={"status":"true","result":"","message":"No Data Found"}
                        return data1

                else:
                    return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/eventInterest1', methods=['POST'])
def verifyPost1231():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','eventId','userTypeId']
        commonfile.writeLog("eventInterest",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            approvedUserId = inputdata["userId"]
            postId = inputdata["eventId"]
            userTypeId = int(inputdata["userTypeId"])

            WhereCondition = " and eventId = '" + str(postId) + "' and userId = '" + str(approvedUserId) + "'"
            count = databasefile.SelectCountQuery("eventInterest",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EventInterstAlreadyExistMsg()
            else:
                print("333333333333333333333")
             
               
                column = "userId,eventId,userTypeId,UserCreate"                
                values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(approvedUserId) + "'"
                data = databasefile.InsertQuery("eventInterest",column,values)
                if data!="0":
                    column="count(*) as count"
                    whereCondition="  and eventId ='" + str(postId) + "' "
                    data1=databasefile.SelectQuery("eventInterest",column,whereCondition,"",startlimit,endlimit)
                    if (data1["status"]!="false"):
                        o=[]
                        y=data1["result"][0]
                        print(y,"+++++++++++++++++=")
                        whereCondition99= " and eventId ='" + str(postId) + "' and userId='" + str(approvedUserId) + "'"
                        column88="status"
                        da1=databasefile.SelectQuery("eventInterest",column88,whereCondition99,"",startlimit,endlimit)
                        if da1['status'] != 'false':
                            for i in da1['result']:
                                i['makedone']=1
                                y2={"makedone":1}
                                y.update(y2)
                                o.append(y)        
                       

                      
                       
                        data1={"status":"true","result":o,"message":""}
                        return data1
                    else:
                        data1={"status":"true","result":"","message":"No Data Found"}
                        return data1

                else:
                    return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output



@app.route('/commentsEvent', methods=['POST'])
def commentsevent():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','eventId','userTypeId','commentDescription']
        commonfile.writeLog("verifyPost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            userId = inputdata["userId"]
            postId = inputdata["eventId"]
            userTypeId = int(inputdata["userTypeId"])
            print("333333333333333333333")
            if (userTypeId == 7) or (userTypeId =='7'):
         
                commentDescription=inputdata['commentDescription']
                column = "userId,eventId,userTypeId,commentDescription,status"                
                values = " '" + str(userId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(commentDescription) + "','" + str('1')  + "'"
                data = databasefile.InsertQuery("eventComment",column,values)
            else:
                commentDescription=inputdata['commentDescription']
                column = "userId,eventId,userTypeId,commentDescription,status"                
                values = " '" + str(userId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(commentDescription)+ "','" + str('1') + "'"
                data = databasefile.InsertQuery("eventComment",column,values)


            WhereCondition="  and pm.userId ='"+str(userId)+"'  and eventId='" + str(postId) + "' and pm.userId=um.userId"
            column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            orderby=" id "
            data1 = databasefile.SelectQueryOrderby("eventComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)
            r=[]
            r2=data1['result'][-1]
            r.append(r2)


                




            if data!="0":
                data1={"status":"true","result":r,"message":""}
                return data1
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output








@app.route('/commentsMarketingInsight', methods=['POST'])
def commentsMarketingInsight():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','marketingInsightId','userTypeId','commentDescription']
        commonfile.writeLog("verifyPost",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            userId = inputdata["userId"]
            postId = inputdata["marketingInsightId"]
            userTypeId = int(inputdata["userTypeId"])
            print("333333333333333333333")
            if (userTypeId == 7) or (userTypeId =='7'):
         
                commentDescription=inputdata['commentDescription']
                column = "userId,marketingInsightId,userTypeId,commentDescription,status"                
                values = " '" + str(userId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(commentDescription) + "','" + str('1')  + "'"
                data = databasefile.InsertQuery("marketingInsightComment",column,values)
            else:
                commentDescription=inputdata['commentDescription']
                column = "userId,marketingInsightId,userTypeId,commentDescription,status"                
                values = " '" + str(userId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(commentDescription)+ "','" + str('1') + "'"
                data = databasefile.InsertQuery("marketingInsightComment",column,values)


          
            WhereCondition="  and pm.userId ='"+str(userId)+"'  and marketingInsightId='" + str(postId) + "' and um.userId=pm.userId"
            column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            orderby=" id "
            data1 = databasefile.SelectQueryOrderby("marketingInsightComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)
            print(data1)
            r=[]
            r2=data1['result'][-1]
            print(r2,"111111@@@@@@@@@@@@@@@@@@@@2")
            r.append(r2)


                




            if data!="0":
                data1={"status":"true","result":r,"message":""}
                return data1
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/allEventThread', methods=['POST'])
def allEventThread():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        WhereCondition1=""

        keyarr = ['Id']
        print(inputdata,"B")
        commonfile.writeLog("allEventThread",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,WhereCondition1="",""


            

            if "userId" in inputdata and 'Id' in inputdata:
                eventId=inputdata['Id']
                if inputdata['userId'] != "":
                    userId =inputdata["userId"]
                    WhereCondition="  and pm.userId ='"+str(userId)+"' and  pm.userId=um.userId and pm.eventId='" + str(eventId) + "' "
                    column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    orderby=" pm.id "
                    data1 = databasefile.SelectQueryOrderbyAsc("eventComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)

                    WhereCondition=" and  pm.userId =um.userId and pm.status='1' and  pm.eventId='" + str(eventId) + "' "
                    column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    orderby=" pm.id "
                    data2 = databasefile.SelectQueryOrderbyAsc("eventComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)
                    if data1['result'] == "":
                        data1['result']=[]
                    for i in data2['result']:
                        if i not in data1['result']:
                            data1['result'].append(i)




                    WhereCondition=" and ev.id='"+str(eventId)+"'"
                    column = " ev.id,ev.Status,ev.UserCreate,ev.eventTitle,ev.eventSummary,ev.eventLocation,date_format(CONVERT_TZ(ev.eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate, date_format(CONVERT_TZ(ev.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath ,um.userName "
                    data = databasefile.SelectQuery1("parliamentEvent as ev,userMaster um",column,WhereCondition)

                  
            
           
            print(data,"22222222222222")
            data1['result']=sorted(data1['result'], key = lambda i: i['DateCreate'])
    



            
            
            if (data['status']!='False'):
            
                        
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data1['result']}
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



@app.route('/allEventThread1', methods=['POST'])
def allEventThread1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        WhereCondition1=""

        keyarr = ['Id']
        print(inputdata,"B")
        commonfile.writeLog("allEventThread",inputdata,0)
        data1={"status":"true","message":"","result":[]}
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            orderby="pm.id"
            postId,WhereCondition1="",""

           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])

            if "userId" in inputdata and 'Id' in inputdata:
                eventId=inputdata['Id']
                if inputdata['userId'] != "":
                    userId =inputdata["userId"]
                    WhereCondition="  and pm.userId ='"+str(userId)+"'  and pm.eventId='" + str(eventId) + "' or pm.status='1'"
                    column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    orderby=" id "
                    data1 = databasefile.SelectQueryOrderbyAsc("eventComment as pm,userMaster as um",column1,WhereCondition1,"",orderby,startlimit,endlimit)
                    WhereCondition=" and ev.id='"+str(eventId)+"'"
                    column = " ev.id,ev.Status,ev.UserCreate,ev.eventTitle,ev.eventSummary,ev.eventLocation,date_format(CONVERT_TZ(ev.eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate, date_format(CONVERT_TZ(ev.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath ,um.userName "
                    data = databasefile.SelectQuery1("parliamentEvent as ev,userMaster um",column,WhereCondition)

                  
            
            if 'Id' in inputdata:
                eventId=inputdata['Id']
              
                column1="pm.id,um.userName,um.email,pm.Status,pm.commentDescription,(pm.userId)commentedBy,pm.userTypeId, date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                WhereCondition="  and pm.userId=um.userId and pm.eventId='" + str(eventId) + "'" 
                orderby=" id "

                data1 = databasefile.SelectQueryOrderbyAsc("eventComment as pm,userMaster as um",column1,WhereCondition,"",orderby,startlimit,endlimit)
                data2= databasefile.SelectQuery4("eventComment as pm,userMaster as um",column1,WhereCondition)
               
                WhereCondition=" and ev.id='"+str(eventId)+"'"
                column = " ev.id,ev.Status,ev.UserCreate,ev.eventTitle,ev.eventSummary,ev.eventLocation,date_format(CONVERT_TZ(ev.eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate, date_format(CONVERT_TZ(ev.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath ,um.userName "
                data = databasefile.SelectQuery1("parliamentEvent as ev,userMaster um",column,WhereCondition)
                print(data,"111111111111111111")

            print(data,"22222222222222")



            
            
            if (data['status']!='False'):
            
                        
                        
                
                print("111111111111111")          
                Data = {"status":"true","message":"","result":[data1['result'],[data['result']]],"totalcount":len(data2['result'])}
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


#comments
@app.route('/commentsEventApproved', methods=['POST'])
def commentsevent2():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['id']
        commonfile.writeLog("commentsEventApproved",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            Id = inputdata["id"]
            whereCondition=" and id ='"+str(Id)+"'"
            
           
           
            
            column = "status=1"                
            
            data = databasefile.UpdateQuery("eventComment",column,whereCondition)

            if data!="0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/commentsEventRejected', methods=['POST'])
def commentsevent1():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['id']
        commonfile.writeLog("commentsEventRejected",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            Id = inputdata["id"]
            whereCondition=" and id ='"+str(Id)+"'"
            
           
           
            
            column = "status=2"                
            
            data = databasefile.UpdateQuery("eventComment",column,whereCondition)

            if data!="0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/commentsMarketApproved', methods=['POST'])
def commentsevent222():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['id']
        commonfile.writeLog("commentsMarketApproved",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            Id = inputdata["id"]
            whereCondition=" and id ='"+str(Id)+"'"
            
           
           
            
            column = "status=1"                
            
            data = databasefile.UpdateQuery("marketingInsightComment",column,whereCondition)

            if data!="0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output


@app.route('/commentsMarketRejected', methods=['POST'])
def commentsevent12():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['id']
        commonfile.writeLog("commentsMarketRejected",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            Id = inputdata["id"]
            whereCondition=" and id ='"+str(Id)+"'"
            
           
           
            
            column = "status=2"                
            
            data = databasefile.UpdateQuery("marketingInsightComment",column,whereCondition)

            if data!="0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output        







@app.route('/enrollUpSkills', methods=['POST'])
def enrollUpSkills():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','upSkillsId','userTypeId']
        commonfile.writeLog("enrollUpSkills",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            approvedUserId = inputdata["userId"]
            postId = inputdata["upSkillsId"]
            userTypeId = int(inputdata["userTypeId"])

            WhereCondition = " and upSkillsId = '" + str(postId) + "' and userId = '" + str(approvedUserId) + "'"
            count = databasefile.SelectCountQuery("enrollUpskills",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EmailMobileAlreadyExistMsg()
            else:
                print("333333333333333333333")
             
               
                column = "userId,upSkillsId,userTypeId"                
                values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "'"
                data = databasefile.InsertQuery("enrollUpskills",column,values)
                if data!="0":
                    column="count(*) as count"
                    whereCondition=" and upSkillsId ='" + str(postId) + "'"
                    data1=databasefile.SelectQuery("enrollUpskills",column,whereCondition,"",startlimit,endlimit)
                    if (data1["status"]!="false"):
                        o=[]
                        y=data1["result"][0]
                        print(y,"+++++++++++++++++=")
                        whereCondition99= " and upSkillsId  ='" + str(postId) + "' and userId='" + str(approvedUserId) + "'"
                        column88="status"
                        da1=databasefile.SelectQuery("enrollUpskills",column88,whereCondition99,"",startlimit,endlimit)
                        if da1['status'] != 'false':
                            for i in da1['result']:
                                i['makedone']=1
                                y2={"makedone":1}
                                y.update(y2)
                                o.append(y)    
                      
                       
                        data1={"status":"true","result":o,"message":""}
                        return data1
                    else:
                        data1={"status":"true","result":"","message":"No Data Found"}
                        return data1

                else:
                    return commonfile.Errormessage()
        else:
            return msg 

    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output

@app.route('/landingPageDashboard', methods=['POST'])
def landingPageDashboard1():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        startlimit,endlimit="",""
        
        data1={"message":"","status":"true","result":[]}
        orderby=" id "
        inputdata={}
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())
            print(inputdata,"++++++++++++++++++")        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
            
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
        
            
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+" and  userTypeId=0  or userTypeId='"+str(userTypeId)+"'"
                    column1 = "id,Status,UserCreate,title,summary,videoLink, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath  "
                    data1 = databasefile.SelectQueryOrderby("announcement",column1,WhereCondition,"",startlimit,endlimit,orderby)
                    print(data1,"")
                    
                    if data1["result"]=="":
                        data1["result"]=[]
                    else:
                        for i in data1["result"]:
                            if i["imagePath"]!="":
                                i["imagePath"]=ConstantData.GetBaseURL()+i["imagePath"]

        if "userTypeId" not in inputdata:
            column = "id,Status,UserCreate,newsTitle,summary,newsDesc, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
            data = databasefile.SelectQueryOrderby("news ",column,WhereCondition,"","0","3",orderby)
            if data["result"]=="":
                data["result"]=[]
        else :
            column = "id,Status,UserCreate,newsTitle,summary,newsDesc, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
            data = databasefile.SelectQueryOrderby("news ",column,WhereCondition,"","0","10",orderby)
            if data["result"]=="":
                data["result"]=[]
        

        column2 = "id,Status,UserCreate, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath  "
        data2 = databasefile.SelectQueryOrderby("gallery",column2,"","","0","10",orderby)
        
        if data2["result"]=="":
            data2["result"]=[]

        print("1111----")

        if "userTypeId" not in inputdata:
            
            WhereCondition229=" and ev.Status<2  "
            column3 = "ev.id,ev.Status,ev.UserCreate,ev.eventTitle,ev.eventSummary,ev.eventLocation,date_format(CONVERT_TZ(ev.eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate, date_format(CONVERT_TZ(ev.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
            data3 = databasefile.SelectQueryOrderby("parliamentEvent ev ",column3,WhereCondition229,"","0","10",orderby)
            
            if data3:
                if data3["result"]=="":
                    data3["result"]=[]
            

       
        if "userTypeId" in inputdata:
            if inputdata['userTypeId'] != "":
                userTypeId =inputdata["userTypeId"]
                WhereCondition229=" and ev.Status<2 and  ev.userTypeId=0  or ev.userTypeId='"+str(userTypeId)+"'"
                column3 = "ev.id,ev.Status,ev.UserCreate,ev.eventTitle,ev.eventSummary,ev.eventLocation,date_format(CONVERT_TZ(ev.eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate, date_format(CONVERT_TZ(ev.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
                data3 = databasefile.SelectQueryOrderby("parliamentEvent ev ",column3,WhereCondition229,"","0","10",orderby)
                if data3["result"]=="":
                    data3["result"]=[]

        for i in data3['result']:
            if 'userId' in inputdata:
                userId=inputdata['userId']
                marketingInsightId=i['id']
                whereCondition999="and lki.eventId='"+str(marketingInsightId)+"' and lki.userId='"+str(userId)+"'"
                column999="lki.status"
                makedone=databasefile.SelectQuery('eventInterest as lki',column999,whereCondition999,"","","")
                if makedone['status']!="false":
                    i['makedone']=1
                else:
                    i['makedone']=0

            else:
                i['makedone']=0    



        column4 = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,videoPath,text,UserCreate  "
        WhereCondition2= " and Status<2"
        data4 = databasefile.SelectQuery("promisingInitiatives",column4,WhereCondition2,"","0","10")
        print(data4)

        if data4["result"]=="":
            data4["result"]=[]

        for m in data4['result']:
                if m['imagePath']!='':
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoPath']!="":
                    y=m['videoPath'].split('=')
                    print(y,'++++++')
                    m['videoId']=y[1]
            
        column5 = "id,Status,UserCreate,newsTitle,summary,newsDesc, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
        data5 = databasefile.SelectQueryOrderby("promissingIntiatives",column5,WhereCondition,"","0","10",orderby)
        if data5["result"]=="":
            data5["result"]=[]


        if "userTypeId" in inputdata:
            if inputdata['userTypeId'] != "":
                userTypeId =inputdata["userTypeId"]
                WhereCondition="and  mi.Status <2 and  mi.userTypeId=0  or mi.userTypeId='"+str(userTypeId)+"'"

                column6 = "mi.id,mi.Status,mi.UserCreate,mi.newsTitle,mi.userTypeId,mi.summary,mi.newsDesc,date_format(CONVERT_TZ(mi.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',mi.imagePath)imagePath   "
                data6 = databasefile.SelectQueryOrderby("marketingInsights as mi ",column6,WhereCondition,"","0","10",orderby)
                if data6["result"]=="":
                    data6["result"]=[]

                for i in data6['result']:
                    marketingInsightId=i['id']

                    if 'userId' in inputdata:
                        userId=inputdata['userId']

                   
                        whereCondition="and lki.marketingInsightId='"+str(marketingInsightId)+"'"
                        columns="count(*) as count"
                        likeCount=databasefile.SelectQuery('likeMarketingInsight as lki',columns,whereCondition,"","","")
                        print(likeCount,"+++++++++++++")
                        if likeCount['status']!='false':
                            whereCondition999="and lki.marketingInsightId='"+str(marketingInsightId)+"' and userId='"+str(userId)+"'"
                            column999="status"
                            makedone=databasefile.SelectQuery('likeMarketingInsight as lki',column999,whereCondition999,"","","")
                           

                            lki=likeCount['result'][0]['count']

                                

                            i['likeCount']=lki
                            if makedone['status']!="false":
                                i['makedone']=1
                            else:
                                i['makedone']=0
                                
                            
                        else:
                            i['likeCount']=0
                            i['makedone']=0
               
                column7 = "mi.id,mi.Status,mi.UserCreate,mi.isFeatured,mi.newsTitle,mi.userTypeId,mi.summary,mi.newsDesc,date_format(CONVERT_TZ(mi.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',mi.imagePath)imagePath,mi.length,mi.level,mi.language,mi.effort,mi.price,mi.videoTranscript"
                data7 = databasefile.SelectQueryOrderby("upSkillsOpportunity  as mi",column7,WhereCondition,"","0","10",orderby)
                if data7["result"]=="":
                    data7["result"]=[]
                a=[]
                b=[]
                for i in data7['result']:

                    if 'userId' in inputdata:
                        userId=inputdata['userId']
                        marketingInsightId=i['id']
                        whereCondition999="and lki.upSkillsId='"+str(marketingInsightId)+"' and lki.userId='"+str(userId)+"'"
                        column999="lki.status"
                        makedone=databasefile.SelectQuery('enrollUpskills as lki',column999,whereCondition999,"","","")
                        if makedone['status']!="false":

                            i['makedone']=1
                        else:
                            i['makedone']=0
                        if i['isFeatured'] ==0:
                            a.append(i)
                        if i['isFeatured']==1:
                            b.append(i)


        if "userTypeId" not  in inputdata:
           
   
            WhereCondition="and  mi.Status <2 "

            column6 = "mi.id,mi.Status,mi.UserCreate,mi.newsTitle,mi.userTypeId,mi.summary,mi.newsDesc,date_format(CONVERT_TZ(mi.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',mi.imagePath)imagePath   "
            data6 = databasefile.SelectQueryOrderby("marketingInsights as mi ",column6,WhereCondition,"","0","10",orderby)
            if data6["result"]=="":
                data6["result"]=[]
            for i in data6['result']:
                marketingInsightId=i['id']

                if 'userId' in inputdata:
                    userId=inputdata['userId']

               
                    whereCondition="and lki.marketingInsightId='"+str(marketingInsightId)+"'"
                    columns="count(*) as count"
                    likeCount=databasefile.SelectQuery('likeMarketingInsight as lki',columns,whereCondition,"","","")
                    print(likeCount,"+++++++++++++")
                    if likeCount['status']!='false':
                        whereCondition999="and lki.marketingInsightId='"+str(marketingInsightId)+"' and userId='"+str(userId)+"'"
                        column999="status"
                        makedone=databasefile.SelectQuery('likeMarketingInsight as lki',column999,whereCondition999,"","","")
                       

                        lki=likeCount['result'][0]['count']

                            

                        i['likeCount']=lki
                        if makedone['status']!="false":
                            i['makedone']=1
                        else:
                            i['makedone']=0
                            
                        
                    else:
                        i['likeCount']=0
                        i['makedone']=0
                else:
                   
                    i['makedone']=0
                                
           
            column7 = "mi.id,mi.Status,mi.UserCreate,mi.newsTitle,mi.userTypeId,mi.summary,mi.newsDesc,date_format(CONVERT_TZ(mi.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',mi.imagePath)imagePath,mi.length,mi.level,mi.language,mi.effort,mi.price,mi.videoTranscript"
            data7 = databasefile.SelectQueryOrderby("upSkillsOpportunity  as mi",column7,WhereCondition,"","0","10",orderby)
            if data7["result"]=="":
                data7["result"]=[]
            for i in data7['result']:
                if 'userId' in inputdata:
                    userId=inputdata['userId']
                    marketingInsightId=i['id']
                    whereCondition999="and lki.upSkillsId='"+str(marketingInsightId)+"' and lki.userId='"+str(userId)+"'"
                    column999="lki.status"
                    makedone=databasefile.SelectQuery('enrollUpskills as lki',column999,whereCondition999,"","","")
                    if makedone['status']!="false":

                        i['makedone']=1
                    else:
                        i['makedone']=0
                else:
                    i['makedone']=0


        column99 = "id,Status,UserCreate, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath  "
        data99 = databasefile.SelectQueryOrderby("ourPartners",column99,"","","0","10",orderby)
        
        if data99["result"]=="":
            data99["result"]=[]

                        




            

            

       


        if data != "0":
            
            return {"message":"","status":"true","marketingInsights":data6['result'],"upSkillsOpportunity":{"featured Programs":a,"top Rated Programs":b},"promissingIntiatives":data5["result"],"news":data["result"],"announcement":data1["result"],"gallery":data2["result"],"event":data3["result"],"promisingInitiatives":data4["result"],"ourPartners":data99}
            
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()                









# @app.route('/landingPageDashboard1', methods=['POST'])
# def landingPageDashboard():

#     try:
#         WhereCondition,startlimit,endlimit="","",""
#         WhereCondition=WhereCondition+" and Status<2"
        
#         data1={"message":"","status":"true","result":[]}
#         orderby=" id "
#         if request.get_data():
#             inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
#             if "startlimit" in inputdata:
#                 if inputdata['startlimit'] != "":
#                     startlimit =str(inputdata["startlimit"])
            
#             if "endlimit" in inputdata:
#                 if inputdata['endlimit'] != "":
#                     endlimit =str(inputdata["endlimit"])
        
            
#             if "userTypeId" in inputdata:
#                 if inputdata['userTypeId'] != "":
#                     userTypeId =inputdata["userTypeId"]
#                     WhereCondition=WhereCondition+" and  userTypeId=0  or userTypeId='"+str(userTypeId)+"'"
#                     column1 = "id,Status,UserCreate,title,summary,videoLink, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath  "
#                     data1 = databasefile.SelectQueryOrderby("announcement",column1,WhereCondition,"",startlimit,endlimit,orderby)
#                     print(data1,"")
                    
#                     if data1["result"]=="":
#                         data1["result"]=[]
#                     else:
#                         for i in data1["result"]:
#                             if i["imagePath"]!="":
#                                 i["imagePath"]=ConstantData.GetBaseURL()+i["imagePath"]

#         if "userTypeId" not in inputdata:
#             column = "id,Status,UserCreate,newsTitle,summary,newsDesc, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
#             data = databasefile.SelectQueryOrderby("news ",column,WhereCondition,"","0","3",orderby)
#             if data["result"]=="":
#                 data["result"]=[]
#         else :
#             column = "id,Status,UserCreate,newsTitle,summary,newsDesc,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
#             data = databasefile.SelectQueryOrderby("news ",column,WhereCondition,"","0","10",orderby)
#             if data["result"]=="":
#                 data["result"]=[]
        

#         column2 = "id,Status,UserCreate,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath  "
#         data2 = databasefile.SelectQueryOrderby("gallery",column2,"","","0","10",orderby)
        
#         if data2["result"]=="":
#             data2["result"]=[]

#         print("1111----")
#         WhereCondition1 = " on pm.eventId = ev.id " 
#         column3 = "ev.id,ev.Status,ev.UserCreate,ev.eventTitle,ev.eventSummary, ifnull(pm.id,0) as likedId,ev.eventLocation,date_format(ev.eventDate,'%Y-%m-%d %H:%i:%s')eventDate, date_format(ev.DateCreate,'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
#         data3 = databasefile.SelectQueryOrderbyNew("parliamentEvent ev left outer join eventInterest pm",column3,WhereCondition1,"",0,0,orderby)
        
#         if data3["result"]=="":
#             data3["result"]=[]

#         column4 = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,videoPath,text,UserCreate  "
#         WhereCondition2= " and Status<2"
#         data4 = databasefile.SelectQuery("promisingInitiatives",column4,WhereCondition2,"","0","10")
#         print(data4)

#         if data4["result"]=="":
#             data4["result"]=[]

#         for m in data4['result']:
#                 if m['imagePath']!='':
#                     m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
#                 if  m['videoPath']!="":
#                     y=m['videoPath'].split('=')
#                     print(y,'++++++')
#                     m['videoId']=y[1]
            
#         column5 = "id,Status,UserCreate,newsTitle,summary,newsDesc, date_format(DateCreate,'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath   "
#         data5 = databasefile.SelectQueryOrderby("promissingIntiatives",column5,WhereCondition,"","0","10",orderby)
#         if data5["result"]=="":
#             data5["result"]=[]


            

       


#         if data != "0":
            
#             return {"message":"","status":"true","promissingIntiatives":data5["result"],"news":data["result"],"announcement":data1["result"],"gallery":data2["result"],"event":data3["result"],"promisingInitiatives":data4["result"]}
            
#         else:
#             return commonfile.Errormessage()

#     except Exception as e :
#         print("Exception--->" + str(e))                                  
#         return commonfile.Errormessage()





@app.route('/landingPageDashboardtest', methods=['POST'])
def landingPageDashboardtest():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        
        data1={"message":"","status":"true","result":[]}
        orderby=" id "
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())   
            print(inputdata)     
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
            
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])

             
            if "key" in inputdata:
                if inputdata['key'] != "":
                    key =(inputdata["key"])


            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+" and  userTypeId=0  or userTypeId='"+str(userTypeId)+"'"

            if key == 1:        
        
            
                if "userTypeId" in inputdata:
                    if inputdata['userTypeId'] != "":
                        userTypeId =inputdata["userTypeId"]
                        WhereCondition=WhereCondition+" and  userTypeId=0  or userTypeId='"+str(userTypeId)+"'"
                        column1 = "id,Status,UserCreate,title,summary,videoLink, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath  "
                        data1 = databasefile.SelectQueryOrderby("announcement",column1,WhereCondition,"",startlimit,endlimit,orderby)
                        print(data1,"")
                        
                        if data1["result"]=="":
                            data1["result"]=[]
                        else:
                            for i in data1["result"]:
                                if i["imagePath"]!="":
                                    i["imagePath"]=ConstantData.GetBaseURL()+i["imagePath"]
                data={"result":data1['result'],"status":"true","message":""}
                return data

            if key ==2:

                if "userTypeId" not in inputdata:
                    column = "id,Status,UserCreate,newsTitle,summary,newsDesc, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,videoLink as videoPath,imagePath   "
                    data = databasefile.SelectQueryOrderby("news ",column,WhereCondition,"","0","3",orderby)
                    if data["result"]=="":
                        data["result"]=[]
                else :
                    column = "id,Status,UserCreate,newsTitle,summary,newsDesc, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, imagePath,videoLink  as videoPath  "
                    data = databasefile.SelectQueryOrderby("news ",column,WhereCondition,"",startlimit,endlimit,orderby)
                    if data["result"]=="":
                        data["result"]=[]


                for m in data['result']:
                        if m['imagePath']!='':
                            m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                        if  m['videoPath']!=None:
                            y=m['videoPath'].split('=')
                            print(y,'++++++')
                           
                            d=len(y)
                            if d>1:
                                if d == 2:
                                    m['videoId']=y[1]
                                if d ==3:
                                    k9=y[1].split('&')
                                    m['videoId']=k9[0]
                            else:
                                m['videoId']=y[0]
                        else:
                            m['videoId']=""



                data22={"result":{"headline":data['result'][0],"news":data['result'][1:]},"status":"true","message":""}
                return data22
            
            if key ==3:

        

                column2 = "id,Status,title,UserCreate,videoLink as videoPath ,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, imagePath  "
                data2 = databasefile.SelectQueryOrderby("gallery",column2,"","",startlimit,endlimit,orderby)
                
                if data2["result"]=="":
                    data2["result"]=[]

                for m in data2['result']:
                        if m['imagePath']!='':
                            m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                        if  m['videoPath']!=None:
                            y=m['videoPath'].split('=')
                            d=len(y)
                            if d>1:

                                if d == 2:
                                    m['videoId']=y[1]
                                if d ==3:
                                    k9=y[1].split('&')
                                    m['videoId']=k9[0]
                            else:
                                m['videoId']=y[0]
                        else:
                            m['videoId']=""

                data22={"result":data2['result'],"status":"true","message":""}
                return data22
            if key ==4:


                print("1111----")
                WhereCondition229=" and ev.Status<2 and  ev.userTypeId=0  or ev.userTypeId='"+str(userTypeId)+"'"
                column3 = "ev.id,ev.Status,ev.videoLink as videoPath,ev.UserCreate,ev.eventTitle,ev.eventSummary,ev.eventLocation,date_format(CONVERT_TZ(ev.eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate,date_format(CONVERT_TZ(ev.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, imagePath   "
                data3 = databasefile.SelectQueryOrderby("parliamentEvent ev ",column3,WhereCondition229,"",startlimit,endlimit,orderby)
                
                if data3["result"]=="":
                    data3["result"]=[]

                for i in data3['result']:
                    if i['imagePath']!='':
                        i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                    if  i['videoPath']!=None:
                        y=i['videoPath'].split('=')
                        print(y,'++++++')
                        d=len(y)
                        if d>1:
                            if d == 2:
                                i['videoId']=y[1]
                            if d ==3:
                                k9=y[1].split('&')
                                i['videoId']=k9[0]
                        else:
                            i['videoId']=y[0]
                    else:
                        i['videoId']=""
                    if 'userId' in inputdata:
                        userId=inputdata['userId']
                        marketingInsightId=i['id']
                        whereCondition="and lki.eventId='"+str(marketingInsightId)+"'"
                        columns="count(*) as count"
                        likeCount=databasefile.SelectQuery('eventInterest as lki',columns,whereCondition,"","","")
                        likeCount1=databasefile.SelectQuery('eventInterest1 as lki',columns,whereCondition,"","","")
                        print(likeCount)
                        if likeCount['status']!='false':

                            lki=likeCount['result'][0]['count']

                                

                            i['likeCount']=lki
                            
                            whereCondition999="and lki.eventId='"+str(marketingInsightId)+"' and lki.userId='"+str(userId)+"'"
                            column999="lki.status"
                            makedone=databasefile.SelectQuery('eventInterest as lki',column999,whereCondition999,"","","")
                            if makedone['status']!="false":
                                i['makedone']=1
                            else:
                                i['makedone']=0
                        else:
                            i['likeCount']=0
                            i['makedone']=0
                        if likeCount1['status']!='false':

                            lki=likeCount1['result'][0]['count']

                                

                            i['interestCount']=1
                            
                            whereCondition999="and lki.eventId='"+str(marketingInsightId)+"' and lki.userId='"+str(userId)+"'"
                            column999="lki.status"
                            makedone=databasefile.SelectQuery('eventInterest1 as lki',column999,whereCondition999,"","","")
                            if makedone['status']!="false":
                                i['interestCount']=1
                            else:
                                i['interestCount']=0
                        else:
                            i['interestCount']=0
                            



                    else:
                        i['makedone']=0    

                data22={"result":data3['result'],"status":"true","message":""}
                return data22
            
            if key ==5:
                orderby="id"



                column4 = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,videoPath,text,UserCreate  "
                WhereCondition2= " and Status<2"
                data4 = databasefile.SelectQueryOrderby("promisingInitiatives",column4,WhereCondition2,"",startlimit,endlimit,orderby)
                print(data4)

                if data4["result"]=="":
                    data4["result"]=[]

                for m in data4['result']:
                        if m['imagePath']!='':
                            m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                        if  m['videoPath']!=None:
                            y=m['videoPath'].split('=')
                            print(y,'++++++')
                            d=len(y)
                            print(d,"")
                            
                            if d>1:
                                if d == 2:
                                    m['videoId']=y[1]
                                if d ==3:
                                    k9=y[1].split('&')
                                    m['videoId']=k9[0]
                                
                            else:
                                m['videoId']=""
                        else:
                            m['videoId']=""

                data22={"result":data4['result'],"status":"true","message":""}
                return data22
            if key ==6:



                    
                column5 = "id,Status,UserCreate,videoLink as videoPath,newsTitle,summary,newsDesc,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath   "
                data5 = databasefile.SelectQueryOrderby("promissingIntiatives",column5,WhereCondition,"",startlimit,endlimit,orderby)
                if data5["result"]=="":
                    data5["result"]=[]

                for m in data5['result']:
                        if m['imagePath']!='':
                            m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                        if  m['videoPath']!=None:
                            y=m['videoPath'].split('=')
                           
                            d=len(y)
                            if d>1:
                                if d == 2:
                                    m['videoId']=y[1]
                                if d ==3:
                                    k9=y[1].split('&')
                                    m['videoId']=k9[0]
                                
                            else:
                                m['videoId']=y[0]
                        else:
                            m['videoId']=""

                data22={"result":data5['result'],"status":"true","message":""}
                return data22

            if key ==7:
               


                    
                column6 = "mi.id,mi.Status,mi.videoLink as videoPath,mi.UserCreate,mi.newsTitle,mi.userTypeId,mi.summary,mi.newsDesc,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath   "
                data6 = databasefile.SelectQueryOrderby("marketingInsights as mi ",column6,WhereCondition,"",startlimit,endlimit,orderby)
                if data6["result"]=="":
                    data6["result"]=[]

                for i in data6['result']:
                    marketingInsightId=i['id']
                    if i['imagePath']!='':
                        i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                    
                    if  i['videoPath']!=None:
                        y=i['videoPath'].split('=')
                        print(y,'++++++')
                        
                        d=len(y)
                        if d>1:
                            if d == 2:
                                i['videoId']=y[1]
                            if d ==3:
                                k9=y[1].split('&')
                                i['videoId']=k9[0]
                            
                        else:
                            i['videoId']=y[0]
                    else:
                        i['videoId']=""
                    if 'userId' in inputdata:
                        userId=inputdata['userId']

                   
                    whereCondition="and lki.marketingInsightId='"+str(marketingInsightId)+"'"
                    columns="count(*) as count"
                    likeCount=databasefile.SelectQuery('likeMarketingInsight as lki',columns,whereCondition,"","","")
                    print(likeCount)
                    if likeCount['status']!='false':
                        whereCondition999="and lki.marketingInsightId='"+str(marketingInsightId)+"' and userId='"+str(userId)+"'"
                        column999="status"
                        makedone=databasefile.SelectQuery('likeMarketingInsight as lki',column999,whereCondition999,"","","")
                       

                        lki=likeCount['result'][0]['count']

                            

                        i['likeCount']=lki
                        if makedone['status']!="false":
                            i['makedone']=1
                        else:
                            i['makedone']=0
                            
                        
                    else:
                        i['likeCount']=0
                        i['makedone']=0


                data22={"result":data6['result'],"status":"true","message":""}
                return data22

            if key ==8:
                WhereCondition229=" and mi.Status<2 and  mi.userTypeId=0  or mi.userTypeId='"+str(userTypeId)+"'"
               



                    
                column7 = "mi.id,mi.Status,mi.videoLink as videoPath,mi.isFeatured,mi.UserCreate,mi.newsTitle,mi.userTypeId,mi.summary,mi.newsDesc,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,mi.length,mi.level,mi.language,mi.effort,mi.price,mi.videoTranscript"
                data7 = databasefile.SelectQueryOrderby("upSkillsOpportunity  as mi",column7,WhereCondition229,"",startlimit,endlimit,orderby)
                print(data7)
                if data7["result"]=="":
                    data7["result"]=[]
                a=[]
                b=[]
                for i in data7['result']:
                    

                    if i['imagePath']!='':
                        i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                    if  i['videoPath']!=None:
                        y=i['videoPath'].split('=')
                        print(y,'++++++')
                        
                        d=len(y)
                        if d>1:
                            if d == 2:
                                i['videoId']=y[1]
                            if d ==3:
                                k9=y[1].split('&')
                                i['videoId']=k9[0]
                            
                        else:
                            i['videoId']=y[0]
                    else:
                        i['videoId']=""
                    if 'userId' in inputdata:
                        userId=inputdata['userId']
                    marketingInsightId=i['id']
                    whereCondition999="and lki.upSkillsId='"+str(marketingInsightId)+"' and lki.userId='"+str(userId)+"'"
                    column999="lki.status"
                    makedone=databasefile.SelectQuery('enrollUpskills as lki',column999,whereCondition999,"","","")
                    if makedone['status']!="false":

                        i['makedone']=1
                    else:
                        i['makedone']=0

                    if i['isFeatured'] == 0:
                        print(i,"++++++++++")
                        a.append(i)
                    
                    if i['isFeatured']==1:
                        print(b,"++++++++")
                        b.append(i)



                data22={"result":{"featured Programs":b,"top Rated Programs":a},"status":"true","message":""}
                return data22
            if key ==9:

                column99 = "id,Status,UserCreate,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath  "
                data99 = databasefile.SelectQueryOrderby("ourPartners",column99,"","",startlimit,endlimit,orderby)
                
                if data99["result"]=="":
                    data99["result"]=[]

                data22={"result":data99['result'],"status":"true","message":""}
                return data22
                        
            
 


           



            



            
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()

# @app.route('/getNewsCategory', methods=['POST'])
# def getNewsCategoryMaster():

#     try:
#         WhereCondition,startlimit,endlimit="","",""
       
#         column = " id,category  "
#         data = databasefile.SelectQuery("newsCategoryMaster",column,WhereCondition,"",startlimit,endlimit)
#         if data != "0":
#             return data
#         else:
#             return commonfile.Errormessage()

#     except Exception as e :
#         print("Exception--->" + str(e))                                  
#         return commonfile.Errormessage()


@app.route('/announcements', methods=['POST'])
def announcements():

    try:
       
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        print("announcements",inputdata)
        commonfile.writeLog("announcements",inputdata,0)
        keyarr = ["title","userTypeId"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            if "title" in inputdata:
                if inputdata['title'] != "":
                    title =commonfile.EscapeSpecialChar(inputdata["title"])
                    column=" title "
                    values=" '"+ str(title) +"'"

            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "" :
                    userTypeId =inputdata["userTypeId"]
                    column=column+" ,userTypeId "
                    values=values+" ,'"+ str(userTypeId) +"'"


            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =commonfile.EscapeSpecialChar(inputdata["summary"])
                    column=column+", summary"
                    values=values+ ",'"+str(summary)+"'"
        
            if "videoLink" in inputdata:
                if (inputdata['videoLink'] != "") or (inputdata['videoLink'] != None) :
                    videoLink =inputdata["videoLink"]
                    if videoLink[0:24]!="https://www.youtube.com/":
                        return {"message":"Please upload only youtube Link","result":"","status":"False"}
                    else:
                        column=column+" ,videoLink"
                        values=values+",'" +str(videoLink)+"'"
            
            
            if 'postImage' in request.files:      
                    file = request.files.get('postImage')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getAnnouncementsPath(filename)  

                    filepath = '/announcementsImage/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
                    column=column+" ,ImagePath"
                    values=values+",'"+ str(ImagePath)+"'"

            if "UserId" in inputdata:
                if inputdata['UserId'] != "":
                    UserId =inputdata["UserId"]
                column =column + ",UserCreate"
                values =   values + str(UserId) + "'"
                data = databasefile.InsertQuery("announcement",column,values)        
            else:
                column = column+ " "
                
                data = databasefile.InsertQuery("announcement",column,values)

            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 



@app.route('/announcements1', methods=['POST'])
def announcements1():

    try:
       
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        print("announcements",inputdata)
        commonfile.writeLog("announcements",inputdata,0)
        keyarr = ["title","userTypeId","flag"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            summary=""
            videoLink=""
            flag=inputdata['flag']
            if "title" in inputdata:
                if inputdata['title'] != "":
                    title =inputdata["title"]
                    column=" title "
                    values=" '"+ str(title) +"'"

            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    column=column+" ,userTypeId "
                    values=values+" ,'"+ str(userTypeId) +"'"


            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =inputdata["summary"]
                    column=column+", summary"
                    values=values+ ",'"+str(summary)+"'"
        
            if "videoLink" in inputdata :
                print("1111111111111111111")
               
                if (inputdata['videoLink'] != None) and (inputdata['videoLink'] != "") :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink[0:24]!="https://www.youtube.com/":
                        print("3333333333333333333")
                        return {"message":"Please upload only youtube Link","result":"","status":"False"}
                    else:
                        column=column+" ,videoLink"
                        values=values+",'" +str(videoLink)+"'"
            
            
            if 'postImage' in request.files:      
                    file = request.files.get('postImage')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getAnnouncementsPath(filename)  

                    filepath = '/announcementsImage/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
                    column=column+" ,imagePath"
                    values=values+",'"+ str(ImagePath)+"'"
            if flag =="i":
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                    column =column + ",UserCreate"
                    values =   values + str(UserId) + "'"
                    data = databasefile.InsertQuery("announcement",column,values)        
                else:
                    column = column+ " "
                    
                    data = databasefile.InsertQuery("announcement",column,values)
            if flag =='u':

                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]

                # if "UserId" in inputdata:
                #     if inputdata['UserId'] != "":
                #         UserId =inputdata["UserId"]
                #         whereCondition=" UserCreate= '"+ str(UserId)+"'"
                #         column="title='"+ str(title)+"',summary='"+ str(summary)+"',userTypeId='"+ str(userTypeId)+"',videoLink='"+ str(userTypeId)+"',ImagePath='"+ str(ImagePath)+"',Status='"+ str(status)+"'"
                #         data=databasefile.UpdateQuery("announcement",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        print(inputdata1,"==========================================")
                        if  inputdata1 !=None: 
                            index=re.search("/announcementsImage", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]
                        whereCondition=" and  id= '"+ str(Id)+"'"
                        column="title='"+ str(title)+"',summary='"+ str(summary)+"',userTypeId='"+ str(userTypeId)+"',videoLink='"+ str(videoLink)+"',imagePath='"+ str(ImagePath)+"',Status='"+ str(status)+"'"
                        data=databasefile.UpdateQuery("announcement",column,whereCondition)


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()         

@app.route('/getAnnouncement', methods=['POST'])
def getAnnouncement():

    try:        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2 "
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"]
                    WhereCondition=WhereCondition+" and id='"+str(Id)+"'"
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =str(inputdata["userTypeId"])
                    WhereCondition=WhereCondition+"  and userTypeId="+str(userTypeId)+""
        
        column = "id,Status,userTypeId,title,summary,videoLink,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,UserCreate  "
        data = databasefile.SelectQuery("announcement",column,WhereCondition,"",startlimit,endlimit)
        for i in data["result"]:
            if i["imagePath"]!="":
                i["imagePath"]=ConstantData.GetBaseURL()+i["imagePath"]
        if data != "0":
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()
# @app.route('/getNews', methods=['POST'])
# def getNews():
#     try:
#         inputdata = commonfile.DecodeInputdata(request.get_data()) 
#         commonfile.writeLog("getNews",inputdata,0)
#         keyarr = ["newsId"]           
#         msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
#         if msg == "1":
#             if "newsId" in inputdata:
#                 if inputdata['newsId'] != "":
#                     newsId =inputdata["newsId"]
                    
#             newsLink=ConstantData.getwebBaseurl()+"/news?newsId="+str(newsId)
#             WhereCondition,startlimit,endlimit="","",""
#             print("1111111111111111111")
#             WhereCondition=WhereCondition+" and n.newsType=nc.id and um.UserId=n.UserCreate and n.id= "+str(newsId)
           
#             column = "(n.newsType)newsTypeId,(um.UserName)writer,n.id,(newsTitle)title,(nc.category)newsType,n.summary,n.newsDesc,date_format(n.DateCreate,'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',n.imagePath)imagePath  "
#             data= databasefile.SelectQuery("news n,newsCategoryMaster nc,UserMaster um",column,WhereCondition,"",startlimit,endlimit) 
#             print("data==========================>",data)
            
            
            
            
#             newsTypeId=str(data["result"][0]["newsTypeId"])
#             print("newsTypeId===============>",newsTypeId)
#             WhereCondition,startlimit,endlimit="","",""
#             WhereCondition=WhereCondition+" and n.newsType=nc.id and um.UserId=n.UserCreate and n.newsType= "+str(newsTypeId)+" and n.id<> "+str(newsId)
#             print("2222222222222222222",WhereCondition)
#             column = "(um.UserName)writer,n.id,(newsTitle)title,(nc.category)newsType,n.summary,n.newsDesc,date_format(n.DateCreate,'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',n.imagePath)imagePath  "
#             data1= databasefile.SelectQuery("news n,newsCategoryMaster nc,UserMaster um",column,WhereCondition,"",'0','3') 
#             print("134567890")
#             data["relatedNews"]=data1
#             data["newsLink"]=newsLink
#             if data !=0 :                
#                 return data
#             else:
#                 return commonfile.Errormessage()
#         else:
#             return msg
        
#     except Exception as e:
#         print("Exception--->" + str(e))                                  
#         return commonfile.Errormessage()




#admin insert gallery images
@app.route('/galleryImages', methods=['POST'])
def galleryImages():
    try:
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        startlimit,endlimit="",""
        keyarr = ["userId"]
        
        commonfile.writeLog("galleryImages",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            column,values="",""


            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"

            if "title" in inputdata:
                if inputdata['title'] != "":
                    title =inputdata["title"]
                    column=" title "
                    values=" '"+ str(title) +"'"

                           
            if 'postImage' in request.files:      
                file = request.files.get('postImage')        
                filename = file.filename or ''                 
                filename = filename.replace("'","") 

                print(filename)
                # filename = str(campaignId)                    
                #folder path to save campaign image
                FolderPath = ConstantData.getGalleryPath(filename)  

                filepath = '/gallery/' + filename    
                

                file.save(FolderPath)
                ImagePath = filepath
            if "userId" in inputdata:
                if inputdata['userId'] != "":
                    userId =inputdata["userId"]
                column =column+ " imagePath,UserCreate"
                values =values+ " '"+ str(ImagePath)+ "','" + str(userId) + "'"
                data = databasefile.InsertQuery("gallery",column,values)        
            else:
                column =column+ " imagePath "
                values =values+ " '"+ str(ImagePath)+  "'"
                data = databasefile.InsertQuery("gallery",column,values)

            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 


@app.route('/galleryImages1', methods=['POST'])
def galleryImages1():
    try:
        inputdata = request.form.get('data') 

        inputdata = json.loads(inputdata) 
        startlimit,endlimit="",""
        keyarr = ["userId","flag"]
        
        commonfile.writeLog("galleryImages",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            ImagePath=""
            flag=inputdata['flag']
            title=""

            column,values="",""


            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"

            if "title" in inputdata:
                if inputdata['title'] != "":
                    title =inputdata["title"]
                    column=column+" title ,"
                    values=values+" '"+ str(title) +"',"

            if 'postImage' in request.files:      
                file = request.files.get('postImage')        
                filename = file.filename or ''                 
                filename = filename.replace("'","") 

                print(filename)
                # filename = str(campaignId)                    
                #folder path to save campaign image
                FolderPath = ConstantData.getGalleryPath(filename)  

                filepath = '/gallery/' + filename    
                

                file.save(FolderPath)
                ImagePath = filepath
           
            
            if flag =="i":
                if "userId" in inputdata:
                    if inputdata['userId'] != "":
                        userId =inputdata["userId"]
                    column =column+ " imagePath,UserCreate"
                    values =values+ " '"+ str(ImagePath)+ "','" + str(userId) + "'"
                    data = databasefile.InsertQuery("gallery",column,values)        
                else:
                    column =column+ " imagePath "
                    values =values+ " '"+ str(ImagePath)+  "'"
                    data = databasefile.InsertQuery("gallery",column,values)
            if flag =="u":
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                # if "userId" in inputdata:

                #     if inputdata['userId'] != "":
                #         userId =inputdata["userId"]
                #         whereCondition=" and UserCreate='" + str(userId) + "'"
                #         column="imagePath='"+ str(ImagePath)+  "',status='"+ str(status)+  "'"
                #         data=databasefile.UpdateQuery("gallery",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        print(inputdata1,"==========================================")
                        if  inputdata1 is not None: 
                            print(inputdata1)

                            index=re.search("/gallery", inputdata1).start()
                          
                            ImagePath=""
                            ImagePath=inputdata1[index:]

                        whereCondition=" and  id='" + str(Id) + "'"
                        column="videoLink='"+ str(videoLink)+  "',title='"+ str(title)+  "',imagePath='"+ str(ImagePath)+  "',status='"+ str(status)+  "'"
                        data=databasefile.UpdateQuery("gallery",column,whereCondition)




            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 


@app.route('/getGalleryImages', methods=['POST'])
def getGalleryImages():

    try:        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+"  and id='"+str(Id)+"'"
            orderby="id"
        
        column = "id,Status,title,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,videoLink,imagePath,UserCreate  "
        data = databasefile.SelectQueryOrderby(" gallery ",column,WhereCondition,"",startlimit,endlimit,orderby)
        print(data)
        data2 = databasefile.SelectTotalCountQuery("gallery",WhereCondition,"")
       
        
        if data != "0":
            data["totalCount"]=data2
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=y[0]
                else:
                    i['videoId']=""
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()
 

# create parliamentEvent by admin

@app.route('/parliamentEvent', methods=['POST'])
def parliamentEvent():

    try:
       
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        print("parliamentEvent",inputdata)
        commonfile.writeLog("parliamentEvent",inputdata,0)
        keyarr = ["eventTitle","eventSummary","eventLocation"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("1111111111111")
        if msg == "1":
            ImagePath=""
            column,values="",""

            print("22222222222222")
            if "eventTitle" in inputdata:
                if inputdata['eventTitle'] != "":
                    eventTitle =commonfile.EscapeSpecialChar(inputdata["eventTitle"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "eventSummary" in inputdata:
                if inputdata['eventSummary'] != "":
                    eventSummary =commonfile.EscapeSpecialChar(inputdata["eventSummary"])
            
            if "eventLocation" in inputdata:
                if inputdata['eventLocation'] != "":
                    eventLocation =inputdata["eventLocation"]
            
            if "eventDate" in inputdata:
                if inputdata['eventDate'] != "":
                    eventDate =inputdata["eventDate"]

            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"

            if 'postImage' in request.files:      
                    file = request.files.get('postImage')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getEventPath(filename)  

                    filepath = '/eventImages/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            
            if "UserId" in inputdata:
                if inputdata['UserId'] != "":
                    UserId =inputdata["UserId"]
                column =column+ "eventTitle,userTypeId,imagePath,eventSummary,eventLocation,eventDate,UserCreate"
                values =values+ " '"+ str(eventTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(eventSummary) +"','" + str(eventLocation) + "','" + str(eventDate) + "','" + str(UserId) + "'"
                data = databasefile.InsertQuery("parliamentEvent",column,values)        
            else:

                column = "eventTitle,userTypeId,imagePath,eventSummary,eventLocation,eventDate"
                values = " '"+ str(eventTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(eventSummary) +"','" + str(eventLocation) + "','" + str(eventDate) + "'"
                data = databasefile.InsertQuery("parliamentEvent",column,values)

            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 



#update parliament event
@app.route('/parliamentEvent1', methods=['POST'])
def parliamentEvent1():

    try:
       
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        print("parliamentEvent",inputdata)
        commonfile.writeLog("parliamentEvent",inputdata,0)
        keyarr = ["eventTitle","eventSummary","eventLocation",'flag']           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("1111111111111")
        if msg == "1":
            flag=inputdata['flag']
            ImagePath=""
            column,values="",""

            print("22222222222222")
            if "eventTitle" in inputdata:
                if inputdata['eventTitle'] != "":
                    eventTitle =commonfile.EscapeSpecialChar(inputdata["eventTitle"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "eventSummary" in inputdata:
                if inputdata['eventSummary'] != "":
                    eventSummary =commonfile.EscapeSpecialChar(inputdata["eventSummary"])
            
            if "eventLocation" in inputdata:
                if inputdata['eventLocation'] != "":
                    eventLocation =inputdata["eventLocation"]
            
            if "eventDate" in inputdata:
                if inputdata['eventDate'] != "":
                    eventDate =inputdata["eventDate"]

            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            if 'postImage' in request.files:
                    print("333333333333333")      
                    file = request.files.get('postImage') 
                    print(file)       
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getEventPath(filename)  

                    filepath = '/eventImages/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            if flag =="i":
            
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                    column =column+ "eventTitle,userTypeId,imagePath,eventSummary,eventLocation,eventDate,UserCreate"
                    values =values+ " '"+ str(eventTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(eventSummary) +"','" + str(eventLocation) + "','" + str(eventDate) + "','" + str(UserId) + "'"
                    data = databasefile.InsertQuery("parliamentEvent",column,values)        
                else:

                    column = "eventTitle,userTypeId,imagePath,eventSummary,eventLocation,eventDate"
                    values = " '"+ str(eventTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(eventSummary) +"','" + str(eventLocation) + "','" + str(eventDate) + "'"
                    data = databasefile.InsertQuery("parliamentEvent",column,values)
            if flag =="u":
                if "status"  in inputdata:
                    if inputdata['status'] != "":
                        status=inputdata["status"]

                # if "UserId" in inputdata:
                #     if inputdata['UserId'] != "":
                #         UserId =inputdata["UserId"]
                #         whereCondition= " and UserCreate='" + str(UserId) + "' "
                #         column="eventTitle='"+ str(eventTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',eventSummary='"+ str(eventSummary) +"',eventLocation='"+ str(eventLocation) +"',eventDate='"+ str(eventDate) +"',Status='"+ str(status) +"'"
                #         data=databasefile.UpdateQuery('parliamentEvent',column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        y=type(inputdata1)
                        print(y)
                        y2=len(inputdata1)
                        if  y2>4:
                            index=re.search("/eventImages", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]
                        whereCondition= " and id='" + str(Id) + "' "
                        column="videoLink='"+ str(videoLink) +"' ,eventTitle='"+ str(eventTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',eventSummary='"+ str(eventSummary) +"',eventLocation='"+ str(eventLocation) +"',eventDate='"+ str(eventDate) +"',Status='"+ str(status) +"'"
                        data=databasefile.UpdateQuery('parliamentEvent',column,whereCondition)
                


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 



@app.route('/getParliamentEvent', methods=['POST'])
def getParliamentEvent():

    try:        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and id='"+str(Id)+"'"
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"] 
                    WhereCondition=WhereCondition+" and userTypeId='"+str(userTypeId)+"'"
        
        column = "id,Status,UserCreate,eventTitle,userTypeId,eventSummary,eventLocation,date_format(CONVERT_TZ(eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,videoLink,imagePath   "
        data = databasefile.SelectQuery("parliamentEvent",column,WhereCondition,"",startlimit,endlimit)
        print(data)
        data2 = databasefile.SelectTotalCountQuery("parliamentEvent ",WhereCondition,"")

        if data['result'] != "":



            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                   if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=""
                else:
                    i['videoId']=""

                y2=i['id']
                column="userId"
                whereCondition=" and eventId='"+str(y2)+"'"
                dat=databasefile.SelectQuery('eventInterest',column,whereCondition,"",startlimit,endlimit)
                print(dat,'++++++++++++++++++++++++++++')
                if dat['result'] !="":
                    i['eventInterestCount']=len(dat['result'])
                else:
                    i['eventInterestCount']=0


            return data
        else:
            return data

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/userDropDown', methods=['POST'])
def userDropdown():
    try:
        columns=" id,userName  "
        startlimit,endlimit="",""
        whereCondition=" and id>4 and id<10 or id=13"
        
        data = databasefile.SelectQuery("userTypeMaster",columns,whereCondition,"",startlimit,endlimit)
       

        if data:
            data['result'].append({"id":0,"userName":"All"})
            #data["result"][0]["id"]=0           
            Data = {"status":"true","message":"","result":data["result"]}
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":""}
            return output

    except Exception as e :
        print("Exception---->" + str(e))    
        output =  {"status":"false","message":"something went wrong","result":""}
        return output           
 


@app.route('/deleteNews', methods=['POST'])
def deleteNews():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data())

        WhereCondition="" 
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteNews",inputdata,0)

        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("news",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()

@app.route('/deleteMarketingInsights', methods=['POST'])
def deleteNews1():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data())

        WhereCondition="" 
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteMarketingInsights",inputdata,0)

        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("marketingInsights",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/deleteUpSkillsOpportunity', methods=['POST'])
def deleteNews2():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data())

        WhereCondition="" 
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteUpSkillsOpportunity",inputdata,0)

        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("upSkillsOpportunity",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()           

@app.route('/deleteGallery', methods=['POST'])
def deleteGallery():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        WhereCondition=""
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteGallery",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("gallery",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/deleteEvent', methods=['POST'])
def deleteEvent():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data())
        WhereCondition="" 
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteEvent",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("parliamentEvent",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/deleteAnnouncement', methods=['POST'])
def deleteAnnouncement():
    try:


        inputdata =  commonfile.DecodeInputdata(request.get_data()) 

        WhereCondition=""
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteEvent",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("announcement",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()








@app.route('/promisingInitiatives', methods=['POST'])
def promisingInitiatives():
    try:
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        startlimit,endlimit="",""
        keyarr = ["flag"]
        
        commonfile.writeLog("promisingInitiatives",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            text=""
            videoLink=""
            ImagePath=""
            flag=inputdata['flag']
            if "text" in inputdata:
                if inputdata['text'] != "":
                    text =inputdata["text"]

           
            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoPath,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoPath,"
                        values="'" +str(videoLink)+"',"

            if 'postImage' in request.files:      
                    file = request.files.get('postImage')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getpromisingEvent(filename)  

                    filepath = '/promisingEvent/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            
            



            if flag =="i":
                if "userId" in inputdata:
                    if inputdata['userId'] != "":
                        userId =inputdata["userId"]
                    column = column+" UserCreate,text,imagePath"
                    values = values+ "'" + str(userId) + "','" + str(text) + "','" + str(ImagePath) + "'"
                    data = databasefile.InsertQuery("promisingInitiatives",column,values)        
                
                else:
                    column = column+" text,imagePath"
                    values = values+  ",'" + str(text) + "','" + str(ImagePath) + "'"
                    data = databasefile.InsertQuery("promisingInitiatives",column,values)
            
            
            if flag =="u":
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
              
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        print(inputdata1,"@##!!@@")
                        y=type(inputdata1)
                        print(y)
                        y2=len(inputdata1)
                        if  y2>4:
                            print("@@!!!@###################")
                            index=re.search("/promisingEvent", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]

                        whereCondition=" and  id='" + str(Id) + "'"
                        column="videoPath='"+ str(videoLink)+  "',Status='"+ str(status)+  "',text='" + str(text) + "',imagePath='" + str(ImagePath) + "'"
                        data=databasefile.UpdateQuery("promisingInitiatives",column,whereCondition)

            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 




@app.route('/getpromisingInitiatives', methods=['POST'])
def getpromisingInitiatives():

    try:        
        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+"  and id='"+str(Id)+"'"
        
        
        column = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,videoPath,text,UserCreate  "
        data = databasefile.SelectQuery("promisingInitiatives",column,WhereCondition,"",startlimit,endlimit)

        
         
        if data['result'] != "":
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
            return data

        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()




@app.route('/getpromisingInitiativesAdmin', methods=['POST'])
def getpromisingInitiatives1():

    try:        
        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<3"
        
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+"  and id='"+str(Id)+"'"
            orderby='id'
        
        
        column = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,videoPath,text,UserCreate  "
        data = databasefile.SelectQueryOrderby("promisingInitiatives",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectTotalCountQuery("promisingInitiatives ",WhereCondition,"")
        
         
        if data['result'] != "":
            data["totalCount"]=data2
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoPath']!=None:
                   if i['videoPath']!=None:
                    y=i['videoPath'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=""
                else:
                    i['videoId']=""
            return data

        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/signUpVideo', methods=['POST'])
def signUpVideo():
    try:
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        startlimit,endlimit="",""
        keyarr = ["flag"]
        
        commonfile.writeLog("galleryImages",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            column=""
            values=""
            text=""
            ImagePath=""
            videoLink=""
            flag=inputdata['flag']
            if "text" in inputdata:
                if inputdata['text'] != "":
                    text =commonfile.EscapeSpecialChar(inputdata["text"])
            
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]

            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"
            
                      
                    else:
                        column=" videoLink,"
                        values="'" +str(videoLink)+"',"

            if 'postImage' in request.files:      
                    file = request.files.get('postImage')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getSignUpVideo(filename)  

                    filepath = '/signUpImage/' + filename 
                       
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            
            
            
            if flag =="i":
                if "userId" in inputdata:
                    column1='Status=2'
                    whereCondition=" and userTypeId='" + str(userTypeId) + "'"
                    data5=databasefile.UpdateQuery('signUpVideo',column1,whereCondition)
                    
                    if inputdata['userId'] != "":
                        userId =inputdata["userId"]
                    column = column+"UserCreate,text,userTypeId,imagePath"
                    values =values+ "'" + str(userId) + "','" + str(text) + "','" + str(userTypeId) + "','" + str(ImagePath) + "'"
                    data = databasefile.InsertQuery("signUpVideo",column,values)        
                else:
                    column = column+"text,userTypeId,imagePath"
                    values =values+   "'" + str(text) + "','" + str(userTypeId) + "','" + str(ImagePath) + "'"
                    data = databasefile.InsertQuery("signUpVideo",column,values)
            
            if flag =="u":
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                column1='Status=2'
                whereCondition=" and userTypeId='" + str(userTypeId) + "'"
                data5=databasefile.UpdateQuery('signUpVideo',column1,whereCondition)


                # if "userId" in inputdata:

                #     if inputdata['userId'] != "":
                #         userId =inputdata["userId"]
                #         whereCondition=" and UserCreate='" + str(userId) + "'"
                #         column="imagePath='"+ str(ImagePath)+  "',status='"+ str(status)+  "'"
                #         data=databasefile.UpdateQuery("gallery",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        if  inputdata1 !=None: 
                            index=re.search("/signUpImage", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]
                        whereCondition=" and  id='" + str(Id) + "'"
                        column="videoLink='"+ str(videoLink)+  "',Status='"+ str(status)+  "',text='" + str(text) + "',userTypeId='" + str(userTypeId) + "',imagePath='" + str(ImagePath) + "'"
                        data=databasefile.UpdateQuery("signUpVideo",column,whereCondition)

            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 




@app.route('/getSignUpVideo', methods=['POST'])
def getSignUpVideo():

    try:        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+"  and id='"+str(Id)+"'"

            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"] 
                    WhereCondition=WhereCondition+"  and userTypeId='"+str(userTypeId)+"'"
        
        column = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,videoLink,text,UserCreate,userTypeId,imagePath"
        data = databasefile.SelectQuery("signUpVideo",column,WhereCondition,"",startlimit,endlimit)
        
        if data['result'] != "":
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                   if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=""
                else:
                    i['videoId']=""
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/getSignUpVideoAdmin', methods=['POST'])
def getSignUpVideo1():

    try:        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<3"
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+"  and id='"+str(Id)+"'"

            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"] 
                    WhereCondition=WhereCondition+"  and userTypeId='"+str(userTypeId)+"'"
        
        column = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,videoLink,text,UserCreate,userTypeId,imagePath"
        data = databasefile.SelectQuery("signUpVideo",column,WhereCondition,"",startlimit,endlimit)
        
        if data['result'] != "":
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                   if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=""
                else:
                    i['videoId']=""
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/deletePromisingInitiatives', methods=['POST'])
def deletePromisingInitiatives():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data())
        WhereCondition="" 
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deletePromisingInitiatives",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("promisingInitiatives",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()




@app.route('/deletePromissingInitiatives', methods=['POST'])
def deletePromissingInitiatives():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data())
        WhereCondition="" 
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deletePromisingInitiatives",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("promissingIntiatives",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()




@app.route('/deleteSignUpVideo', methods=['POST'])
def deleteSignUpVideo():
    try:


        inputdata =  commonfile.DecodeInputdata(request.get_data()) 

        WhereCondition=""
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteSignUpVideo",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("signUpVideo",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/aboutUs', methods=['POST'])
def aboutUs(): 





    try: 
        startlimit,endlimit="",""   
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        aboutId = '3'
        keyarr = ['description','flag']
        commonfile.writeLog("aboutUs",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
       
        if msg == "1":      
            description = commonfile.EscapeSpecialChar(inputdata["description"])
            flag = inputdata["flag"]
            print('====',flag)
        
            WhereCondition = " "
            count = databasefile.SelectCountQuery("aboutUs",WhereCondition,"")
            
            if int(count) > 0 and flag == 'n':
                print('F')         
                return commonfile.aboutUsDescriptionAlreadyExistMsg()
            else:
                if flag == 'n':
                    columns = " description"          
                    values = " '" + str(description) + "'"       
                    data = databasefile.InsertQuery("aboutUs",columns,values)
                    if data != "0":
                        column = '*'
                        WhereCondition = " and description = '" + str(description) + "'"
                        
                        data11 = databasefile.SelectQuery("aboutUs",column,WhereCondition,"",startlimit,endlimit)
                        return data11
                if flag == 'u':
                    WhereCondition = " and id='" + str(aboutId) + "'"
                    column = " description = '" + str(description) + "'"
                    data = databasefile.UpdateQuery("aboutUs",column,WhereCondition)
                    return data
                else:
                    return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 


@app.route('/deleteAboutUs', methods=['POST'])
def deleteAboutUs():
    try:


        inputdata =  commonfile.DecodeInputdata(request.get_data()) 

        WhereCondition=""
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteAboutUs",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("aboutUs",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()

@app.route('/allaboutUs', methods=['POST'])
def allaboutUs():
    try:
        columns=" * "
        
        data = databasefile.SelectQueryMaxId("aboutUs",columns)
       

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




@app.route('/ourPartnersImages1', methods=['POST'])
def ourPartners():
    try:
        inputdata = request.form.get('data') 

        inputdata = json.loads(inputdata) 
        startlimit,endlimit="",""
        keyarr = ["userId","flag"]
        
        commonfile.writeLog("ourPartners",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            ImagePath=""
            flag=inputdata['flag']
            if 'postImage' in request.files:      
                file = request.files.get('postImage')        
                filename = file.filename or ''                 
                filename = filename.replace("'","") 

                print(filename)
                # filename = str(campaignId)                    
                #folder path to save campaign image
                FolderPath = ConstantData.getourPartners(filename)  

                filepath = '/ourPartners/' + filename    
                

                file.save(FolderPath)
                ImagePath = filepath
           
            
            if flag =="i":
                if "userId" in inputdata:
                    if inputdata['userId'] != "":
                        userId =inputdata["userId"]
                    column = " imagePath,UserCreate"
                    values = " '"+ str(ImagePath)+ "','" + str(userId) + "'"
                    data = databasefile.InsertQuery("ourPartners",column,values)        
                else:
                    column = " imagePath "
                    values = " '"+ str(ImagePath)+  "'"
                    data = databasefile.InsertQuery("ourPartners",column,values)
            if flag =="u":
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                # if "userId" in inputdata:

                #     if inputdata['userId'] != "":
                #         userId =inputdata["userId"]
                #         whereCondition=" and UserCreate='" + str(userId) + "'"
                #         column="imagePath='"+ str(ImagePath)+  "',status='"+ str(status)+  "'"
                #         data=databasefile.UpdateQuery("gallery",column,whereCondition)
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        print(inputdata1,"==========================================")
                        if  inputdata1 !=None: 
                            index=re.search("/ourPartners", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]
                        whereCondition=" and  id='" + str(Id) + "'"
                        column="imagePath='"+ str(ImagePath)+  "',status='"+ str(status)+  "'"
                        data=databasefile.UpdateQuery("ourPartners",column,whereCondition)




            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 


@app.route('/getOurPartners', methods=['POST'])
def getOurPartners():

    try:        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+"  and id='"+str(Id)+"'"
        
        column = "id,Status,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath,UserCreate  "
        data = databasefile.SelectQuery(" ourPartners ",column,WhereCondition,"",startlimit,endlimit)
        
        if data != "0":
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/deleteOurPartners', methods=['POST'])
def deleteOurPartners():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        WhereCondition=""
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteOurPartners",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("ourPartners",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()

@app.route('/usersContent', methods=['POST'])
def userContent():

    try:
       
        inputdata = request.form.get('userContent')    
        inputdata = json.loads(inputdata) 
        print("userContent",inputdata)
        commonfile.writeLog("usersContent",inputdata,0)
        keyarr = ["content","flag","userTypeId"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            flag=inputdata['flag']
           
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
           
            
            if "content" in inputdata:
                if inputdata['content'] != "":
                    content =commonfile.EscapeSpecialChar(inputdata["content"]) 

             
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"]        
            
            
            if 'postImage' in request.files:      
                    file = request.files.get('postImage')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getuserContent(filename)  

                    filepath = '/contentimages/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
            if flag =='i':      
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                    
                    whereCondition=" and  userTypeId='"+str(userTypeId)+"'"


                    data1=databasefile.DeleteQuery("userContent",whereCondition)


                      
                    column = "userTypeId,imagePath,content,UserCreate"
                    values = " '" + str(userTypeId)+"','" + str(ImagePath) +"','" + str(content) + "','" + str(UserId) + "'"
                    data = databasefile.InsertQuery("userContent",column,values)        
                else:
                    column = "userTypeId,imagePath,content"
                    values =  " '" + str(userTypeId)+"','" + str(ImagePath) +"','" + str(content) + "'"
                    data = databasefile.InsertQuery("userContent",column,values)
            if flag =='u':
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
                # if "UserId" in inputdata:
                #     if inputdata['UserId'] != "":
                #         UserId =inputdata["UserId"]
                      
                #     whereCondition= " and id= '"+ str(Id) +"' and UserCreate='"+ str(UserId) +"'" 
                #     column="newsTitle='"+ str(newsTitle) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',summary='"+ str(summary) +"',newsDesc='"+ str(newsDesc) +"',Status='"+ str(status) +"'"
                #     data=databasefile.UpdateQuery("news",column,whereCondition)
                if "id" in inputdata:
                    
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        
                        if ((inputdata1!=ConstantData.GetBaseURL()) and (inputdata1 !="")) :
                            if  inputdata1 !=None: 
                                index=re.search("/contentimages", inputdata1).start()
                                ImagePath=""
                                ImagePath=inputdata1[index:]


                        whereCondition=" and id= '"+ str(Id) +"'"
                        column="content='"+ str(content) +"',userTypeId='"+ str(userTypeId) +"',imagePath='"+ str(ImagePath) +"',Status='"+ str(status) +"'"
                        data=databasefile.UpdateQuery("userContent",column,whereCondition)


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 



@app.route('/getuserContent', methods=['POST'])
def getuserContent():

    try:
        print('AA')
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and n.Status<2 "
        if request.get_data():
            print('B')
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+"  and n.userTypeId IN(0,'"+str(userTypeId)+"')"

            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and n.id='"+str(Id)+"'"
        orderby=" n.id "
       
        column = " n.id,n.Status,n.userTypeId,n.content ,date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',n.imagePath)imagePath "
        data = databasefile.SelectQueryOrderby("userContent n",column,WhereCondition,"","0","10",orderby)
        print(data,"-------------------------------------------")
        data2 = databasefile.SelectTotalCountQuery("userContent","","")
        data["totalCount"]=data2
        if data != "0":
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print('EXC')
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()




@app.route('/deleteUserContent', methods=['POST'])
def deleteUserContent():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        WhereCondition=""
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteUserContent",inputdata,0)
        
        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("userContent",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()        




@app.route('/generateOrder', methods=['POST'])
def generateOrder():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        #startlimit,endlimit="",""
        keyarr = ['order_amount',"userId"]
        order_currency = 'USD'
        print(inputdata,"B")
        commonfile.writeLog("generateOrder",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        order_receipt = 'order_rcptid_11'
        notes = {'Shipping address': 'Bommanahalli, Bangalore'}   # OPTIONAL

        if msg =="1":
            order_amount=inputdata["order_amount"]
            #userId=inputdata["userId"]
            #client = razorpay.Client(auth=("rzp_live_OsvYv63WTYaI4c", "hYnMMNEGGKXVEwcWtTa2ABUD"))
            client = razorpay.Client(auth=("rzp_test_4u8muzEpnmCqNG", "b18AZC0JeXQhg5lqVREI4HU5"))
            clientId=client.order.create({"amount":order_amount, "currency":order_currency, "receipt":order_receipt, "notes":notes, "payment_capture":'0'})
       
            if clientId:
                Data = {"status":"true","message":"","result":clientId}                  
                return Data
            else:
                return {"status":"false","message":"Invalid Email","result":""}  
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

#_________________________________dashboard______________________


@app.route('/dashboard', methods=['POST'])
def dashboard():

    try:
       
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        print("dashboard",inputdata)
        commonfile.writeLog("dashboard",inputdata,0)
        keyarr = ["dashboardId","flag"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            summary=""
            videoLink=""
            flag=inputdata['flag']
            column,values="",""
            # if "title" in inputdata:
            #     if inputdata['title'] != "":
            #         title =inputdata["title"]
            #         column=" title "
            #         values=" '"+ str(title) +"'"

            if "dashboardId" in inputdata:
                dashboardId =inputdata["dashboardId"]
                print(dashboardId)
                column=column+" dashboardId "
                values=values+" '"+ str(dashboardId) +"'"


            # if "summary" in inputdata:
            #     if inputdata['summary'] != "":
            #         summary =inputdata["summary"]
            #         column=column+", summary"
            #         values=values+ ",'"+str(summary)+"'"
        
            
            if "videoLink" in inputdata:
                print("1111111111111111111")
                if (inputdata['videoLink'] != None) :
                    print("2222222222222222")
                    videoLink =inputdata["videoLink"]
                    if videoLink=="":
                        videoLink=""
                        column=column+" ,videoLink,"
                        values=values+",'" +str(videoLink)+"',"
            
                      
                    else:
                        column=column+", videoLink,"
                        values=values+",'" +str(videoLink)+"',"
            
            
            if 'postImage' in request.files:      
                    file = request.files.get('postImage')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getDashboard(filename)  

                    filepath = '/dashboard/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
                   
            if flag =="i":
                if "UserId" in inputdata:
                    if inputdata['UserId'] != "":
                        UserId =inputdata["UserId"]
                    column =column + "UserCreate,imagePath"
                    values =   values+"'"+ str(UserId)+"','" + str(ImagePath)+"'"
                    data = databasefile.InsertQuery("dashboard",column,values)        
                else:
                    column = column+ " "
                    
                    data = databasefile.InsertQuery("dashboard",column,values)
            if flag =='u':

                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]

                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        print(inputdata1,"==========================================")
                        if  inputdata1 !=None: 
                            index=re.search("/dashboard", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]
                        whereCondition=" and  id= '"+ str(Id)+"'"
                        column="dashboardId='"+ str(dashboardId)+"',videoLink='"+ str(videoLink)+"',imagePath='"+ str(ImagePath)+"',Status='"+ str(status)+"'"
                        data=databasefile.UpdateQuery("dashboard",column,whereCondition)


            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/landingPageDashboard2', methods=['POST'])
def landingPageDashboard12():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and status<2"
        whereCondition2=""
        startlimit,endlimit="0","1"
        
        data1={"message":"","status":"true","result":[]}
        orderby=" id "
        inputdata={}
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())
            print(inputdata,"++++++++++++++++++")        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
            
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])

        #marketInsight            
        
        whereCondition2=WhereCondition+ " and dashboardId='1' "


        column = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')dateCreate, imagePath,videoLink   "
        data = databasefile.SelectQueryOrderby("dashboard ",column,whereCondition2,"",startlimit,endlimit,orderby)
        if data["result"]=="":
            data["result"]=[]


        for m in data['result']:
                if m['imagePath']!='':
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoLink']!=None:
                    y=m['videoLink'].split('=')
                    print(y,'++++++')

                    d=len(y)
                    if d>1:
                        m['videoId']=y[1]
                    else:
                        m['videoId']=y[0]

                    
                else:
                    m['videoId']=''

        #news    

        whereCondition2=WhereCondition+  " and dashboardId='2' "   
        

        column2 = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, imagePath,videoLink  "
        data2 = databasefile.SelectQueryOrderby("dashboard",column2,whereCondition2,"",startlimit,endlimit,orderby)
        
        if data2["result"]=="":
            data2["result"]=[]


        for m in data2['result']:
                if m['imagePath']!='' :
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoLink']!=None:
                    y=m['videoLink'].split('=')
                    print(y,'++++++')

                    d=len(y)
                    if d>1:
                        m['videoId']=y[1]
                    else:
                        m['videoId']=y[0]
                   
                else:
                    m['videoId']=''

        print("1111----")

       
            

        #event
        whereCondition2=WhereCondition+ " and dashboardId='5' " 
        column3 = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath,videoLink  "
        data3 = databasefile.SelectQueryOrderby("dashboard ",column3,whereCondition2,"",startlimit,endlimit,orderby)
        if data3["result"]=="":
            data3["result"]=[]

        
        for m in data3['result']:
                if m['imagePath']!='':
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoLink']!=None:
                    y=m['videoLink'].split('=')
                    print(y,'++++++')

                    d=len(y)
                    if d>1:
                        m['videoId']=y[1]
                    else:
                        m['videoId']=y[0]
           
                else:
                    m['videoId']=''    

        #medAchieversTv    

        whereCondition2=WhereCondition+ " and dashboardId='3' "     
        column4 = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath ,videoLink  "
        data4 = databasefile.SelectQueryOrderby("dashboard",column4,whereCondition2,"",startlimit,endlimit,orderby)
        print(data4)

        if data4["result"]=="":
            data4["result"]=[]

        for m in data4['result']:
                if m['imagePath']!='':
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoLink']!=None:
                    y=m['videoLink'].split('=')
                    print(y,'++++++')
                    
                    d=len(y)
                    if d>1:
                        m['videoId']=y[1]
                    else:
                        m['videoId']=y[0]
                else:
                    m['videoId']=''

        #highl.in            

        
        whereCondition2=WhereCondition+ " and dashboardId='4' "             
            
        column5 = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath ,videoLink   "
        data5 = databasefile.SelectQueryOrderby("dashboard",column5,whereCondition2,"",startlimit,endlimit,orderby)
        if data5["result"]=="":
            data5["result"]=[]

        for m in data5['result']:
            if m['imagePath']!='':
                m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
            if  m['videoLink']!=None:
                y=m['videoLink'].split('=')
                print(y,'++++++')
                
                d=len(y)
                if d>1:
                    m['videoId']=y[1]
                else:
                    m['videoId']=y[0]

            else:
                m['videoId']=''
    
        
        #gallery        


        whereCondition2=WhereCondition+ " and dashboardId='6' "  

        column6 = "mi.id,date_format(CONVERT_TZ(mi.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,mi.imagePath,videoLink   "
        data6 = databasefile.SelectQueryOrderby("dashboard as mi ",column6,whereCondition2,"",startlimit,endlimit,orderby)
        if data6["result"]=="":
            data6["result"]=[]


        for m in data6['result']:
                if m['imagePath']!='':
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoLink']!=None:
                    y=m['videoLink'].split('=')
                    print(y,'++++++')
                   
                    d=len(y)
                    if d>1:
                        m['videoId']=y[1]
                    else:
                        m['videoId']=y[0]
                else:
                    m['videoId']=''

        #Acadmic Council    

        whereCondition2=WhereCondition+ " and dashboardId='7' "    
       
                            
       
        column7 = "mi.id,date_format(CONVERT_TZ(mi.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,mi.imagePath,videoLink"
        data7 = databasefile.SelectQueryOrderby("dashboard  as mi",column7,whereCondition2,"",startlimit,endlimit,orderby)
        if data7["result"]=="":
            data7["result"]=[]


        for m in data7['result']:
                if m['imagePath']!='':
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoLink']!=None:
                    y=m['videoLink'].split('=')
                    print(y,'++++++')
                   
                    d=len(y)
                    if d>1:
                        m['videoId']=y[1]
                    else:
                        m['videoId']=y[0]
                else:
                    m['videoId']=''

        #OurPartners



        column99 = "id,Status,UserCreate, date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, concat('"+ ConstantData.GetBaseURL() + "',imagePath)imagePath  "
        data99 = databasefile.SelectQueryOrderby("ourPartners",column99,"","",startlimit,endlimit,orderby)
        
        if data99["result"]=="":
            data99["result"]=[]




        whereCondition2=WhereCondition+ " and dashboardId='8' "             
            
        column51 = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath ,videoLink   "
        data51 = databasefile.SelectQueryOrderby("dashboard",column51,whereCondition2,"",startlimit,endlimit,orderby)
        if data51["result"]=="":
            data51["result"]=[]

        for m in data51['result']:
            if m['imagePath']!='':
                m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
            if  m['videoLink']!=None:
                y=m['videoLink'].split('=')
                print(y,'++++++')
                
                d=len(y)
                if d>1:
                    m['videoId']=y[1]
                else:
                    m['videoId']=y[0]

            else:
                m['videoId']=''


        whereCondition2=WhereCondition+ " and dashboardId='9' "             
            
        column512 = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,imagePath ,videoLink   "
        data512 = databasefile.SelectQueryOrderby("dashboard",column512,whereCondition2,"",startlimit,endlimit,orderby)
        if data512["result"]=="":
            data512["result"]=[]

        for m in data512['result']:
            if m['imagePath']!='':
                m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
            if  m['videoLink']!=None:
                y=m['videoLink'].split('=')
                print(y,'++++++')
                
                d=len(y)
                if d>1:
                    m['videoId']=y[1]
                else:
                    m['videoId']=y[0]

            else:
                m['videoId']=''

        

        

                        




            

            

       


        if data != "0":
            
            return {"message":"","status":"true","marketingInsights":data['result'],"upSkillsOpportunity":{"featured Programs":data7['result'],"top Rated Programs":data7['result']},"highlightedIntiatives":data5["result"],"news":data2["result"],"gallery":data6["result"],"event":data3["result"],"medAchieversTv":data4["result"],"ourPartners":data99['result'],"publicAffairs":data51['result'],"medStreet":data512['result']}
            
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/operationAdminDashboard', methods=['POST'])
def landingPageDashboard121():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and status<2"
        whereCondition2=""
        startlimit,endlimit="",""
        
        data1={"message":"","status":"true","result":[]}
        orderby=" id "
        inputdata={}
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())
            print(inputdata,"++++++++++++++++++")        
        
            if "startLimit" in inputdata:
                if inputdata['startLimit'] != "":
                    startlimit =str(inputdata["startLimit"])
            
            if "endLimit" in inputdata:
                if inputdata['endLimit'] != "":
                    endlimit =str(inputdata["endLimit"])

            if "dashboardId" in inputdata:
                if inputdata['dashboardId'] != "":
                    dashboardId =str(inputdata["dashboardId"])
                    WhereCondition=WhereCondition+" and dashboardId='"+str(dashboardId)+"'"

            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =int(inputdata["id"])
                    WhereCondition=WhereCondition+" and id='"+str(Id)+"'"        
        

                  
        
       


        column = "id, date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')dateCreate,imagePath,videoLink"
        data = databasefile.SelectQueryOrderby("dashboard ",column, WhereCondition,"",startlimit,endlimit,orderby)
        coundata=databasefile,SelectQuery4("dashboard",column,WhereCondition)
        totalcount=len(coundata['result'])
        if data["result"]=="":
            data["result"]=[]

        for m in data['result']:
                if m['imagePath']!=None:
                    m['imagePath']=str(ConstantData.GetBaseURL())+ str(m['imagePath'])
                if  m['videoLink']!=None:
                    y=m['videoLink'].split('=')
                    print(y,'++++++')
                    m['videoId']=y[1]
                else:
                    m['videoId']=""

               

        



            

       


        if data != "0":
            
            return {"message":"","status":"true","result":data['result'],"totalcount":totalcount}
            
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/ourPartnersImages', methods=['POST'])
def ourPartners155():
    try:
        inputdata = request.form.get('data') 

        inputdata = json.loads(inputdata) 
        print(inputdata)
        startlimit,endlimit="",""
        keyarr = ["userId","flag"]
        
        commonfile.writeLog("ourPartnersImages1",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            print("111111111111111")
            ImagePath=""
            flag=inputdata['flag']
            print("2222222222222222222222222222")
            file_Count=inputdata['fileCount']
            fileCount=int(file_Count)
            imagelist=[]
            l2=[]

            if fileCount!=0:
                print(fileCount)
                for ll in range(fileCount):
                    print(ll,"Timmmmmmmmmmmmmmmmmesssssss")
                    file= request.files.get('postImage_'+str(ll+1)+'')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 
                    print(filename,"++++++++++++++=")
                    imagelist.append(filename)

                print(imagelist,"immmmmmmmmmmmaggge")
                for i in imagelist:
                    FolderPath = ConstantData.getourPartners(i)  
                    print(FolderPath,"9999999999999999999999999999999999999999")
                    filepath = '/ourPartners/' + i
                    l2.append(filepath)   
                    file.save(FolderPath)
                    ImagePath = filepath

            print(l2,"++11111111111111")
            l1=[]
        
            
            if flag =="i":
                print("11was")
                print(l2,"qqqqqqqqqqq")
                if "userId" in inputdata:
                    if inputdata['userId'] != "":
                        userId =inputdata["userId"]
                    print(l2,"@@@@@@@@@@@@@@@@2")
                    l3=[]
                    
                    for m in l2:
                        if m not in l1:
                            l1.append(m)
                    print(l1,"Lissssssttt")
                    for j in l1:
                        l3.append(j)
                        print(l3,"!@!@@!@!@@!@!#!@!@@!!@!!")

                        column = " imagePath,UserCreate"
                        values = " '"+ str(j)+ "','" + str(userId) + "'"
                        data = databasefile.InsertQuery("ourPartners",column,values)
                    return data        
                
            if flag =="u":
                
                if "status" in inputdata:
                    if inputdata['status'] != "":
                        status =inputdata["status"]
               
                if "id" in inputdata:
                    if inputdata['id'] != "":
                        Id =inputdata["id"]
                        inputdata1 = request.form.get('postImage')
                        print(inputdata1,"==========================================")
                        if  inputdata1 is not None: 
                            index=re.search("/ourPartners", inputdata1).start()
                            ImagePath=""
                            ImagePath=inputdata1[index:]
                        whereCondition=" and  id='" + str(Id) + "'"
                        column="imagePath='"+ str(ImagePath)+  "',status='"+ str(status)+  "'"
                        data=databasefile.UpdateQuery("ourPartners",column,whereCondition)
                        return data




            
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/getMarketingInsights1', methods=['POST'])
def getMarketingInsights1111():

    try:
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and n.Status<2 "
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
                    WhereCondition=WhereCondition+"  and n.userTypeId IN(0,'"+str(userTypeId)+"')"

            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and n.id='"+str(Id)+"'"
        
       
        
        

        orderby=" n.id "
        WhereCondition=WhereCondition+" and n.UserCreate=um.userId "
        column = " n.id,n.Status,n.newsTitle,n.videoLink,n.userTypeId,n.summary,n.newsDesc, date_format(CONVERT_TZ(n.DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate, n.imagePath ,um.userName "
        data = databasefile.SelectQueryOrderby("marketingInsights n,userMaster um",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectTotalCountQuery("marketingInsights","","")

        if data != "0":
            data["totalCount"]=data2
            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=y[0]
                else:
                    i['videoId']=""
                
                marketingInsightId=i['id']
                whereCondition="and lki.marketingInsightId='"+str(marketingInsightId)+"'"
                columns="count(*) as count"
                likeCount=databasefile.SelectQuery('likeMarketingInsight as lki',columns,whereCondition,"","","")
                print(likeCount)
                if likeCount['status']!='false':
                    lki=likeCount['result'][0]['count']

                        

                    i['likeCount']=lki
                    
                        
                    
                else:
                    i['likeCount']=0
                whereCondition=" and  lki.marketingInsightId='"+str(marketingInsightId)+"'"
                commentCount=databasefile.SelectQuery('marketingInsightComment as lki',columns,whereCondition,"","","")
                if commentCount['status']!='false':
                    com=commentCount['result'][0]['count']

                        

                    i['commentCount']=com
                    
                        
                    
                else:
                    i['commentCount']=0
                   


            return data
            
        else:
            return commonfile.Errormessage()

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()




@app.route('/getParliamentEvent1', methods=['POST'])
def getParliamentEventa():

    try:        
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+" and Status<2"
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
          
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "id" in inputdata:
                if inputdata['id'] != "":
                    Id =inputdata["id"] 
                    WhereCondition=WhereCondition+" and id='"+str(Id)+"'"
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"] 
                    WhereCondition=WhereCondition+" and userTypeId IN(0,'"+str(userTypeId)+"')"
            orderby=" id "
        
        column = "id,Status,UserCreate,eventTitle,userTypeId,eventSummary,eventLocation,date_format(CONVERT_TZ(eventDate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')eventDate,date_format(CONVERT_TZ(DateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate,videoLink,imagePath   "
        data = databasefile.SelectQueryOrderby("parliamentEvent",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectTotalCountQuery("parliamentEvent ",WhereCondition,"")
        print(data)
        if data['result'] != "":
            data['totalcount']=data2


            for i in data['result']:
                if i['imagePath']!='':
                    i['imagePath']=str(ConstantData.GetBaseURL())+ str(i['imagePath'])
                if i['videoLink']!=None:
                   if i['videoLink']!=None:
                    y=i['videoLink'].split('=')
                     
                    d=len(y)
                    if d>1:
                        i['videoId']=y[1]
                    else:
                        i['videoId']=""
                else:
                    i['videoId']=""

                y2=i['id']
                columns="count(*) as count"
                whereCondition=" and eventId='"+str(y2)+"'"
                dat=databasefile.SelectQuery('eventInterest',columns,whereCondition,"",startlimit,endlimit)
                print(dat,'++++++++++++++++++++++++++++')
                if dat['status']!='false':
                    lki=dat['result'][0]['count']
                    i['likeCount']=lki
                else:
                    i['likeCount']=0

                columns="count(*) as count"
                whereCondition=" and eventId='"+str(y2)+"'"
                dat3=databasefile.SelectQuery('eventInterest1',columns,whereCondition,"",startlimit,endlimit)
                print(dat,'++++++++++++++++++++++++++++')
                if dat3['status']!='false':
                    lkii=dat3['result'][0]['count']
                    i['interestCount']=lkii
                else:
                    i['interestCount']=0

                columns="count(*) as count"
                whereCondition=" and eventId='"+str(y2)+"'"
                dat1=databasefile.SelectQuery('eventComment',columns,whereCondition,"",startlimit,endlimit)
                print(dat1,'++++++++++++++++++++++++++++')
                if dat1['status']!='false':
                    com=dat1['result'][0]['count']
                    i['commentCount']=com
                else:
                    i['commentCount']=0


            return data
        else:
            return data

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()







@app.route('/getParliamentEventInterest', methods=['POST'])
def getParliamentEvent11():

    try:        
       startlimit,endlimit="",""
       WhereCondition=""
       if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "Id" in inputdata:
                if inputdata['Id'] != "":
                    Id =inputdata["Id"] 
                    WhereCondition=WhereCondition+" and pm.id='"+str(Id)+"'"
            
            column = " us.userName,us.email,us.mobileNo, date_format(CONVERT_TZ(ei.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=WhereCondition+" and pm.id=ei.eventId and us.userId=ei.userId"
            orderby='ei.id'

            data1 = databasefile.SelectQuery4("parliamentEvent as pm,eventInterest1 as ei,userMaster as us",column,WhereCondition)
            
            data2 = databasefile.SelectQueryOrderby("parliamentEvent as pm,eventInterest1 as ei,userMaster as us",column,WhereCondition,"",startlimit,endlimit,orderby)
            
          
            if data1['status'] != "false":
                data={"status":"true","result":data2['result'],"totalCount":len(data1['result'])}
                return data
            else:
                return data

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()  



@app.route('/getmarketingInsightslikes', methods=['POST'])
def getmarketingInsightslikes():

    try:        
       startlimit,endlimit="",""
       WhereCondition=""
       if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "Id" in inputdata:
                if inputdata['Id'] != "":
                    Id =inputdata["Id"] 
                    WhereCondition=WhereCondition+" and mi.id='"+str(Id)+"'"
            column = " us.userName,us.email,us.mobileNo, date_format(CONVERT_TZ(lki.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
        
            WhereCondition= WhereCondition+" and mi.id=lki.marketingInsightId and us.userId=lki.userId "
            orderby="lki.id"

            data1 = databasefile.SelectQuery4("marketingInsights as mi,likeMarketingInsight as lki,userMaster as us",column,WhereCondition)
            
            data2= databasefile.SelectQueryOrderby("marketingInsights as mi,likeMarketingInsight as lki,userMaster as us",column,WhereCondition,"",startlimit,endlimit,orderby)
            
            if data1['status'] != "false":
                data={"status":"true","result":data2['result'],"totalCount":len(data1['result'])}
                return data
            else:
                return data

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/getParliamentEventlikes', methods=['POST'])
def getParliamentEvent1():

    try:        
       startlimit,endlimit="",""
       WhereCondition=""
       if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())        
        
           
            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            if "Id" in inputdata:
                if inputdata['Id'] != "":
                    Id =inputdata["Id"] 
                    WhereCondition=WhereCondition+" and pm.id='"+str(Id)+"'"
            
            column = " us.userName,us.email,us.mobileNo, date_format(CONVERT_TZ(ei.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition=WhereCondition+" and pm.id=ei.eventId and us.userId=ei.userId"
            orderby="ei.id"


            data1 = databasefile.SelectQuery4("parliamentEvent as pm,eventInterest as ei,userMaster as us",column,WhereCondition)
        
            data2= databasefile.SelectQueryOrderby("parliamentEvent as pm,eventInterest as ei,userMaster as us",column,WhereCondition,'',startlimit,endlimit,orderby)
            if data1['status'] != "false":
                data={"status":"true","result":data2['result'],"totalCount":len(data1['result'])}
                return data
            else:
                return data

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()                                     




@app.route('/eventInterest2', methods=['POST'])
def verifyPost12312():
    try:
        print("nnnnnnnnnnnn",request.get_data(),"===================",type(request.get_data()))
        inputdata =  commonfile.DecodeInputdata(request.get_data()) 
        print("mmmmmmmmmmm")
        startlimit,endlimit="",""
        print("111111111111111111111111")
        keyarr = ['userId','eventId','userTypeId']
        commonfile.writeLog("eventInterest12",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        print("22222222222222222222222")
        if msg == "1":
            approvedUserId = inputdata["userId"]
            postId = inputdata["eventId"]
            userTypeId = int(inputdata["userTypeId"])

            WhereCondition = " and eventId = '" + str(postId) + "' and userId = '" + str(approvedUserId) + "'"
            count = databasefile.SelectCountQuery("eventInterest1",WhereCondition,"")
            
            if int(count) > 0:
                print('F')         
                return commonfile.EventInterstAlreadyExistMsg()
            else:
                print("333333333333333333333")
             
               
                column = "userId,eventId,userTypeId,UserCreate"                
                values = " '" + str(approvedUserId) + "','" + str(postId) + "','" + str(userTypeId) + "','" + str(approvedUserId) + "'"
                data = databasefile.InsertQuery("eventInterest1",column,values)
                if data!="0":
                    column="count(*) as count"
                    whereCondition="  and eventId ='" + str(postId) + "' "
                    data1=databasefile.SelectQuery("eventInterest1",column,whereCondition,"",startlimit,endlimit)
                    if (data1["status"]!="false"):
                        o=[]
                        y=data1["result"][0]
                        print(y,"+++++++++++++++++=")
                        whereCondition99= " and eventId ='" + str(postId) + "' and userId='" + str(approvedUserId) + "'"
                        column88="status"
                        da1=databasefile.SelectQuery("eventInterest1",column88,whereCondition99,"",startlimit,endlimit)
                        if da1['status'] != 'false':
                            for i in da1['result']:
                                i['interested']=1
                                y2={"interested":1}
                                y.update(y2)
                                o.append(y)        
                       

                      
                       
                        data1={"status":"true","result":o,"message":""}
                        return data1
                    else:
                        data1={"status":"true","result":"","message":"No Data Found"}
                        return data1

                else:
                    return commonfile.Errormessage()
        else:
            return msg 
    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()


@app.route('/superAdminNotification', methods=['POST'])
def superAdminNotification():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        commonfile.writeLog("superAdminNotification",inputdata,0)
        data={"status":"true","message":"","result":[]}
        
        msg = "1"
        if msg =="1":
            orderby="Id"
            postId,whereCondition="",""

            
            # userTypeId=inputdata["userTypeId"]
            # userId=inputdata['userId']

            if "startlimit" in inputdata:
                if inputdata['startlimit'] != "":
                    startlimit =str(inputdata["startlimit"])
                
            if "endlimit" in inputdata:
                if inputdata['endlimit'] != "":
                    endlimit =str(inputdata["endlimit"])
            
            column="pm.postDescription,um.userName,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
            WhereCondition= "and pm.superadminStatus ='0' and pm.userId=um.userId"
            data = databasefile.SelectQuery4("userPost as pm,userMaster as um",column,WhereCondition)
            print(data,"data2")
            if data['result'] != "":
                for i in data['result']:
                    postId=i['postId']
                    whereCondition=" and  postId= '"+str(postId) +"'"
                    column=" superadminStatus ='1'"
                    data1=databasefile.UpdateQuery('userPost',column,whereCondition)
                print("111111111111111")          
                Data = {"status":"true","message":"","result":data['result'],"totalcount":len(data['result'])}
                print(Data,"@@@@@@@@@@@@@@@@@@")
                return Data
            else:
                print('2')
                column="pm.postId"
                WhereCondition= "and pm.superadminStatus='1' "
                data = databasefile.SelectQuery4("userPost as pm",column,WhereCondition)
                print(data,"1bguifesuy")
                a=[]
                if data['status'] !='false':
                
                    print(data['result'],"____________________________________________________-")
                    
                    print(type(data['result']),"99999999999999999")
                
                    
                 


                    for i in data['result']:

                        print("22222222222222222222222222222y373vedvsfswsf",i)
                    
                        print(i['postId'],'wwwwwwwwwwww9999999999999999999999999999999999999')
                        print("111111111111111")

                        column= "pm.commentDescription as postDescription,um.userName,pm.postId,up.userId,pm.status,pm.id as Id,up.postTitle,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                        whereCondition= " and pm.approvedUserId=um.userId  and  pm.superstatus='0'  and pm.postId=up.postId and pm.postId= '"+str(i['postId'])+"'"
                        data2=databasefile.SelectQuery4('approvedBy as pm,userPost as up,userMaster as um',column,whereCondition)
                        print(data2['result'],"@@@@@@@@$%9999999999999999999999999999999999976666666")

                        
                        
                        if data2['status']!='false':
                            print('sggggggggggggggggggggggg',data2['result'])
                            for m in data2['result']:
                                a.append(m)
                            for i in a:
                                postId=i['postId']
                                whereCondition=" and  postId= '"+str(postId) +"'"
                                column=" superstatus='1' "
                                data=databasefile.UpdateQuery('approvedBy',column,whereCondition)
                    a=sorted(a, key = lambda i: i['DateCreate'])
                    print(a,"aaaaaaaaaaa")
                            
                            
                            
                            
                        

                    Data = {"status":"true","message":"","result":a,"totalcount":len(a)}
                    print(Data,"@@@@@@@@@@@@@@@@@@")
                    return Data
                else:
                    output = {"status":"false","message":"No Data Found","result":"","totalcount":0}
                    return output
        else:
            return msg
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 

@app.route('/superAdminNotificationCount', methods=['POST'])
def superAdminNotificationCount():
    try:

          
        column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
        WhereCondition= "and pm.superadminStatus='0'"
        data = databasefile.SelectQuery4("userPost as pm",column,WhereCondition)
        print(data,"999999999999999999")
        print('11111111')
        
        if data['status'] !='false':
                   
            Data = {"status":"true","message":"","result":"","totalcount":len(data['result'])}
            return Data
        else:
            print('1')
            column="pm.postId"
            WhereCondition= "and pm.superadminStatus='1'  "
            data = databasefile.SelectQuery4("userPost as pm",column,WhereCondition)
            print(data,'11111111111111111111111')
            a=[]
            if data['status'] !='false':
                
                print(data['result'],"____________________________________________________-")
                print(type(data['result']),"99999999999999999")
            
                
             


                for i in data['result']:

                    print("22222222222222222222222222222y373vedvsfswsf",i)
                
                    print(i['postId'],'wwwwwwwwwwww9999999999999999999999999999999999999')
                    print("111111111111111")

                    column= "pm.commentDescription as postDescription,um.userName,pm.postId,up.userId,pm.status,pm.id as Id,up.postTitle,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    whereCondition= " and pm.approvedUserId=um.userId  and  pm.superstatus='0'  and pm.postId=up.postId and pm.postId= '"+str(i['postId'])+"'"
                    data2=databasefile.SelectQuery4('approvedBy as pm,userPost as up,userMaster as um',column,whereCondition)
                    print(data2['result'],"@@@@@@@@$%9999999999999999999999999999999999976666666")

                    
                    
                    if data2['status']!='false':
                        print('sggggggggggggggggggggggg',data2['result'])
                        for m in data2['result']:
                            a.append(m)

                        
                        
                        
                        
                    

                Data = {"status":"true","message":"","result":"","totalcount":len(a)}
                print(Data,"@@@@@@@@@@@@@@@@@@")
                return Data

               
            else:
                output = {"status":"false","message":"No Data Found","result":"","totalcount":0}
                return output
      
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output

#_______________


@app.route('/adminNotification1', methods=['POST'])
def adminNotification():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        orderby="Id"
        postId,whereCondition="",""

        
        userTypeId=inputdata["userTypeId"]

        if "startlimit" in inputdata:
            if inputdata['startlimit'] != "":
                startlimit =str(inputdata["startlimit"])
            
        if "endlimit" in inputdata:
            if inputdata['endlimit'] != "":
                endlimit =str(inputdata["endlimit"])
        
        
        column="pm.postDescription,pm.postId,um.userName,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
        WhereCondition= " and  pm.status='0' and pm.userTypeId='" + str(userTypeId) + "' and pm.userId=um.userId"
        data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectQuery4("userPost as pm,userMaster as um",column,WhereCondition)
       
       

        

        
        if (data['status']!="false"):
            for i in data['result']:
                postId=i['postId']
                whereCondition=" and  postId= '"+str(postId) +"'"
                column="status='1'"
                data1=databasefile.UpdateQuery('userPost',column,whereCondition)
            print("111111111111111")          
            Data = {"status":"true","message":"","result":data['result'],"totalcount":len(data2['result'])}
            print(Data,"@@@@@@@@@@@@@@@@@@")
            return Data
        else:

            output = {"status":"false","message":"No Data Found","result":"","totalcount":0}
            return output
       
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 


@app.route('/adminNotificationCount1', methods=['POST'])
def adminNotificationCount():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

       
        orderby="Id"
        postId,whereCondition="",""

        
        userTypeId=inputdata["userTypeId"]
       
        
        column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
        WhereCondition= " and  pm.status='0' and pm.userTypeId='" + str(userTypeId) + "'"
        data = databasefile.SelectQuery4("userPost as pm",column,WhereCondition)
       
       

        

        
        if (data['status']!="false"):
            print("111111111111111")          
            Data = {"status":"true","message":"","result":"","totalcount":len(data['result'])}
            print(Data,"@@@@@@@@@@@@@@@@@@")
            return Data
        else:
            output = {"status":"false","message":"No Data Found","result":"","totalcount":0}
            return output
      
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output  

@app.route('/adminNotificationCount', methods=['POST'])
def adminNotificationCount1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

       
        orderby="Id"
        postId,whereCondition="",""

        
        userTypeId=inputdata["userTypeId"]
       
        
        column="pm.postDescription,pm.postId,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
        WhereCondition= " and  pm.status='0' and pm.userTypeId='" + str(userTypeId) + "'"
        data = databasefile.SelectQuery4("userPost as pm",column,WhereCondition)
       
       

        

        
        if (data['status']!="false"):
            print("111111111111111")          
            Data = {"status":"true","message":"","result":"","totalcount":len(data['result'])}
            print(Data,"@@@@@@@@@@@@@@@@@@")
            return Data
        else:
            column="pm.postId"
            WhereCondition= "and pm.status='1' and pm.userTypeId='" + str(userTypeId) + "' "
            data = databasefile.SelectQuery4("userPost as pm",column,WhereCondition)
            a=[]
            

            if data['status'] !='false':
                
                print(data['result'],"____________________________________________________-")
                print(type(data['result']),"99999999999999999")
            
                
             


                for i in data['result']:

                    print("22222222222222222222222222222y373vedvsfswsf",i)
                
                    print(i['postId'],'wwwwwwwwwwww9999999999999999999999999999999999999')
                    print("111111111111111")

                    column= "pm.commentDescription as postDescription,um.userName,pm.postId,up.userId,pm.status,pm.id as Id,up.postTitle,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    whereCondition= " and pm.approvedUserId=um.userId and pm.userTypeId='" + str(userTypeId) + "' and  pm.adminstatus='0'  and pm.postId=up.postId and pm.postId= '"+str(i['postId'])+"'"
                    data2=databasefile.SelectQuery4('approvedBy as pm,userPost as up,userMaster as um',column,whereCondition)
                    print(data2['result'],"@@@@@@@@$%9999999999999999999999999999999999976666666")

                    
                    
                    if data2['result']!= "":
                        print('sggggggggggggggggggggggg',data2['result'])
                        for m in data2['result']:
                            a.append(m)
                        
                        
                        
                        
                    

                Data = {"status":"true","message":"","result":"","totalcount":len(a)}
                print(Data,"@@@@@@@@@@@@@@@@@@")
                return Data

               
            else:
                output = {"status":"false","message":"No Data Found","result":"","totalcount":0}
                return output
      
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output  




@app.route('/adminNotification', methods=['POST'])
def adminNotification1():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""

        orderby="Id"
        postId,whereCondition="",""

        
        userTypeId=inputdata["userTypeId"]

        if "startlimit" in inputdata:
            if inputdata['startlimit'] != "":
                startlimit =str(inputdata["startlimit"])
            
        if "endlimit" in inputdata:
            if inputdata['endlimit'] != "":
                endlimit =str(inputdata["endlimit"])
        
        
        column="pm.postDescription,pm.postId,um.userName,pm.userId,pm.status,pm.id as Id,pm.postImage,pm.postTitle,pm.postImagePath,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
        WhereCondition= " and  pm.status='0' and pm.userTypeId='" + str(userTypeId) + "' and pm.userId=um.userId"
        data = databasefile.SelectQueryOrderby("userPost as pm,userMaster as um",column,WhereCondition,"",startlimit,endlimit,orderby)
        data2 = databasefile.SelectQuery4("userPost as pm,userMaster as um",column,WhereCondition)
       
       

        

        
        if (data['result']!=""):
            for i in data['result']:
                postId=i['postId']
                whereCondition=" and  postId= '"+str(postId) +"'"
                column="status='1'"
                data1=databasefile.UpdateQuery('userPost',column,whereCondition)
            print("111111111111111")          
            Data = {"status":"true","message":"","result":data['result'],"totalcount":len(data2['result'])}
            print(Data,"@@@@@@@@@@@@@@@@@@")
            return Data
        else:
            column="pm.postId"
            WhereCondition= "and pm.status='1' and pm.userTypeId='" + str(userTypeId) + "' "
            data = databasefile.SelectQuery4("userPost as pm",column,WhereCondition)
            a=[]
            if data['status'] !='false':
                
                print(data['result'],"____________________________________________________-")
                print(type(data['result']),"99999999999999999")
            
                
             


                for i in data['result']:

                    print("22222222222222222222222222222y373vedvsfswsf",i)
                
                    print(i['postId'],'wwwwwwwwwwww9999999999999999999999999999999999999')
                    print("111111111111111")

                    column= "pm.commentDescription as postDescription,um.userName,pm.postId,up.userId,pm.status,pm.id as Id,up.postTitle,pm.userTypeId as userTypeId,date_format(CONVERT_TZ(pm.dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate"
                    whereCondition= " and pm.approvedUserId=um.userId and pm.userTypeId='" + str(userTypeId) + "' and  pm.adminstatus='0'  and pm.postId=up.postId and pm.postId= '"+str(i['postId'])+"'"
                    data2=databasefile.SelectQuery4('approvedBy as pm,userPost as up,userMaster as um',column,whereCondition)
                    print(data2['result'],"@@@@@@@@$%9999999999999999999999999999999999976666666")

                    
                    
                    if data2['result']!= "":
                        print('sggggggggggggggggggggggg',data2['result'])
                        for m in data2['result']:
                            a.append(m)
                        for i in a:
                            postId=i['postId']
                            whereCondition=" and  postId= '"+str(postId) +"'"
                            column=" adminstatus='1' "
                            data=databasefile.UpdateQuery('approvedBy',column,whereCondition)
                a=sorted(a, key = lambda i: i['DateCreate'])
                        
                        
                        
                        
                    

                Data = {"status":"true","message":"","result":a,"totalcount":len(a)}
                print(Data,"@@@@@@@@@@@@@@@@@@")
                return Data

               
            else:
                output = {"status":"false","message":"No Data Found","result":"","totalcount":0}
                return output
       
    except Exception as e :
        print("Exception---->" + str(e))    
        output = {"status":"false","message":"something went wrong","result":""}
        return output 



# create notification by admin

@app.route('/adduserNotification', methods=['POST'])
def adduserNotification():

    try:
       
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        print("Notification",inputdata)
        commonfile.writeLog("adminNotification",inputdata,0)
        keyarr = ["Title","summary","Desc","UserType","UserId"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            if "Title" in inputdata:
                if inputdata['Title'] != "":
                    Title =inputdata["Title"]

            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =inputdata["summary"]

           
            if "Desc" in inputdata:
                if inputdata['Desc'] != "":
                    Desc =inputdata["Desc"]

            if "UserType" in inputdata:
                if inputdata['UserType'] != "":
                    UserType =inputdata["UserType"]


                             
            
            if 'NotificationBanner' in request.files:      
                    file = request.files.get('NotificationBanner')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getNotificationPath(filename)  

                    filepath = '/notificationimages/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath

            if "UserId" in inputdata:
                if inputdata['UserId'] != "":
                    UserId =inputdata["UserId"]
                
                column = "title,imagePath,summary,description,UserCreate,UserType"
                values = " '"+ str(Title) +"','" + str(ImagePath)+"','" + str(summary) +"','" + str(Desc)  + "','" + str(UserId) + "','" + str(UserType)+ "'"
                data = databasefile.InsertRtnId("Notification",column,values)
                print(data)

            else:
                column = "title,imagePath,summary,description,UserType"
                values = " '"+ str(Title) +"','" + str(ImagePath)+"','" + str(summary) +"','" + str(Desc)  +"','" + str(UserType)  + "'"
                data = databasefile.InsertRtnId("Notification",column,values)
            print(data)
            notificationId=data['Id']

            
            column="MobileToken,userId,userName"
            WhereCondition=" and userTypeId='"+str(UserType)+"'"
            data1=databasefile.SelectQuery4('userMaster',column,WhereCondition)
            for i in  data1['result']:
                MobileToken=i['MobileToken']
                userId=i['userId']
                userName=i['userName']
                column="notificationId,title,imagePath,summary,description,MobileToken,userId,userName"
                values= " '"+ str(notificationId)  +"','" + str(Title)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(Desc)  + "','" + str(MobileToken)  + "','" + str(userId) + "','" + str(userName)+ "'"
                data66=databasefile.InsertQuery('userNotification',column,values)
                if MobileToken !=None:
                    a=ConstantData.userNotification(MobileToken,title,description,summary,userName)








            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 


@app.route('/getUserNotification', methods=['POST'])
def getUserNotification():
    try:  
        WhereCondition,startlimit,endlimit="","",""
        WhereCondition=WhereCondition+"  "
        if request.get_data():
            inputdata =  commonfile.DecodeInputdata(request.get_data())      
            
            WhereCondition = " and n.UserCreate = um.UserId "
            orderby = " n.DateCreate "
            NotificationId = ""
            if 'Id' in inputdata:
                if  inputdata["Id"]!= "":
                    Id = inputdata["Id"] 
                    if Id != "" and Id != "0":                           
                        WhereCondition = WhereCondition + " and n.id = " + str(Id)

            if 'startlimit' in inputdata:
                if inputdata["startlimit"] != "":
                    startlimit = str(inputdata["startlimit"])
            if 'endlimit' in inputdata:
                if inputdata["endlimit"] != "":
                    endlimit = str(inputdata["endlimit"])

            column = "n.id as Id,n.title,n.summary,n.description,n.UserCreate,n.UserType,um.UserName,n.DateCreate,"
            column = column + "concat('" + ConstantData.GetBaseURL() + "',"
            column = column + "if(n.imagePath is NULL or n.imagePath = '','"+ConstantData.GetdefaultNotificationImage()+"',n.imagePath))imagePath"
     
            if 'UserId' in inputdata:
                if  inputdata["UserId"]!= "":
                    UserId = inputdata["UserId"]
                    WhereCondition = WhereCondition + " and n.UserCreate = '" + str(UserId) + "' "
                          
            data= databasefile.SelectQueryOrderby("Notification n, userMaster um",column,WhereCondition,"",startlimit,endlimit,orderby)        
            count = databasefile.SelectCountQuery("Notification","","")
            data["totalnotification"]=count   
            data1= databasefile.SelectQuery4("Notification n, UserMaster um",column,WhereCondition)
            if data !=0 :
               

                return data
        else:
            return commonfile.Errormessage()

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 







@app.route('/deleteUserNotification', methods=['POST'])
def deleteUserNotification():
    try: 

        inputdata =  commonfile.DecodeInputdata(request.get_data())

        WhereCondition="" 
  
        if len(inputdata) > 0:           
            commonfile.writeLog("deleteUserNotification",inputdata,0)

        keyarr = ['id']
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if "id" in inputdata:
            if inputdata['id'] != "":
                Id =inputdata["id"] 
                WhereCondition=WhereCondition+" and id='"+str(Id)+"'" 
        if msg == "1":                        
            
            data = databasefile.DeleteQuery("Notification",WhereCondition)

            if data != "0":
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



@app.route('/adduserNotificationUpdate', methods=['POST'])
def adduserNotificationUpdate():

    try:
       
        inputdata = request.form.get('data')    
        inputdata = json.loads(inputdata) 
        print("Notification",inputdata)
        commonfile.writeLog("adminNotification",inputdata,0)
        keyarr = ["Title","summary","Desc","UserType","Id"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            ImagePath=""
            column2=""
            if "Title" in inputdata:
                if inputdata['Title'] != "":
                    Title =inputdata["Title"]

            if "Id" in inputdata:
                if inputdata['Id'] != "":
                    Id =inputdata["Id"]        

            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =inputdata["summary"]

           
            if "Desc" in inputdata:
                if inputdata['Desc'] != "":
                    Desc =inputdata["Desc"]

            if "UserType" in inputdata:
                if inputdata['UserType'] != "":
                    UserType =inputdata["UserType"]


                             
            
            if 'NotificationBanner' in request.files:      
                    file = request.files.get('NotificationBanner')        
                    filename = file.filename or ''                 
                    filename = filename.replace("'","") 

                    print(filename)
                    # filename = str(campaignId)                    
                    #folder path to save campaign image
                    FolderPath = ConstantData.getNotificationPath(filename)  

                    filepath = '/notificationimages/' + filename    
                    

                    file.save(FolderPath)
                    ImagePath = filepath
                    column2=" ,imagePath='"+str(ImagePath)+"'"
            else:
                inputdata1 = request.form.get('NotificationBanner')
                y=type(inputdata1)
                print(y)
                print("  update news1 ")
                y2=len(inputdata1)
                if  y2>4: 
                    print("  update news ")
                    index=re.search("/notificationimages/", inputdata1).start()
                    ImagePath=""
                    ImagePath=inputdata1[index:]
                    print("  update news 2")
                    print(inputdata1)

            if "UserId" in inputdata:
                if inputdata['UserId'] != "":
                    UserId =inputdata["UserId"]
                
                column = "title='"+ str(Title) +"',summary='"+ str(summary) +"',description='"+ str(description) +"',UserCreate='"+ str(UserCreate) +"',UserType='"+ str(UserType) +"'"+column2
                whereCondition=" and id ='"+str(Id)+"'"
                data = databasefile.UpdateQuery("Notification",column,whereCondition)
                print(data)

           
            print(data)
            notificationId=data['Id']

            
            column="MobileToken,userId,userName"
            WhereCondition=" and userTypeId='"+str(UserType)+"'"
            data1=databasefile.SelectQuery4('userMaster',column,WhereCondition)
            for i in  data1['result']:
                MobileToken=i['MobileToken']
                userId=i['userId']
                userName=i['userName']
                column="title='" + str(Title)+"',summary='" + str(summary)+"',description='" + str(description)+"',MobileToken='" + str(MobileToken)+"',userId='" + str(userId)+"',userName='" + str(userName)+"'"+column2
                whereCondition=" and notificationId='"+str(Id)+"'"
                data66=databasefile.UpdateQuery('userNotification',column,whereCondition)
                if MobileToken !=None:
                    a=ConstantData.userNotification(MobileToken,title,description,summary,userName)








            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()         

@app.route('/getDynamicNotification', methods=['POST'])
def getDynamicNotification():
    try:  
        startlimit,endlimit,UserId="","",""
        
        inputdata = commonfile.DecodeInputdata(request.get_data()) 
        
        commonfile.writeLog("getDynamicNotification",inputdata,0)
        
        WhereCondition= "  "

        if 'userId' in inputdata:
            if inputdata["userId"] != "":
                UserId = inputdata["userId"]
                WhereCondition = WhereCondition + " and userId = '"+str(UserId)+"'"

        orderby = " DateCreate "

        if 'startlimit' in inputdata:
            if inputdata["startlimit"] != "":
                startlimit = str(inputdata["startlimit"])
        if 'endlimit' in inputdata:
            if inputdata["endlimit"] != "":
                endlimit = str(inputdata["endlimit"])

        column = "title,imagePath,summary,description,MobileToken,userId,userName,date_format(CONVERT_TZ(dateCreate,'+00:00','+05:30'),'%Y-%m-%d %H:%i:%s')DateCreate" 

        data= databasefile.SelectQueryOrderby("userNotification ",column,WhereCondition,"",startlimit,endlimit,orderby)        
        count = databasefile.SelectCountQuery("userNotification","","")
        data["totalnotification"]=count

        if data !=0 :
            return data
        else:
            return commonfile.Errormessage()

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 


@app.route('/updateMobileToken', methods=['POST'])
def updateMobileToken():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userId','MobileToken']
        print(inputdata,"B")
        commonfile.writeLog("updatePassword",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userId=str(inputdata["userId"])
            MobileToken=str(inputdata["MobileToken"])
         
            column="MobileToken='" + MobileToken+ "'"
            whereCondition= "  and UserId = '" + str(userId)+ "' "
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


@app.route('/updateWebToken', methods=['POST'])
def updateWebToken():
    try:
        inputdata =  commonfile.DecodeInputdata(request.get_data())
        startlimit,endlimit="",""
        keyarr = ['userId','WebToken']
        print(inputdata,"B")
        commonfile.writeLog("updatePassword",inputdata,0)
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        if msg =="1":
            userId=str(inputdata["userId"])
            WebToken=str(inputdata["WebToken"])
         
            column="WebToken='" + WebToken+ "'"
            whereCondition= "  and UserId = '" + str(userId)+ "' "
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



@app.route('/Login11', methods=['GET'])
def login122():
    try:
        password = request.args['password']
       
        mobile = request.args['email']
        WebToken = request.args['WebToken']
        MobileToken = request.args['MobileToken']

        column=  "us.profilePic,us.mobileNo,us.userName,us.email,um.id as userTypeId,us.userId as userId,us.status as status"
        whereCondition= " and us.email = '" + mobile + "' and us.password = '" + password + "'  and  us.userTypeId=um.id "
        groupby,startlimit,endlimit="","",""
        loginuser=databasefile.SelectQuery("userMaster as us,userTypeMaster as um",column,whereCondition, groupby,startlimit,endlimit)
        
               
      
        if  (loginuser["status"]!="false"): 
            if loginuser["result"][0]["profilePic"]==None:
                loginuser["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+"/profilePic/profilePic.jpg"
            else:
                loginuser["result"][0]["profilePic"]=str(ConstantData.GetBaseURL())+str(loginuser["result"][0]["profilePic"])

            if loginuser["result"][0]["status"]== 0:
                Data = {"status":"true","message":"","result":loginuser["result"]} 
                return Data
            if loginuser["result"][0]["status"]== 2:
                Data = {"status":"false","message":"your account is Deactivated by admin","result":""} 
                return Data
            if MobileToken !=None:
                databasefile.UpdateQuery("userMaster as us,userTypeMaster as um","MobileToken = '"+str(MobileToken)+"'",WhereCondition)
            if WebToken !=None:
                databasefile.UpdateQuery("userMaster as us,userTypeMaster as um","WebToken = '"+str(WebToken)+"'",WhereCondition)
                

            else:
                Data = {"status":"False","message":"Till Now your account is not approved By admin, after admin approval you can access your account","result":""} 
                return Data
        else:
            data = {"status":"false","message":" Your Email or Password is Wrong, Try Again","result":""}
            return data

    except KeyError as e:
        print("Exception---->" +str(e))        
        output = {"status":"false","message":"No Data Found","result":""}
        return output 
    
    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"status":"false","message":"something went wrong","result":""}
        return output 


if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5031,debug=True)
    
