package cn.ms.util;

import com.alibaba.fastjson.JSON;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongodbUtil {
	
	public static DB getDb() {
		DB db = null;
		try {
			// 连接到mongodb
			Mongo mongo = new Mongo("localhost", 27017);
			// 打开数据库test
			db = mongo.getDB("test");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return db;
	}
	
	/**
	 * 将实体类转成mongodb对象
	 * @param obj
	 * @return
	 */
	public static DBObject toDBObject(Object obj) {
		String json = JSON.toJSONString(obj);
		DBObject d = (DBObject) com.mongodb.util.JSON.parse(json);
		return d;
	}
}
