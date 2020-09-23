package com.dz.blogserver.controller;

import com.dz.blogserver.config.MyFunction;
import com.dz.blogserver.service.SettingService;
import com.dz.blogserver.util.CommonUtils;
import com.dz.blogserver.vo.Param;
import com.dz.blogserver.vo.UserAccountQuery;
import com.dz.blogserver.vo.result.ResultEntity;
import com.dz.blogserver.vo.setting.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@RestController
@RequestMapping("controller/setting")
public class SettingController {
    private static final Logger logger = LoggerFactory.getLogger(SettingController.class);
    @Autowired
    private SettingService settingService;

    @RequestMapping(value = "findSettingBlogLabelList", method = RequestMethod.POST)
    public ResultEntity findSettingBlogLabelList(@RequestBody QuerySettingBlogType querySettingBlogType) {
        ResultEntity resultEntity = new ResultEntity();
        if (querySettingBlogType.getUserId() == null || "".equals(querySettingBlogType.getUserId())) {
            resultEntity.failed("用户ID不能为空");

            return resultEntity;
        }

        resultEntity = settingService.findSettingBlogLabelList(querySettingBlogType);

        return resultEntity;
    }

    @RequestMapping(value = "/saveSettingBlogLabel", method = RequestMethod.POST)
    public ResultEntity saveSettingBlogLabel(SettingBlogLabelEdit settingBlogLabelEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();
        if (settingBlogLabelEdit.getLabel() == null || "".equals(settingBlogLabelEdit.getLabel())) {
            resultEntity.failed("标签不能为空");
            return resultEntity;
        }

        if (settingBlogLabelEdit.getAble() == null || "".equals(settingBlogLabelEdit.getAble())) {
            resultEntity.failed("有效标志不能为空");
            return resultEntity;
        }

        resultEntity = settingService.saveSettingBlogLabel(settingBlogLabelEdit, userId);
        return resultEntity;
    }

    @RequestMapping(value = "/editSettingBlogLabel", method = RequestMethod.POST)
    public ResultEntity editSettingBlogLabel(SettingBlogLabelEdit settingBlogLabelEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();

        if (settingBlogLabelEdit.getLabel() == null || "".equals(settingBlogLabelEdit.getLabel())) {
            resultEntity.failed("标签不能为空");
            return resultEntity;
        }

        if (settingBlogLabelEdit.getAble() == null || "".equals(settingBlogLabelEdit.getAble())) {
            resultEntity.failed("有效标志不能为空");
            return resultEntity;
        }

        resultEntity = settingService.editSettingBlogLabel(settingBlogLabelEdit, userId);
        return resultEntity;
    }

    @RequestMapping(value = "/deleteSettingBlogLabel", method = RequestMethod.POST)
    public ResultEntity deleteSettingBlogLabel(String id, String userId) {
        ResultEntity resultEntity = new ResultEntity();

        if (id == null || "".equals(id)) {
            resultEntity.failed("请选择要删除的标签");
            return resultEntity;
        }

        resultEntity = settingService.deleteSettingBlogLabel(id, userId);
        return resultEntity;
    }

    @PostMapping("/findSettingBlogTypePage")
    public ResultEntity findSettingBlogTypePage(@RequestBody @Valid SettingBlogTypeQuery query, BindingResult bindingResult) {
        logger.debug("调用方法findSettingBlogTypePage，入参{}", query);
        ResultEntity resultEntity = new ResultEntity();
        try {
            if (bindingResult.hasErrors()) {
                CommonUtils.invalidData2ResultEntity(resultEntity, bindingResult);
            }
        } catch (Exception e) {
            resultEntity = settingService.findSettingTypePage(query);
        }

        return resultEntity;
    }

    @PostMapping("/findSettingBlogType")
    @RequiresPermissions("examination:queryPageOfUserAndMenu")
    public ResultEntity findSettingBlogType(@RequestBody @Valid UserAccountQuery query, BindingResult bindingResult) {
        logger.debug("调用方法findSettingBlogType，入参{}", query);
        ResultEntity resultEntity = new ResultEntity();
        try {
            if (bindingResult.hasErrors()) {
                CommonUtils.invalidData2ResultEntity(resultEntity, bindingResult);
            } else {
                resultEntity = settingService.findSettingType(query.getUserAccount());
            }
        } catch (Exception e) {
            logger.error("调用方法findSettingBlogType未知异常,入参{}", query, e);
        }

        return resultEntity;
    }

    @RequestMapping(value = "/saveSettingBlogType", method = RequestMethod.POST)
    public ResultEntity saveSettingBlogType(SettingBlogTypeEdit settingBlogTypeEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();

        if (settingBlogTypeEdit.getType() == null || "".equals(settingBlogTypeEdit.getType())) {
            resultEntity.failed("分类不能为空");
            return resultEntity;
        }


        resultEntity = settingService.saveSettingBlogType(settingBlogTypeEdit, userId);
        return resultEntity;
    }

    @RequestMapping(value = "/editSettingBlogType", method = RequestMethod.POST)
    public ResultEntity editSettingBlogType(SettingBlogTypeEdit settingBlogTypeEdit, String userId) {
        ResultEntity resultEntity = new ResultEntity();

        if (settingBlogTypeEdit.getType() == null || "".equals(settingBlogTypeEdit.getType())) {
            resultEntity.failed("分类不能为空");
            return resultEntity;
        }

        resultEntity = settingService.editSettingBlogType(settingBlogTypeEdit, userId);
        return resultEntity;
    }

    @RequestMapping(value = "/deleteSettingBlogType", method = RequestMethod.POST)
    public ResultEntity deleteSettingBlogType(String id, String userId) {
        ResultEntity resultEntity = new ResultEntity();

        if (id == null || "".equals(id)) {
            resultEntity.failed("请选择要删除的分类");
            return resultEntity;
        }

        resultEntity = settingService.deleteSettingBlogType(id, userId);
        return resultEntity;
    }

    /**
     * 查询关于我们
     * @return
     */
    @RequestMapping(value = "/findAbout", method = RequestMethod.POST)
    public ResultEntity findAbout() {
        ResultEntity resultEntity = new ResultEntity();

        resultEntity = settingService.findAbout();
        return resultEntity;
    }

    /**
     * 保存关于我们
     * @param settingAboutEdit
     * @return
     */
    @RequestMapping(value = "/saveAbout", method = RequestMethod.POST)
    public ResultEntity saveAbout(SettingAboutEdit settingAboutEdit) {
        ResultEntity resultEntity = new ResultEntity();

        resultEntity = settingService.saveAbout(settingAboutEdit);
        return resultEntity;
    }

    /**
     * test
     */
    @RequestMapping(value = "/invoke", method = RequestMethod.POST)
    public Object invoke(@RequestBody Param param) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = settingService.getClass().getMethods();
        Method invokeMethod = null;
        for (Method method : methods) {
            MyFunction fun = method.getAnnotation(MyFunction.class);
            if (fun != null && Arrays.asList(fun.values()).contains(param.getKey()) && method.getParameters().length ==
                    param.getArgs().length) {
                invokeMethod = method;
                break;
            }
        }

        if (invokeMethod != null) {
            return invokeMethod.invoke(settingService, param.getArgs());
        }

        return "啥也不是";
    }
}
