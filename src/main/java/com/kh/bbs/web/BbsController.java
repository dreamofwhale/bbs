package com.kh.bbs.web;


import com.kh.bbs.domain.bbs.svc.BbsSVC;
import com.kh.bbs.domain.entity.Bbs;
import com.kh.bbs.web.form.bbs.DetailForm;
import com.kh.bbs.web.form.bbs.WriteForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/bbss")
@RequiredArgsConstructor
public class BbsController {

  final private BbsSVC bbsSVC;

  //게시글 목록
  @GetMapping         //GET http://localhost:9080/bbss
  public String findAll(Model model) {
    List<Bbs> list = bbsSVC.findAll();
    model.addAttribute("list", list);
    return "bbs/all";
  }

  //게시글 작성화면
  @GetMapping("/write")     //Get http://localhost:9080/bbss/write
  public String writeForm() {

    return "bbs/write";
  }

  //게시글 작성 처리
  @PostMapping("/write")
  public String write(
      WriteForm writeForm,
      RedirectAttributes redirectAttributes

  ){
    log.info("bbsHead={}, bbsWriter={}, bbsBody={}",writeForm.getBbsHead(),writeForm.getBbsWriter(),writeForm.getBbsBody());

    Bbs bbs = new Bbs();
    bbs.setBbsHead(writeForm.getBbsHead());
    bbs.setBbsWriter(writeForm.getBbsWriter());
    bbs.setBbsBody(writeForm.getBbsBody());

    Long bid = bbsSVC.write(bbs);
    redirectAttributes.addAttribute("id", bid);

    return "redirect:/bbss/{id}";
  }


  //게시글 상세
  @GetMapping("/{id}")
  public String findById(
      @PathVariable("id") Long id,
      Model model
  ) {
    log.info("id={}", id);


    Optional<Bbs> optionalBbs = bbsSVC.findById(id);
    Bbs findedBbs = optionalBbs.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setBbsId(findedBbs.getBbsId());
    detailForm.setBbsHead(findedBbs.getBbsHead());
    detailForm.setBbsWriter(findedBbs.getBbsWriter());
    detailForm.setBbsBody(findedBbs.getBbsBody());
    detailForm.setBbsDate(findedBbs.getBbsDate());
    detailForm.setBbsUpdate(findedBbs.getBbsUpdate());

    model.addAttribute("detailForm", detailForm);

    return "bbs/detailForm";
  }
}
