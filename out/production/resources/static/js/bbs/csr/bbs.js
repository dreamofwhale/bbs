import { ajax,  PaginationUI} from '/js/common.js';

let currentPage = 1; // 현재 페이지를 위한 전역 변수
let initialPage = 1; // 상품 추가 후 이동할 페이지 (1페이지)

const recordsPerPage = 10;        // 페이지당 레코드수
const pagesPerPage = 10;          // 한페이지당 페이지수

//댓글 등록
const writeBbs = async bbs => {
  try {
    const url = '/api/bbss';
    const result = await ajax.post(url, bbs);
    if (result.header.rtcd === 'S00') {
      console.log(result.body);
      frm.reset();
      initialPage = 1; // 생성 후 1페이지로 이동
      getBbss(initialPage, recordsPerPage); // 첫 페이지의 기본 레코드로 호출
      configPagination();
    } else if(result.header.rtcd.substr(0,1) == 'E'){
        for(let key in result.header.details){
            console.log(`필드명:${key}, 오류:${result.header.details[key]}`);
        }
    } else {
      alert(result.header.rtmsg);
    }
  } catch (err) {
    console.error(err.message);
  }
};

//게시글 조회
const getBbs = async bid => {
  try {
    const url = `/api/bbss/${bid}`;
    const result = await ajax.get(url);
    console.log(result);
    if (result.header.rtcd === 'S00') {
      console.log(result.body);
      // productId2.value = result.body.productId;
      bbsId2.setAttribute('value', result.body.bbsId);
      bbsHead2.setAttribute('value', result.body.bbsHead);
      bbsBody2.setAttribute('value', result.body.bbsBody);
      bbsWriter2.setAttribute('value', result.body.bbsWriter);
      bbsDate2.setAttribute('value', result.body.bbsDate);
      bbsUpdate2.setAttribute('value', result.body.bbsUpdate);

      bbsId2.value = result.body.bbsId;
      bbsHead2.value = result.body.bbsHead;
      bbsBody2.value =  result.body.bbsBody;
      bbsWriter2.value =  result.body.bbsWriter;
      bbsDate2.value =  result.body.bbsDate;
      bbsUpdate2.value = result.body.bbsUpdate;

    } else if(result.header.rtcd.substr(0,1) == 'E'){
        for(let key in result.header.details){
            console.log(`필드명:${key}, 오류:${result.header.details[key]}`);
        }
    } else {
      alert(result.header.rtmsg);
    }
  } catch (err) {
    console.error(err);
  }
};

//게시글 삭제
const delBbs = async (bid, frm) => {
  try {
    const url = `/api/bbss/${bid}`;
    const result = await ajax.delete(url);
    console.log(result);
    if (result.header.rtcd === 'S00') {
      console.log(result.body);
      const $inputs = frm.querySelectorAll('input');
      [...$inputs].forEach(ele => (ele.value = '')); //폼필드 초기화
      getBbss(currentPage, recordsPerPage); // 현재 페이지의 기본 레코드로 호출
    } else if(result.header.rtcd.substr(0,1) == 'E'){
        for(let key in result.header.details){
            console.log(`필드명:${key}, 오류:${result.header.details[key]}`);
        }
    } else {
      alert(result.header.rtmsg);
    }
  } catch (err) {
    console.error(err);
  }
};

//게시글 수정
const modifyBbs = async (bid, bbs) => {
  try {
    const url = `/api/bbss/${bid}`;
    const result = await ajax.patch(url, bbs);
    if (result.header.rtcd === 'S00') {
      console.log(result.body);
      getProducts(currentPage, recordsPerPage); // 현재 페이지의 기본 레코드로 호출
    } else if(result.header.rtcd.substr(0,1) == 'E'){
        for(let key in result.header.details){
            console.log(`필드명:${key}, 오류:${result.header.details[key]}`);
        }
    } else {
      alert(result.header.rtmsg);
    }
  } catch (err) {
    console.error(err.message);
  }
};

