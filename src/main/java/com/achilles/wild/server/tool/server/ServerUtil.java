package com.achilles.wild.server.tool.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ServerUtil {
    /**
     * 获取本机所有IP
     */
@SuppressWarnings("rawtypes")
private static String[] getAllLocalHostIP() {
    List<String> res = new ArrayList<String>();
    Enumeration netInterfaces;
    try {
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) netInterfaces
                    .nextElement();
            System.out.println("---Name---:" + ni.getName());
            Enumeration nii = ni.getInetAddresses();
            while (nii.hasMoreElements()) {
                ip = (InetAddress) nii.nextElement();
                if (ip.getHostAddress().indexOf(":") == -1) {
                    res.add(ip.getHostAddress());
                    System.out.println("本机的ip=" + ip.getHostAddress());
                }
            }
        }
    } catch (SocketException e) {
        e.printStackTrace();
        }
        return (String[]) res.toArray(new String[0]);
    }
public static String getLocalIP() {
       String ip = "";
       try {
               Enumeration<?> e1 = (Enumeration<?>) NetworkInterface.getNetworkInterfaces();
               while (e1.hasMoreElements()) {
                   NetworkInterface ni = (NetworkInterface) e1.nextElement();
                    System.out.println ("getLocalIP--nic.getDisplayName ():" + ni.getDisplayName ()); 
                System.out.println ("getLocalIP--nic.getName ():" + ni.getName ());
                   if (!ni.getName().equals("eth0")) {
                       continue;
                   } else {
                   Enumeration<?> e2 = ni.getInetAddresses();
                       while (e2.hasMoreElements()) {
                       InetAddress ia = (InetAddress) e2.nextElement();
                       if (ia instanceof Inet6Address)
                           continue;
                       ip = ia.getHostAddress();
                   }
                       break;
                   }
               }
           } catch (SocketException e) {
               e.printStackTrace();
               System.exit(-1);
           }
           return ip;
       }
    public static String getWinLocalIP () 
        { 
            String ip = ""; 
            try 
            { 
                Enumeration <?> e1 = (Enumeration <?>) NetworkInterface.getNetworkInterfaces (); 
                while (e1.hasMoreElements ()) 
                { 
                    NetworkInterface ni = (NetworkInterface) e1.nextElement ();
                    System.out.println ("getWinLocalIP--nic.getDisplayName ():" + ni.getDisplayName ()); 
                    System.out.println ("getWinLocalIP--nic.getName ():" + ni.getName ());
                    Enumeration <?> e2 = ni.getInetAddresses (); 
                    while (e2.hasMoreElements ()) 
                    { 
                        InetAddress ia = (InetAddress) e2.nextElement (); 
                        ip = ia.getHostAddress (); 
                } 
                } 
            } 
            catch (SocketException e) 
            { 
                e.printStackTrace (); 
            System.exit (-1); 
            } 
            return ip; 
        }
    /**
     * 获取本机所有物理地址
     *
     * @return
     */
    public static String getMacAddress() {
    String mac = "";
    String line = "";
    String os = System.getProperty("os.name");
   if (os != null && os.startsWith("Windows")) {
        try {
                String command = "cmd.exe /c ipconfig /all";
                Process p = Runtime.getRuntime().exec(command);
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("Physical Address") > 0) {
                        int index = line.indexOf(":") + 2;
                        mac = line.substring(index);
                        break;
                    }
                }
                br.close();
            } catch (IOException e) {
            }
        }
        return mac;
    }
 
    public String getMacAddress(String host) {
        String mac = "";
        StringBuffer sb = new StringBuffer();
 
        try {
            NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress
                    .getByName(host));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
 
        mac = sb.toString();
        mac = mac.substring(0, mac.length() - 1);
 
        return mac;
}
 
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
    /**
34
     * @return 本机IP
35
     * @throws SocketException
36
     */
    public static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP
        Enumeration<NetworkInterface> netInterfaces =
            NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }
        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
    	getAllLocalHostIP();
        System.out.println("LocalIP:"+getLocalIP());
        System.out.println("getWinLocalIP:"+getWinLocalIP());
        System.out.println(getMacAddress());
    }
}
