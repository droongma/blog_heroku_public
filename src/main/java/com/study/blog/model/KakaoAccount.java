package com.study.blog.model;

import javax.annotation.Generated;

import lombok.Data;

@Data
@Generated("jsonschema2pojo")
public class KakaoAccount {
	public Boolean profile_nickname_needs_agreement;
	public Boolean profile_image_needs_agreement;
	public Profile profile;
	public Boolean has_email;
	public Boolean email_needs_agreement;
	public Boolean is_email_valid;
	public Boolean is_email_verified;
	public String email;
}