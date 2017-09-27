package com.daymooc.fcms.index;

import java.util.List;

import com.daymooc.fcms.blog.BlogService;
import com.daymooc.fcms.common.account.AccountService;
import com.daymooc.fcms.common.controller.BaseController;
import com.daymooc.fcms.common.model.Posts;
import com.daymooc.fcms.common.model.Tags;
import com.daymooc.fcms.common.model.User;
import com.daymooc.fcms.tags.TagsService;
import com.jfinal.plugin.activerecord.Page;

public class IndexController extends BaseController
{
	private static final BlogService blogSrv = BlogService.me;
	private static final AccountService accountSrv = AccountService.me;
	private static final TagsService tagSrv = TagsService.me;
	public void index()
	{
		Page<Posts> postPage= blogSrv.getArticles(getParaToInt("p", 1));
		List<User> hotUsers = accountSrv.getHotUsers();
		List<Tags> hotTags = tagSrv.getHotTags(20);
		List<Posts> hotPosts = blogSrv.getHotPost(8);
		List<Posts> newPosts = blogSrv.getNewestPost(8);
		setAttr("postPage", postPage);
		setAttr("hotUsers", hotUsers);
		setAttr("hotTags", hotTags);
		setAttr("hotPosts", hotPosts);
		setAttr("newPosts", newPosts);
		render("index.html");
	}
	
	public void hot()
	{
		Page<Posts> postPage= blogSrv.getHotArticles(getParaToInt("p", 1));
		List<User> hotUsers = accountSrv.getHotUsers();
		List<Tags> hotTags = tagSrv.getHotTags(20);
		List<Posts> hotPosts = blogSrv.getHotPost(8);
		List<Posts> newPosts = blogSrv.getNewestPost(8);
		setAttr("postPage", postPage);
		setAttr("hotUsers", hotUsers);
		setAttr("hotTags", hotTags);
		setAttr("hotPosts", hotPosts);
		setAttr("newPosts", newPosts);
		render("hot.html");
	}
	
}
