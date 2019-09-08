package com.gt.filters;
/**
 * 过滤器
 * 功能：对访问者IP进行限制访问
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.gt.util.Constant;

public class IpFilter implements Filter{

    //用来存放允许访问的ip
    private List<String> allowList = new ArrayList<String>();

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        try {
            System.out.println("过滤器IpFilter开始初始化，功能：IP访问限制");
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        //获取访问的IP地址
        String remoteAddr = request.getRemoteAddr();
        //System.out.println("===============" + remoteAddr);
        //如果allowList为空,则认为没做限制,不为空则检查是否限制
        if(allowList.size() == 0 || allowList == null) {
            filterChain.doFilter(request, response);
        } else {
            Boolean flag = false;  //访问标志，默认为false，限制访问
            //进行逐个检查
            for(String regex : allowList){
                if(remoteAddr.matches(regex)){
                    //ip没被限制，正常访问
                    filterChain.doFilter(request, response);
                    flag = true;  //置为true，表示不限制访问
                    break;
                }
            }
            if(!flag) {
                //ip被限制，跳到指定页面
                //request.getRequestDispatcher("WEB-INF/success/error.jsp").forward(request, response);
            }
        }

    }

    @Override
    public void destroy() {
        System.out.println("过滤器IpFilter结束。");
    }

    /**
     * 对配置文件进行初始化并校验
     * @throws IOException
     */
    public void initConfig() throws IOException {

        //获取三种配置方式的值
        String allowIP = Constant.allowIP;
        String allowIPRange = Constant.allowIPRange;
        String allowIPWildcard = Constant.allowIPWildcard;

        //校验,校验失败后抛出异常
        if(!validate(allowIP, allowIPRange, allowIPWildcard)) {
            throw new RuntimeException("配置文件有错，请检查！");
        }

        /*
         * 将每一种配置方法放置到allowList中
         */
        //将第一种配置方法放到allowList中
        if(null != allowIP && !"".equals(allowIP.trim())) {
            String[] allowIPs = allowIP.split(",|;");
            for(String ip : allowIPs) {
                allowList.add(ip);
            }
        }

        //将第二种配置方法放到allowList中
        if(null != allowIPRange && 
                !"".equals(allowIPRange.trim())) {
            //先进行每一段的分割
            String[] allowIPRanges = allowIPRange.split(",|;");

            if(allowIPRanges.length > 0) {
                //对每一段进行遍历
                for(String allowRanges : allowIPRanges) {
                    if(allowRanges != null && 
                            !"".equals(allowRanges.trim())) {
                        //对该段的ip进行解析
                        String[] ips = allowRanges.split("-");
                        if(ips.length > 0 && ips.length < 3) {
                            String from = ips[0];//得到该段的起始ip
                            String to = ips[1];  //得到该段的结束ip

                            //获取该ip段地址的前三段，因为起始和结束的ip的前三段一样
                            String share = from.substring(0, from.lastIndexOf(".")+1);

                            //获取该ip段的起始ip的最后一段
                            int start = Integer.parseInt(from.substring(from.lastIndexOf(".")+1, 
                                                                from.length()));
                            //获取该ip段的结束ip的最后一段
                            int end = Integer.parseInt(to.substring(to.lastIndexOf(".")+1, 
                                                                to.length()));
                            for(int i=start; i<=end; i++) {
                                String ip = share + String.valueOf(i);
                                allowList.add(ip);
                            }
                        } else {
                            throw new RuntimeException("配置文件有错，请检查！");
                        }
                    }

                }
            }

        }

        //将第三种配置方法放到allowList中
        if(allowIPWildcard != null && 
                !"".equals(allowIPWildcard)) {
            //获取每个含通配符的ip地址
            String[] allowIPWildcards = allowIPWildcard.split(",|;");

            if(allowIPWildcards.length > 0) {
                for(String ip : allowIPWildcards) {
                    if(ip.indexOf("*") != -1) {
                        //对*进行替换
                        ip = ip.replaceAll("\\*", "(25[0-5]|2[0-4]\\\\d|[0-1]\\\\d{2}|[1-9]?\\\\d)");

                        allowList.add(ip);
                    } else {
                        throw new RuntimeException("配置文件有错，请检查！");
                    }

                }

            }
        }

        //打印输出allowList
        for(String str : allowList) {
            System.out.println(str);
        }

    }

    /**
     * 对配置文件进行校验
     * @param allowIP
     * @param allowIPRange
     * @param allowIPWildcard
     * @return
     */
    public Boolean validate(String allowIP, String allowIPRange, String allowIPWildcard) {
        Boolean result = false;
        //IP地址每一段的正则
        String regx = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
        //整个ip的正则
        String ipRegx = regx + "\\." + regx + "\\."+ regx + "\\." + regx;

        //对第一种方式进行校验
        Pattern pattern = Pattern.compile("("+ipRegx+")|("+ipRegx+"(,|;))*");
        if(this.isNullorMatches(allowIP, pattern)){
            result = true;  //匹配成功
        } else {
            result = false;
        }

        //对第二种方式进行校验
        pattern = Pattern.compile("("+ipRegx+")\\-("+ipRegx+")|" + 
                        "(("+ipRegx+")\\-("+ipRegx+")(,|;))*");
        if(this.isNullorMatches(allowIPRange, pattern)){
            result = true;  //匹配成功
        } else {
            result = false;
        }

        //对第三种方式进行校验
        pattern = Pattern.compile("("+regx+"\\."+ regx+"\\."+regx+"\\."+ "\\*)|" + 
                        "("+regx+"\\."+regx+"\\."+regx+"\\."+ "\\*(,|;))*");
        if(this.isNullorMatches(allowIPWildcard, pattern)){
            result = true;  //匹配成功
        } else {
            result = false;
        }

        return result;
    }

    /**
     * 进行正则匹配
     * @param allow
     * @return
     */
    public Boolean isNullorMatches(String allow, Pattern pattern) {
        //如果为空，说明用户没添加该项，不做处理
        if(allow == null || "".equals(allow.trim())) {
            return true;
        } else {
            //在最后面没有,或;的给添上
            if(!allow.endsWith(";") && !allow.endsWith(",")) {
                allow += ";";
            }
            //如果匹配，则返回true
            if(pattern.matcher(allow).matches()) {
                return true;
            }
        }

        return false;
    }
}
