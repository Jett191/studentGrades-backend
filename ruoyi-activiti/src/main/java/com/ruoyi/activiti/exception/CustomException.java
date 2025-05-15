package com.ruoyi.activiti.exception;

import lombok.Data;
import org.activiti.bpmn.model.BpmnModel;


@Data
public class CustomException extends Throwable {

    String errMsg;
    BpmnModel model;

    public CustomException(String errMsg) {
        this.errMsg = errMsg;
    }
}
