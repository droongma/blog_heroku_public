
let index = {
	init: function(){ // btn-save 버튼 클릭시 this.save() 호출
		$("#btn-save").on("click", ()=>{ // 화살표 함수 쓰는 이유 : this를 바인딩하기 위해서! 
			this.save(); // 여기서의 this는 index 변수를 가리킴!(만약 화살표 함수가 아니라 function을 썼다면 this가 window 객체를 가리킴)
		}); // 이벤트 리스너
		$("#btn-update").on("click", ()=>{ 
			this.update();
		});
	},
	
	save: function(){
		// alert("user의 save 함수 호출!");
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
		}
	
		//console.log(data);
		
		/* ajax 호출시 default로 비동기 호출 수행*/
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		$.ajax({
			//회원가입 수행 요청
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), // 자바스크립트 object를 Json으로 바꿔 HTTP body에 담아 보냄
			contentType: "application/json; charset=utf-8", // body 데이터(보낼 데이터)의 타입(MIME) 명시
			
			// 요청에 대한 응답은 기본적으로 문자열이다. 
			// 그러나 dataType을 json으로 명시하면, 받은 데이터가 Json일 경우 자바스크립트 객체로 변환해주는 역할을 함
			// 사실 서버가 json을 리턴해주면, 자동으로 이것을 자바 오브젝트로 변환해주므로 아래 dataType은 생략해도 된다. 
			dataType: "json", 
		}).done(function(resp){ // 성공시
			if (resp.status === 500){
				alert("회원가입 실패 : 중복된 username입니다.")
			} else{
				alert("회원가입이 완료되었습니다.");	
				location.href = "/";
			}	
		}).fail(function(error){ // 실패시
			alert(JSON.stringify(error))
		}); 
	},
	
	update: function(){
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
		}

		/* ajax 호출시 default로 비동기 호출 수행*/
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		$.ajax({
			//회원가입 수행 요청
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), // 자바스크립트 object를 Json으로 바꿔 HTTP body에 담아 보냄
			contentType: "application/json; charset=utf-8", // body 데이터(보낼 데이터)의 타입(MIME) 명시
			
			// 요청에 대한 응답은 기본적으로 문자열이다. 
			// 그러나 dataType을 json으로 명시하면, 받은 데이터가 Json일 경우 자바스크립트 객체로 변환해주는 역할을 함
			// 사실 서버가 json을 리턴해주면, 자동으로 이것을 자바 오브젝트로 변환해주므로 아래 dataType은 생략해도 된다. 
			dataType: "json", 
		}).done(function(resp){ // 성공시
			alert("회원정보 수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error){ // 실패시
			alert(JSON.stringify(error))
		}); 
	},
	
	/* login 함수 : 전통적인 로그인 방법을 이용하기 위해 세팅한 함수.
		login: function(){
			// alert("user의 save 함수 호출!");
			let data = {
				username: $("#username").val(),
				password: $("#password").val(),
			}
	
			/* ajax 호출시 default로 비동기 호출 수행
			// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
			
			$.ajax({
				//회원가입 수행 요청
				type: "POST",
				url: "api/user/login",
				data: JSON.stringify(data), // 자바스크립트 object를 Json으로 바꿔 HTTP body에 담아 보냄
				contentType: "application/json; charset=utf-8", // body 데이터(보낼 데이터)의 타입(MIME) 명시
				
				// 요청에 대한 응답은 기본적으로 문자열이다. 
				// 그러나 dataType을 json으로 명시하면, 받은 데이터가 Json일 경우 자바스크립트 객체로 변환해주는 역할을 함
				// 사실 서버가 json을 리턴해주면, 자동으로 이것을 자바 오브젝트로 변환해주므로 아래 dataType은 생략해도 된다. 
				dataType: "json", 
			}).done(function(resp){ // 성공시
				alert("로그인이 완료되었습니다.");
				location.href = "/";
			}).fail(function(error){ // 실패시
				alert(JSON.stringify(error))
			}); 
			
		}
	*/
}

index.init();