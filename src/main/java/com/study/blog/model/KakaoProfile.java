package com.study.blog.model;

import javax.annotation.Generated;

import lombok.Data;

@Data
@Generated("jsonschema2pojo")
public class KakaoProfile {
	public Long id;
	public String connected_at;
	public Properties properties;
	public KakaoAccount kakao_account;
}