package com.cctsort.dwrtest1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;

public class Test2 {
	public static final String DEFAULT_MARK = "userId";//�ỰScriptSession����ݱ�ʶ���������ֲ�ͬ�û�ScriptSession
	public static final String DEFAULT_METHOD_NAME = "showMessage";//javascript�н�����Ϣ�ķ���
	public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static List<String> list = new ArrayList<String>();
	
	static {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				System.out.println("=======��ʼ������Ϣ==========");
				for(String userId: list) {
					//���͸����ĸ���Ϣ�û�
					if(userId != null && userId.startsWith("msgType@")) {
						sendMessageSingle(userId, format.format(new Date()));
						System.out.println("==����==" + userId);
					}
				}
			}
		}, 10000, 1000);
	}
	
	/**
	 * ���������û�����Ҫά��
	 * 
	 * @param userId
	 */
	public void onPageLoad(String userId) {
		//��ȡ�˴λỰ��ScriptSession����
		//ע��һ��HttpSession���ж��ScriptSession������ʹ��Map<HttpSessionId, ScriptSession>
		//��Map<userId, ScriptSession>�����ʹ�ú��ߣ�������ͬһ�û��򿪲�ͬҳ����ղ�ͬ����Ϣ
		//�������Ը�һ���û����Ͳ�ͬ����Ϣ����ͬ��ҳ�棩
		//����ʵ��ScriptSessionListener�࣬ʵ�ֶ�ScriptSession�Ĵ���������
		//�˴���ά��ScriptSession�������涩���û�userId��list�У�ͼ���㣬����ȡ��
		//����û��˳��������list���Ƴ���
		ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
		scriptSession.setAttribute(DEFAULT_MARK, userId);//���ǣ�
		//��¼��list��
		list.add(userId);
		System.out.println("==========�����û�============" + userId);
	}

	/**
	 * ������Ϣ�������û�
	 * 
	 * @param userid
	 * @param message
	 */
	public static void sendMessageSingle(String userid, String message) {
		final String userId = userid;
		final String autoMessage = message;
		Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
			//���ˣ���׼���͸�ָ���û�
			public boolean match(ScriptSession session) {
				if (session.getAttribute(DEFAULT_MARK) == null)//�Ҳ�����ݱ�ʶ������
					return false;
				else
					//����ҵ���Ӧ���û����򷵻�true
					return (session.getAttribute(DEFAULT_MARK)).equals(userId);
			}
		}, new Runnable() {
			private ScriptBuffer script = new ScriptBuffer();
			public void run() {
				//����javascript��showMessage�����������ݴ����ҳ��
				script.appendCall(DEFAULT_METHOD_NAME, autoMessage);
				//���沢���Ƿ��������û����������˹���
				Collection<ScriptSession> sessions = Browser.getTargetSessions();
				for (ScriptSession scriptSession : sessions) {
					scriptSession.addScript(script);
				}
			}
		});
	}
}