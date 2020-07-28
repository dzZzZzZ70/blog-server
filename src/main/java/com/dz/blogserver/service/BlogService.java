package com.dz.blogserver.service;

import com.dz.blogserver.ApplicationConstants;
import com.dz.blogserver.dao.business.BusinessBlogRepository;
import com.dz.blogserver.dao.management.ApplicationUserRepository;
import com.dz.blogserver.entity.business.BusinessBlog;
import com.dz.blogserver.entity.management.ApplicationUser;
import com.dz.blogserver.util.CommonUtils;
import com.dz.blogserver.vo.business.BlogQuery;
import com.dz.blogserver.vo.business.BusinessBlogEdit;
import com.dz.blogserver.vo.result.PageEntity;
import com.dz.blogserver.vo.result.ResultEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.*;

@Service
public class BlogService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired private ApplicationUserRepository applicationUserRepository;
    @Autowired private BusinessBlogRepository businessBlogRepository;

    /**
     * 查找用户下的博客
     *
     * @return
     */
    public ResultEntity findBlogByUser(String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
//            List<BusinessBlog> businessBlogList =
//                businessBlogRepository.findByCreateUserAndDataFlag(
//                    userId, ApplicationConstants.Able.VALID.getIndex());
            List<BusinessBlog> dataMaps = businessBlogRepository.countByType(userId);
            resultEntity.setFlag(ApplicationConstants.ResultFlag.SUCCESS.getIndex());
            resultEntity.setResult(dataMaps);
        } catch (Exception e) {
            logger.error("invoke findBlogByUser method error " + e.getMessage());
            e.printStackTrace();

            resultEntity.setFlag(ApplicationConstants.ResultFlag.ERROR.getIndex());
            resultEntity.setMessage("查找用户博客失败,请联系系统管理员.");
            return resultEntity;
        }
        return resultEntity;
    }

    /**
     * 查找分类下的博客
     *
     * @return
     */
    public ResultEntity findBlogByType(BlogQuery query) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            PageRequest page = PageRequest.of((int) query.getCurrent(), (int) query.getSize());
            Page<BusinessBlog> pageResult =
                businessBlogRepository.findByBlogTypeIdAndDataFlag(
                    query.getBlogTypeId(), ApplicationConstants.Able.VALID.getIndex(), page);
            PageEntity pageEntity = CommonUtils.convert2CommonPage(pageResult);
            pageEntity.setRecords(pageResult.getContent());
            resultEntity.setPageEntity(pageEntity);
        } catch (Exception e) {
            logger.error("invoke findBlogByUser method error " + e.getMessage());
            e.printStackTrace();
            resultEntity.error("查找博客失败,请联系系统管理员.");
        }
        return resultEntity;
    }

    public ResultEntity saveBusinessBlog(BusinessBlogEdit businessBlogEdit) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            ApplicationUser applicationUser =
                applicationUserRepository.findByUserAccount(businessBlogEdit.getUserAccount());
            Integer count =
                businessBlogRepository.countByCreateUserAndDataFlagAndTitleAndBlogType(
                    applicationUser.getId(),
                    ApplicationConstants.Able.VALID.getIndex(),
                    businessBlogEdit.getTitle(),
                    businessBlogEdit.getBlogType());
            if (count != null && count > 0) {
                resultEntity.failed("保存博客失败,博客标题已在当前博客分类中使用");
                return resultEntity;
            }
            BusinessBlog businessBlog = new BusinessBlog();
            try{
                BeanUtils.copyProperties(businessBlogEdit, businessBlog);
            } catch (Exception e) {
                businessBlog.setShortContent("");
            }

            // 截取简要 保留第一个 <p></p>中的内容作为博客简要
            businessBlog.setShortContent(cutString(businessBlog.getContent()));
            businessBlog.setId(UUID.randomUUID().toString());
            businessBlog.setCreateDate(new Date(System.currentTimeMillis()));
            businessBlog.setUpdateDate(new Date(System.currentTimeMillis()));
            businessBlog.setEditUser(applicationUser.getId());
            businessBlog.setCreateUser(applicationUser.getId());
            businessBlog.setCreateDate(new Date((System.currentTimeMillis())));
            businessBlog.setDataFlag(ApplicationConstants.Able.VALID.getIndex());
            businessBlogRepository.save(businessBlog);

            resultEntity.setFlag(ApplicationConstants.ResultFlag.SUCCESS.getIndex());
        } catch (Exception e) {
            logger.error("invoke saveBusinessBlog method error " + e.getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }

        return resultEntity;
    }

    public ResultEntity editBusinessBlog(BusinessBlogEdit businessBlogEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            BusinessBlog businessBlog =
                businessBlogRepository.findByIdAndDataFlag(
                    businessBlogEdit.getId(), ApplicationConstants.Able.VALID.getIndex());
            if (businessBlog == null) {
                resultEntity.setFlag(ApplicationConstants.ResultFlag.FAILED.getIndex());
                resultEntity.setMessage("修改博客失败,博客未找到.");
                return resultEntity;
            }

            if (!businessBlog.getCreateUser().equals(userId)) {
                resultEntity.setFlag(ApplicationConstants.ResultFlag.FAILED.getIndex());
                resultEntity.setMessage("修改博客失败,只允许作者修改.");
                return resultEntity;
            }

            if (!businessBlog.getTitle().equals(businessBlogEdit.getTitle())) {
                Integer count =
                    businessBlogRepository.countByCreateUserAndDataFlagAndTitleAndBlogType(
                        userId,
                        ApplicationConstants.Able.VALID.getIndex(),
                        businessBlogEdit.getTitle(),
                        businessBlogEdit.getBlogType());

                if (count != null && count > 0) {
                    resultEntity.setFlag(ApplicationConstants.ResultFlag.FAILED.getIndex());
                    resultEntity.setMessage("修改博客失败,博客标题已在当前博客分类中使用");
                    return resultEntity;
                }
            }
            BeanUtils.copyProperties(businessBlogEdit, businessBlog);
            businessBlog.setEditUser(userId);
            businessBlog.setUpdateDate(new Date(System.currentTimeMillis()));
            businessBlogRepository.save(businessBlog);

            resultEntity.setFlag(ApplicationConstants.ResultFlag.SUCCESS.getIndex());
        } catch (Exception e) {
            logger.error("invoke editBusinessBlog error " + e.getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }

        return resultEntity;
    }

    public ResultEntity deleteBusinessBlogById(String id, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            BusinessBlog businessBlog =
                businessBlogRepository.findByIdAndDataFlag(
                    id, ApplicationConstants.Able.VALID.getIndex());
            if (businessBlog == null) {
                resultEntity.failed("删除失败, 博客不存在.");
                return resultEntity;
            }

            if (businessBlog.getCreateUser() != null && businessBlog.getCreateUser().equals(userId)) {
                resultEntity.failed("删除失败,只允许作者删除该博客.");
                return resultEntity;
            }

            businessBlog.setEditUser(userId);
            businessBlog.setUpdateDate(new Date(System.currentTimeMillis()));
            businessBlog.setDataFlag(ApplicationConstants.Able.UNVALID.getIndex());

            businessBlogRepository.save(businessBlog);
            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke deleteBusinessBlogById method error " + e.getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }

        return resultEntity;
    }

    public static void main(String[] args) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println("{" + threadInfo.getThreadId() + "}" + threadInfo.getThreadName());
        }
    }

    /**
     * 用于截取博客简介
     * @param origin
     * @return
     */
    private String cutString(String origin) throws StringIndexOutOfBoundsException {
        if (CommonUtils.isEmpty(origin)) {
            return "";
        }

        return origin.substring(0, origin.indexOf("</p>"));
    }

    /**
     * 根据ID查询博客
     * @param id
     * @return
     */
    public ResultEntity findBlog(String id) {
        ResultEntity resultEntity = new ResultEntity();
        BusinessBlog blog = businessBlogRepository.findById(id).orElse(null);
        if (blog == null) {
            resultEntity.failed("当前博客不存在");
            return resultEntity;
        }
        resultEntity.setResult(blog);
        resultEntity.success();
        return resultEntity;
    }
}
