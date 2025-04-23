window.onload = () => {

  document.querySelector('#email').addEventListener('keypress', (event) => {
    // document.getElementById('passwd').addEventListener('keypress', (event) => {
    if(event.key === 'Enter') { // ===: 타입까지 확인
      document.getElementById('passwd').focus();
    }
  });

  document.querySelector('#passwd').addEventListener('keypress', (event) => {
    if (event.key === 'Enter') {
      if (send()) {
        document.getElementById('frm').submit(); // 폼 직접 제출
      }
    }
  });


}

function send() {
  let email = document.getElementById('email');
  let email_msg = document.getElementById('email_msg');
  if (!email || !email_msg) return false;

  if (email.value.trim().length == 0) {
    email_msg.innerHTML = 'ID 입력은 필수 입니다. ID(이메일)는 3자이상 권장합니다.';
    email_msg.classList.add('span_warning');
    email.focus();
    return false;
  }

  let passwd = document.getElementById('passwd');
  let passwd_msg = document.getElementById('passwd_msg');
  if (!passwd || !passwd_msg) return false;

  if (passwd.value.trim().length == 0) {
    passwd_msg.innerHTML = '패스워드 입력은 필수 입니다. 패스워드는 8자이상 권장합니다.';
    passwd_msg.classList.add('span_warning');
    passwd.focus();
    return false;
  }

  return true; // 폼 제출 허용
}


// function send() { // 회원 가입 처리
//   let email = document.getElementById('email');
//   let email_msg = document.getElementById('email_msg');
//
//   if (email.value.trim().length == 0) {
//     email_msg.innerHTML= 'ID 입력은 필수 입니다. ID(이메일)는 3자이상 권장합니다.';
//     email_msg.classList.add('span_warning');    // class 적용
//     email.focus();
//
//     return false;  // 로그인 진행 중지
//
//   }
//
//   let passwd = document.getElementById('passwd');
//   let passwd_msg = document.getElementById('passwd_msg');
//
//   if (passwd.value.trim().length == 0) {
//     passwd_msg.innerHTML= '패스워드 입력은 필수 입니다. 패스워드는 8자이상 권장합니다.';
//     passwd_msg.classList.add('span_warning');    // class 적용
//     passwd.focus();
//
//     return false;  // 로그인 진행 중지
//
//   }
//
//   document.getElementById('frm').submit(); // required="required" 작동 안됨.
// }