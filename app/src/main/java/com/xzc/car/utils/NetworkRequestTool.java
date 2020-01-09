package com.xzc.car.utils;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description:网络请求工具
 *
 * 此Demo博客地址：http://blog.csdn.net/u012814441/article/details/49643865
 * <p/>
 * author:Edward
 * <p/>
 * 2015/10/26
 */
public class NetworkRequestTool {
    private final String CONNECTION_NETWORK_FAILURE = "网络连接失败，请检查网络连接！";
    private final String URL_PARSE_FAILURE = "URL解析错误";
    private final String NETWORK_RESPONSE_FAILURE = "网络响应失败";
    //连接时间，默认（5秒）
    private int connTime = 5;
    //读取时间，默认（5秒）
    private int readTime = 5;
    //返回数据成功
    private int CONNECTION_SUCCEED = 200;
    //编码类型，默认为UTF-8
    private String encodingType = "utf-8";
    //成功获取返回结果值
    private final int GET_RESULT_STRING_SUCCEED = 1;
    //获取返回结果值失败
    private final int GET_RESULT_STRING_FAILURE = 0;
    //网络错误
    private final int ERROR = 2;
    //回调监听接口
    private NetworkCallbackListener networkCallbackListener;
    //线程池变量
    private ExecutorService executorService;
    //是否开启线程池
    private boolean isOpenThreadPool = true;
    //线程变量
    private Thread thread;
    //
    private List<ServerHeaderFieldBean> serverHeaderFieldList;

