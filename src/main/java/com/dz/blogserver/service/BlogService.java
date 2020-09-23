package com.dz.blogserver.service;

import com.dz.blogserver.ApplicationConstants;
import com.dz.blogserver.dao.business.BusinessBlogRepository;
import com.dz.blogserver.dao.management.ApplicationUserRepository;
import com.dz.blogserver.entity.business.BusinessBlog;
import com.dz.blogserver.entity.management.ApplicationUser;
import com.dz.blogserver.entity.setting.SettingAbout;
import com.dz.blogserver.util.AppUtils;
import com.dz.blogserver.util.CommonUtils;
import com.dz.blogserver.util.IDozer;
import com.dz.blogserver.util.IDozerImpl;
import com.dz.blogserver.vo.business.BlogQuery;
import com.dz.blogserver.vo.business.BlogVO;
import com.dz.blogserver.vo.business.BusinessBlogEdit;
import com.dz.blogserver.vo.result.PageEntity;
import com.dz.blogserver.vo.result.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
//    public ResultEntity findBlogByUser(String userId) {
//        ResultEntity resultEntity = new ResultEntity();
//        try {
////            List<BusinessBlog> businessBlogList =
////                businessBlogRepository.findByCreateUserAndDataFlag(
////                    userId, ApplicationConstants.Able.VALID.getIndex());
//            List<BusinessBlog> dataMaps = businessBlogRepository.countByType(userId);
//            resultEntity.setFlag(ApplicationConstants.ResultFlag.SUCCESS.getIndex());
//            resultEntity.setResult(dataMaps);
//        } catch (Exception e) {
//            logger.error("invoke findBlogByUser method error " + e.getMessage());
//            e.printStackTrace();
//
//            resultEntity.setFlag(ApplicationConstants.ResultFlag.ERROR.getIndex());
//            resultEntity.setMessage("查找用户博客失败,请联系系统管理员.");
//            return resultEntity;
//        }
//        return resultEntity;
//    }

    public ResultEntity findBlogByUser(BlogQuery query, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        Pageable page = PageRequest.of(query.getCurrent() - 1, query.getSize());
        Page<BusinessBlog> datas = businessBlogRepository.findAll((Specification<BusinessBlog>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("createUser"), userId));
            return null;
        }, page);

        PageEntity pageEntity = CommonUtils.convert2CommonPage(datas);
        resultEntity.setResult(datas.getContent());
        resultEntity.setPageEntity(pageEntity);
        return resultEntity;
    }

    /**
     * 查询所有博客 分页
     * @param query
     * @param
     * @return
     */
    public ResultEntity findBlogPage(BlogQuery query) {
        ResultEntity resultEntity = new ResultEntity();
        Pageable page = PageRequest.of(
            query.getCurrent() - 1,
            query.getSize(),
            Sort.by(Sort.Direction.DESC, "updateDate"));
        Page<BusinessBlog> datas =
            businessBlogRepository
                .findAll(
                    (Specification<BusinessBlog>) (root, criteriaQuery, criteriaBuilder) -> {
                        List<Predicate> predicates = new ArrayList<>();
                        if (!StringUtils.isEmpty(query.getBlogTypeId())) {
                            predicates.add(criteriaBuilder.equal(root.get("blogTypeId"), query.getBlogTypeId()));
                        }

                        return null;
                    }, page);
//        IDozerImpl dozer = new IDozerImpl();
//        List<BlogVO> dataList = dozer.convert(datas.getContent(), BlogVO.class);
        List<BlogVO> dataList = new ArrayList<>();
        for(BusinessBlog blog : datas.getContent()) {
            BlogVO blogVO = new BlogVO();
            BeanUtils.copyProperties(blog, blogVO);
            dataList.add(blogVO);
        }

        PageEntity pageEntity = CommonUtils.convert2CommonPage(datas);
        pageEntity.setCurrent(pageEntity.getCurrent() + 1);
        resultEntity.setResult(dataList);
        resultEntity.setPageEntity(pageEntity);
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
            Integer count =
                businessBlogRepository.countByCreateUserAndDataFlagAndTitleAndBlogType(
                    AppUtils.getUser().getId(),
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
            businessBlog.setShortContent(this.cutString(businessBlog.getContent()));
            businessBlog.setId(UUID.randomUUID().toString());
            businessBlog.setCreateDate(new Date(System.currentTimeMillis()));
            businessBlog.setUpdateDate(new Date(System.currentTimeMillis()));
            businessBlog.setEditUser(AppUtils.getUser().getId());
            businessBlog.setCreateUser(AppUtils.getUser().getId());
            businessBlog.setCreateDate(new Date((System.currentTimeMillis())));
            businessBlog.setDataFlag(ApplicationConstants.Able.VALID.getIndex());
            businessBlog.setAuthor(AppUtils.getUser().getName());
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

    public ResultEntity editBusinessBlog(BusinessBlogEdit businessBlogEdit) {
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

            if (!businessBlog.getCreateUser().equals(AppUtils.getUser().getId())) {
                resultEntity.setFlag(ApplicationConstants.ResultFlag.FAILED.getIndex());
                resultEntity.setMessage("修改博客失败,只允许作者修改.");
                return resultEntity;
            }

            if (!businessBlog.getTitle().equals(businessBlogEdit.getTitle())) {
                Integer count =
                    businessBlogRepository.countByCreateUserAndDataFlagAndTitleAndBlogType(
                        AppUtils.getUser().getId(),
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
            businessBlog.setEditUser(AppUtils.getUser().getId());
            businessBlog.setUpdateDate(new Date(System.currentTimeMillis()));
            businessBlog.setAuthor(AppUtils.getUser().getName());
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

        if (!origin.contains("\\n")) {
            return origin;
        }

        return origin.substring(0, origin.indexOf("\\n"));
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

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(rollbackFor = Exception.class)
    public void batchSave(List<?> dataList, int flushLimit) {
//        AtomicInteger flushCount = 0;
        int commit = 1;
        Iterator interator = dataList.iterator();
        int size = dataList.size();
        int i = 1;
        Date date = new Date();
        while (interator.hasNext()) {
            entityManager.persist(interator.next());
            if (i % flushLimit == 0 || i == size) {
                entityManager.flush();
                entityManager.clear();
                System.out.println("第" + commit + "次提交");
                commit++;
            }
            i++;
        }
//        int size = dataList.size();
//        int commit = 1;
//        for (int i = 1; i <= size; i++) {
//            entityManager.persist(dataList.get(i - 1));
//            if (i % flushLimit == 0 || i == size) {
//                entityManager.flush();
//                System.out.println("第" + commit + "次提交");
//                commit++;
//            }
//        }


        System.out.println(new Date().getTime() - date.getTime());
    }

    /**
     * JDBC批量导入性能测试
     * @param dataList
     */
    @Autowired
    DataSource dataSource;
    public void jdbcBatchSave(List<SettingAbout> dataList) {
        if (dataList == null || dataList.size() == 0) {
            System.out.println("传入为空");
            return;
        }
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            String sql = "INSERT INTO setting_about(id, address, intro, phone) VALUES(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            Iterator<SettingAbout> iterator = dataList.iterator();
            int index = 1;
            int size = dataList.size();
            SettingAbout about;
            while (iterator.hasNext()) {
                about = iterator.next();
                ps.setString(1, about.getId());
                ps.setString(2, about.getAddress());
                ps.setString(3, about.getIntro());
                ps.setString(4, about.getPhone());
                ps.addBatch();
                if (index % 500 == 0 || index == size) {
                    ps.executeBatch();
                    ps.clearBatch();
                }

                index++;
            }

            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
