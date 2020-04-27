package com.esiran.greenpay.admin.controller.pay;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.esiran.greenpay.pay.entity.Product;
import com.esiran.greenpay.pay.entity.ProductDTO;
import com.esiran.greenpay.pay.entity.Type;
import com.esiran.greenpay.pay.service.IProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/v1/pay/products")
public class APIAdminPayProductController {
    private final IProductService productService;

    public APIAdminPayProductController(IProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public IPage<ProductDTO> page(
            @RequestParam(required = false,defaultValue = "1") Integer current,
            @RequestParam(required = false, defaultValue = "10") Integer size){
        return productService.selectPage(new Page<>(current,size),null);
    }

}