    //设置编码格式
    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }

    //设置连接时间
    public void setConnTime(int connTime) {
        if (connTime > 0) {
            this.connTime = connTime;
        } else {
            Log.e("------------->", "设置连接时间不能少于0");
        }
    }

    //设置读取时间
    public void setReadTime(int readTime) {
        if (readTime > 0) {
            this.readTime = readTime;
        } else {
            Log.e("------------->", "设置读取时间不能少于0");
        }
    }

    //设置回调接口
    public void setNetworkCallbackListener(NetworkCallbackListener networkCallbackListener) {
        this.networkCallbackListener = networkCallbackListener;
    }

    /**
     * 构造方法
     *
     * @param networkCallbackListener
     */
    public NetworkRequestTool(NetworkCallbackListener networkCallbackListener) {
        this(5, 5, 5, true, networkCallbackListener);
    }

    /**
     * 构造方法
     *
     * @param connTime
     * @param readTime
     * @param threadPoolNumber
     * @param isOpenThreadPool
     * @param networkCallbackListener
     */
    public NetworkRequestTool(int connTime, int readTime, int threadPoolNumber, boolean isOpenThreadPool, NetworkCallbackListener networkCallbackListener) {
        this.connTime = connTime;
        this.readTime = readTime;
        this.isOpenThreadPool = isOpenThreadPool;
        this.networkCallbackListener = networkCallbackListener;
        //初始化线程池（默认为5）
        executorService = Executors.newFixedThreadPool(threadPoolNumber);
    }

    /**
     * get网络请求（异步）没有包含设置请求头参数
     */
    public void getNetworkRequest(String url) {
        getNetworkRequest(url, null);
    }

    /**
     * get网络请求（异步）包含设置请求头参数
     */
    public void getNetworkRequest(String url, HashMap<String, String> setRequestPropertyMap) {
        if (TextUtils.isEmpty(url)) {
            Log.e("---------->", "url不能为空");
            return;
        }
        //是否使用线程池
        if (isOpenThreadPool) {
            executorService.submit(new GetThreadRunnable(url, setRequestPropertyMap));
        } else {
            thread = new Thread(new GetThreadRunnable(url, setRequestPropertyMap));
            //开启一个线程
            thread.start();
        }
    }

    /**
     * post网络请求（异步）
     *
     * @param urlString             访问地址
     * @param requestParamsMap      设置请求参数（字符串）
     * @param jsonBody              设置josn格式参数（字符串）
     * @param setRequestPropertyMap 设置请求头参数
     */
    public void postNetworkRequest(String urlString, HashMap<String, String> requestParamsMap, HashMap<String, String> jsonBody, HashMap<String, String> setRequestPropertyMap) {
        if (TextUtils.isEmpty(urlString)) {
            Log.e("---------->", "url不能为空");
            return;
        }
        //将HashMap的requestParamsMap拼接成一个字符串
        String resultRequestString = combineRequestParams(requestParamsMap);
        //将HashMap的jsonBody拼接成一个字符串。
        String resultJsonString = combineJsonBody(jsonBody);
        //调用
        postNetworkRequest(urlString, resultRequestString, resultJsonString, setRequestPropertyMap);
    }

    /**
     * post网络请求（异步）
     *
     * @param urlString             访问地址
     * @param requestParamsString   设置请求参数（HashMap）
     * @param jsonBody              设置josn格式参数（HashMap）
     * @param setRequestPropertyMap 设置请求头参数
     */
    public void postNetworkRequest(String urlString, String requestParamsString, String jsonBody, HashMap<String, String> setRequestPropertyMap) {
        if (isOpenThreadPool) {
            executorService.submit(new PostThreadRunnable(urlString, requestParamsString, jsonBody, setRequestPropertyMap));
        } else {
            thread = new Thread(new PostThreadRunnable(urlString, requestParamsString, jsonBody, setRequestPropertyMap));
            //开启一个线程
            thread.start();
        }
    }

    /**
     * GET的线程操作
     */
    public class GetThreadRunnable implements Runnable {
        private String urlString;
        private HashMap<String, String> setRequestPropertyMap;

        public GetThreadRunnable(String urlString, HashMap<String, String> setRequestPropertyMap) {
            this.urlString = urlString;
            this.setRequestPropertyMap = setRequestPropertyMap;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                // 忽略缓存
                httpURLConnection.setUseCaches(false);
                //设置读取时间
                httpURLConnection.setReadTimeout(readTime * 1000);
                //设置连接时间
                httpURLConnection.setConnectTimeout(connTime * 1000);
                //提出Post请求
                httpURLConnection.setRequestMethod("GET");

                //设置请求头参数（请求参数必须写在Http正文之内）
                if (setRequestPropertyMap != null) {
                    setRequestProperty(httpURLConnection, setRequestPropertyMap);
                }

                //获取服务器返回Header
                getServerHeaderField(httpURLConnection);

                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == CONNECTION_SUCCEED) {
                    //此处才是发送数据的真正操作
                    InputStream inputStream = httpURLConnection.getInputStream();

                    String responseResult = inputStreamConvertString(inputStream);
                    if (!TextUtils.isEmpty(responseResult)) {
//                        Log.e("----------->网络请求工具最终返回值", responseResult);
                        handler.sendMessage(handler.obtainMessage(GET_RESULT_STRING_SUCCEED, responseResult));
                    } else {
                        handler.sendEmptyMessage(GET_RESULT_STRING_FAILURE);
                    }
                } else {
                    handler.sendMessage(handler.obtainMessage(ERROR, NETWORK_RESPONSE_FAILURE));
                }

            } catch (MalformedURLException e) {
                Log.e("--------------->", "Url字符串解析错误！");
                handler.sendMessage(handler.obtainMessage(ERROR, URL_PARSE_FAILURE));
            } catch (IOException e) {
                Log.e("--------------->", "网络连接失败，请检查网络连接！");
                handler.sendMessage(handler.obtainMessage(ERROR, CONNECTION_NETWORK_FAILURE));
            }
        }
    }

    /**
     * 获取服务器返回的Header（不可覆盖）
     *
     * @param httpURLConnection
     */
    private void getServerHeaderField(HttpURLConnection httpURLConnection) {
        //实例化
        serverHeaderFieldList = new ArrayList<>();
        String key = null;
        for (int i = 1; (key = httpURLConnection.getHeaderFieldKey(i)) != null; i++) {
            ServerHeaderFieldBean searverHeaderFieldBean = new ServerHeaderFieldBean();

            searverHeaderFieldBean.setKey(key);
            searverHeaderFieldBean.setValue(httpURLConnection.getHeaderField(key));
            Log.e("----->服务器HeaderField", key + ":" + httpURLConnection.getHeaderField(key));

            serverHeaderFieldList.add(searverHeaderFieldBean);
        }
    }

    /**
     * POST的线程操作
     */
    public class PostThreadRunnable implements Runnable {
        private String urlString;
        private String requestParamsString;
        private String jsonBody;
        private HashMap<String, String> setRequestPropertyMap;

        public PostThreadRunnable(String urlString, String requestParamsString, String jsonBody, HashMap<String, String> setRequestPropertyMap) {
            this.urlString = urlString;
            this.jsonBody = jsonBody;
            this.requestParamsString = requestParamsString;
            this.setRequestPropertyMap = setRequestPropertyMap;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(urlString);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                // 忽略缓存
                httpURLConnection.setUseCaches(false);
                //设置读取时间
                httpURLConnection.setReadTimeout(readTime * 1000);
                //设置连接时间
                httpURLConnection.setConnectTimeout(connTime * 1000);
                //提出Post请求
                httpURLConnection.setRequestMethod("POST");

                //设置请求头参数（请求参数必须写在Http正文之内）
                if (setRequestPropertyMap != null) {
                    setRequestProperty(httpURLConnection, setRequestPropertyMap);
                }

                //开启一个输出流（getOutputStream方法已经在里面调用connect方法）
                DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                //设置请求参数
                if (requestParamsString != null) {
                    dataOutputStream.write(requestParamsString.getBytes(encodingType));
                }

                //设置实体JSON参数
                if (jsonBody != null) {
                    dataOutputStream.write(jsonBody.getBytes(encodingType));
                    Log.e("------------>设置JSON:", jsonBody);
                }

                dataOutputStream.flush();
                dataOutputStream.close();

                //获取服务器返回Header
                getServerHeaderField(httpURLConnection);

                //得到网络响应码
                int responseCode = httpURLConnection.getResponseCode();

                if (responseCode == CONNECTION_SUCCEED) {
                    //此处才是发送数据的真正操作
                    InputStream inputStream = httpURLConnection.getInputStream();

                    String responseResult = inputStreamConvertString(inputStream);
                    if (!TextUtils.isEmpty(responseResult)) {
//                        Log.e("----------->网络请求工具最终返回值", responseResult);
                        handler.sendMessage(handler.obtainMessage(GET_RESULT_STRING_SUCCEED, responseResult));
                    } else {
                        handler.sendEmptyMessage(GET_RESULT_STRING_FAILURE);
                    }
                } else {
                    handler.sendMessage(handler.obtainMessage(ERROR, NETWORK_RESPONSE_FAILURE));
                }

            } catch (MalformedURLException e) {
                Log.e("--------------->", "Url字符串解析错误！");
                handler.sendMessage(handler.obtainMessage(ERROR, URL_PARSE_FAILURE));
            } catch (IOException e) {
                Log.e("--------------->", "网络连接失败，请检查网络连接！");
                handler.sendMessage(handler.obtainMessage(ERROR, CONNECTION_NETWORK_FAILURE));
            }
        }
    }

    /**
     * 将输入流转换为字符串
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String inputStreamConvertString(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        //将输入流暂时写进缓冲区中
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int length = 0;
        byte[] by = new byte[1024];
        while ((length = bis.read(by)) != -1) {
            bos.write(by, 0, length);
        }
        bos.flush();
        String result = new String(bos.toByteArray(), encodingType);

        bos.close();
        bis.close();

        return result;
    }

    /**
     * 拼接请求参数
     *
     * @return
     */
    public String combineRequestParams(HashMap<String, String> requestParamsMap) {
        if (requestParamsMap == null) {
            return null;
        }
        Iterator<Entry<String, String>> iterator = requestParamsMap.entrySet().iterator();

        StringBuffer stringBuffer = new StringBuffer();

        while (iterator.hasNext()) {
            Map.Entry<String, String> tempMap = iterator.next();

            stringBuffer.insert(0, tempMap.getKey() + "=" + tempMap.getValue() + "&");
        }
        //删除最后一个&
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);

        System.out.println(stringBuffer.toString());

        Log.e("-------------->拼接请求参数：", stringBuffer.toString());
        return stringBuffer.toString();
    }

    /**
     * 拼接字符串
     *
     * @param jsonBody
     * @return
     */
    public String combineJsonBody(HashMap<String, String> jsonBody) {
        if (jsonBody == null) {
            return null;
        }
        Iterator<Entry<String, String>> iterator = jsonBody.entrySet().iterator();

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("{");

        while (iterator.hasNext()) {
            Map.Entry<String, String> map = iterator.next();
            // 将键值对插入到第1个字符之后（从0开始）
            stringBuffer.insert(1, "\"" + map.getKey() + "\":\"" + map.getValue() + "\",");
        }
        //删除最后一个逗号
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer.append("}");
        Log.e("------------>拼接请求JSON参数", stringBuffer.toString());

        return stringBuffer.toString();
    }


    /**
     * 设置请求头参数（不能被覆盖）注意请求头参数的设置，因为它有可能决定了服务器返回值的类型
     *
     * @param httpURLConnection
     */
    private void setRequestProperty(HttpURLConnection httpURLConnection, HashMap<String, String> setRequestPropertyMap) {
        Iterator<Entry<String, String>> iterator = setRequestPropertyMap.entrySet().iterator();
        //设置遍历了一遍Map
        while (iterator.hasNext()) {
            Map.Entry<String, String> map = iterator.next();

            Log.e("------------>设置请求头:", map.getKey() + "    " + map.getValue());
            httpURLConnection.setRequestProperty(map.getKey(), map.getValue());
        }
    }

    /**
     * Handler处理子线程返回的数据
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //获取子线程发送过来的字符串
            String resultString = (String) msg.obj;
            switch (msg.what) {
                case GET_RESULT_STRING_SUCCEED:
                    //将子线程得到的结果值回调出去
                    networkCallbackListener.networkSuccess(resultString, serverHeaderFieldList);
                    break;
                case GET_RESULT_STRING_FAILURE:
                    networkCallbackListener.networkFailure();
                    break;
                case ERROR:
                    networkCallbackListener.networkError(resultString);
                    break;
            }
        }
    };

    /**
     * 回调接口
     */
    public interface NetworkCallbackListener {
        //网络连接成功
        void networkSuccess(String returnString, List<ServerHeaderFieldBean> serverHeaderFieldBeanList);

        //网络连接失败
        void networkFailure();

        //网络错误
        void networkError(String errorString);
    }

    /**
     * HeaderField实体类，用来保存服务器返回的Headers和Cookies
     */
    public static class ServerHeaderFieldBean {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