//게시글 목록
const getBbss = async (reqPage, reqRec) => {

  try {
    const url = `/api/bbss/paging?pageNo=${reqPage}&numOfRows=${reqRec}`;
    const result = await ajax.get(url);

    if (result.header.rtcd === 'S00') {
      currentPage = reqPage; // 현재 페이지 업데이트
      displayBbsList(result.body);

    } else {
      alert(result.header.rtmsg);
    }
  } catch (err) {
    console.error(err);
  }
};

//게시글 등록 화면
function displayForm() {
  //게시글 등록
  const $writeFormWrap = document.createElement('div');
  $writeFormWrap.innerHTML = `
    <form id="frm">
      <div>
          <label for="bbsHead">제목</label>
          <input type="text" id="bbsHead" name="bbsHead"/>
          <span class="field-error client" id="errBbsHead"></span>
      </div>
      <div>
          <label for="bbsWriter">작성자</label>
          <input type="text" id="bbsWriter" name="bbsWriter"/>
          <span class="field-error client" id="errBbsWriter"></span>
      </div>
      <div>
          <label for="bbsBody">본문</label>
          <input type="textarea" id="bbsBody" name="bbsBody"/>
          <span class="field-error client" id="errBbsBody"></span>
      </div>
      <div>
          <button id="btnAdd" type="submit">등록</button>
      </div>
    </form>
  `;
  document.body.insertAdjacentElement('afterbegin', $writeFormWrap);
  const $frm = $writeFormWrap.querySelector('#frm');
  $frm.addEventListener('submit', e => {
    e.preventDefault(); // 기본동작 중지

    //유효성 체크
    if($frm.bbsHead.value.trim().length === 0) {
      errBbsHead.textContent = '제목은 필수 입니다';
      $frm.bbsHead.focus();
      return;
    }
    if($frm.bbsWriter.value.trim().length === 0) {
      errBbsWriter.textContent = '작성자는 필수 입니다';
      $frm.bbsWriter.focus();
      return;
    }
    if($frm.bbsBody.value.trim().length === 0) {
      errBbsBody.textContent = '본문은 필수 입니다';
      $frm.bbsBody.focus();
      return;
    }

    const formData = new FormData(e.target); //폼데이터가져오기
    const bbs = {};
    [...formData.keys()].forEach(
      ele => (bbs[ele] = formData.get(ele)),
    );

    writeBbs(bbs);

  });
}

