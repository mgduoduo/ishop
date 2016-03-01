package com.zerol.crm.common.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * Created by gaoqiang on 22/1/2015.
 */
public class PropertiesUtil {

    public static Logger LOGGER = Logger.getLogger(PropertiesUtil.class);

    private static final String DEFAULT_PROPERTY_FOLD_PATH = "src/main/resources/";
    private static final String DEFAULT_PROPERTY_FILE = "taocrm.properties";

    private static PropertiesUtil instance;
    public Properties prop;

    private PropertiesUtil(){
       loadConfiguration();
    }

    private static PropertiesUtil getInstance(){
        if(instance==null){
            instance = new PropertiesUtil();
        }
        return instance;
    }

    public void loadConfiguration(){
        InputStream inputStream = null;
        prop = new Properties();
        try{
            inputStream = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PROPERTY_FILE);
            prop.load(inputStream);
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getProperty(String key){
        if(getInstance().prop.containsKey(key)){
            return getInstance().prop.getProperty(key);
        }else {
            return null;
        }
    }

    public static void setProperty(String keyName,String keyValue){
        OutputStream os = null;
        try{
            os = new FileOutputStream(DEFAULT_PROPERTY_FOLD_PATH+DEFAULT_PROPERTY_FILE);
            getInstance().prop.setProperty(keyName, keyValue);
            getInstance().prop.store(os, "Update '" + keyName + "' value");
        }catch(FileNotFoundException e){
            LOGGER.error("配置文件路径没有找到！",e);
        }catch(IOException e){
            LOGGER.error("属性文件更新错误",e);
        }finally{
            try{
                os.close();
            }catch(IOException e){
                LOGGER.error(e.getMessage(),e);
            }

        }
    }

    /*public static void main(String[] args) throws Exception {

        //System.out.print(System.getProperty("user.dir"));
        PropertiesUtil util = PropertiesUtil.getInstance();
        String s = util.getProperty("main.dir");
        System.out.print(s);
        util.setProperty("asdasd", "asd6666666666");

        System.out.print(util.getProperty("asdasd"));
    }*/
}
