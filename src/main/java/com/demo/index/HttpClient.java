package com.demo.index;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class HttpClient {
	
	
	/**
	 * 启动入口
	 */
	public static void main(String[] args) {
		//System.out.println(HttpClient.doGet("http://xawxfw.xacg.gov.cn/Urban/rest/loginAction.action?telephone=1&password=xian123"));
			String[] openids = new String[]{ "oFILIvhaik9KZfGS4LJXVFWVG44s",""};
		for(int i=0;i<9999;i++){
			System.out.println(
					HttpClient.doPost(
				"http://xawxfw.xacg.gov.cn/Urban/rest/saveComplainInfo?"
					,"openid=\u672a\u77e5\u9519\u8bef"
						+ "&telephone=\u672a\u77e5\u9519\u8bef"
							+ "&name=\u672a\u77e5\u9519\u8bef"
								+ "&content=\u672a\u77e5\u9519\u8bef"
									+ "&dataTime=\u672a\u77e5\u9519\u8bef"
										+ "&picturepath1=\u672a\u77e5\u9519\u8bef"
									+ "&picturepath2=\u672a\u77e5\u9519\u8bef"
								+ "&jingdu=\u672a\u77e5\u9519\u8bef"
							+ "&weidu=\u672a\u77e5\u9519\u8bef"
						+ "&address=\u672a\u77e5\u9519\u8bef"
					+ "&wentidizhi=\u672a\u77e5\u9519\u8bef"
						)
					);
			try {
				System.out.println(i);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	
    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }

    
    /**
     * 将请求的头信息获取到session中
     * @param request
     * @param connection
     */
    private static void getCookieToSession(HttpServletRequest request,
            URLConnection connection) {

        Map<String, List<String>> map = connection.getHeaderFields();
        for (String key : map.keySet()) {
            System.out.println(key + "--->" + map.get(key));
        }
        HttpSession session = request.getSession();

        List<String> cookie=map.get("Set-Cookie");
        if(cookie!=null){

            String verCode=getCookieBySet("sid",cookie.get(0));

            String JSESSIONID=getCookieBySet("JSESSIONID",cookie.get(0));

            if(verCode!=null){
                session.setAttribute("verCode",verCode);
            }

            if(JSESSIONID!=null){
                session.setAttribute("JSESSIONID",JSESSIONID);
            }
        }

    }
    
    /**
     * 获取verCode=b5pcogZaFGikpAc1mQ+G5wOJGBWtXLsHafpf5wlgF5s=; Path=/SSODAO/; HttpOnly
     * 的cookie信息
     */
    public static String getCookieBySet(String name,String set){

        String regex=name+"=(.*?);";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher =pattern.matcher(set);
        if(matcher.find()){
            return matcher.group();
        }
        return null;

    }
    
    public static String doPost(String httpUrl, String param) {

        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            //
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }
}