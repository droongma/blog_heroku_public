package com.study.blog.model;

import javax.annotation.Generated;
import lombok.Data;

@Data
@Generated("jsonschema2pojo")
public class Profile {
	public String nickname;
	public String thumbnail_image_url;
	public String profile_image_url;
	public Boolean is_default_image;
}
