package com.tutorial.basic.dao;

import java.util.List;

import com.tutorial.basic.model.Post;

public interface PostDAO2 {
	
	public List<Post> getPosts();
	public Post getPost(Long id);
	public Post savePost(Post post);
	public Post editPost(Post post);
	public void deletePost(Post post);

}
