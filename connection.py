
import pymysql

def DBconnection():
    mysqlcon = pymysql.connect(host='localhost',
                            user='medparliament',
                            password='Medp23$@873',
                            db='medParliament',
                            charset='utf8mb4',
                            cursorclass=pymysql.cursors.DictCursor)
    
    return mysqlcon

