package com.kh.bbs.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/csr")
public class CsrController {

  @GetMapping("/bbss")
  public  String bbss() {

    return "csr/bbs/bbs";

  }
}
