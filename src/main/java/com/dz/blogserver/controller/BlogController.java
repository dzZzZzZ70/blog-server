package com.dz.blogserver.controller;

import com.dz.blogserver.service.BlogService;
import com.dz.blogserver.util.CommonUtils;
import com.dz.blogserver.vo.business.BlogQuery;
import com.dz.blogserver.vo.business.BusinessBlogEdit;
import com.dz.blogserver.vo.result.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("controller/blog")
public class BlogController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/findBlogByUser", method = RequestMethod.POST)
    public ResultEntity findBlogByUser(String userId) {
        ResultEntity resultEntity = new ResultEntity();
        if (userId == null || "".equals(userId)) {
            resultEntity.failed("用户不能为空");
            return resultEntity;
        }

        resultEntity = blogService.findBlogByUser(userId);
        return resultEntity;
    }

    @RequestMapping(value = "/findBlogByBlogType", method = RequestMethod.POST)
    public ResultEntity findBlogByUser(@RequestBody BlogQuery query) {
        ResultEntity resultEntity = new ResultEntity();
        if (query.getBlogTypeId() == null || "".equals(query.getBlogTypeId())) {
            resultEntity.failed("分类不能为空");
            return resultEntity;
        }

        resultEntity = blogService.findBlogByType(query);
        return resultEntity;
    }

    @RequestMapping(value = "/saveBusinessBlog", method = RequestMethod.POST)
    public ResultEntity saveBusinessBlog(@RequestBody
                                                     BusinessBlogEdit businessBlogEdit) {
        ResultEntity resultEntity = new ResultEntity();
        if (businessBlogEdit.getTitle() == null || "".equals(businessBlogEdit.getTitle())) {
            resultEntity.failed("标题不能为空");
            return resultEntity;
        }

        if (businessBlogEdit.getContent() == null || "".equals(businessBlogEdit.getContent())) {
            resultEntity.failed("内容不能为空");
            return resultEntity;
        }

        resultEntity = blogService.saveBusinessBlog(businessBlogEdit);
        return resultEntity;
    }

    @RequestMapping(value = "/editBusinessBlog", method = RequestMethod.POST)
    public ResultEntity editBusinessBlog(BusinessBlogEdit businessBlogEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();

        if (businessBlogEdit.getTitle() == null || "".equals(businessBlogEdit.getTitle())) {
            resultEntity.failed("标题不能为空");
            return resultEntity;
        }

        if (businessBlogEdit.getContent() == null || "".equals(businessBlogEdit.getContent())) {
            resultEntity.failed("内容不能为空");
            return resultEntity;
        }

        resultEntity = blogService.editBusinessBlog(businessBlogEdit, userId);
        return resultEntity;
    }

    @RequestMapping(value = "/deleteBusinessBlogById", method = RequestMethod.POST)
    public ResultEntity deleteBusinessBlogById(String id, String userId) {
        ResultEntity resultEntity = new ResultEntity();

        if (id == null || "".equals(id)) {
            resultEntity.failed("请选择要删除的博客");
            return resultEntity;
        }

        resultEntity = blogService.deleteBusinessBlogById(id, userId);
        return resultEntity;
    }

    /**
     * 根据博客ID查询博客内容
     * @param query
     * @return
     */
    @PostMapping("/findBlog")
    public ResultEntity findBlog(@RequestBody BlogQuery query) {
        ResultEntity resultEntity = new ResultEntity();
        if (CommonUtils.isEmpty(query.getId())) {
            resultEntity.failed("博客ID不能为空");
            return resultEntity;
        }

        return blogService.findBlog(query.getId());
    }
}
