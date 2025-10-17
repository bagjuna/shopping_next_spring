package org.zerock.apiserver.product.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.apiserver.product.dto.PageRequestDTO;
import org.zerock.apiserver.product.dto.PageResponseDTO;
import org.zerock.apiserver.product.dto.ProductDTO;
import org.zerock.apiserver.product.dto.ProductListDTO;
import org.zerock.apiserver.product.service.ProductService;
import org.zerock.apiserver.product.util.FileUploader;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Log4j2
public class ProductAPIController {

    private final FileUploader fileUploader;

    private final ProductService productService;

//    curl -X POST \
//      -F "files=@/Users/zerock/capture/img1.jpeg" \
//      -F "files=@/Users/zerock/capture/img2.jpeg" \
//      -F "pname=Product1" \
//      -F "sale=true" \
//      -F "price=12000" \
//      -F "writer=user1" \
//      http://localhost:8080/api/products
    @PostMapping("")
    public ProductDTO register(ProductDTO productDTO, @RequestParam("files") MultipartFile[] files) {

        log.info(productDTO);
        log.info(files);

        //판매중으로 세팅
        productDTO.setSale(true);

        if(files != null && files.length > 0) {
            log.info("uploading files....");
            List<String> uploadfileNames = fileUploader.upload(files);
            log.info(uploadfileNames);
            productDTO.setFileNames(uploadfileNames);

        }

        ProductDTO resultDTO = productService.register(productDTO);
        log.info("register success");

        return resultDTO;
    }

    @GetMapping("/{pno}")
    public ProductDTO getOne(@PathVariable("pno") Integer pno) {
        log.info("getOne...............");
        log.info(pno);
        return productService.getOne(pno);
    }


//    curl -X PUT \
//            -F "files=@/Users/zerock/capture/img1.jpeg" \
//            -F "files=@/Users/zerock/capture/img2.jpeg" \
//            -F "pno=1" \
//            -F "pname=Update P1" \
//            -F "price=6000" \
//            -F "sale=true" \
//    http://localhost:8080/api/products/1

    @PutMapping("{pno}")
    public ProductDTO modify(
            @PathVariable("pno") Integer pno,
            ProductDTO productDTO,
            @RequestParam(value = "files", required = false) MultipartFile[] files
            ) {
        log.info("modify...............");
        log.info(productDTO);

        //데이터베이스에 있는 파일들 - 나중에 정말 필요없는 파일들을 삭제 하기 위해
        List<String> oldSavedFiles = productService.getOne(pno).getFileNames();
        log.info("oldSavedFiles............... ");
        log.info(oldSavedFiles);
        log.info("-----------------------------------------1");

        //새로운 이미지 파일들
        if(files != null && files.length > 0) {
            log.info("uploading files....");
            List<String> uploadfileNames = fileUploader.upload(files);
            log.info(uploadfileNames);
            uploadfileNames.forEach(newFileName -> productDTO.addFileName(newFileName));

        }
        log.info("-----------------------------------------2 최종적으로 저장해야 하는 파일 목록");
        log.info(productDTO.getFileNames());

        //수정
        ProductDTO modifiedDTO =  productService.update(productDTO);

        List<String> modifiedFileNames = modifiedDTO.getFileNames();
        //실제로 삭제해야할 파일들 이름
        List<String> uselessFileNames =
                oldSavedFiles.stream().filter(oldFileName -> modifiedFileNames.contains(oldFileName) == false ).toList();

        log.info("uselessFileNames : " + uselessFileNames);

        fileUploader.delete(uselessFileNames);


        return productDTO;
    }

    @DeleteMapping("{pno}")
    public void remove(@PathVariable("pno") Integer pno) {
        log.info("remove...............");
        log.info(pno);
        productService.remove(pno);
    }

    @GetMapping("event")
    public List<Integer> getTops(@RequestParam("count") int count) {
        log.info("getTops...............");
        log.info(count);
        return productService.getTops(count);
    }

    @GetMapping("list")
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {

        log.info("list...............");
        log.info(pageRequestDTO);
        return productService.list(pageRequestDTO);

    }

    @GetMapping("countCatalog")
    public Integer countCatalog(@RequestParam("size") int size ) {
        int count =  productService.getCatalogPageCount(size);

        return (int)(Math.ceil(count / (double)size));
    }

}

