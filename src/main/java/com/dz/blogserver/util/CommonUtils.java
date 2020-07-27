package com.dz.blogserver.util;

import com.dz.blogserver.vo.result.PageEntity;
import com.dz.blogserver.vo.result.ResultEntity;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author dz
 * @date 2020/7/6
 * @time 10:42
 */
public class CommonUtils {
    public static ResultEntity invalidData2ResultEntity(BindingResult bindingResult) {
        ResultEntity resultEntity = new ResultEntity();

        List<ObjectError> errorList = bindingResult.getAllErrors();
        StringBuilder stringBuilder = new StringBuilder();
        for (ObjectError objectError : errorList) {
            stringBuilder.append(objectError.getDefaultMessage()).append(" ");
        }

        resultEntity.failed(stringBuilder.toString());
        return resultEntity;
    }

    public static ResultEntity invalidData2ResultEntity(ResultEntity resultEntity, BindingResult bindingResult) {
        if (resultEntity == null) {
            return invalidData2ResultEntity(bindingResult);
        } else {
            resultEntity.failed();
            List<ObjectError> errorList = bindingResult.getAllErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (ObjectError objectError : errorList) {
                stringBuilder.append(objectError.getDefaultMessage()).append(" ");
            }
            resultEntity.setMessage(stringBuilder.toString());
        }

        return resultEntity;
    }

    public static PageEntity convert2CommonPage(Page page) {
        PageEntity pageEntity = new PageEntity();
        pageEntity.setCurrent(page.getNumber());
        pageEntity.setSize(page.getSize());
        pageEntity.setTotalElements(page.getTotalElements());
        pageEntity.setTotalPages(page.getTotalPages());

        return pageEntity;
    }

    public static boolean isEmpty(String var) {
        return var == null || var.length() == 0 || var.trim().length() == 0;
    }
}
