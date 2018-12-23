package com.min.spring.cloud.eureka.client;

import com.min.spring.cloud.service1.api.TestApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create By minzhiwei On 2018/12/21 14:39
 * TODO 功能描述
 */
@SpringBootApplication
@RestController
@EnableDiscoveryClient
@EnableFeignClients(basePackageClasses = {TestApi.class})
@Slf4j
public class App {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TestApi testApi;

    @GetMapping("/dc")
    public String dc() {
        String services = "Services: " + discoveryClient.getServices();
        System.out.println(services);
        return services;
    }

    @GetMapping("test")
    public String test() {
        return restTemplate.getForObject("http://service1/test", String.class);
    }

//    @GetMapping("test1")
//    public String test1() {
//        return api.test();
//    }

    @GetMapping("test2")
    public String test2() {
        log.info("test2");
        return testApi.test();
    }

    @GetMapping("upload")
    public String upload() throws IOException {
        final String filename = "51CTO下载-Redis命令参考手册完整版.pdf";
        final String pathname = "C:\\Users\\sirmin\\Desktop\\" + filename;
        final DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        final FileItem file1 = diskFileItemFactory.createItem("file", MediaType.TEXT_PLAIN_VALUE, true, filename);
        final FileInputStream fileInputStream = new FileInputStream(pathname);
        final OutputStream outputStream = file1.getOutputStream();
        IOUtils.copy(fileInputStream, outputStream);

        final CommonsMultipartFile file = new CommonsMultipartFile(file1);
        return testApi.upload(file);
    }

    @PostMapping(value = "upload2", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload2(@RequestPart("file") MultipartFile multipartFile) throws IOException {
        return testApi.upload(multipartFile);
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
