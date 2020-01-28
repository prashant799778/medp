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


from flask import Flask, render_template
app = Flask(__name__)
app.config['SECRET_KEY'] = 'secret!'

@app.route('/login', methods=['GET'])
def login1():
    try:
        # userid = request.args['userid']
        password = request.args['password']
        name = request.args['name']
        Name=name
        
             
          
        query ="select  um.Email,um.ID,um.name as name,us.Usertype as Usertype,um.Usertype_Id as Usertype_Id from userMaster  as um,Usertype_master as us  where um.Usertype_Id=us.ID and  um.Email = '" + name + "' and password='" + password + "' and um.Status<>'2' ;"   
        conn=Connection()
        cursor = conn.cursor()
        cursor.execute(query)
        loginuser = cursor.fetchall()
                            
            return data

    
    except KeyError as e:
        print("Exception---->" +str(e))        
        output = {"result":"Input Keys are not Found","status":"false"}
        return output 
    
    except Exception as e :
        print("Exception---->" +str(e))           
        output = {"result":"something went wrong","status":"false"}
        return output


if __name__ == "__main__":
    CORS(app, support_credentials=True)
    app.run(host='0.0.0.0',port=5090,debug=True)
    
