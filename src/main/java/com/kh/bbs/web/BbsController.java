package com.kh.bbs.web;


import com.kh.bbs.domain.bbs.svc.BbsSVC;
import com.kh.bbs.domain.entity.Bbs;
import com.kh.bbs.web.form.bbs.DetailForm;
import com.kh.bbs.web.form.bbs.EditForm;
import com.kh.bbs.web.form.bbs.WriteForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
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

  ) {
    log.info("bbsHead={}, bbsWriter={}, bbsBody={}", writeForm.getBbsHead(), writeForm.getBbsWriter(), writeForm.getBbsBody());

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

  //게시글 삭제(단건)
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long bbsId) {

    int rows = bbsSVC.deleteById(bbsId);

    return "redirect:/bbss";
  }

  //게시글 삭제(여러건)
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("bbsIds") List<Long> bbsIds) {

    log.info("bbsIds={}", bbsIds);

    int rows = bbsSVC.deleteByIds(bbsIds);

    log.info("게시글 {}건이 삭제됨.", rows);

    return "redirect:/bbss";
  }


  //게시글 수정
  @GetMapping("/{id}/edit")
  public String editById(
      @PathVariable("id") Long id,
      Model model
  ){
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

    // 모델에 추가
    model.addAttribute("detailForm", detailForm);

    return "bbs/edit";
  }

  //게시글 수정 처리
  @PostMapping("/edit")
  public String edit(
      @ModelAttribute EditForm editForm,
      RedirectAttributes redirectAttributes
  ) {
    log.info("받은 값: bbsId={}, bbsHead={}, bbsBody={}",
        editForm.getBbsId(), editForm.getBbsHead(), editForm.getBbsBody());

    Bbs bbs = new Bbs();
    bbs.setBbsHead(editForm.getBbsHead());
    bbs.setBbsBody(editForm.getBbsBody());
    bbs.setBbsWriter(editForm.getBbsWriter());
    bbs.setBbsUpdate(new Timestamp(System.currentTimeMillis())); // 현재 시간

    bbsSVC.updateById(editForm.getBbsId(), bbs);

    redirectAttributes.addAttribute("id", editForm.getBbsId());
    return "redirect:/bbss"; // 목록 페이지로 이동
  }
}
