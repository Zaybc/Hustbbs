package com.Hustbbs.community.mapper;

import com.Hustbbs.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}