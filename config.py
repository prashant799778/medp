from flask import request
from datetime import datetime
import json
import uuid
import pymysql
from flask import Flask, send_from_directory, abort                
from flask_cors import CORS


def Connection():
    connection = pymysql.connect(host='localhost',
                                user='root',
                                password='OHAvgawzUw##32',
                                db='medParliament',
                                charset='utf8mb4',
                                cursorclass=pymysql.cursors.DictCursor)

    #cursor = connection.cursor()
    return connection



URL = "https://fcm.googleapis.com/fcm/send"  # request url

headers = {  
"Content-Type": "application/json",
"Authorization":"key=AAAAiJLwYcw:APA91bGlz5tuH5hPhSn0Gedr9Ffsrvt5iT06wWe_nE6HfWj9hw3ZAq8Qn6yacJaNvxIIArzOEoua991H8XM6v2-5UiCNrq3j8yGWEE-yNl2F3lH4Mvm_HuXDkqu9VyblpuCTnumikyjS"}
data ={ 
"to":"", 
"notification" : {
"body" : "New announcement assigned",
"OrganizationId":"2",
"content_available" : "true",
"priority" : "high",
"subtitle":"welcome to MedParliament",
"Title":"hello User"
}
}    
