package com.dyst.activemq.cas;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.dyst.activemq.entities.*;

public class SyncMessage implements Serializable {
	 /**
     * 
     */
    private static final long serialVersionUID = -4995848483963127420L;

    private SyncType type = null;

    private  String keyId = null;
    
    private Object object;
    
    private String clientId = null;	//客户端ID
    
    

	public SyncMessage() {
	}
    
    public SyncMessage(SyncType type) {
		super();
		this.type = type;
	}
    
    public SyncMessage(SyncType type, String keyId, Object object) {
		super();
		this.type = type;
		this.keyId = keyId;
		this.object = object;
	}

	public SyncType getType() {
        return type;
    }

    public void setType(SyncType type) {
        this.type = type;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String userLoginId) {
        this.keyId = userLoginId;
    }

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	public  String toJson(){
		return JsonUtil.toJSON(this);
	}
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public static SyncMessage parseJson(String jsonStr){
		//将字符转换成json对象
		JSONObject jsonObject = JSONObject.parseObject(jsonStr);
		SyncMessage syncMessage = JsonUtil.parseObject(jsonStr, SyncMessage.class);
		String type = jsonObject.getString("type");
//		String keyId = jsonObject.getString("keyId");
		String obj = jsonObject.getString("object");
		Object object = null;
      	if(SyncType.USER_ADD.toString().equals(type)){	//1、保存用户
      		object = (SyncUser) JsonUtil.parseObject(obj, SyncUser.class);
      	}else if(SyncType.USER_DELETE.toString().equals(type)){	//2、删除用户
      		object = (SyncUser) JsonUtil.parseObject(obj, SyncUser.class);
      	}else if(SyncType.DEPT_ADD.toString().equals(type)){	//3、保存部门
      		object = (SyncDept) JsonUtil.parseObject(obj, SyncDept.class);
      	}else if(SyncType.DEPT_DELETE.toString().equals(type)){	//4、删除部门
      		object = (SyncDept) JsonUtil.parseObject(obj, SyncDept.class);
      	}else if(SyncType.USER_DEPT_LINK.toString().equals(type)){	//5、保存用户与部门关系
      		object = JsonUtil.parseList(obj, SyncUserDept.class);
      	}
//      	else if(SyncType.BATCH_INIT_DEPT.toString().equals(type)){	//5、保存用户与部门关系
//      		object = JsonUtil.parseList(obj, SyncDept.class);
//      	}else if(SyncType.BATCH_INIT_USER.toString().equals(type)){	//5、保存用户与部门关系
//      		object = JsonUtil.parseList(obj, SyncUser.class);
//      	}
      	syncMessage.setObject(object);
      	return syncMessage;
	}
}
