package com.maizhiyu.yzt.utils;

import com.maizhiyu.yzt.entity.PsArticleComment;

import java.util.ArrayList;
import java.util.List;

public class CommentHelper {

    //构建树形结构
    public static List<PsArticleComment> buildTree(List<PsArticleComment> commentList){
        List<PsArticleComment> trees = new ArrayList<>();
        //便利所有评论
        for (PsArticleComment psArticleComment:commentList){
            if(psArticleComment.getReplyId()==0){
                trees.add(findChildrens(psArticleComment,commentList));
            }
        }
        return  trees;
    }

    //从根节点进行递归查询，查询子节点
    //判断reply_id == id
    public static PsArticleComment findChildrens(PsArticleComment psArticleComment , List<PsArticleComment> commentList){

        return null;
    }
}
