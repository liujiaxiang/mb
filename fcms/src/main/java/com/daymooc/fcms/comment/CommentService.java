package com.daymooc.fcms.comment;

import java.util.Date;
import java.util.List;

import com.daymooc.fcms.common.account.AccountService;
import com.daymooc.fcms.common.model.PostsComment;
import com.daymooc.fcms.like.LikeMessageLogService;
import com.daymooc.fcms.newsfeed.NewsFeedService;
import com.daymooc.fcms.newsfeed.ReferMeKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

public class CommentService
{
	public static final CommentService me = new CommentService();
	final PostsComment commentDao = new PostsComment().dao();
	int pageSize  = 20;
	final String REF_TYPE_POST = "posts";//posts表，目前只有这个

	/**
	 * 保存评论及回复
	 */
	public Ret saveReply(Integer postId, Integer myId, String content) {
		PostsComment reply = new PostsComment();
		reply.setPostId(postId);
		reply.setUserId(myId);
		reply.setContent(content);
		reply.setCreateAt(new Date());

		reply.save();

		//评论数加1
		Db.update("update posts set comments=comments+1 where id=?",postId);

		//增加动态信息
		//@提到我
		List<Integer> referAccounts = ReferMeKit.buildAtMeLink(reply);
		NewsFeedService.me.createCommentsNewsFeed(myId, reply, referAccounts);
		// 向被回复的人发送私信，鼓励创造更多资源
		final Integer userId = getUserIdOfRef(REF_TYPE_POST, postId);
        LikeMessageLogService.me.sendSystemMessage(myId, userId, REF_TYPE_POST, postId);

		return Ret.ok("reply", reply);
	}
	
    // 获取被评论资源的创建者
    private Integer getUserIdOfRef(String refType, int refId) {
    	String userId = "userId";
    	if (refType.equals("video"))
		{
			userId = "user_id";
		}
 
        return Db.queryInt("select "+userId+" from " + refType + " where id=? limit 1", refId);
    }
	
	/**
	 * 获取评论
	 * select sr.*, a.nickName, a.avatar from share_reply sr inner join user a on sr.accountId = a.id where shareId = 13;
	 */
	public Page<PostsComment> getReplyPage(int postId, int pageNumber) {
		String select = "select cnt.*, u.nickName, u.avatar";
		String from = "from posts_comment cnt inner join user u on cnt.userId = u.id "
				+ "where postId = ? order by cnt.createAt desc";
		Page<PostsComment> replyPage = commentDao.paginate(pageNumber, pageSize, select, from, postId);
		return replyPage;
	}
	
	/**
	 * 获取某用户的评论及评论post详情
	 */
	public Page<PostsComment> getCommentAndPost(int pageNum,int userId)
	{
		String select = "select pc.*, p.title, p.summary, p.userId as author, p.createAt as pTime, p.post_type";
		String from = "from posts_comment pc join posts p on pc.postId=p.id and pc.userId=?";
		
		Page<PostsComment> commentPage = commentDao.paginate(pageNum, pageSize, select, from, userId);
		
		AccountService.me.join("author", commentPage.getList(), "nickName", "avatar");
		
		System.out.println(commentPage.getList().toString());
		return commentPage;
	}
	
	/**
	 * 获取评论数
	 */
	public int getComments(int postId)
	{
		Number comments = Db.queryNumber("select count(*) from posts_comment where postId=?", postId);
		
		return comments.intValue();
	}
}
