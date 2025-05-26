//수정
btnUpdate.addEventListener('click', e=>{
  const id = bbsId.value;
  location.href = `/bbss/${id}/edit`;     // GET http://localhost:9080/bbss/{id}/edit
});

//삭제
btnDelete.addEventListener('click', e=>{

  //모달창 띄우기
  modalDel.showModal();// modal

  //모달창이 닫힐때
  modalDel.addEventListener('close',e=>{

    const id = bbsId.value;
    $btnYes = document.querySelector('#modalDel .btnYes');
    if(modalDel.returnValue == $btnYes.value){
      location.href = `/bbss/${id}/del`;     // GET http://localhost:9080/bbss/{id}/delete
    }else{
      return;
    }
  });
});

//목록
btnList.addEventListener('click', e=>{
  location.href = '/bbss';               // GET http://localhost:9080/bbs
});