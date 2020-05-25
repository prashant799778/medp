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
"Authorization":"key=AAAACCftdfM:APA91bG2v1DlrKLfDDNfso43UuaDXkzy2FZb-zgVY4rw1K3P3zSNn0dMbfpUfUDxfaGs_qu6kG0N2C6Rmw9c8v2BdhQQE3wR24BwZTHEjM50AU5xyFlNjQ_gF5gmUPD56R1GMQWszA-r"}
data ={ 
"to":"", 

"result":{}
}
    
