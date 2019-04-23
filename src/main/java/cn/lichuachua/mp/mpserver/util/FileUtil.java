package cn.lichuachua.mp.mpserver.util;

import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileUtil {
    /**
     * 文件上传
     * @param file
     * @param path
     * @param fileName
     * @throws Exception
     */
    public static void uploadFile(byte[] file, String path, String fileName) throws Exception {
//        File rootPath = ResourceUtils.getFile("classpath:resources");
//        File rootPath = new File(ResourceUtils.getURL("classpath:resources").getPath());
//        String filePath = rootPath.getAbsolutePath()+path;
        String rootPath ="C:/Users/Administrator/Desktop/Mp/mutualPlatform/src/main/resources";
        String filePath = rootPath+path;
        System.out.println(filePath);
        File targetFile = new File(String.valueOf(filePath));
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }


    /**
     * 文件下载
     * @param response
     * @param path
     * @param fileName
     * @throws UnsupportedEncodingException
     */
    public static void download(HttpServletResponse response, String path, String fileName) throws UnsupportedEncodingException {
        String rootPath ="C:/Users/Administrator/Desktop/Mp/mutualPlatform/src/main/resources";
        String filePath = rootPath+path;
        File file = new File(filePath+fileName);
            fileName = new String(file.getName().getBytes("UTF-8"),"iso-8859-1");
        if (file.exists()){
            //设置强制下载不打开
            response.setContentType("application/force-download");
            //设置文件名
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1){
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if (bis != null){
                    try {
                        bis.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                if (fis != null){
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
