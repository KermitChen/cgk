package com.dyst.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class CreateIndex {

	public static void main(String[] args) {
		String serverIp = "192.168.1.241";//es����һ���ڵ��ip
		String name = "elasticsearch";//es��Ⱥ����
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name",name)
				.put("client.transport.sniff", true).build();
		Client client = new TransportClient(settings)
			.addTransportAddress(new InetSocketTransportAddress(serverIp, 9300));
		try {
			createLltj(client);
			createZfyc(client);
			createServerInfo(client);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			client.close();
		}
		
	}
	/**
	 * ��������ͳ��index
	 */
	private static void createLltj(Client client)throws Exception{
		String mapping = "{\"sum\":{\"_all\":{\"enabled\":false},\"properties\":{\"count\":{\"type\":\"long\",\"store\":true}," +
				"\"created_time\":{\"type\":\"date\",\"store\":true,\"format\":\"yyyy-MM-ddHH:mm:ss\"}," +
				"\"jcd\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"lane\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}}}}";
		CreateIndexRequest request= new CreateIndexRequest("lltj").mapping("sum", mapping);
		client.admin().indices().create(request);
		System.out.println("��������ͳ�������ɹ���");
	}
	
	/**
	 * �������ҹ������
	 * @param client
	 * @throws Exception
	 */
	private static void createZfyc(Client client)throws Exception{
		String mapping = "{\"zfyc\":{\"properties\":{\"cphm\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"cqid\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true},\"yccs\":{\"type\":\"long\",\"store\":true}," +
				"\"zfcs\":{\"type\":\"long\",\"store\":true},\"ts\":{\"type\":\"date\",\"store\":true,\"format\":\"yyyy-MM-dd\"}}}}";
		CreateIndexRequest request = new CreateIndexRequest("daily").mapping("zfyc", mapping);
		client.admin().indices().create(request);
		System.out.println("�������ҹ�������ɹ���");
	}
	
	/**
	 * ������������Ϣ����
	 * @param client
	 * @throws Exception
	 */
	private static void createServerInfo(Client client)throws Exception{
		String mapping = "{\"info\":{\"properties\":{\"cpuModel\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"cpuRate\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"cpu_num\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"disk_free\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"disk_size\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"disk_used\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"disk_usedPecent\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"host\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"memBuffers\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"memFree\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"memPecent\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"memTotal\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"memUsed\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"status\":{\"type\":\"string\",\"index\":\"not_analyzed\",\"store\":true}," +
				"\"queryTime\":{\"type\":\"long\",\"store\":true}}}}";
		CreateIndexRequest request = new CreateIndexRequest("server").mapping("info", mapping);
		client.admin().indices().create(request);
		System.out.println("������������Ϣ�����ɹ���");
	}
}
