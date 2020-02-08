

# create news by admin
@app.route('/news1', methods=['POST'])
def news():

    try:
       
        inputdata = request.form.get('news')    
        inputdata = json.loads(inputdata) 
        print("newsdata",inputdata)
        commonfile.writeLog("news",inputdata,0)
        keyarr = ["newsTitle","userTypeId","summary","newsDesc"]           
        msg = commonfile.CheckKeyNameBlankValue(keyarr,inputdata)
        
        if msg == "1":
            if "newsTitle" in inputdata:
                if inputdata['newsTitle'] != "":
                    newsTitle =inputdata["newsTitle"]
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "summary" in inputdata:
                if inputdata['summary'] != "":
                    summary =inputdata["summary"]
            
            if "newsDesc" in inputdata:
                if inputdata['newsDesc'] != "":
                    newsDesc =inputdata["newsDesc"]
            
            
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
            if "UserId" in inputdata:
                if inputdata['UserId'] != "":
                    UserId =inputdata["UserId"]
                column = "newsTitle,userTypeId,imagePath,summary,newsDesc,UserCreate"
                values = " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) + "','" + str(UserId) + "'"
                data = databasefile.InsertQuery("news",column,values)        
            else:
                column = "newsTitle,userTypeId,imagePath,summary,newsDesc"
                values = " '"+ str(newsTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(summary) +"','" + str(newsDesc) +  "'"
                data = databasefile.InsertQuery("news",column,values)

            if data !=0 :                
                return data
            else:
                return commonfile.Errormessage()
        else:
            return msg

    except Exception as e:
        print("Exception--->" + str(e))                                  
        return commonfile.Errormessage() 



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
        
            if "videoLink" in inputdata:
                if inputdata['videoLink'] != "":
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
                column = " imagePath,UserCreate"
                values = " '"+ str(ImagePath)+ "','" + str(userId) + "'"
                data = databasefile.InsertQuery("gallery",column,values)        
            else:
                column = " imagePath "
                values = " '"+ str(ImagePath)+  "'"
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

            print("22222222222222")
            if "eventTitle" in inputdata:
                if inputdata['eventTitle'] != "":
                    eventTitle =inputdata["eventTitle"]
            if "userTypeId" in inputdata:
                if inputdata['userTypeId'] != "":
                    userTypeId =inputdata["userTypeId"]
        
            if "eventSummary" in inputdata:
                if inputdata['eventSummary'] != "":
                    eventSummary =inputdata["eventSummary"]
            
            if "eventLocation" in inputdata:
                if inputdata['eventLocation'] != "":
                    eventLocation =inputdata["eventLocation"]
            
            if "eventDate" in inputdata:
                if inputdata['eventDate'] != "":
                    eventDate =inputdata["eventDate"]
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
                column = "eventTitle,userTypeId,imagePath,eventSummary,eventLocation,eventDate,UserCreate"
                values = " '"+ str(eventTitle) +"','" + str(userTypeId)+"','" + str(ImagePath)+"','" + str(eventSummary) +"','" + str(eventLocation) + "','" + str(eventDate) + "','" + str(UserId) + "'"
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









































