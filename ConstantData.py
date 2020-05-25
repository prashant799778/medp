

import config  
import requests
import commonfile
import json
from pyfcm import FCMNotification



def GetBaseURL():
 
    path = "http://134.209.153.34:5031"
    return path

def getwebBaseurl():
 
    path = "http://134.209.153.34:4200"
    return path


def GetdefaultNotificationImage():
 
    path = "/notificationimages/default.jpg"
    return path

def GetdefaultNewsImage():
 
    path = "/newsimages/default.jpg"
    return path

def Getdefaultimage():
 
    path = "/CampImages/default.jpg"
    return path

def GetRewardDefaultimage():
 
    path = "/RewardImage/default.jpg"
    return path

def GetSecurityDefaultimage():
 
    path = "/SecurityImage/default.jpg"
    return path

def sendNotification(DeviceToken,title,description,summary,UserName,result):
    try:
        push_service = FCMNotification(api_key=config.FCM_KEY)
        registration_id = str(DeviceToken)
        result = push_service.single_device_data_message(registration_id=registration_id, data_message=json.dumps(result))
    except Exception as e :
        print("Exception--->" + str(e)) 
        return commonfile.Errormessage() 

def userNotification(DeviceToken,title,description,summary,UserName,result):
    try:
        config.data['to'] = str(DeviceToken)
        # config.data['subtitle'] = "Dear ,"+str(UserName)+" title  "+str(title)+" description "+str(description)+" summary"+str(summary)+" "
        config.data['result']=result
        print(config.data)        
        r=requests.post(config.URL, headers=config.headers, data=json.dumps(config.data))
        response=json.loads(r.text) 
        if response:
            return response
        else:
            return commonfile.Errormessage()
    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()



def newmessage(DeviceToken,comment,adminName,UserName):
    try:
        config.data['to'] = str(DeviceToken)
        config.data['subtitle'] = "Dear ,"+str(UserName)+" you got new message  from "+str(adminName)+" message "+str(comment)+" "

        print(config.data)        
        r=requests.post(config.URL, headers=config.headers, data=json.dumps(config.data))
        response=json.loads(r.text) 
        if response:
            return response
        else:
            return commonfile.Errormessage()
    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()




def newmessage1(DeviceToken,comment,adminName,UserName):
    try:
        config.data['to'] = str(DeviceToken)
        config.data['subtitle'] = "Dear ,"+str(adminName)+" you got new message  from "+str(UserName)+" message"+str(comment)+" "
        print(config.data)        
        r=requests.post(config.URL, headers=config.headers, data=json.dumps(config.data))
        response=json.loads(r.text) 
        if response:
            return response
        else:
            return commonfile.Errormessage()
    except Exception as e :
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage()

def GetSecurityDocumentPath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/SecurityDocuments/"+filename
    return path

#folder path to save image
def GetCampaignImagePath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/CampImages/"+filename
    return path

def getforumCommentImagePath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/forumCommentImage/"+filename
    return path

def getforumCommentReplyImagePath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/forumCommentReplyImage/"+filename
    return path


#folder path to save image
def GetPostImagePath(filename):

    path = "/var/www/medParliament/backend/med_parliament/postImage/"+filename
    return path

def getSecurityCampaignUpdatePath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/securityCampaignUpdate/"+filename
    return path

def getNotificationPath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/notificationimages/"+filename
    return path

def getNewsPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/newsimages/"+filename
    return path


def getNotificationPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/notificationimages/"+filename
    return path    


def GetdefaultNotificationImage():
 
    path = "/notificationimages/default.jpg"
    return path    


def getMarketingInsightsPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/marketingInsights/"+filename
    return path


def getDashboard(filename):

    path = "/var/www/medParliament/backend/med_parliament/dashboard/"+filename
    return path 




def getUpSkillsOpportunity(filename):

    path = "/var/www/medParliament/backend/med_parliament/UpSkillsOpportunity/"+filename
    return path    


def getAnnouncementsPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/announcementsImage/"+filename
    return path

def getAnnouncementsVideoPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/announcementsVideoLink/"+filename
    return path

def getGalleryPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/gallery/"+filename
    return path


def getourPartners(filename):

    path = "/var/www/medParliament/backend/med_parliament/ourPartners/"+filename
    return path



def getuserContent(filename):

    path = "/var/www/medParliament/backend/med_parliament/contentimages/"+filename
    return path         


def getpromisingEvent(filename):

    path = "/var/www/medParliament/backend/med_parliament/promisingEvent/"+filename
    return path

def getSignUpVideo(filename):

    path = "/var/www/medParliament/backend/med_parliament/signUpImage/"+filename
    return path


def getEventPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/eventImages/"+filename
    return path

def GetsecurityCampaignImagePath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/SecurityImage/"+filename
    return path
    
def GetProfilePicPath(filename):

    path = "/var/www/medParliament/backend/med_parliament/profilePic/"+filename
    return path

def GetTeamPicPath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/TeamLogo/"+filename
    return path

def GetArtistPicPath(filename):

    path = "/var/www/FandomLive/backend/FandomLive/Api/ArtistImage/"+filename
    return path
    
def GetDeviceTypeId(DeviceType):
 
    values = {
        "MOBILE" :1,
        "DESKTOP" :2,
        "TAB" :3,
        "IPHONE" :4,
        "OTHER" :5
    }
    return values.get(DeviceType, 4)

def artistType(id):
 
    values = {
        "1" :"Singer",
        "2" :"Dance",
        "3" :"Speech",
        "4" :"other"
    }
    return values.get(id, 4)

def GetGender(Gender):
    values = {
        "MALE" :1,
        "FEMALE" :2,
        "OTHER" :3
    }
    return values.get(Gender, Gender)
    
def GetSocialMediaId(SocialMediaName):
    values = {
        "GMAIL" :1,
        "FACEBOOK" :2,
        "INSTAGRAM" :3,
        "EMAIL" :4,
        "OTHER":5
    }
    return values.get(SocialMediaName,5)
