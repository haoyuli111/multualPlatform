package cn.lichuachua.mp.mpserver.web.controller;

import cn.lichuachua.mp.mpserver.dto.FileInfo;
import cn.lichuachua.mp.mpserver.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

/**
 * @author 李歘歘
 */
@RestController
@RequestMapping("/articleImg")
@Slf4j
public class FileController {

//    @Value("")
//    String folder = System.getProperty("user.dir")+File.separator+"upload"+File.separator;
    /**
     * 在配置文件中配置的文件保存路径
     */
    @Value("C:/Users/Administrator/Desktop/Mp/mutualPlatform/src/main/resources/static/articleImages/")
    private String folder;
    @Value("C:/Users/Administrator/Desktop/Mp/mutualPlatform/target/classes/static/articleImages/")
    private String folder1;
    @PostMapping
    public FileInfo upload(HttpServletRequest request, @RequestParam(value = "editormd-image-file", required = false) MultipartFile file) throws Exception {
        log.info("【FileController】 fileName={},fileOrginNmae={},fileSize={}", file.getName(), file.getOriginalFilename(), file.getSize());
        log.info(request.getContextPath());
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String newFileName = new Date().getTime() + "." + suffix;
        FileUtil.uploadFile1(file.getBytes(),"/static/articleImages/",newFileName);
        File localFile = new File(folder, newFileName);
        file.transferTo(localFile);
        log.info(localFile.getAbsolutePath());
        return new FileInfo(1, "上传成功", request.getRequestURL().substring(0,request.getRequestURL().lastIndexOf("/"))+"/articleImages/"+newFileName);
    }

    @GetMapping("/{id}")
    public void downLoad(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try (InputStream inputStream = new FileInputStream(new File(folder1, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (Exception e) {

        }
    }
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
