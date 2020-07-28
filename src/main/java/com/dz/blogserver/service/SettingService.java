package com.dz.blogserver.service;

import com.dz.blogserver.ApplicationConstants;
import com.dz.blogserver.config.MyFunction;
import com.dz.blogserver.dao.setting.SettingAboutRepository;
import com.dz.blogserver.dao.setting.SettingBlogLabelRepository;
import com.dz.blogserver.dao.setting.SettingBlogTypeRepository;
import com.dz.blogserver.entity.setting.SettingAbout;
import com.dz.blogserver.entity.setting.SettingBlogLabel;
import com.dz.blogserver.entity.setting.SettingBlogType;
import com.dz.blogserver.util.CommonUtils;
import com.dz.blogserver.util.IDozerImpl;
import com.dz.blogserver.vo.result.PageEntity;
import com.dz.blogserver.vo.result.ResultEntity;
import com.dz.blogserver.vo.setting.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SettingService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private IDozerImpl iDozer;
    @Autowired
    private SettingBlogLabelRepository settingBlogLabelRepository;
    @Autowired
    private SettingBlogTypeRepository settingBlogTypeRepository;
    @Autowired
    private SettingAboutRepository settingAboutRepository;

    public ResultEntity findSettingBlogLabelList(QuerySettingBlogType querySettingBlogType) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<SettingBlogLabel> settingBlogLabelList = settingBlogLabelRepository
                    .findByEditUser(querySettingBlogType.getUserId());
            List<SettingBlogLabelVo> dataList = iDozer.convert(settingBlogLabelList, SettingBlogLabelVo.class);

            resultEntity.setResult(dataList);
            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke saveSettingBlogLabel method error " + e.getMessage());
            resultEntity.error();
            return resultEntity;
        }

        return resultEntity;
    }

    public ResultEntity saveSettingBlogLabel(SettingBlogLabelEdit settingBlogLabelEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            Integer count = settingBlogLabelRepository.countByEditUserAndLabelAndDataFlag(userId, settingBlogLabelEdit
                    .getLabel(), ApplicationConstants.Able.VALID.getIndex());

            if (count != null && count > 0) {
                resultEntity.failed("保存标签失败,当前用户已有该标签.");
                return resultEntity;
            }

            SettingBlogLabel settingBlogLabel = new SettingBlogLabel();
            BeanUtils.copyProperties(settingBlogLabelEdit, settingBlogLabel);
            settingBlogLabel.setId(UUID.randomUUID().toString());
            settingBlogLabel.setAble(ApplicationConstants.Able.VALID.getIndex());
            settingBlogLabel.setCreateDate(new Date(System.currentTimeMillis()));
            settingBlogLabel.setUpdateDate(new Date(System.currentTimeMillis()));
            settingBlogLabel.setEditUser(userId);

            settingBlogLabelRepository.save(settingBlogLabel);

            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke saveSettingBlogLabel method error " + e.getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }
        return resultEntity;
    }

    public ResultEntity editSettingBlogLabel(SettingBlogLabelEdit settingBlogLabelEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            SettingBlogLabel settingBlogLabel = settingBlogLabelRepository.findByIdAndDataFlag(settingBlogLabelEdit
                    .getId(), ApplicationConstants.Able.VALID.getIndex());

            if (settingBlogLabel == null) {
                resultEntity.failed("修改标签失败,标签不存在");
                return resultEntity;
            }

            if (!settingBlogLabel.getEditUser().equals(userId)) {
                resultEntity.failed("修改标签失败,只允许本人修改");
                return resultEntity;
            }

            if (!settingBlogLabel.getLabel().equals(settingBlogLabelEdit.getLabel())) {
                Integer count =  settingBlogLabelRepository.countByEditUserAndLabelAndDataFlag(userId,
                        settingBlogLabelEdit.getLabel(), ApplicationConstants.Able.VALID.getIndex());

                if (count != null && count > 0) {
                    resultEntity.failed("修改标签失败,当前用户已有该标签");
                    return resultEntity;
                }
            }

            BeanUtils.copyProperties(settingBlogLabelEdit, settingBlogLabel);
            settingBlogLabel.setUpdateDate(new Date(System.currentTimeMillis()));
            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke editSettingBlogLabel method error " + e.getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }
        return resultEntity;
    }

    public ResultEntity deleteSettingBlogLabel(String id, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            SettingBlogLabel settingBlogLabel = settingBlogLabelRepository.findByIdAndDataFlag(id, ApplicationConstants
                    .Able.VALID.getIndex());

            if (settingBlogLabel == null) {
                resultEntity.failed("删除标签失败,标签不存在");
                return resultEntity;
            }

            if (!settingBlogLabel.getEditUser().equals(userId)) {
                resultEntity.failed("删除标签失败,只允许本人删除");
                return resultEntity;
            }
            settingBlogLabel.setUpdateDate(new Date(System.currentTimeMillis()));
            settingBlogLabel.setDataFlag(ApplicationConstants.Able.UNVALID.getIndex());

            settingBlogLabelRepository.save(settingBlogLabel);

            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke deleteSettingBlogLabel method error " + e.getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }
        return resultEntity;
    }

    public ResultEntity saveSettingBlogType(SettingBlogTypeEdit settingBlogTypeEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            Integer count = settingBlogTypeRepository.countByEditUserAndTypeAndDataFlag(userId, settingBlogTypeEdit
                    .getType(), ApplicationConstants.Able.VALID.getIndex());

            if (count != null && count > 0) {
                resultEntity.failed("保存博客分类失败,分类已存在");
                return resultEntity;
            }

            SettingBlogType settingBlogType = new SettingBlogType();
            BeanUtils.copyProperties(settingBlogTypeEdit, settingBlogType);
            settingBlogType.setId(UUID.randomUUID().toString());
            settingBlogType.setEditUser(userId);
            settingBlogType.setCreateDate(new Date(System.currentTimeMillis()));
            settingBlogType.setUpdateDate(new Date(System.currentTimeMillis()));
            settingBlogType.setDataFlag(ApplicationConstants.Able.VALID.getIndex());

            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke " + Thread.currentThread().getStackTrace()[1].getClassName() + " method error " + e
                    .getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }

        return resultEntity;
    }

    public ResultEntity editSettingBlogType(SettingBlogTypeEdit settingBlogTypeEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            SettingBlogType settingBlogType = settingBlogTypeRepository.findByIdAndDataFlag(settingBlogTypeEdit.getId(),
                    ApplicationConstants.Able.VALID.getIndex());

            if (settingBlogType == null) {
                resultEntity.failed("修改博客分类失败,分类未找到");
                return resultEntity;
            }

            if (!settingBlogType.getEditUser().equals(userId)) {
                resultEntity.failed("修改失败,只允许本人修改");
                return resultEntity;
            }

            if (!settingBlogType.getType().equals(settingBlogTypeEdit.getType())) {
                Integer count = settingBlogTypeRepository.countByEditUserAndTypeAndDataFlag(userId, settingBlogTypeEdit
                        .getType(), ApplicationConstants.Able.VALID.getIndex());

                if (count != null && count > 0) {
                    resultEntity.failed("修改博客分类失败,分类已存在");
                    return resultEntity;
                }
            }

            BeanUtils.copyProperties(settingBlogTypeEdit, settingBlogType);
            settingBlogType.setEditUser(userId);
            settingBlogType.setUpdateDate(new Date(System.currentTimeMillis()));
            settingBlogType.setDataFlag(ApplicationConstants.Able.VALID.getIndex());

            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke " + Thread.currentThread().getStackTrace()[1].getClassName() + " method error " + e
                    .getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }

        return resultEntity;
    }

    public ResultEntity deleteSettingBlogType(String id, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            SettingBlogType settingBlogType = settingBlogTypeRepository.findByIdAndDataFlag(id, ApplicationConstants
                    .Able.VALID.getIndex());

            if (settingBlogType == null) {
                resultEntity.failed("删除分类失败,分类不存在");
                return resultEntity;
            }

            if (!settingBlogType.getEditUser().equals(userId)) {
                resultEntity.failed("删除分类失败,只允许本人删除");
                return resultEntity;
            }
            settingBlogType.setUpdateDate(new Date(System.currentTimeMillis()));
            settingBlogType.setDataFlag(ApplicationConstants.Able.UNVALID.getIndex());

            settingBlogTypeRepository.save(settingBlogType);

            resultEntity.success();
        } catch (Exception e) {
            logger.error("invoke " + Thread.currentThread().getStackTrace()[1].getClassName() + " method error " + e
                    .getMessage());
            e.printStackTrace();
            resultEntity.error();
            return resultEntity;
        }
        return resultEntity;
    }

    /**
     * 查询所属用户的文章分类
     * @param query
     * @return
     */
    public ResultEntity findSettingTypePage(SettingBlogTypeQuery query) {
        ResultEntity resultEntity = new ResultEntity();
        Pageable pageable = PageRequest.of((int) query.getCurrent(), (int) query.getSize());
        Page<SettingBlogType> dataPage = settingBlogTypeRepository
                .findByEditUserAndDataFlag(query.getEditUser(), ApplicationConstants.Able.VALID.getIndex(), pageable);
        PageEntity pageEntity = CommonUtils.convert2CommonPage(dataPage);
        resultEntity.setResult(pageEntity);

        return resultEntity;
    }

    /**
     * 查询所属用户的文章分类
     * @param editUser
     * @return
     */
    public ResultEntity findSettingType(String editUser) {
        ResultEntity resultEntity = new ResultEntity();
//        List<SettingBlogType> dataList = settingBlogTypeRepository
//                .findByEditUserAndDataFlag(editUser, ApplicationConstants.Able.VALID.getIndex());
        List<Map<String, Object>> maps = settingBlogTypeRepository.countByType(editUser);
        maps.sort((o1, o2) -> (int) ((long) o2.get("qty") - (long) o1.get("qty")));
        resultEntity.setResult(maps);

        return resultEntity;
    }

    /**
     * 查询关于我们
     * @return
     */
    public ResultEntity findAbout() {
        ResultEntity resultEntity = new ResultEntity();
        try {
            Iterable iterable = settingAboutRepository.findAll();
        } catch (Exception e) {
            logger.error("");
        }

        resultEntity.success();
        return resultEntity;
    }

    /**
     * 保存关于我们
     * @param settingAboutEdit
     * @return
     */
    public ResultEntity saveAbout(SettingAboutEdit settingAboutEdit) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            SettingAbout settingAbout = iDozer.convert(settingAboutEdit, SettingAbout.class);
            settingAboutRepository.save(settingAbout);
        } catch (Exception e) {
            logger.error("保存关于我们失败");
            resultEntity.error("未知异常，请联系系统管理员");
            return resultEntity;
        }

        resultEntity.success();
        return resultEntity;
    }

    @MyFunction(values = {"FUN_2001", "FUN_2002", "FUN_2003"})
    public String sayHello(String name, int age, String city) {

        return "大家好我叫" + name + "今年" + age + "岁" + "来自" + city;
    }

    @MyFunction(values = "FUN_3001")
    public String playGame(String name, String gameName) {
        return name + "开始" + gameName;
    }
}
