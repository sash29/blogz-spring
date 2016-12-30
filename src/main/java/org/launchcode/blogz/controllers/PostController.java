package org.launchcode.blogz.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.launchcode.blogz.models.Post;
import org.launchcode.blogz.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController extends AbstractController {

	@RequestMapping(value = "/blog/newpost", method = RequestMethod.GET)
	public String newPostForm() {
		return "newpost";
	}
	
	@RequestMapping(value = "/blog/newpost", method = RequestMethod.POST)
	public String newPost(HttpServletRequest request, Model model) {
		
		// TODO - implement newPost
		//get request parameters
		String t = request.getParameter("title") ;
		String b = request.getParameter("body") ;
		HttpSession thisSession = request.getSession();
		User author = getUserFromSession( thisSession);
		String retStr = "";
		System.out.println("newpost" + t);
		System.out.println("newpost" + b);
		if(t.length() ==0 || b.length() ==0)//validate parameters.//if not valid, send back to the form, with error message 
		{
			model.addAttribute("newpost_error","Either title or post is empty. Pls enter valid content.");
			//return "redirect:" + author.getUsername();
			retStr =  "newpost";
		}
		else {
			Post newPost = new Post(t,b,author);//If valid, create new Post
			postDao.save(newPost);// save in database
			System.out.println("newpost added");
			retStr = "redirect:" + author.getUsername() + "/" + newPost.getUid();// TODO - this redirect should go to the new post's page  		
		}
		return retStr;
		
	}
	
	@RequestMapping(value = "/blog/{username}/{uid}", method = RequestMethod.GET)
	public String singlePost(@PathVariable String username, @PathVariable int uid, Model model) {
		
		// TODO - implement singlePost
		//get the given post
		
		User theuser = userDao.findByUsername(username);
	
		List<Post> allPosts = theuser.getPosts();
		
//		List<Post> allPosts = postDao.findByAuthor(theuser.getUid());
		
		Post thepost = allPosts.get(uid);
//		Post thepost =  postDao.findByAuthor(theuser.getUid()).get(uid);
		//pass the  post into the template
		model.addAttribute("post",thepost);
		return "post";
		
	}
	
	@RequestMapping(value = "/blog/{username}", method = RequestMethod.GET)
	public String userPosts(@PathVariable String username, Model model) {
		
		// TODO - implement userPosts
		//get all of the user's posts
		User correspondingUsr = userDao.findByUsername(username);
		List<Post> allPosts= correspondingUsr.getPosts();
		//pass the posts into the template
		model.addAttribute("posts",allPosts);
		
		return "blog";
	}
	
}
