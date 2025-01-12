package com.musinsa.platform.biz.core.web;

import com.musinsa.platform.biz.core.api.brand.service.BrandService;
import com.musinsa.platform.biz.core.api.catalog.service.CatalogService;
import com.musinsa.platform.biz.core.api.common.model.PriceSortType;
import com.musinsa.platform.biz.core.api.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class ViewController {

    private final CatalogService catalogService;
    private final BrandService brandService;
    private final GoodsService goodsService;


    @GetMapping("/")
    public String index() {
        return "/index";
    }

    @GetMapping("/catalog/low/categories")
    public ModelAndView lowCategoriesCatalogView() {
        var lowPriceCategories = catalogService.getCategoriesCatalog(PriceSortType.LOW);

        return new ModelAndView("/catalog/categories")
                .addObject("data", lowPriceCategories);
    }

    @GetMapping("/catalog/low/brand")
    public ModelAndView lowBrandCatalogView() {
        var brandCatalog = catalogService.getBrandCatalog(PriceSortType.LOW);

        return new ModelAndView("/catalog/brand")
                .addObject("data", brandCatalog.brandCatalog());
    }

    @GetMapping("/catalog/low-high/category")
    public String lowHighCategoryCatalogView() {
        return "/catalog/category";
    }

    @GetMapping("/admin/brand")
    public ModelAndView brandView() {
        var brandList = brandService.findAllBrands();

        return new ModelAndView("/admin/brand")
                .addObject("brandList", brandList);
    }

    @GetMapping("/admin/goods")
    public ModelAndView goodsView() {
        var goodsList = goodsService.findAllGoods();

        return new ModelAndView("/admin/goods")
                .addObject("goodsList", goodsList);
    }
}