//게시글 조회 화면
function displayReadForm() {
  //상태 : 조회 mode-read, 편집 mode-edit
  const changeEditMode = frm => {
    frm.classList.toggle('mode-edit', true);
    [...frm.querySelectorAll('input')]
      .filter(input => input.name !== 'bbsId')
      .forEach(input => input.removeAttribute('readonly'));

    const $btns = frm.querySelector('.btns');
    $btns.innerHTML = `
      <button id="btnSave" type="button">저장</button>
      <button id="btnCancel" type="button">취소</button>
    `;

    const $btnSave = $btns.querySelector('#btnSave');
    const $btnCancel = $btns.querySelector('#btnCancel');

    //저장
    $btnSave.addEventListener('click', e => {
      const formData = new FormData(frm); //폼데이터가져오기
      const bbs = {};

      [...formData.keys()].forEach(
        ele => (bbs[ele] = formData.get(ele)),
      );

      modifyBbs(bbs.bbsId, bbs); //수정
      getBbs(bbs.bbsId); //조회
      changeReadMode(frm); //읽기모드
    });

    //취소
    $btnCancel.addEventListener('click', e => {
      frm.reset(); //초기화
      changeReadMode(frm);
    });
  };

  const changeReadMode = frm => {
    frm.classList.toggle('mode-read', true);
    [...frm.querySelectorAll('input')]
      .filter(input => input.name !== 'bbsId')
      .forEach(input => input.setAttribute('readonly', ''));

    const $btns = frm.querySelector('.btns');
    $btns.innerHTML = `
      <button id="btnEdit" type="button">수정</button>
      <button id="btnDelete" type="button">삭제</button>
    `;

    const $btnDelete = $btns.querySelector('#btnDelete');
    const $btnEdit = $btns.querySelector('#btnEdit');

    //수정
    $btnEdit.addEventListener('click', e => {
      changeEditMode(frm);
    });

    //삭제
    $btnDelete.addEventListener('click', e => {
      const bid = frm.bbsId.value;
      if (!bid) {
        alert('게시글 조회 후 삭제바랍니다.');
        return;
      }

      if (!confirm('삭제하시겠습니까?')) return;
      delBbs(bid, frm);
    });
  };

  const $readFormWrap = document.createElement('div');
  $readFormWrap.innerHTML = `
    <form id="frm2">

      <div>
          <label for="bbsId2">게시글 아이디</label>
          <input type="text" id="bbsId2" name="bbsId" readonly/>
      </div>
      <div>
          <label for="bbsHead">제목</label>
          <input type="text" id="bbsHead2" name="bbsHead"/>
      </div>
      <div>
          <label for="bbsWriter">작성자</label>
          <input type="text" id="bbsWriter2" name="bbsWriter"/>
      </div>
      </div>
      <div>
          <label for="bbsBody">본문</label>
          <input type="text" id="bbsBody2" name="bbsBody"/>
      </div>
        <div>
          <label for="bbsDate2">작성일</label>
          <input type="text" id="bbsDate2" name="bbsDate" readonly/>
        </div>
        <div>
          <label for="bbsUpdate2">수정일</label>
          <input type="text" id="bbsUpdate2" name="bbsUpdate" readonly/>
        </div>
        <div class='btns'></div>
      </div>
      <div class='btns'></div>

    </form>
  `;
  document.body.insertAdjacentElement('afterbegin', $readFormWrap);
  const $frm2 = $readFormWrap.querySelector('#frm2');
  changeReadMode($frm2);
}

//게시글 목록 화면
function displayBbsList(bbss) {

  const makeTr = bbss => {
    const $tr = bbss
      .map(
        bbs =>
          `<tr data-bid=${bbs.bbsId}>
            <td>${bbs.bbsId}</td>
            <td>${bbs.bbsHead}</td></tr>`,
      )
      .join('');
    return $tr;
  };

  $list.innerHTML = `
    <table>
      <caption> 게 시 글 목 록 </caption>
      <thead>
        <tr>
          <th>게시글번호</th>
          <th>제목</th>
        </tr>
      </thead>
      <tbody>
        ${makeTr(bbss)}
      </tbody>
    </table>`;

  const $bbss = $list.querySelectorAll('table tbody tr');

  // Array.from($bbss)
  [...$bbss].forEach(bbs =>
    bbs.addEventListener('click', e => {
      const bid = e.currentTarget.dataset.bid;
      getBbs(bid);
    }),
  );
}

displayReadForm(); //조회
displayForm();//등록
//getProducts();//목록

const $list = document.createElement('div');
$list.setAttribute('id','list')
document.body.appendChild($list);

const divEle = document.createElement('div');
divEle.setAttribute('id','reply_pagenation');
document.body.appendChild(divEle);

async function configPagination(){
  const url = '/api/bbss/totCnt';
  try {
    const result = await ajax.get(url);

    const totalRecords = result.body; // 전체 레코드수

    const handlePageChange = (reqPage)=>{
      return getBbss(reqPage,recordsPerPage);
    };

    // Pagination UI 초기화
    var pagination = new PaginationUI('reply_pagenation', handlePageChange);

    pagination.setTotalRecords(totalRecords);       //총건수
    pagination.setRecordsPerPage(recordsPerPage);   //한페이지당 레코드수
    pagination.setPagesPerPage(pagesPerPage);       //한페이지당 페이지수

    // 첫페이지 가져오기
    pagination.handleFirstClick();

  }catch(err){
    console.error(err);
  }
}
configPagination();