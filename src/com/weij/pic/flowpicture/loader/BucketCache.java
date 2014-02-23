package com.weij.pic.flowpicture.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.auth.BCSSignCondition;
import com.baidu.inf.iis.bcs.http.HttpMethodName;
import com.baidu.inf.iis.bcs.model.BCSClientException;
import com.baidu.inf.iis.bcs.model.BCSServiceException;
import com.baidu.inf.iis.bcs.model.BucketSummary;
import com.baidu.inf.iis.bcs.model.DownloadObject;
import com.baidu.inf.iis.bcs.model.Empty;
import com.baidu.inf.iis.bcs.model.ObjectListing;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.model.ObjectSummary;
import com.baidu.inf.iis.bcs.model.Resource;
import com.baidu.inf.iis.bcs.model.SuperfileSubObject;
import com.baidu.inf.iis.bcs.model.X_BS_ACL;
import com.baidu.inf.iis.bcs.policy.Policy;
import com.baidu.inf.iis.bcs.policy.PolicyAction;
import com.baidu.inf.iis.bcs.policy.PolicyEffect;
import com.baidu.inf.iis.bcs.policy.Statement;
import com.baidu.inf.iis.bcs.request.CreateBucketRequest;
import com.baidu.inf.iis.bcs.request.GenerateUrlRequest;
import com.baidu.inf.iis.bcs.request.GetObjectRequest;
import com.baidu.inf.iis.bcs.request.ListBucketRequest;
import com.baidu.inf.iis.bcs.request.ListObjectRequest;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.request.PutSuperfileRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class BucketCache {

	private static final String TAG = "BucketCache";
	// ----------------------------------------
	static String host = "bcs.duapp.com";
	static String accessKey = "DW5VZ6dMQuw8aDXpUcZt1rta";
	static String secretKey = "SndpN2l4y00OiUtU3wL4QGAcUqrPiQ8B";
	static String bucket = "flowpicturebucket";
	// ----------------------------------------
	static String object = "/first-object";
	static File destFile = new File("c:/test");
	
	public static void putObjectToDestFile(String obj,File dest)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		try {
			getObjectWithDestFile(baiduBCS, obj, dest);
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
	}
	
	public static InputStream getInputStreamByObject(String obj)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		InputStream is = null;
		try {
			is = getDownloadObject(baiduBCS,obj).getContent();;
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return is;
	}
	
	public static Map<String, InputStream> listObjectByDir(String dir)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		String prefix = dir;
		Map<String, InputStream> map = new HashMap<String, InputStream>();
		List<ObjectSummary> list = null;
		try {
			list = listOnlyObject(baiduBCS,prefix);
		for (ObjectSummary os : list) {
			String key = os.getName();
			InputStream is = getDownloadObject(baiduBCS,key).getContent();;
			map.put(key, is);
			
		}
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, Long> listOnlyObjectByDir(String dir)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		String prefix = dir;
		Map<String, Long> map = new HashMap<String, Long>();
		List<ObjectSummary> list = null;
		try {
			list = listOnlyObject(baiduBCS,prefix);
		for (ObjectSummary os : list) {
			String key = os.getName();
			Long l = os.getSize();
			map.put(key, l);
			
		}
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static Map<String, InputStream> listFirstDir(String dir)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		String prefix = dir;
		Map<String, InputStream> map = new HashMap<String, InputStream>();
		List<ObjectSummary> list = null;
		try {
			list = listObject(baiduBCS,prefix);
		for (ObjectSummary os : list) {
			String prefix1 = os.getName();
			String key = os.getName();
			List<ObjectSummary> list1 = listObject(baiduBCS,prefix1);
			InputStream is = null;
			for (ObjectSummary os1 : list1){
				if(os1.isDir()){
					key = key.concat(os1.getName().substring(prefix1.length()));				
				}else{
					is = getDownloadObject(baiduBCS,os1.getName()).getContent();
				}
			}
			map.put(key, is);
			
		}
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> listOnlyDir1(String dir)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		String prefix = dir;
		Map<String, String> map = new HashMap<String, String>();
		List<ObjectSummary> list = null;
		try {
			list = listObject(baiduBCS,prefix);
		for (ObjectSummary os : list) {
			String prefix1 = os.getName();
			String key = os.getName();
			List<ObjectSummary> list1 = listObject(baiduBCS,prefix1);
			String is = "";
			for (ObjectSummary os1 : list1){
				if(os1.isDir()){
					is = is.concat(os1.getName().substring(prefix1.length()));				
				}else{
					key = key.concat(os1.getName().substring(prefix1.length()));
				}
			}
			map.put(key, is);
			
		}
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, Integer> listOnlyDir2(String dir)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		String prefix = dir;
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<ObjectSummary> list = null;
		try {
			list = listObject(baiduBCS,prefix);
		for (ObjectSummary os : list) {
			String prefix1 = os.getName();
			String key = os.getName();
			List<ObjectSummary> list1 = listObject(baiduBCS,prefix1);
			Integer is = list1.size();
			if(is>0) key = key.concat(list1.get(0).getName().substring(prefix1.length()));
			map.put(key, is);
			
		}
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, InputStream> listSecondDir(String dir)
	{
		BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
		BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
		
		// baiduBCS.setDefaultEncoding("GBK");
		baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8
		String prefix = dir;
		Map<String, InputStream> map = new HashMap<String, InputStream>();
		List<ObjectSummary> list = null;
		try {
			list = listObject(baiduBCS,prefix);
		for (ObjectSummary os : list) {
			String prefix1 = os.getName();
			String key = os.getName();
			List<ObjectSummary> list1 = listObject(baiduBCS,prefix1);
			InputStream is = null;
			for (ObjectSummary os1 : list1){
				if(!os1.isDir()){
					is = getDownloadObject(baiduBCS,os1.getName()).getContent();
					break;
				}
			}
			map.put(key, is);
			
		}
		} catch (BCSServiceException e) {
			Log.i(TAG,"Bcs return:" + e.getBcsErrorCode() + ", " + e.getBcsErrorMessage() + ", RequestId=" + e.getRequestId());
		} catch (BCSClientException e) {
			e.printStackTrace();
		}
		return map;
	}

	public static void generateUrl(BaiduBCS baiduBCS) {
		GenerateUrlRequest generateUrlRequest = new GenerateUrlRequest(HttpMethodName.GET, bucket, object);
		generateUrlRequest.setBcsSignCondition(new BCSSignCondition());
		generateUrlRequest.getBcsSignCondition().setIp("1.1.1.1");
		generateUrlRequest.getBcsSignCondition().setTime(123455L);
		generateUrlRequest.getBcsSignCondition().setSize(123455L);
		System.out.println(baiduBCS.generateUrl(generateUrlRequest));
	}

	public static void copyObject(BaiduBCS baiduBCS, String destBucket, String destObject) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("image/jpeg");
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject), objectMetadata);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject), null);
		baiduBCS.copyObject(new Resource(bucket, object), new Resource(destBucket, destObject));
	}

	private static void createBucket(BaiduBCS baiduBCS) {
		// baiduBCS.createBucket(bucket);
		baiduBCS.createBucket(new CreateBucketRequest(bucket, X_BS_ACL.PublicRead));
	}

	private static void deleteBucket(BaiduBCS baiduBCS) {
		baiduBCS.deleteBucket(bucket);
	}

	public static void deleteObject(BaiduBCS baiduBCS) {
		Empty result = baiduBCS.deleteObject(bucket, object).getResult();
		Log.i(TAG,result.toString());
	}

	private static void getBucketPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getBucketPolicy(bucket);

		Log.i(TAG,"After analyze: " + response.getResult().toJson());
		Log.i(TAG,"Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	public static void getObjectMetadata(BaiduBCS baiduBCS) {
		ObjectMetadata objectMetadata = baiduBCS.getObjectMetadata(bucket, object).getResult();
		Log.i(TAG,objectMetadata.toString());
	}

	private static void getObjectPolicy(BaiduBCS baiduBCS) {
		BaiduBCSResponse<Policy> response = baiduBCS.getObjectPolicy(bucket, object);
		Log.i(TAG,"After analyze: " + response.getResult().toJson());
		Log.i(TAG,"Origianal str: " + response.getResult().getOriginalJsonStr());
	}

	private static void getObjectWithDestFile(BaiduBCS baiduBCS) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, object);
		baiduBCS.getObject(getObjectRequest, destFile);
	}

	private static void getObjectWithDestFile(BaiduBCS baiduBCS,String obj,File dest) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, obj);
		baiduBCS.getObject(getObjectRequest, dest);
	}

	private static DownloadObject getDownloadObject(BaiduBCS baiduBCS,String obj) {
		GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, obj);
		return baiduBCS.getObject(getObjectRequest).getResult();
	}

	private static void listBucket(BaiduBCS baiduBCS) {
		ListBucketRequest listBucketRequest = new ListBucketRequest();
		BaiduBCSResponse<List<BucketSummary>> response = baiduBCS.listBucket(listBucketRequest);
		for (BucketSummary bucket : response.getResult()) {
			Log.i(TAG,bucket.toString());
		}
	}

	private static List<ObjectSummary> listObject(BaiduBCS baiduBCS,String prefix) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		// ------------------by dir
		{
			// prefix must start with '/' and end with '/'
			listObjectRequest.setPrefix(prefix);
			listObjectRequest.setListModel(2);
		}
		// ------------------only object
		{
			// prefix must start with '/'
			// listObjectRequest.setPrefix("/1/");
		}
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		return response.getResult().getObjectSummaries();
	}

	private static List<ObjectSummary> listOnlyObject(BaiduBCS baiduBCS,String prefix) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		// ------------------by dir
		{
			// prefix must start with '/' and end with '/'
			listObjectRequest.setPrefix(prefix);
			//listObjectRequest.setListModel(2);
		}
		// ------------------only object
		{
			// prefix must start with '/'
			// listObjectRequest.setPrefix("/1/");
		}
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		return response.getResult().getObjectSummaries();
	}

	private static void listObject(BaiduBCS baiduBCS) {
		ListObjectRequest listObjectRequest = new ListObjectRequest(bucket);
		listObjectRequest.setStart(0);
		listObjectRequest.setLimit(20);
		// ------------------by dir
		{
			// prefix must start with '/' and end with '/'
			//listObjectRequest.setPrefix("/picture/");
			//listObjectRequest.setListModel(2);
		}
		// ------------------only object
		{
			// prefix must start with '/'
			// listObjectRequest.setPrefix("/1/");
		}
		BaiduBCSResponse<ObjectListing> response = baiduBCS.listObject(listObjectRequest);
		Log.i(TAG,"we get [" + response.getResult().getObjectSummaries().size() + "] object record.");
		for (ObjectSummary os : response.getResult().getObjectSummaries()) {
			
			Log.i(TAG,os.toString());
		}
	}

	private static void putBucketPolicyByPolicy(BaiduBCS baiduBCS) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + "/111").addResource(bucket + "/111");
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putBucketPolicy(bucket, policy);
	}

	private static void putBucketPolicyByX_BS_ACL(BaiduBCS baiduBCS, X_BS_ACL acl) {
		baiduBCS.putBucketPolicy(bucket, acl);
	}

	public static void putObjectByFile(BaiduBCS baiduBCS) {
		PutObjectRequest request = new PutObjectRequest(bucket, object, createSampleFile());
		ObjectMetadata metadata = new ObjectMetadata();
		// metadata.setContentType("text/html");
		request.setMetadata(metadata);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
		ObjectMetadata objectMetadata = response.getResult();
		Log.i(TAG,"x-bs-request-id: " + response.getRequestId());
		Log.i(TAG,objectMetadata.toString());
	}

	public static void putObjectByInputStream(BaiduBCS baiduBCS) throws FileNotFoundException {
		File file = createSampleFile();
		InputStream fileContent = new FileInputStream(file);
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html");
		objectMetadata.setContentLength(file.length());
		PutObjectRequest request = new PutObjectRequest(bucket, object, fileContent, objectMetadata);
		ObjectMetadata result = baiduBCS.putObject(request).getResult();
		Log.i(TAG,result.toString());
	}

	private static void putObjectPolicyByPolicy(BaiduBCS baiduBCS) {
		Policy policy = new Policy();
		Statement st1 = new Statement();
		st1.addAction(PolicyAction.all).addAction(PolicyAction.get_object);
		st1.addUser("zhengkan").addUser("zhangyong01");
		st1.addResource(bucket + object).addResource(bucket + object);
		st1.setEffect(PolicyEffect.allow);
		policy.addStatements(st1);
		baiduBCS.putObjectPolicy(bucket, object, policy);
	}

	private static void putObjectPolicyByX_BS_ACL(BaiduBCS baiduBCS, X_BS_ACL acl) {
		baiduBCS.putObjectPolicy(bucket, object, acl);
	}

	public static void putSuperfile(BaiduBCS baiduBCS) {
		List<SuperfileSubObject> subObjectList = new ArrayList<SuperfileSubObject>();
		// 0
		BaiduBCSResponse<ObjectMetadata> response1 = baiduBCS.putObject(bucket, object + "_part0", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part0", response1.getResult().getETag()));
		// 1
		BaiduBCSResponse<ObjectMetadata> response2 = baiduBCS.putObject(bucket, object + "_part1", createSampleFile());
		subObjectList.add(new SuperfileSubObject(bucket, object + "_part1", response2.getResult().getETag()));
		// put superfile
		PutSuperfileRequest request = new PutSuperfileRequest(bucket, object + "_superfile", subObjectList);
		BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putSuperfile(request);
		ObjectMetadata objectMetadata = response.getResult();
		Log.i(TAG,"x-bs-request-id: " + response.getRequestId());
		Log.i(TAG,objectMetadata.toString());
	}

	public static void setObjectMetadata(BaiduBCS baiduBCS) {
		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType("text/html12");
		baiduBCS.setObjectMetadata(bucket, object, objectMetadata);
	}

	private static File createSampleFile() {
		try {
			File file = File.createTempFile("java-sdk-", ".txt");
			file.deleteOnExit();

			Writer writer = new OutputStreamWriter(new FileOutputStream(file));
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.write("01234567890123456789\n");
			writer.close();

			return file;
		} catch (IOException e) {
			Log.e(TAG,"tmp file create failed.");
			return null;
		}
	}
}
