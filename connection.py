
import pymysql

def DBconnection():
    mysqlcon = pymysql.connect(host='localhost',
                            user='root',
                            password='OHAvgawzUw##32',
                            db='FandomLive',
                            charset='utf8mb4',
                            cursorclass=pymysql.cursors.DictCursor)
    
    return mysqlcon

