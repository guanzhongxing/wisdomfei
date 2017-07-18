package com.admintwo.model.pojo;

import com.admintwo.model.Comments;

public class CommentsUserLabels {
	private Comments comments;
	private UserLabels u1;
	private UserLabels u2;

	public Comments getComments() {
		return comments;
	}

	public UserLabels getU1() {
		return u1;
	}

	public UserLabels getU2() {
		return u2;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}

	public void setU1(UserLabels u1) {
		this.u1 = u1;
	}

	public void setU2(UserLabels u2) {
		this.u2 = u2;
	}

	@Override
	public String toString() {
		return "CommentsUserLabels [comments=" + comments + ", u1=" + u1 + ", u2=" + u2 + "]";
	}
}
