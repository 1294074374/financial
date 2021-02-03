package com.booth.controller;

import com.booth.pojo.BillVO;
import com.booth.service.IBillFileService;
import com.booth.service.impl.BillFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BillFileController {

    @Autowired
    private BillFileServiceImpl billFileService;

    @RequestMapping(value = "/loadBillFile", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String loadBillFile() {
        billFileService.loadBillFile();
        return "";
    }

    @RequestMapping(value = "/loadMyPagesDate", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String loadMyPagesDate(BillVO billVO) {
        return billFileService.loadMyPagesDate(billVO);
    }
}
