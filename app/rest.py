import imp
import pymysql
from app import app
from db import mysql
from flask import jsonify,request
from datetime import datetime

@app.route('/')
def test():
    messsage = {"Success":"Flask app running successfully"}
    resp = jsonify(messsage)
    resp.status_code = 200
    return resp    



@app.route('/test', methods=['POST'])
def tests():
    try:
        _json = request.json
        _name = _json['name']
        _date = _json['date']
        _location = _json['location']
        # save edits
        sql = "INSERT INTO pers_test(pers_name,pers_location) VALUES(%s, %s)"
        data = (_name,_location,)
        conn = mysql.connect()
        cursor = conn.cursor()
        cursor.execute(sql,data)
        conn.commit()
        resp = jsonify('Persistence added successfully!')
        resp.status_code = 200
        return resp
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()
    

@app.route('/addpersistence', methods=['POST'])
def add_persistence():
    try:
        _json = request.json
        _name = _json['name']
        _date = _json['date']
        _location = _json['location']
        if not _date:
            _date = datetime.now()
            _date = _date.strftime('%Y-%m-%d %H:%M:%S')
            
        # validate the received values
        if _name  and _location and request.method == 'POST':
            # save edits
            sql = "INSERT INTO pers_test(pers_name, pers_date, pers_location) VALUES(%s, %s, %s)"
            data = (_name, _date, _location,)
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(sql, data)
            conn.commit()
            resp = jsonify('Persistence added successfully!')
            resp.status_code = 200
            return resp
        else:
            return not_found()
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()
  
		
@app.route('/persistences')
def get_all_persistences():
	try:
		conn = mysql.connect()
		cursor = conn.cursor(pymysql.cursors.DictCursor)
		cursor.execute("SELECT * FROM pers_test")
		rows = cursor.fetchall()
		resp = jsonify(rows)
		resp.status_code = 200
		return resp
	except Exception as e:
		print(e)
	finally:
		cursor.close() 
		conn.close()  

@app.route('/persistence/<int:id>')
def get_persistence_data(id):
	try:
		conn = mysql.connect()
		cursor = conn.cursor(pymysql.cursors.DictCursor)
		cursor.execute("SELECT * FROM pers_test WHERE pers_id=%s", id)
		row = cursor.fetchone()
		resp = jsonify(row)
		resp.status_code = 200
		return resp
	except Exception as e:
		print(e)
	finally:
		cursor.close() 
		conn.close()

@app.route('/updatepersistence', methods=['POST'])
def update_persistence():
    try:
        _json = request.json
        _id = _json['id']
        _name = _json['name']
        _date = _json['date']
        _location = _json['location']
        if not _date:
            _date = datetime.now()
            _date = _date.strftime('%Y-%m-%d %H:%M:%S')		
        # validate the received values
        if _name and _location and _id and request.method == 'POST':
            #do not save password as a plain text
            # save edits
            sql = "UPDATE pers_test SET pers_name=%s, pers_date=%s, pers_location=%s WHERE pers_id=%s"
            data = (_name,_date, _location, _id,)
            conn = mysql.connect()
            cursor = conn.cursor()
            cursor.execute(sql, data)
            conn.commit()
            resp = jsonify('Persistence updated successfully!')
            resp.status_code = 200
            return resp
        else:
            return not_found()
    except Exception as e:
        print(e)
    finally:
        cursor.close() 
        conn.close()

@app.route('/deletepersistence/<int:id>')
def delete_persistence(id):
	try:
		conn = mysql.connect()
		cursor = conn.cursor()
		cursor.execute("DELETE FROM pers_test WHERE pers_id=%s", (id,))
		conn.commit()
		resp = jsonify('Persistence deleted successfully!')
		resp.status_code = 200
		return resp
	except Exception as e:
		print(e)
	finally:
		cursor.close() 
		conn.close()

@app.errorhandler(404)
def not_found(error=None):
    message = {
        'status': 404,
        'message': 'Not Found: ' + request.url,
    }
    resp = jsonify(message)
    resp.status_code = 404
    return resp

if __name__ == "__main__":
    app.run(debug=True,host='0.0.0.0')
