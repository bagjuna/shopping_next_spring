package org.zerock.apiserver.product.util;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.ScaleMethod;
import com.sksamuel.scrimage.webp.WebpWriter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
public class FileUploader {

    @Value("${org.zerock.upload.folder}")
    private String uploadDir;

    public List<String> upload(MultipartFile[] files) {

        List<String> fileNames = new ArrayList<>();

        log.info("-----------------------------------------------");
        log.info("uploading files....");
        log.info(files.length);

        for(MultipartFile file : files) {

            String originalFileName = file.getOriginalFilename();

            if(originalFileName == null || originalFileName.isEmpty() || file.getSize() == 0) {
                continue;
            }

            String fileName = UUID.randomUUID()+"_"+originalFileName;


            log.info("uploading file : " + fileName);

            String webpFileName = fileName.substring(0, fileName.lastIndexOf("."))+".webp";

            try(InputStream inputStream = file.getInputStream()){

                // scrimage를 사용하여 이미지 로드
                ImmutableImage image = ImmutableImage.loader().fromStream(inputStream);

                // WebP로 저장 (기본 설정)
                File outputWebpFile = new File(uploadDir, webpFileName);
                image.output(WebpWriter.DEFAULT, outputWebpFile);



                int thumbWidth = 300;
                int thumbHeight = 200;

                // 이미지를 비율을 유지하면서 썸네일 크기로 조절합니다.
                // `ScaleMethod.Bicubic`은 고품질 리사이징을 제공합니다.
                // `fit(width, height)`: 비율을 유지하면서 주어진 박스 안에 이미지를 맞춥니다.
                // `cover(width, height)`: 비율을 유지하면서 주어진 박스를 꽉 채웁니다 (일부 잘릴 수 있음).
                // `scaleToWidth(width)`: 너비를 기준으로 비율 유지.
                // `scaleToHeight(height)`: 높이를 기준으로 비율 유지.
                ImmutableImage thumbnailImage = image.scaleToWidth(thumbWidth, ScaleMethod.Bicubic); // 너비 300px에 맞춰 비율 유지


                File outputThumbnailWebpFile = new File(uploadDir, "s_"+webpFileName );
                // 썸네일은 원본보다 품질을 더 낮춰서 파일 크기를 줄일 수도 있습니다.
                // WebpWriter.builder().withQuality(60).build() 등으로 설정
                thumbnailImage.output(WebpWriter.DEFAULT, outputThumbnailWebpFile);

                fileNames.add(webpFileName);

            }catch(Exception e){
                log.error(e.getMessage());
            }
        }//end for

        return fileNames;
    }

    public void delete(List<String> fileNames) {

        for(String fileName : fileNames) {

            log.info("delete file : " + fileName);

            File file = new File(uploadDir, fileName);

            if(file.exists()) {
                file.delete();
            }
            File thumbnailFile = new File(uploadDir, "s_"+fileName);
            if(thumbnailFile.exists()) {
                thumbnailFile.delete();
            }
        }
    }
}
